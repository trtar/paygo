package com.tariku.paygov.repository;

import com.tariku.paygov.domain.Paynow;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Paynow entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaynowRepository extends JpaRepository<Paynow, Long> {}
