package com.app.web.rest;

import com.app.MedicManagerApp;
import com.app.domain.SideEffect;
import com.app.repository.SideEffectRepository;
import com.app.service.SideEffectService;
import com.app.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.app.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link SideEffectResource} REST controller.
 */
@SpringBootTest(classes = MedicManagerApp.class)
public class SideEffectResourceIT {

    private static final String DEFAULT_SIDE_EFFECT = "AAAAAAAAAA";
    private static final String UPDATED_SIDE_EFFECT = "BBBBBBBBBB";

    @Autowired
    private SideEffectRepository sideEffectRepository;

    @Autowired
    private SideEffectService sideEffectService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restSideEffectMockMvc;

    private SideEffect sideEffect;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SideEffectResource sideEffectResource = new SideEffectResource(sideEffectService);
        this.restSideEffectMockMvc = MockMvcBuilders.standaloneSetup(sideEffectResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SideEffect createEntity(EntityManager em) {
        SideEffect sideEffect = new SideEffect()
            .sideEffect(DEFAULT_SIDE_EFFECT);
        return sideEffect;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SideEffect createUpdatedEntity(EntityManager em) {
        SideEffect sideEffect = new SideEffect()
            .sideEffect(UPDATED_SIDE_EFFECT);
        return sideEffect;
    }

    @BeforeEach
    public void initTest() {
        sideEffect = createEntity(em);
    }

    @Test
    @Transactional
    public void createSideEffect() throws Exception {
        int databaseSizeBeforeCreate = sideEffectRepository.findAll().size();

        // Create the SideEffect
        restSideEffectMockMvc.perform(post("/api/side-effects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sideEffect)))
            .andExpect(status().isCreated());

        // Validate the SideEffect in the database
        List<SideEffect> sideEffectList = sideEffectRepository.findAll();
        assertThat(sideEffectList).hasSize(databaseSizeBeforeCreate + 1);
        SideEffect testSideEffect = sideEffectList.get(sideEffectList.size() - 1);
        assertThat(testSideEffect.getSideEffect()).isEqualTo(DEFAULT_SIDE_EFFECT);
    }

    @Test
    @Transactional
    public void createSideEffectWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sideEffectRepository.findAll().size();

        // Create the SideEffect with an existing ID
        sideEffect.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSideEffectMockMvc.perform(post("/api/side-effects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sideEffect)))
            .andExpect(status().isBadRequest());

        // Validate the SideEffect in the database
        List<SideEffect> sideEffectList = sideEffectRepository.findAll();
        assertThat(sideEffectList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSideEffects() throws Exception {
        // Initialize the database
        sideEffectRepository.saveAndFlush(sideEffect);

        // Get all the sideEffectList
        restSideEffectMockMvc.perform(get("/api/side-effects?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sideEffect.getId().intValue())))
            .andExpect(jsonPath("$.[*].sideEffect").value(hasItem(DEFAULT_SIDE_EFFECT)));
    }
    
    @Test
    @Transactional
    public void getSideEffect() throws Exception {
        // Initialize the database
        sideEffectRepository.saveAndFlush(sideEffect);

        // Get the sideEffect
        restSideEffectMockMvc.perform(get("/api/side-effects/{id}", sideEffect.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sideEffect.getId().intValue()))
            .andExpect(jsonPath("$.sideEffect").value(DEFAULT_SIDE_EFFECT));
    }

    @Test
    @Transactional
    public void getNonExistingSideEffect() throws Exception {
        // Get the sideEffect
        restSideEffectMockMvc.perform(get("/api/side-effects/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSideEffect() throws Exception {
        // Initialize the database
        sideEffectService.save(sideEffect);

        int databaseSizeBeforeUpdate = sideEffectRepository.findAll().size();

        // Update the sideEffect
        SideEffect updatedSideEffect = sideEffectRepository.findById(sideEffect.getId()).get();
        // Disconnect from session so that the updates on updatedSideEffect are not directly saved in db
        em.detach(updatedSideEffect);
        updatedSideEffect
            .sideEffect(UPDATED_SIDE_EFFECT);

        restSideEffectMockMvc.perform(put("/api/side-effects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSideEffect)))
            .andExpect(status().isOk());

        // Validate the SideEffect in the database
        List<SideEffect> sideEffectList = sideEffectRepository.findAll();
        assertThat(sideEffectList).hasSize(databaseSizeBeforeUpdate);
        SideEffect testSideEffect = sideEffectList.get(sideEffectList.size() - 1);
        assertThat(testSideEffect.getSideEffect()).isEqualTo(UPDATED_SIDE_EFFECT);
    }

    @Test
    @Transactional
    public void updateNonExistingSideEffect() throws Exception {
        int databaseSizeBeforeUpdate = sideEffectRepository.findAll().size();

        // Create the SideEffect

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSideEffectMockMvc.perform(put("/api/side-effects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sideEffect)))
            .andExpect(status().isBadRequest());

        // Validate the SideEffect in the database
        List<SideEffect> sideEffectList = sideEffectRepository.findAll();
        assertThat(sideEffectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSideEffect() throws Exception {
        // Initialize the database
        sideEffectService.save(sideEffect);

        int databaseSizeBeforeDelete = sideEffectRepository.findAll().size();

        // Delete the sideEffect
        restSideEffectMockMvc.perform(delete("/api/side-effects/{id}", sideEffect.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SideEffect> sideEffectList = sideEffectRepository.findAll();
        assertThat(sideEffectList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
