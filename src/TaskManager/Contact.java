package TaskManager;

import java.io.*;
import java.util.ArrayList;

public class Contact implements Serializable {
    private String name;
    private String phone;
    private String email;

    public Contact() {
        this.name = "Без контакта";
        this.phone = "";
        this.email = "";
    }

    public Contact(String name) {
        this.name = name;
    }

    public Contact(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public Contact(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        name = newName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String newPhone) {
        phone = newPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String newEmail) {
        email = newEmail;
    }

    public static void save(ArrayList<Contact> contacts, String  fileName) {
        try {
            ObjectOutputStream ous = new ObjectOutputStream(new FileOutputStream(fileName));
            ous.writeObject(contacts);
            ous.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Contact> load(String fileName) {
        ArrayList<Contact> contacts= new ArrayList<>();
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName));
            contacts = (ArrayList<Contact>) ois.readObject();
            ois.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return contacts;
    }

    public String toString(){
        String res = "|";
        res = res + this.name + ";  " + this.phone + ";  " + this.email + "|";
        return res;
    }
}
