package TaskManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainForm {
    private JPanel panel1;
    private JButton buttonPrint;
    private DefaultListModel listModel = new DefaultListModel();
    //private JList jList1;
    private JButton buttonAddTask;
    private JButton buttonWrite;
    private JButton buttonAddContact;
    private JLabel labelDate;
    private JLabel labelTime;
    private JButton buttonRemove;
    private JButton buttonPrintDescription;
    private JList jList1;
    private JLabel labelResult1;
    private TaskList taskList = TaskListManager.loadTaskList("File1");
    private AddTask addTask = new AddTask();
    private AddContact addContact = new AddContact();
    private AlertForm alertForm = new AlertForm(- 1);
    private RemoveTask removeTask = new RemoveTask(- 1);
    private boolean anyButtonWasClicked = false;
    Timer timer = new Timer();

    public JPanel getPanel1(){
        return panel1;
    }

    public MainForm() {

        taskList = TaskListManager.loadTaskList("File1");

        buttonPrint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                taskList = TaskListManager.loadTaskList("File1");
                listModel.removeAllElements();
                jList1.setModel(listModel);
                listModel.addElement("           Название      ;     Дата    ;  Время  ;  Контакты  ");
                for (int i = 0; i < taskList.getLength(); i++) {
                    if (!taskList.getTask(i).isCompleted())
                        listModel.addElement(taskList.writeTask(i));
                }
            }
        });
        buttonAddTask.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                anyButtonWasClicked = true;
                addTask = new AddTask();
                addTask.setTitle("Добавление задачи");
                addTask.setResizable(false);
                addTask.pack();
                addTask.setVisible(true);
            }
        });
        buttonWrite.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                taskList = TaskListManager.loadTaskList("File1");
                listModel.removeAllElements();
                jList1.setModel(listModel);
                listModel.addElement("          Название     ;     Дата    ;  Время  ;  Контакты  ");
                //listModel.addElement("Первое задание ;  26.12.2018;  22:22;  |Третий;  +79315648998;  test-Again@google.com|")
                for (int i = 0; i < taskList.getLength(); i++) {
                    if (taskList.getTask(i).isCompleted())
                        listModel.addElement(taskList.writeTask(i));
                }
            }
        });
        buttonAddContact.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addContact = new AddContact();
                addContact.setResizable(false);
                addContact.setTitle("Добавление контакта");
                addContact.pack();
                addContact.setVisible(true);

            }
        });
        buttonRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                anyButtonWasClicked = true;
                if (jList1.getSelectedIndex() > -1) {
                    removeTask = new RemoveTask(jList1.getSelectedIndex() - 1);
                    removeTask.setResizable(false);
                    removeTask.setTitle("Удаление задачи");
                    removeTask.pack();
                    removeTask.setVisible(true);
                }
                else {
                    JOptionPane.showMessageDialog(null, "Выберите задачу для удаления");
                }
            }
        });
        buttonPrintDescription.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jList1.getSelectedIndex() > -1) {
                    JOptionPane.showMessageDialog(null, taskList.getTask(jList1.getSelectedIndex() - 1).getDescription());
                }
                else {
                    JOptionPane.showMessageDialog(null, "Выберите задачу для вывода описания");
                }
            }
        });

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (anyButtonWasClicked) {
                    taskList = TaskListManager.loadTaskList("File1");
                    anyButtonWasClicked = false;
                }
                labelDate.setText(new SimpleDateFormat("E dd.MM.yyyy").format(new Date()));
                labelTime.setText(new SimpleDateFormat("HH:mm:ss").format(new Date()));
                if (taskList.getLength() > 0) {
                    for (int i = 0; i < taskList.getLength(); i++) {
                        if ((!taskList.getTask(i).isCompleted()) && (taskList.getTask(i).getDate().equals(new SimpleDateFormat("dd.MM.yyyy").format(new Date()))) && (taskList.getTask(i).getTime().equals(new SimpleDateFormat("HH:mm").format(new Date())))) {
                            anyButtonWasClicked = true;
                            alertForm = new AlertForm(i);
                            alertForm.setTitle("Оповещение");
                            alertForm.setResizable(false);
                            alertForm.pack();
                            alertForm.setVisible(true);
                        }
                    }
                }
            }
        }, 0, 1000);
    }
}
