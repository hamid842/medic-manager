package com.app.web.rest;

import com.app.domain.MedicineInfo;
import com.app.service.MedicineInfoService;
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
 * REST controller for managing {@link com.app.domain.MedicineInfo}.
 */
@RestController
@RequestMapping("/api")
public class MedicineInfoResource {

    private final Logger log = LoggerFactory.getLogger(MedicineInfoResource.class);

    private static final String ENTITY_NAME = "medicineInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MedicineInfoService medicineInfoService;

    public MedicineInfoResource(MedicineInfoService medicineInfoService) {
        this.medicineInfoService = medicineInfoService;
    }

    /**
     * {@code POST  /medicine-infos} : Create a new medicineInfo.
     *
     * @param medicineInfo the medicineInfo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new medicineInfo, or with status {@code 400 (Bad Request)} if the medicineInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/medicine-infos")
    public ResponseEntity<MedicineInfo> createMedicineInfo(@RequestBody MedicineInfo medicineInfo) throws URISyntaxException {
        log.debug("REST request to save MedicineInfo : {}", medicineInfo);
        if (medicineInfo.getId() != null) {
            throw new BadRequestAlertException("A new medicineInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MedicineInfo result = medicineInfoService.save(medicineInfo);
        return ResponseEntity.created(new URI("/api/medicine-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /medicine-infos} : Updates an existing medicineInfo.
     *
     * @param medicineInfo the medicineInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated medicineInfo,
     * or with status {@code 400 (Bad Request)} if the medicineInfo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the medicineInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/medicine-infos")
    public ResponseEntity<MedicineInfo> updateMedicineInfo(@RequestBody MedicineInfo medicineInfo) throws URISyntaxException {
        log.debug("REST request to update MedicineInfo : {}", medicineInfo);
        if (medicineInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MedicineInfo result = medicineInfoService.save(medicineInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, medicineInfo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /medicine-infos} : get all the medicineInfos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of medicineInfos in body.
     */
    @GetMapping("/medicine-infos")
    public ResponseEntity<List<MedicineInfo>> getAllMedicineInfos(Pageable pageable) {
        log.debug("REST request to get a page of MedicineInfos");
        Page<MedicineInfo> page = medicineInfoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /medicine-infos/:id} : get the "id" medicineInfo.
     *
     * @param id the id of the medicineInfo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the medicineInfo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/medicine-infos/{id}")
    public ResponseEntity<MedicineInfo> getMedicineInfo(@PathVariable Long id) {
        log.debug("REST request to get MedicineInfo : {}", id);
        Optional<MedicineInfo> medicineInfo = medicineInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(medicineInfo);
    }

    /**
     * {@code DELETE  /medicine-infos/:id} : delete the "id" medicineInfo.
     *
     * @param id the id of the medicineInfo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/medicine-infos/{id}")
    public ResponseEntity<Void> deleteMedicineInfo(@PathVariable Long id) {
        log.debug("REST request to delete MedicineInfo : {}", id);
        medicineInfoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
