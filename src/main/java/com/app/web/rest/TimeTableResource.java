package com.app.web.rest;

import com.app.domain.TimeTable;
import com.app.service.TimeTableService;
import com.app.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.app.domain.TimeTable}.
 */
@RestController
@RequestMapping("/api")
public class TimeTableResource {

    private final Logger log = LoggerFactory.getLogger(TimeTableResource.class);

    private static final String ENTITY_NAME = "timeTable";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TimeTableService timeTableService;

    public TimeTableResource(TimeTableService timeTableService) {
        this.timeTableService = timeTableService;
    }

    /**
     * {@code POST  /time-tables} : Create a new timeTable.
     *
     * @param timeTable the timeTable to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new timeTable, or with status {@code 400 (Bad Request)} if the timeTable has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/time-tables")
    public ResponseEntity<TimeTable> createTimeTable(@RequestBody TimeTable timeTable) throws URISyntaxException {
        log.debug("REST request to save TimeTable : {}", timeTable);
        if (timeTable.getId() != null) {
            throw new BadRequestAlertException("A new timeTable cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TimeTable result = timeTableService.save(timeTable);
        return ResponseEntity.created(new URI("/api/time-tables/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /time-tables} : Updates an existing timeTable.
     *
     * @param timeTable the timeTable to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated timeTable,
     * or with status {@code 400 (Bad Request)} if the timeTable is not valid,
     * or with status {@code 500 (Internal Server Error)} if the timeTable couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/time-tables")
    public ResponseEntity<TimeTable> updateTimeTable(@RequestBody TimeTable timeTable) throws URISyntaxException {
        log.debug("REST request to update TimeTable : {}", timeTable);
        if (timeTable.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TimeTable result = timeTableService.save(timeTable);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, timeTable.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /time-tables} : get all the timeTables.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of timeTables in body.
     */
    @GetMapping("/time-tables")
    public ResponseEntity<List<TimeTable>> getAllTimeTables(Pageable pageable) {
        log.debug("REST request to get a page of TimeTables");
        Page<TimeTable> page = timeTableService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /time-tables/:id} : get the "id" timeTable.
     *
     * @param id the id of the timeTable to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the timeTable, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/time-tables/{id}")
    public ResponseEntity<TimeTable> getTimeTable(@PathVariable Long id) {
        log.debug("REST request to get TimeTable : {}", id);
        Optional<TimeTable> timeTable = timeTableService.findOne(id);
        return ResponseUtil.wrapOrNotFound(timeTable);
    }

    /**
     * {@code DELETE  /time-tables/:id} : delete the "id" timeTable.
     *
     * @param id the id of the timeTable to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/time-tables/{id}")
    public ResponseEntity<Void> deleteTimeTable(@PathVariable Long id) {
        log.debug("REST request to delete TimeTable : {}", id);
        timeTableService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
