package com.tariku.paygov.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.tariku.paygov.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PaynowTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Paynow.class);
        Paynow paynow1 = new Paynow();
        paynow1.setId(1L);
        Paynow paynow2 = new Paynow();
        paynow2.setId(paynow1.getId());
        assertThat(paynow1).isEqualTo(paynow2);
        paynow2.setId(2L);
        assertThat(paynow1).isNotEqualTo(paynow2);
        paynow1.setId(null);
        assertThat(paynow1).isNotEqualTo(paynow2);
    }
}
