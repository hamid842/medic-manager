package com.app.service.impl;

import com.app.service.TimeTableService;
import com.app.domain.TimeTable;
import com.app.repository.TimeTableRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TimeTable}.
 */
@Service
@Transactional
public class TimeTableServiceImpl implements TimeTableService {

    private final Logger log = LoggerFactory.getLogger(TimeTableServiceImpl.class);

    private final TimeTableRepository timeTableRepository;

    public TimeTableServiceImpl(TimeTableRepository timeTableRepository) {
        this.timeTableRepository = timeTableRepository;
    }

    /**
     * Save a timeTable.
     *
     * @param timeTable the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TimeTable save(TimeTable timeTable) {
        log.debug("Request to save TimeTable : {}", timeTable);
        return timeTableRepository.save(timeTable);
    }

    /**
     * Get all the timeTables.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TimeTable> findAll(Pageable pageable) {
        log.debug("Request to get all TimeTables");
        return timeTableRepository.findAll(pageable);
    }


    /**
     * Get one timeTable by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TimeTable> findOne(Long id) {
        log.debug("Request to get TimeTable : {}", id);
        return timeTableRepository.findById(id);
    }

    /**
     * Delete the timeTable by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TimeTable : {}", id);
        timeTableRepository.deleteById(id);
    }
}
