package com.app.service.impl;

import com.app.service.MedicineInfoService;
import com.app.domain.MedicineInfo;
import com.app.repository.MedicineInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MedicineInfo}.
 */
@Service
@Transactional
public class MedicineInfoServiceImpl implements MedicineInfoService {

    private final Logger log = LoggerFactory.getLogger(MedicineInfoServiceImpl.class);

    private final MedicineInfoRepository medicineInfoRepository;

    public MedicineInfoServiceImpl(MedicineInfoRepository medicineInfoRepository) {
        this.medicineInfoRepository = medicineInfoRepository;
    }

    /**
     * Save a medicineInfo.
     *
     * @param medicineInfo the entity to save.
     * @return the persisted entity.
     */
    @Override
    public MedicineInfo save(MedicineInfo medicineInfo) {
        log.debug("Request to save MedicineInfo : {}", medicineInfo);
        return medicineInfoRepository.save(medicineInfo);
    }

    /**
     * Get all the medicineInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MedicineInfo> findAll(Pageable pageable) {
        log.debug("Request to get all MedicineInfos");
        return medicineInfoRepository.findAll(pageable);
    }


    /**
     * Get one medicineInfo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MedicineInfo> findOne(Long id) {
        log.debug("Request to get MedicineInfo : {}", id);
        return medicineInfoRepository.findById(id);
    }

    /**
     * Delete the medicineInfo by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MedicineInfo : {}", id);
        medicineInfoRepository.deleteById(id);
    }
}
