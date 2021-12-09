package com.tariku.paygov;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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

    public String getData() {
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

        return trasactionId;
    }
}
