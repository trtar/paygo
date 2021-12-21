package com.tariku.paygov;

import com.paypal.exception.*;
import com.paypal.sdk.exceptions.OAuthException;
import java.io.IOException;
import java.util.*;
import javax.xml.parsers.ParserConfigurationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.xml.sax.SAXException;
import urn.ebay.api.PayPalAPI.*;
import urn.ebay.apis.CoreComponentTypes.BasicAmountType;
import urn.ebay.apis.eBLBaseComponents.*;

public class PayPalSoap {

    String token;

    @GetMapping("/soap-set")
    public String payNowSoap(Double d_amount)
        throws ClientActionRequiredException, SSLConfigurationException, MissingCredentialException, OAuthException, InvalidResponseDataException, InvalidCredentialException, IOException, ParserConfigurationException, HttpErrorException, InterruptedException, SAXException {
        PaymentDetailsType paymentDetails = new PaymentDetailsType();
        paymentDetails.setPaymentAction(PaymentActionCodeType.SALE);
        PaymentDetailsItemType item = new PaymentDetailsItemType();
        BasicAmountType amt = new BasicAmountType();
        amt.setCurrencyID(CurrencyCodeType.fromValue("USD"));
        double itemAmount = d_amount;
        amt.setValue(String.valueOf(itemAmount));
        int itemQuantity = 1;
        item.setQuantity(itemQuantity);
        item.setName("item");
        item.setAmount(amt);

        List<PaymentDetailsItemType> lineItems = new ArrayList<PaymentDetailsItemType>();
        lineItems.add(item);
        paymentDetails.setPaymentDetailsItem(lineItems);
        BasicAmountType orderTotal = new BasicAmountType();
        orderTotal.setCurrencyID(CurrencyCodeType.fromValue("USD"));
        orderTotal.setValue(String.valueOf(itemAmount * itemQuantity));
        paymentDetails.setOrderTotal(orderTotal);
        List<PaymentDetailsType> paymentDetailsList = new ArrayList<PaymentDetailsType>();
        paymentDetailsList.add(paymentDetails);

        SetExpressCheckoutRequestDetailsType setExpressCheckoutRequestDetails = new SetExpressCheckoutRequestDetailsType();
        setExpressCheckoutRequestDetails.setReturnURL("http://localhost:8080/response");
        setExpressCheckoutRequestDetails.setCancelURL("http://localhost:8080/");

        setExpressCheckoutRequestDetails.setPaymentDetails(paymentDetailsList);

        SetExpressCheckoutRequestType setExpressCheckoutRequest = new SetExpressCheckoutRequestType(setExpressCheckoutRequestDetails);
        setExpressCheckoutRequest.setVersion("104.0");

        SetExpressCheckoutReq setExpressCheckoutReq = new SetExpressCheckoutReq();
        setExpressCheckoutReq.setSetExpressCheckoutRequest(setExpressCheckoutRequest);

        Map<String, String> sdkConfig = new HashMap<String, String>();
        sdkConfig.put("mode", "sandbox");
        sdkConfig.put("acct1.UserName", "tolosah929_api1.gmail.com");
        sdkConfig.put("acct1.Password", "ZNKHJBXNVL9C4NXJ");
        sdkConfig.put("acct1.Signature", "AjWsTWPqZToOVKDUNiak1LHwFCOzARvVOfBKZ.xHzrhFJyig03ibDX1S");
        PayPalAPIInterfaceServiceService service = new PayPalAPIInterfaceServiceService(sdkConfig);
        SetExpressCheckoutResponseType setExpressCheckoutResponse = service.setExpressCheckout(setExpressCheckoutReq);

        token = setExpressCheckoutResponse.getToken();
        String name = setExpressCheckoutResponse.getVersion();
        System.out.println(token + " uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu " + name + " ddd");

        String link = "https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=" + token;
        System.out.println(link);
        //ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        // externalContext.redirect(link);

        //====================================================================

        return link;
    }

    @GetMapping("/soap-get")
    public String startgetExpressResponse2()
        throws ClientActionRequiredException, SSLConfigurationException, MissingCredentialException, OAuthException, InvalidResponseDataException, InvalidCredentialException, IOException, ParserConfigurationException, HttpErrorException, InterruptedException, SAXException {
        getExpressResponse2("EC-88G01973EF343433U");
        return "GET is started";
    }

    @GetMapping("/soap-do")
    public String startDoExpresResponse()
        throws ClientActionRequiredException, SSLConfigurationException, MissingCredentialException, OAuthException, InvalidResponseDataException, InvalidCredentialException, IOException, ParserConfigurationException, HttpErrorException, InterruptedException, SAXException {
        doExpressResponse(getExpressResponse2("EC-88G01973EF343433U"));
        return "Do class is started";
    }

    public GetExpressCheckoutDetailsResponseDetailsType getExpressResponse2(String token)
        throws ClientActionRequiredException, SSLConfigurationException, MissingCredentialException, OAuthException, InvalidResponseDataException, InvalidCredentialException, IOException, ParserConfigurationException, HttpErrorException, InterruptedException, SAXException {
        System.out.println("B   addr Beeeeeeeeeeeeeeeeet");

        Map<String, String> sdkConfig = new HashMap<String, String>();
        sdkConfig.put("mode", "sandbox");
        sdkConfig.put("acct1.UserName", "tolosah929_api1.gmail.com");
        sdkConfig.put("acct1.Password", "ZNKHJBXNVL9C4NXJ");
        sdkConfig.put("acct1.Signature", "AjWsTWPqZToOVKDUNiak1LHwFCOzARvVOfBKZ.xHzrhFJyig03ibDX1S");
        PayPalAPIInterfaceServiceService service = new PayPalAPIInterfaceServiceService(sdkConfig);

        GetExpressCheckoutDetailsReq getECWrapper = new GetExpressCheckoutDetailsReq();
        // (Required) A timestamped token, the value of which was returned by SetExpressCheckout response.
        // Character length and limitations: 20 single-byte characters
        getECWrapper.setGetExpressCheckoutDetailsRequest(new GetExpressCheckoutDetailsRequestType(token));
        // # API call
        // Invoke the GetExpressCheckoutDetails method in service wrapper object
        GetExpressCheckoutDetailsResponseType getECResponse = service.getExpressCheckoutDetails(getECWrapper);
        getECResponse.getGetExpressCheckoutDetailsResponseDetails();

        System.out.println("B  " + getECResponse.getGetExpressCheckoutDetailsResponseDetails().getToken() + " token Beeeeeeeeeeeeeeeeet");
        System.out.println(
            "B  " + getECResponse.getGetExpressCheckoutDetailsResponseDetails().getPaymentDetails() + " payment detail Beeeeeeeeeeeeeeeeet"
        );
        System.out.println(
            "B  " + getECResponse.getGetExpressCheckoutDetailsResponseDetails().getPayerInfo() + " payer info Beeeeeeeeeeeeeeeeet"
        );
        System.out.println(
            "B  " + getECResponse.getGetExpressCheckoutDetailsResponseDetails().getBillingAddress() + " addr Beeeeeeeeeeeeeeeeet"
        );
        System.out.println(
            "B  " + getECResponse.getGetExpressCheckoutDetailsResponseDetails().getCheckoutStatus() + " status Beeeeeeeeeeeeeeeeet"
        );

        return getECResponse.getGetExpressCheckoutDetailsResponseDetails();
    }

    public String doExpressResponse(GetExpressCheckoutDetailsResponseDetailsType getECResponse)
        throws ClientActionRequiredException, SSLConfigurationException, MissingCredentialException, OAuthException, InvalidResponseDataException, InvalidCredentialException, IOException, ParserConfigurationException, HttpErrorException, InterruptedException, SAXException {
        System.out.println("rrr  rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");

        Map<String, String> sdkConfig = new HashMap<String, String>();
        sdkConfig.put("mode", "sandbox");
        sdkConfig.put("acct1.UserName", "tolosah929_api1.gmail.com");
        sdkConfig.put("acct1.Password", "ZNKHJBXNVL9C4NXJ");
        sdkConfig.put("acct1.Signature", "AjWsTWPqZToOVKDUNiak1LHwFCOzARvVOfBKZ.xHzrhFJyig03ibDX1S");
        PayPalAPIInterfaceServiceService service = new PayPalAPIInterfaceServiceService(sdkConfig);

        // Create request object
        DoExpressCheckoutPaymentRequestType expressrequest = new DoExpressCheckoutPaymentRequestType();
        DoExpressCheckoutPaymentRequestDetailsType requestDetails = new DoExpressCheckoutPaymentRequestDetailsType();
        expressrequest.setDoExpressCheckoutPaymentRequestDetails(requestDetails);

        requestDetails.setPaymentDetails(getECResponse.getPaymentDetails());
        // (Required) The timestamped token value that was returned in the SetExpressCheckout response and passed in the GetExpressCheckoutDetails request.
        requestDetails.setToken(getECResponse.getToken());
        // (Required) Unique PayPal buyer account identification number as returned in the GetExpressCheckoutDetails response
        requestDetails.setPayerID(getECResponse.getPayerInfo().getPayerID());
        // (Required) How you want to obtain payment. It is one of the following values:
        // * Authorization – This payment is a basic authorization subject to settlement with PayPal Authorization and Capture.
        // * Order – This payment is an order authorization subject to settlement with PayPal Authorization and Capture.
        // * Sale – This is a final sale for which you are requesting payment.
        // Note: You cannot set this value to Sale in the SetExpressCheckout request and then change this value to Authorization in the DoExpressCheckoutPayment request.
        requestDetails.setPaymentAction(PaymentActionCodeType.SALE);
        // Invoke the API
        DoExpressCheckoutPaymentReq expresswrapper = new DoExpressCheckoutPaymentReq();
        expresswrapper.setDoExpressCheckoutPaymentRequest(expressrequest);
        // # API call
        // Invoke the DoExpressCheckoutPayment method in service wrapper object
        DoExpressCheckoutPaymentResponseType doECResponse = service.doExpressCheckoutPayment(expresswrapper);
        // Check for API return status

        //        if (doECResponse.getAck().equals(AckCodeType.FAILURE) ||
        //            (doECResponse.getErrors() != null && doECResponse.getErrors().Count > 0)) {
        //            return "faild";
        //        } else {
        //            TempData["TransactionResult"] = "Transaction ID:" + doECResponse.DoExpressCheckoutPaymentResponseDetails.PaymentInfo[0].TransactionID + Environment.NewLine + "Payment status" + doECResponse.DoExpressCheckoutPaymentResponseDetails.PaymentInfo[0].PaymentStatus.Value.ToString();
        //            return RedirectToAction("SaveCustomer", "SignupOrLogin");
        //        }
        DoExpressCheckoutPaymentResponseDetailsType details = doECResponse.getDoExpressCheckoutPaymentResponseDetails();
        List<PaymentInfoType> paymentsList = details.getPaymentInfo();
        PaymentInfoType info = paymentsList.get(0);
        String soapTransactionID = info.getTransactionID();
        System.out.println("Transaction Id" + info.getTransactionID() + "kkkkkk");

        System.out.println("rrr " + doECResponse.getAck() + " rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
        return soapTransactionID;
    }
}
