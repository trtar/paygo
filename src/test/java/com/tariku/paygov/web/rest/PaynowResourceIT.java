package com.tariku.paygov.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.tariku.paygov.IntegrationTest;
import com.tariku.paygov.domain.Paynow;
import com.tariku.paygov.repository.PaynowRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PaynowResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PaynowResourceIT {

    private static final String DEFAULT_CIK = "AAAAAAAAAA";
    private static final String UPDATED_CIK = "BBBBBBBBBB";

    private static final String DEFAULT_CCC = "AAAAAAAAAA";
    private static final String UPDATED_CCC = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/paynows";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PaynowRepository paynowRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaynowMockMvc;

    private Paynow paynow;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Paynow createEntity(EntityManager em) {
        Paynow paynow = new Paynow().cik(DEFAULT_CIK).ccc(DEFAULT_CCC).name(DEFAULT_NAME).email(DEFAULT_EMAIL).phone(DEFAULT_PHONE);
        return paynow;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Paynow createUpdatedEntity(EntityManager em) {
        Paynow paynow = new Paynow().cik(UPDATED_CIK).ccc(UPDATED_CCC).name(UPDATED_NAME).email(UPDATED_EMAIL).phone(UPDATED_PHONE);
        return paynow;
    }

    @BeforeEach
    public void initTest() {
        paynow = createEntity(em);
    }

    @Test
    @Transactional
    void createPaynow() throws Exception {
        int databaseSizeBeforeCreate = paynowRepository.findAll().size();
        // Create the Paynow
        restPaynowMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paynow))
            )
            .andExpect(status().isCreated());

        // Validate the Paynow in the database
        List<Paynow> paynowList = paynowRepository.findAll();
        assertThat(paynowList).hasSize(databaseSizeBeforeCreate + 1);
        Paynow testPaynow = paynowList.get(paynowList.size() - 1);
        assertThat(testPaynow.getCik()).isEqualTo(DEFAULT_CIK);
        assertThat(testPaynow.getCcc()).isEqualTo(DEFAULT_CCC);
        assertThat(testPaynow.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPaynow.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testPaynow.getPhone()).isEqualTo(DEFAULT_PHONE);
    }

    @Test
    @Transactional
    void createPaynowWithExistingId() throws Exception {
        // Create the Paynow with an existing ID
        paynow.setId(1L);

        int databaseSizeBeforeCreate = paynowRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaynowMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paynow))
            )
            .andExpect(status().isBadRequest());

        // Validate the Paynow in the database
        List<Paynow> paynowList = paynowRepository.findAll();
        assertThat(paynowList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPaynows() throws Exception {
        // Initialize the database
        paynowRepository.saveAndFlush(paynow);

        // Get all the paynowList
        restPaynowMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paynow.getId().intValue())))
            .andExpect(jsonPath("$.[*].cik").value(hasItem(DEFAULT_CIK)))
            .andExpect(jsonPath("$.[*].ccc").value(hasItem(DEFAULT_CCC)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)));
    }

    @Test
    @Transactional
    void getPaynow() throws Exception {
        // Initialize the database
        paynowRepository.saveAndFlush(paynow);

        // Get the paynow
        restPaynowMockMvc
            .perform(get(ENTITY_API_URL_ID, paynow.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paynow.getId().intValue()))
            .andExpect(jsonPath("$.cik").value(DEFAULT_CIK))
            .andExpect(jsonPath("$.ccc").value(DEFAULT_CCC))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE));
    }

    @Test
    @Transactional
    void getNonExistingPaynow() throws Exception {
        // Get the paynow
        restPaynowMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPaynow() throws Exception {
        // Initialize the database
        paynowRepository.saveAndFlush(paynow);

        int databaseSizeBeforeUpdate = paynowRepository.findAll().size();

        // Update the paynow
        Paynow updatedPaynow = paynowRepository.findById(paynow.getId()).get();
        // Disconnect from session so that the updates on updatedPaynow are not directly saved in db
        em.detach(updatedPaynow);
        updatedPaynow.cik(UPDATED_CIK).ccc(UPDATED_CCC).name(UPDATED_NAME).email(UPDATED_EMAIL).phone(UPDATED_PHONE);

        restPaynowMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPaynow.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPaynow))
            )
            .andExpect(status().isOk());

        // Validate the Paynow in the database
        List<Paynow> paynowList = paynowRepository.findAll();
        assertThat(paynowList).hasSize(databaseSizeBeforeUpdate);
        Paynow testPaynow = paynowList.get(paynowList.size() - 1);
        assertThat(testPaynow.getCik()).isEqualTo(UPDATED_CIK);
        assertThat(testPaynow.getCcc()).isEqualTo(UPDATED_CCC);
        assertThat(testPaynow.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPaynow.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPaynow.getPhone()).isEqualTo(UPDATED_PHONE);
    }

    @Test
    @Transactional
    void putNonExistingPaynow() throws Exception {
        int databaseSizeBeforeUpdate = paynowRepository.findAll().size();
        paynow.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaynowMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paynow.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paynow))
            )
            .andExpect(status().isBadRequest());

        // Validate the Paynow in the database
        List<Paynow> paynowList = paynowRepository.findAll();
        assertThat(paynowList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPaynow() throws Exception {
        int databaseSizeBeforeUpdate = paynowRepository.findAll().size();
        paynow.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaynowMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paynow))
            )
            .andExpect(status().isBadRequest());

        // Validate the Paynow in the database
        List<Paynow> paynowList = paynowRepository.findAll();
        assertThat(paynowList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPaynow() throws Exception {
        int databaseSizeBeforeUpdate = paynowRepository.findAll().size();
        paynow.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaynowMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paynow))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Paynow in the database
        List<Paynow> paynowList = paynowRepository.findAll();
        assertThat(paynowList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePaynowWithPatch() throws Exception {
        // Initialize the database
        paynowRepository.saveAndFlush(paynow);

        int databaseSizeBeforeUpdate = paynowRepository.findAll().size();

        // Update the paynow using partial update
        Paynow partialUpdatedPaynow = new Paynow();
        partialUpdatedPaynow.setId(paynow.getId());

        partialUpdatedPaynow.name(UPDATED_NAME).email(UPDATED_EMAIL);

        restPaynowMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaynow.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaynow))
            )
            .andExpect(status().isOk());

        // Validate the Paynow in the database
        List<Paynow> paynowList = paynowRepository.findAll();
        assertThat(paynowList).hasSize(databaseSizeBeforeUpdate);
        Paynow testPaynow = paynowList.get(paynowList.size() - 1);
        assertThat(testPaynow.getCik()).isEqualTo(DEFAULT_CIK);
        assertThat(testPaynow.getCcc()).isEqualTo(DEFAULT_CCC);
        assertThat(testPaynow.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPaynow.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPaynow.getPhone()).isEqualTo(DEFAULT_PHONE);
    }

    @Test
    @Transactional
    void fullUpdatePaynowWithPatch() throws Exception {
        // Initialize the database
        paynowRepository.saveAndFlush(paynow);

        int databaseSizeBeforeUpdate = paynowRepository.findAll().size();

        // Update the paynow using partial update
        Paynow partialUpdatedPaynow = new Paynow();
        partialUpdatedPaynow.setId(paynow.getId());

        partialUpdatedPaynow.cik(UPDATED_CIK).ccc(UPDATED_CCC).name(UPDATED_NAME).email(UPDATED_EMAIL).phone(UPDATED_PHONE);

        restPaynowMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaynow.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaynow))
            )
            .andExpect(status().isOk());

        // Validate the Paynow in the database
        List<Paynow> paynowList = paynowRepository.findAll();
        assertThat(paynowList).hasSize(databaseSizeBeforeUpdate);
        Paynow testPaynow = paynowList.get(paynowList.size() - 1);
        assertThat(testPaynow.getCik()).isEqualTo(UPDATED_CIK);
        assertThat(testPaynow.getCcc()).isEqualTo(UPDATED_CCC);
        assertThat(testPaynow.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPaynow.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPaynow.getPhone()).isEqualTo(UPDATED_PHONE);
    }

    @Test
    @Transactional
    void patchNonExistingPaynow() throws Exception {
        int databaseSizeBeforeUpdate = paynowRepository.findAll().size();
        paynow.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaynowMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, paynow.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paynow))
            )
            .andExpect(status().isBadRequest());

        // Validate the Paynow in the database
        List<Paynow> paynowList = paynowRepository.findAll();
        assertThat(paynowList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPaynow() throws Exception {
        int databaseSizeBeforeUpdate = paynowRepository.findAll().size();
        paynow.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaynowMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paynow))
            )
            .andExpect(status().isBadRequest());

        // Validate the Paynow in the database
        List<Paynow> paynowList = paynowRepository.findAll();
        assertThat(paynowList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPaynow() throws Exception {
        int databaseSizeBeforeUpdate = paynowRepository.findAll().size();
        paynow.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaynowMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paynow))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Paynow in the database
        List<Paynow> paynowList = paynowRepository.findAll();
        assertThat(paynowList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePaynow() throws Exception {
        // Initialize the database
        paynowRepository.saveAndFlush(paynow);

        int databaseSizeBeforeDelete = paynowRepository.findAll().size();

        // Delete the paynow
        restPaynowMockMvc
            .perform(delete(ENTITY_API_URL_ID, paynow.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Paynow> paynowList = paynowRepository.findAll();
        assertThat(paynowList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
