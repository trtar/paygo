package com.tariku.paygov;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ReadUserData {

    String filename = "userdata.bin";
    UserData userData = new UserData();

    public void saveData(String cik, String ccc, String amount, String name, String email, String phone) {
        userData.cik = cik;
        userData.ccc = ccc;
        userData.amount = amount;
        userData.name = name;
        userData.email = email;
        userData.phone = phone;

        try {
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(filename));
            os.writeObject(userData);
            os.close();
            System.out.println("22222222 file written22222222");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String getData() {
        String amount = "";
        try {
            ObjectInputStream is = new ObjectInputStream(new FileInputStream(filename));
            UserData data = (UserData) is.readObject();
            System.out.println("22222222 file read 22222222" + data.name + " " + data.amount);
            amount = data.amount;
            is.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return amount;
    }
}
