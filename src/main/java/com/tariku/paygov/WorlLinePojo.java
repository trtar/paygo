package com.tariku.paygov;

import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("paytm.payment.sandbox")
public class WorlLinePojo {

    private String merchantId;

    private String merchantKey;

    private String integrator;

    private String website;

    private String authorizationType;

    private String paytmUrl;

    private Map<String, String> details;

    public WorlLinePojo() {}

    public WorlLinePojo(
        String merchantId,
        String merchantKey,
        String integrator,
        String website,
        String authorizationType,
        String paytmUrl,
        Map<String, String> details
    ) {
        super();
        this.merchantId = merchantId;
        this.merchantKey = merchantKey;
        this.integrator = integrator;
        this.website = website;
        this.authorizationType = authorizationType;
        this.paytmUrl = paytmUrl;
        this.details = details;
    }
}
//getter and setter method
