package com.app.web.rest;

import com.app.MedicManagerApp;
import com.app.domain.MedicineInfo;
import com.app.repository.MedicineInfoRepository;
import com.app.service.MedicineInfoService;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.app.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link MedicineInfoResource} REST controller.
 */
@SpringBootTest(classes = MedicManagerApp.class)
public class MedicineInfoResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_IMPORTANT_INFO = "AAAAAAAAAA";
    private static final String UPDATED_IMPORTANT_INFO = "BBBBBBBBBB";

    private static final String DEFAULT_USAGE = "AAAAAAAAAA";
    private static final String UPDATED_USAGE = "BBBBBBBBBB";

    private static final String DEFAULT_INITIAL_COUNT = "AAAAAAAAAA";
    private static final String UPDATED_INITIAL_COUNT = "BBBBBBBBBB";

    private static final String DEFAULT_PROMISED = "AAAAAAAAAA";
    private static final String UPDATED_PROMISED = "BBBBBBBBBB";

    private static final Instant DEFAULT_REFILL_INFO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_REFILL_INFO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_PHARMACY_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_PHARMACY_NOTES = "BBBBBBBBBB";

    @Autowired
    private MedicineInfoRepository medicineInfoRepository;

    @Autowired
    private MedicineInfoService medicineInfoService;

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

    private MockMvc restMedicineInfoMockMvc;

    private MedicineInfo medicineInfo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MedicineInfoResource medicineInfoResource = new MedicineInfoResource(medicineInfoService);
        this.restMedicineInfoMockMvc = MockMvcBuilders.standaloneSetup(medicineInfoResource)
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
    public static MedicineInfo createEntity(EntityManager em) {
        MedicineInfo medicineInfo = new MedicineInfo()
            .name(DEFAULT_NAME)
            .importantInfo(DEFAULT_IMPORTANT_INFO)
            .usage(DEFAULT_USAGE)
            .initialCount(DEFAULT_INITIAL_COUNT)
            .promised(DEFAULT_PROMISED)
            .refillInfo(DEFAULT_REFILL_INFO)
            .pharmacyNotes(DEFAULT_PHARMACY_NOTES);
        return medicineInfo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MedicineInfo createUpdatedEntity(EntityManager em) {
        MedicineInfo medicineInfo = new MedicineInfo()
            .name(UPDATED_NAME)
            .importantInfo(UPDATED_IMPORTANT_INFO)
            .usage(UPDATED_USAGE)
            .initialCount(UPDATED_INITIAL_COUNT)
            .promised(UPDATED_PROMISED)
            .refillInfo(UPDATED_REFILL_INFO)
            .pharmacyNotes(UPDATED_PHARMACY_NOTES);
        return medicineInfo;
    }

    @BeforeEach
    public void initTest() {
        medicineInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createMedicineInfo() throws Exception {
        int databaseSizeBeforeCreate = medicineInfoRepository.findAll().size();

        // Create the MedicineInfo
        restMedicineInfoMockMvc.perform(post("/api/medicine-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicineInfo)))
            .andExpect(status().isCreated());

        // Validate the MedicineInfo in the database
        List<MedicineInfo> medicineInfoList = medicineInfoRepository.findAll();
        assertThat(medicineInfoList).hasSize(databaseSizeBeforeCreate + 1);
        MedicineInfo testMedicineInfo = medicineInfoList.get(medicineInfoList.size() - 1);
        assertThat(testMedicineInfo.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMedicineInfo.getImportantInfo()).isEqualTo(DEFAULT_IMPORTANT_INFO);
        assertThat(testMedicineInfo.getUsage()).isEqualTo(DEFAULT_USAGE);
        assertThat(testMedicineInfo.getInitialCount()).isEqualTo(DEFAULT_INITIAL_COUNT);
        assertThat(testMedicineInfo.getPromised()).isEqualTo(DEFAULT_PROMISED);
        assertThat(testMedicineInfo.getRefillInfo()).isEqualTo(DEFAULT_REFILL_INFO);
        assertThat(testMedicineInfo.getPharmacyNotes()).isEqualTo(DEFAULT_PHARMACY_NOTES);
    }

    @Test
    @Transactional
    public void createMedicineInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = medicineInfoRepository.findAll().size();

        // Create the MedicineInfo with an existing ID
        medicineInfo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMedicineInfoMockMvc.perform(post("/api/medicine-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicineInfo)))
            .andExpect(status().isBadRequest());

        // Validate the MedicineInfo in the database
        List<MedicineInfo> medicineInfoList = medicineInfoRepository.findAll();
        assertThat(medicineInfoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMedicineInfos() throws Exception {
        // Initialize the database
        medicineInfoRepository.saveAndFlush(medicineInfo);

        // Get all the medicineInfoList
        restMedicineInfoMockMvc.perform(get("/api/medicine-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medicineInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].importantInfo").value(hasItem(DEFAULT_IMPORTANT_INFO)))
            .andExpect(jsonPath("$.[*].usage").value(hasItem(DEFAULT_USAGE)))
            .andExpect(jsonPath("$.[*].initialCount").value(hasItem(DEFAULT_INITIAL_COUNT)))
            .andExpect(jsonPath("$.[*].promised").value(hasItem(DEFAULT_PROMISED)))
            .andExpect(jsonPath("$.[*].refillInfo").value(hasItem(DEFAULT_REFILL_INFO.toString())))
            .andExpect(jsonPath("$.[*].pharmacyNotes").value(hasItem(DEFAULT_PHARMACY_NOTES)));
    }
    
    @Test
    @Transactional
    public void getMedicineInfo() throws Exception {
        // Initialize the database
        medicineInfoRepository.saveAndFlush(medicineInfo);

        // Get the medicineInfo
        restMedicineInfoMockMvc.perform(get("/api/medicine-infos/{id}", medicineInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(medicineInfo.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.importantInfo").value(DEFAULT_IMPORTANT_INFO))
            .andExpect(jsonPath("$.usage").value(DEFAULT_USAGE))
            .andExpect(jsonPath("$.initialCount").value(DEFAULT_INITIAL_COUNT))
            .andExpect(jsonPath("$.promised").value(DEFAULT_PROMISED))
            .andExpect(jsonPath("$.refillInfo").value(DEFAULT_REFILL_INFO.toString()))
            .andExpect(jsonPath("$.pharmacyNotes").value(DEFAULT_PHARMACY_NOTES));
    }

    @Test
    @Transactional
    public void getNonExistingMedicineInfo() throws Exception {
        // Get the medicineInfo
        restMedicineInfoMockMvc.perform(get("/api/medicine-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMedicineInfo() throws Exception {
        // Initialize the database
        medicineInfoService.save(medicineInfo);

        int databaseSizeBeforeUpdate = medicineInfoRepository.findAll().size();

        // Update the medicineInfo
        MedicineInfo updatedMedicineInfo = medicineInfoRepository.findById(medicineInfo.getId()).get();
        // Disconnect from session so that the updates on updatedMedicineInfo are not directly saved in db
        em.detach(updatedMedicineInfo);
        updatedMedicineInfo
            .name(UPDATED_NAME)
            .importantInfo(UPDATED_IMPORTANT_INFO)
            .usage(UPDATED_USAGE)
            .initialCount(UPDATED_INITIAL_COUNT)
            .promised(UPDATED_PROMISED)
            .refillInfo(UPDATED_REFILL_INFO)
            .pharmacyNotes(UPDATED_PHARMACY_NOTES);

        restMedicineInfoMockMvc.perform(put("/api/medicine-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMedicineInfo)))
            .andExpect(status().isOk());

        // Validate the MedicineInfo in the database
        List<MedicineInfo> medicineInfoList = medicineInfoRepository.findAll();
        assertThat(medicineInfoList).hasSize(databaseSizeBeforeUpdate);
        MedicineInfo testMedicineInfo = medicineInfoList.get(medicineInfoList.size() - 1);
        assertThat(testMedicineInfo.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMedicineInfo.getImportantInfo()).isEqualTo(UPDATED_IMPORTANT_INFO);
        assertThat(testMedicineInfo.getUsage()).isEqualTo(UPDATED_USAGE);
        assertThat(testMedicineInfo.getInitialCount()).isEqualTo(UPDATED_INITIAL_COUNT);
        assertThat(testMedicineInfo.getPromised()).isEqualTo(UPDATED_PROMISED);
        assertThat(testMedicineInfo.getRefillInfo()).isEqualTo(UPDATED_REFILL_INFO);
        assertThat(testMedicineInfo.getPharmacyNotes()).isEqualTo(UPDATED_PHARMACY_NOTES);
    }

    @Test
    @Transactional
    public void updateNonExistingMedicineInfo() throws Exception {
        int databaseSizeBeforeUpdate = medicineInfoRepository.findAll().size();

        // Create the MedicineInfo

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMedicineInfoMockMvc.perform(put("/api/medicine-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicineInfo)))
            .andExpect(status().isBadRequest());

        // Validate the MedicineInfo in the database
        List<MedicineInfo> medicineInfoList = medicineInfoRepository.findAll();
        assertThat(medicineInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMedicineInfo() throws Exception {
        // Initialize the database
        medicineInfoService.save(medicineInfo);

        int databaseSizeBeforeDelete = medicineInfoRepository.findAll().size();

        // Delete the medicineInfo
        restMedicineInfoMockMvc.perform(delete("/api/medicine-infos/{id}", medicineInfo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MedicineInfo> medicineInfoList = medicineInfoRepository.findAll();
        assertThat(medicineInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
