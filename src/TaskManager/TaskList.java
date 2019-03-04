package TaskManager;

import java.io.Serializable;
import java.util.ArrayList;

public class TaskList implements Serializable {

    private ArrayList<Task> taskList;

    public TaskList() {
        taskList = new ArrayList<>();
    }

    public void addTask(String name, String description, String date, String time) {
        Task task = new Task(name, description, date, time);
        taskList.add(task);
    }

    public void addTask(String name, String description, String  date, String time, Contact contact) {
        Task task = new Task(name, description, date, time, contact);
        taskList.add(task);
    }

    public void removeTask(Task task) {
        taskList.remove(task);
    }

    public void removeTask(int i) {
        taskList.remove(i);
    }

    public Task getTask(int i){
        return taskList.get(i);
    }

    public String writeTask(int i){
        return taskList.get(i).toString();
    }

    public int getLength() {
        return taskList.size();
    }

}
