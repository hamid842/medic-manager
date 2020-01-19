package com.app.web.rest;

import com.app.MedicManagerApp;
import com.app.domain.TimeTable;
import com.app.repository.TimeTableRepository;
import com.app.service.TimeTableService;
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
 * Integration tests for the {@link TimeTableResource} REST controller.
 */
@SpringBootTest(classes = MedicManagerApp.class)
public class TimeTableResourceIT {

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_IS_TAKEN = "AAAAAAAAAA";
    private static final String UPDATED_IS_TAKEN = "BBBBBBBBBB";

    @Autowired
    private TimeTableRepository timeTableRepository;

    @Autowired
    private TimeTableService timeTableService;

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

    private MockMvc restTimeTableMockMvc;

    private TimeTable timeTable;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TimeTableResource timeTableResource = new TimeTableResource(timeTableService);
        this.restTimeTableMockMvc = MockMvcBuilders.standaloneSetup(timeTableResource)
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
    public static TimeTable createEntity(EntityManager em) {
        TimeTable timeTable = new TimeTable()
            .date(DEFAULT_DATE)
            .isTaken(DEFAULT_IS_TAKEN);
        return timeTable;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TimeTable createUpdatedEntity(EntityManager em) {
        TimeTable timeTable = new TimeTable()
            .date(UPDATED_DATE)
            .isTaken(UPDATED_IS_TAKEN);
        return timeTable;
    }

    @BeforeEach
    public void initTest() {
        timeTable = createEntity(em);
    }

    @Test
    @Transactional
    public void createTimeTable() throws Exception {
        int databaseSizeBeforeCreate = timeTableRepository.findAll().size();

        // Create the TimeTable
        restTimeTableMockMvc.perform(post("/api/time-tables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timeTable)))
            .andExpect(status().isCreated());

        // Validate the TimeTable in the database
        List<TimeTable> timeTableList = timeTableRepository.findAll();
        assertThat(timeTableList).hasSize(databaseSizeBeforeCreate + 1);
        TimeTable testTimeTable = timeTableList.get(timeTableList.size() - 1);
        assertThat(testTimeTable.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testTimeTable.getIsTaken()).isEqualTo(DEFAULT_IS_TAKEN);
    }

    @Test
    @Transactional
    public void createTimeTableWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = timeTableRepository.findAll().size();

        // Create the TimeTable with an existing ID
        timeTable.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTimeTableMockMvc.perform(post("/api/time-tables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timeTable)))
            .andExpect(status().isBadRequest());

        // Validate the TimeTable in the database
        List<TimeTable> timeTableList = timeTableRepository.findAll();
        assertThat(timeTableList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTimeTables() throws Exception {
        // Initialize the database
        timeTableRepository.saveAndFlush(timeTable);

        // Get all the timeTableList
        restTimeTableMockMvc.perform(get("/api/time-tables?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(timeTable.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].isTaken").value(hasItem(DEFAULT_IS_TAKEN)));
    }
    
    @Test
    @Transactional
    public void getTimeTable() throws Exception {
        // Initialize the database
        timeTableRepository.saveAndFlush(timeTable);

        // Get the timeTable
        restTimeTableMockMvc.perform(get("/api/time-tables/{id}", timeTable.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(timeTable.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.isTaken").value(DEFAULT_IS_TAKEN));
    }

    @Test
    @Transactional
    public void getNonExistingTimeTable() throws Exception {
        // Get the timeTable
        restTimeTableMockMvc.perform(get("/api/time-tables/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTimeTable() throws Exception {
        // Initialize the database
        timeTableService.save(timeTable);

        int databaseSizeBeforeUpdate = timeTableRepository.findAll().size();

        // Update the timeTable
        TimeTable updatedTimeTable = timeTableRepository.findById(timeTable.getId()).get();
        // Disconnect from session so that the updates on updatedTimeTable are not directly saved in db
        em.detach(updatedTimeTable);
        updatedTimeTable
            .date(UPDATED_DATE)
            .isTaken(UPDATED_IS_TAKEN);

        restTimeTableMockMvc.perform(put("/api/time-tables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTimeTable)))
            .andExpect(status().isOk());

        // Validate the TimeTable in the database
        List<TimeTable> timeTableList = timeTableRepository.findAll();
        assertThat(timeTableList).hasSize(databaseSizeBeforeUpdate);
        TimeTable testTimeTable = timeTableList.get(timeTableList.size() - 1);
        assertThat(testTimeTable.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testTimeTable.getIsTaken()).isEqualTo(UPDATED_IS_TAKEN);
    }

    @Test
    @Transactional
    public void updateNonExistingTimeTable() throws Exception {
        int databaseSizeBeforeUpdate = timeTableRepository.findAll().size();

        // Create the TimeTable

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTimeTableMockMvc.perform(put("/api/time-tables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timeTable)))
            .andExpect(status().isBadRequest());

        // Validate the TimeTable in the database
        List<TimeTable> timeTableList = timeTableRepository.findAll();
        assertThat(timeTableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTimeTable() throws Exception {
        // Initialize the database
        timeTableService.save(timeTable);

        int databaseSizeBeforeDelete = timeTableRepository.findAll().size();

        // Delete the timeTable
        restTimeTableMockMvc.perform(delete("/api/time-tables/{id}", timeTable.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TimeTable> timeTableList = timeTableRepository.findAll();
        assertThat(timeTableList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
