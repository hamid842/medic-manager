package com.app.repository;

import com.app.domain.MedicineInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MedicineInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MedicineInfoRepository extends JpaRepository<MedicineInfo, Long> {

}
