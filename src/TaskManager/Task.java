package TaskManager;

import java.io.Serializable;

public class Task implements Serializable {
    private String name;
    private String description;
    private String date;
    private String time;
    private Contact contact;
    private boolean completed;

    public Task() {
        this.name = "";
        this.description = "";
        this.date = "";//"10.11.12"
        this.time = "";//15.46
        this.contact = new Contact();
        this.completed = false;
    }

    public Task(String name, String description, String date, String time)  {
        this.name = name;
        this.description = description;
        this.date = date;//this.setDate(date);
        this.time = time;
        this.contact = new Contact();
        this.completed = false;
    }

    public Task(String name, String description, String date, String time, Contact contact){
        this.name = name;
        this.description = description;
        this.date = date;
        this.time = time;
        this.contact = contact;
        this.completed = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        name = newName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String newDescription) {
        description = newDescription;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time){
        this.time = time;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Contact getContact() {
        return contact;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean newStatus) {
        completed = newStatus;
    }

    public String toString(){
        String res = "";
        res = res + this.name;
        for (int i = this.name.length(); i < 15; i++){
            res = res + " ";
        }
        res = res + ";  "  + this.date + ";  " + this.time + ";  " + this.contact;
        return res;
    }

}

