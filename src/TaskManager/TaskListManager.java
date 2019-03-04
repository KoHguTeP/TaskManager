package TaskManager;

import java.io.*;

public class TaskListManager {
    public static void addTask(TaskList list, String name, String description, String date, String time, Contact contact)  {
        list.addTask(name, description, date, time, contact);
    }

    public static void removeTask(TaskList list, Task task) {
        list.removeTask(task);
    }

    public static void removeTask(TaskList taskList, int i) {
        taskList.removeTask(i);
    }

    public static void saveTaskList(TaskList list, String fileName) {
        try {
            ObjectOutputStream ous = new ObjectOutputStream(new FileOutputStream(fileName));
            ous.writeObject(list);
            ous.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static TaskList loadTaskList(String fileName) {
        TaskList taskList = new TaskList();
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName));
            taskList = (TaskList) ois.readObject();
            ois.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return taskList;
    }

    public static void writeTaskList(TaskList taskList, String fileName) {
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            for (int i = 0; i < taskList.getLength(); i++){
                printWriter.println(taskList.writeTask(i));
            }
            printWriter.flush();
            fileWriter.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
