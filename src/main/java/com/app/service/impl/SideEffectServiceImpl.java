package com.app.service.impl;

import com.app.service.SideEffectService;
import com.app.domain.SideEffect;
import com.app.repository.SideEffectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SideEffect}.
 */
@Service
@Transactional
public class SideEffectServiceImpl implements SideEffectService {

    private final Logger log = LoggerFactory.getLogger(SideEffectServiceImpl.class);

    private final SideEffectRepository sideEffectRepository;

    public SideEffectServiceImpl(SideEffectRepository sideEffectRepository) {
        this.sideEffectRepository = sideEffectRepository;
    }

    /**
     * Save a sideEffect.
     *
     * @param sideEffect the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SideEffect save(SideEffect sideEffect) {
        log.debug("Request to save SideEffect : {}", sideEffect);
        return sideEffectRepository.save(sideEffect);
    }

    /**
     * Get all the sideEffects.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SideEffect> findAll(Pageable pageable) {
        log.debug("Request to get all SideEffects");
        return sideEffectRepository.findAll(pageable);
    }


    /**
     * Get one sideEffect by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SideEffect> findOne(Long id) {
        log.debug("Request to get SideEffect : {}", id);
        return sideEffectRepository.findById(id);
    }

    /**
     * Delete the sideEffect by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SideEffect : {}", id);
        sideEffectRepository.deleteById(id);
    }
}
