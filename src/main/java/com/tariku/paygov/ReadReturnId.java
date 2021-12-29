package com.tariku.paygov;

import com.paypal.exception.ClientActionRequiredException;
import com.paypal.exception.HttpErrorException;
import com.paypal.exception.InvalidCredentialException;
import com.paypal.exception.InvalidResponseDataException;
import com.paypal.exception.MissingCredentialException;
import com.paypal.exception.SSLConfigurationException;
import com.paypal.sdk.exceptions.OAuthException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

public class ReadReturnId {

    String filename = "returnid.bin";
    ReturnId returnId = new ReturnId();

    public void saveData(String chackoutId) {
        returnId.transactionId = chackoutId;

        try {
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(filename));
            os.writeObject(returnId);
            os.close();
            System.out.println("22222222 file written22222222");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String getData()
        throws ClientActionRequiredException, SSLConfigurationException, MissingCredentialException, OAuthException, InvalidResponseDataException, InvalidCredentialException, HttpErrorException, IOException, ParserConfigurationException, InterruptedException, SAXException {
        String trasactionId = "";
        try {
            ObjectInputStream is = new ObjectInputStream(new FileInputStream(filename));
            ReturnId data = (ReturnId) is.readObject();
            System.out.println("22222222 file read 22222222" + data.transactionId + " ");
            trasactionId = data.transactionId;
            is.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        PayPalSoap payPalSoap = new PayPalSoap();
        payPalSoap.getExpressResponse2(trasactionId);
        payPalSoap.doExpressResponse(payPalSoap.getExpressResponse2(trasactionId));

        return trasactionId;
    }
}
