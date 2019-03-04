package TaskManager;

import javax.swing.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddTask extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textFieldName;
    private JTextField textFieldDate;
    private JTextField textFieldDescription;
    private JComboBox comboBoxContacts;
    private JTextField textFieldTime;
    private JLabel labelDescriptionError;
    private JLabel labelDateError;
    private JLabel labelTimeError;
    private TaskList taskList = new TaskList();


    public AddTask() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        taskList = TaskListManager.loadTaskList("File1");

        ArrayList<Contact> contacts = new ArrayList<>();

        textFieldDate.setText(new SimpleDateFormat("dd.MM.yyyy").format(new Date()));
        textFieldTime.setText(new SimpleDateFormat("HH:mm").format(new Date()));

        try {
        FileInputStream fileInputStream = new FileInputStream("Contacts");
        ObjectInputStream out = new ObjectInputStream(fileInputStream);
        contacts = Contact.load("Contacts");
        out.close();
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }

        comboBoxContacts.addItem(new Contact().getName());
        for (int i = 0; i < contacts.size(); i++){
            comboBoxContacts.addItem(contacts.get(i).getName());
        }

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
            }) ;

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        // add your code here
        boolean flagDateTime = true;
        labelDateError.setText("");
        labelTimeError.setText("");
        ArrayList<Contact> contacts = new ArrayList<>();
        try {
            FileInputStream fileInputStream = new FileInputStream("Contacts");
            ObjectInputStream out = new ObjectInputStream(fileInputStream);
            contacts = Contact.load("Contacts");
            out.close();
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy") {{ setLenient(false); }};
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm") {{ setLenient(false); }};
        try {
            dateFormat.parse(textFieldDate.getText());
        }
        catch (ParseException e){
            labelDateError.setText("Неверный формат");
            flagDateTime = false;
        }
        try {
            timeFormat.parse(textFieldTime.getText());
        }
        catch (ParseException e){
            labelTimeError.setText("Неверный формат");
            flagDateTime = false;
        }
        if (flagDateTime) {
            TaskListManager.addTask(taskList, textFieldName.getText(), textFieldDescription.getText(), textFieldDate.getText(), textFieldTime.getText(), contacts.get(comboBoxContacts.getSelectedIndex() - 1));
            TaskListManager.saveTaskList(taskList, "File1");
            dispose();
        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        AddTask dialog = new AddTask();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
