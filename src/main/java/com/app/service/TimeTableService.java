package com.app.service;

import com.app.domain.TimeTable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link TimeTable}.
 */
public interface TimeTableService {

    /**
     * Save a timeTable.
     *
     * @param timeTable the entity to save.
     * @return the persisted entity.
     */
    TimeTable save(TimeTable timeTable);

    /**
     * Get all the timeTables.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TimeTable> findAll(Pageable pageable);


    /**
     * Get the "id" timeTable.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TimeTable> findOne(Long id);

    /**
     * Delete the "id" timeTable.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
