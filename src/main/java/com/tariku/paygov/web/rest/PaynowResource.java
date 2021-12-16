package com.tariku.paygov.web.rest;

import com.paypal.exception.ClientActionRequiredException;
import com.paypal.exception.HttpErrorException;
import com.paypal.exception.InvalidCredentialException;
import com.paypal.exception.InvalidResponseDataException;
import com.paypal.exception.MissingCredentialException;
import com.paypal.exception.SSLConfigurationException;
import com.paypal.sdk.exceptions.OAuthException;
import com.tariku.paygov.HelloController;
import com.tariku.paygov.ReadReturnId;
import com.tariku.paygov.ReadUserData;
import com.tariku.paygov.ReturnId;
import com.tariku.paygov.UserData;
import com.tariku.paygov.domain.Paynow;
import com.tariku.paygov.repository.PaynowRepository;
import com.tariku.paygov.service.PaynowService;
import com.tariku.paygov.web.rest.errors.BadRequestAlertException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.xml.parsers.ParserConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.tariku.paygov.domain.Paynow}.
 */
@RestController
@RequestMapping("/api")
public class PaynowResource {

    ReadReturnId readReturnId = new ReadReturnId();
    private final Logger log = LoggerFactory.getLogger(PaynowResource.class);

    private static final String ENTITY_NAME = "paynow";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaynowService paynowService;

    private final PaynowRepository paynowRepository;

    public PaynowResource(PaynowService paynowService, PaynowRepository paynowRepository) {
        this.paynowService = paynowService;
        this.paynowRepository = paynowRepository;
    }

    /**
     * {@code POST  /paynows} : Create a new paynow.
     *
     * @param paynow the paynow to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paynow, or with status {@code 400 (Bad Request)} if the paynow has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/paynows")
    public ResponseEntity<Paynow> createPaynow(@RequestBody Paynow paynow) throws URISyntaxException {
        System.out.println("0000000000000000000000000 inside paynows 0000000000000000000");

        log.debug("REST request to save Paynow : {}", paynow);
        if (paynow.getId() != null) {
            throw new BadRequestAlertException("A new paynow cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Paynow result = paynowService.save(paynow);
        return ResponseEntity
            .created(new URI("/api/paynows/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /paynows/:id} : Updates an existing paynow.
     *
     * @param id the id of the paynow to save.
     * @param paynow the paynow to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paynow,
     * or with status {@code 400 (Bad Request)} if the paynow is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paynow couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/paynows/{id}")
    public ResponseEntity<Paynow> updatePaynow(@PathVariable(value = "id", required = false) final Long id, @RequestBody Paynow paynow)
        throws URISyntaxException {
        log.debug("REST request to update Paynow : {}, {}", id, paynow);
        if (paynow.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paynow.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paynowRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Paynow result = paynowService.save(paynow);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, paynow.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /paynows/:id} : Partial updates given fields of an existing paynow, field will ignore if it is null
     *
     * @param id the id of the paynow to save.
     * @param paynow the paynow to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paynow,
     * or with status {@code 400 (Bad Request)} if the paynow is not valid,
     * or with status {@code 404 (Not Found)} if the paynow is not found,
     * or with status {@code 500 (Internal Server Error)} if the paynow couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/paynows/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Paynow> partialUpdatePaynow(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Paynow paynow
    ) throws URISyntaxException {
        log.debug("REST request to partial update Paynow partially : {}, {}", id, paynow);
        if (paynow.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paynow.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paynowRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Paynow> result = paynowService.partialUpdate(paynow);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, paynow.getId().toString())
        );
    }

    /**
     * {@code GET  /paynows} : get all the paynows.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paynows in body.
     */
    @GetMapping("/paynows")
    public List<Paynow> getAllPaynows() {
        System.out.println("()()()()()()()()()()()()(We are inside get paynowss ");
        log.debug("REST request to get all Paynows");
        return paynowService.findAll();
    }

    /**
     * {@code GET  /paynows/:id} : get the "id" paynow.
     *
     * @param id the id of the paynow to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paynow, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/paynows/{id}")
    public ResponseEntity<Paynow> getPaynow(@PathVariable Long id) {
        log.debug("REST request to get Paynow : {}", id);
        Optional<Paynow> paynow = paynowService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paynow);
    }

    /**
     * {@code DELETE  /paynows/:id} : delete the "id" paynow.
     *
     * @param id the id of the paynow to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/paynows/{id}")
    public ResponseEntity<Void> deletePaynow(@PathVariable Long id) {
        log.debug("REST request to delete Paynow : {}", id);
        paynowService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    @PostMapping("/paynowss")
    public void createPaynw(@RequestBody Paynow paynow) throws URISyntaxException {
        String cik = paynow.getCcc();
        String ccc = paynow.getCcc();
        String amount = paynow.getCcc();
        String name = paynow.getName();
        String email = paynow.getEmail();
        String phone = paynow.getPhone();

        ReadUserData readUserData = new ReadUserData();
        readUserData.saveData(cik, ccc, amount, name, email, phone);
        readUserData.getData();
    }

    @GetMapping("/paynowsss")
    public ReturnId createPaynww() {
        log.debug("REST request to get all Paynows");
        ReturnId returnId = new ReturnId();
        try {
            returnId.transactionId = readReturnId.getData();
        } catch (ClientActionRequiredException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SSLConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MissingCredentialException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (OAuthException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidResponseDataException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidCredentialException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (HttpErrorException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return returnId;
    }
}
