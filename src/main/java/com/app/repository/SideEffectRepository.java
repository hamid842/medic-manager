package com.app.repository;

import com.app.domain.SideEffect;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SideEffect entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SideEffectRepository extends JpaRepository<SideEffect, Long> {

}
