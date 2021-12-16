package com.tariku.paygov;

import com.digitalriver.worldpayments.api.AuthorizationType;
import com.digitalriver.worldpayments.api.PaymentPageRequest;
import com.digitalriver.worldpayments.api.security6.JKSKeyHandlerV6;
import com.ingenico.connect.gateway.sdk.java.Client;
import com.ingenico.connect.gateway.sdk.java.CommunicatorConfiguration;
import com.ingenico.connect.gateway.sdk.java.Factory;
import com.ingenico.connect.gateway.sdk.java.domain.definitions.Address;
import com.ingenico.connect.gateway.sdk.java.domain.definitions.AmountOfMoney;
import com.ingenico.connect.gateway.sdk.java.domain.definitions.Card;
import com.ingenico.connect.gateway.sdk.java.domain.hostedcheckout.CreateHostedCheckoutRequest;
import com.ingenico.connect.gateway.sdk.java.domain.hostedcheckout.CreateHostedCheckoutResponse;
import com.ingenico.connect.gateway.sdk.java.domain.hostedcheckout.GetHostedCheckoutResponse;
import com.ingenico.connect.gateway.sdk.java.domain.hostedcheckout.definitions.HostedCheckoutSpecificInput;
import com.ingenico.connect.gateway.sdk.java.domain.payment.CreatePaymentRequest;
import com.ingenico.connect.gateway.sdk.java.domain.payment.CreatePaymentResponse;
import com.ingenico.connect.gateway.sdk.java.domain.payment.definitions.CardPaymentMethodSpecificInput;
import com.ingenico.connect.gateway.sdk.java.domain.payment.definitions.Customer;
import com.ingenico.connect.gateway.sdk.java.domain.payment.definitions.Order;
import com.paypal.exception.*;
import com.paypal.sdk.exceptions.OAuthException;
import com.worldline.payments.api.PaymentHandler;
import com.worldline.payments.api.PaymentRequest;
import com.worldline.payments.api.PaymentRequestBuilder;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.xml.sax.SAXException;
import urn.ebay.api.PayPalAPI.PayPalAPIInterfaceServiceService;
import urn.ebay.api.PayPalAPI.SetExpressCheckoutReq;
import urn.ebay.api.PayPalAPI.SetExpressCheckoutRequestType;
import urn.ebay.api.PayPalAPI.SetExpressCheckoutResponseType;
import urn.ebay.apis.CoreComponentTypes.BasicAmountType;
import urn.ebay.apis.eBLBaseComponents.*;

@RestController
public class HelloController {

    String new_url;
    String new_checkout_id;
    File propertiesUrl = new File("/home/t/Documents/dev/paygo/src/main/resources/application.properties");
    CommunicatorConfiguration communicatorConfiguration = new CommunicatorConfiguration();
    Client client = Factory.createClient(propertiesUrl.toURI(), "e5ab0a54b4925c4d", "ggyepMGMiI46HKVUyyiV+c9xwgosOJSBFqoflKWbcVU=");
    ReadUserData readUserData = new ReadUserData();
    ReadReturnId readReturnId = new ReadReturnId();

    @GetMapping("/hello")
    public String hello() {
        return "url";
    }

    @RequestMapping("/to-be-redirected")
    public RedirectView payNow()
        throws ClientActionRequiredException, SSLConfigurationException, MissingCredentialException, OAuthException, InvalidResponseDataException, InvalidCredentialException, IOException, ParserConfigurationException, HttpErrorException, InterruptedException, SAXException {
        System.out.println("[[[[[[[[ inside ]]]]]]]]]]]]]");
        RedirectView redirectView = new RedirectView();
        System.out.println("[[[[[[[[ 1 ]]]]]]]]]]]]]");

        HostedCheckoutSpecificInput hostedCheckoutSpecificInput = new HostedCheckoutSpecificInput();
        hostedCheckoutSpecificInput.setLocale("en_GB");
        hostedCheckoutSpecificInput.setVariant("testVariant");
        hostedCheckoutSpecificInput.setReturnUrl("http://localhost:8080/response");
        System.out.println("[[[[[[[[ 2 ]]]]]]]]]]]]]");
        ///===============================================================
        String s_amount = readUserData.getData();
        double d_amount = Double.parseDouble(s_amount);
        PayPalSoap payPalSoap = new PayPalSoap();
        ////==================================================================
        // AmountOfMoney amountOfMoney = new AmountOfMoney();
        // //amountOfMoney.setAmount(l_amount);
        // amountOfMoney.setCurrencyCode("EUR");

        // Address billingAddress = new Address();
        // billingAddress.setCountryCode("US");

        // Customer customer = new Customer();
        // customer.setLocale("en_US");
        // customer.setMerchantCustomerId("1426");
        // customer.setBillingAddress(billingAddress);

        // Order order = new Order();
        // order.setAmountOfMoney(amountOfMoney);
        // order.setCustomer(customer);
        // System.out.println("[[[[[[[[ 3 ]]]]]]]]]]]]]");

        // CreateHostedCheckoutRequest body = new CreateHostedCheckoutRequest();
        // body.setHostedCheckoutSpecificInput(hostedCheckoutSpecificInput);
        // body.setOrder(order);

        // CreateHostedCheckoutResponse response = client.merchant("1426").hostedcheckouts().create(body);
        // new_url = response.getPartialRedirectUrl();
        // new_checkout_id = response.getHostedCheckoutId();
        String linkUrl = payPalSoap.payNowSoap(d_amount);
        this.readReturnId.saveData(payPalSoap.token);

        System.out.println("huuuuuuuuuuuuhhhhhuuuuuuuuhuuuuuuuuuuuuuhuuuuuuu " + payPalSoap.token + " ddddddd");
        System.out.println("_________________________" + new_url + " _____________________");
        //GetHostedCheckoutResponse redirect_response = client.merchant("1426").hostedcheckouts().get("061ae5da-c58b-71ff-ba7d-9b8f43f6efc9");
        // redirect_response.getStatus();
        // System.out.println(redirect_response);
        // System.out.println(redirect_response.getStatus());
        //  System.out.println(redirect_response.getCreatedPaymentOutput().getPayment().getPaymentOutput());

        URL url = null;
        String new_path = null;

        try {
            // create a URL
            url = new URL("https://" + new_url);

            // get the  Path
            new_path = url.getPath();

            // display the URL
            System.out.println("URL = " + url);

            // display the  Path
            System.out.println(" Path= " + new_path);
        } catch (Exception e) { // if any error occurs
            // display the error
            System.out.println(e);
        }

        UriComponents uriComponents = UriComponentsBuilder
            .newInstance()
            .scheme("https")
            .host("payment.pay1.sandbox.secured-by-ingenico.com")
            .path("/" + new_path)
            .buildAndExpand("junit-5");
        System.out.println("???????????????????????" + uriComponents.toUriString() + "??????????????????");

        /////===============================================
        redirectView.setUrl(linkUrl);
        return redirectView;
    }
}
