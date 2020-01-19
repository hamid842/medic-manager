package com.app.service;

import com.app.domain.SideEffect;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link SideEffect}.
 */
public interface SideEffectService {

    /**
     * Save a sideEffect.
     *
     * @param sideEffect the entity to save.
     * @return the persisted entity.
     */
    SideEffect save(SideEffect sideEffect);

    /**
     * Get all the sideEffects.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SideEffect> findAll(Pageable pageable);


    /**
     * Get the "id" sideEffect.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SideEffect> findOne(Long id);

    /**
     * Delete the "id" sideEffect.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
