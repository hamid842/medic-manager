package com.app.service;

import com.app.domain.MedicineInfo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link MedicineInfo}.
 */
public interface MedicineInfoService {

    /**
     * Save a medicineInfo.
     *
     * @param medicineInfo the entity to save.
     * @return the persisted entity.
     */
    MedicineInfo save(MedicineInfo medicineInfo);

    /**
     * Get all the medicineInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MedicineInfo> findAll(Pageable pageable);


    /**
     * Get the "id" medicineInfo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MedicineInfo> findOne(Long id);

    /**
     * Delete the "id" medicineInfo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
