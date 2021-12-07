package com.tariku.paygov.service;

import com.tariku.paygov.domain.Paynow;
import com.tariku.paygov.repository.PaynowRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Paynow}.
 */
@Service
@Transactional
public class PaynowService {

    private final Logger log = LoggerFactory.getLogger(PaynowService.class);

    private final PaynowRepository paynowRepository;

    public PaynowService(PaynowRepository paynowRepository) {
        this.paynowRepository = paynowRepository;
    }

    /**
     * Save a paynow.
     *
     * @param paynow the entity to save.
     * @return the persisted entity.
     */
    public Paynow save(Paynow paynow) {
        log.debug("Request to save Paynow : {}", paynow);
        return paynowRepository.save(paynow);
    }

    /**
     * Partially update a paynow.
     *
     * @param paynow the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Paynow> partialUpdate(Paynow paynow) {
        log.debug("Request to partially update Paynow : {}", paynow);

        return paynowRepository
            .findById(paynow.getId())
            .map(existingPaynow -> {
                if (paynow.getCik() != null) {
                    existingPaynow.setCik(paynow.getCik());
                }
                if (paynow.getCcc() != null) {
                    existingPaynow.setCcc(paynow.getCcc());
                }
                if (paynow.getName() != null) {
                    existingPaynow.setName(paynow.getName());
                }
                if (paynow.getEmail() != null) {
                    existingPaynow.setEmail(paynow.getEmail());
                }
                if (paynow.getPhone() != null) {
                    existingPaynow.setPhone(paynow.getPhone());
                }

                return existingPaynow;
            })
            .map(paynowRepository::save);
    }

    /**
     * Get all the paynows.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Paynow> findAll() {
        log.debug("Request to get all Paynows");
        return paynowRepository.findAll();
    }

    /**
     * Get one paynow by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Paynow> findOne(Long id) {
        log.debug("Request to get Paynow : {}", id);
        return paynowRepository.findById(id);
    }

    /**
     * Delete the paynow by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Paynow : {}", id);
        paynowRepository.deleteById(id);
    }
}
