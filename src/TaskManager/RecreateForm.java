package TaskManager;

import javax.swing.*;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class RecreateForm extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textFieldDate;
    private JTextField textFieldTime;
    private JLabel labelName;
    private JLabel labelDescription;
    private JLabel labelDate;
    private JLabel labelTime;
    private JLabel labelDateError;
    private JLabel labelTimeError;
    private int index;
    private TaskList taskList = new TaskList();

    public RecreateForm(int index) {
        if (index > -1) {
            this.index = index;
            taskList = TaskListManager.loadTaskList("File1");
            setContentPane(contentPane);
            setModal(true);
            getRootPane().setDefaultButton(buttonOK);

            buttonOK.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    onOK();
                }
            });

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

            labelName.setText("Название: " + taskList.getTask(index).getName());
            labelDescription.setText("Описание: " + taskList.getTask(index).getDescription());
            labelDate.setText("Дата: " + taskList.getTask(index).getDate());
            labelTime.setText("Время: " + taskList.getTask(index).getTime());
            textFieldDate.setText(taskList.getTask(index).getDate());
            textFieldTime.setText(taskList.getTask(index).getTime());
        }
    }

    private void onOK() {
        labelDateError.setText("");
        labelTimeError.setText("");
        boolean flagDataTime = true;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy") {{
            setLenient(false);
        }};
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm") {{
            setLenient(false);
        }};
        try {
            dateFormat.parse(textFieldDate.getText());
        }
        catch (ParseException e) {
            labelDateError.setText("Неверный формат");
            flagDataTime = false;
        }
        try {
            timeFormat.parse(textFieldTime.getText());
        }
        catch (ParseException e) {
            labelTimeError.setText("Неверный формат");
            flagDataTime = false;
        }
        if (flagDataTime) {
            taskList.getTask(index).setDate(textFieldDate.getText());
            taskList.getTask(index).setTime(textFieldTime.getText());
            TaskListManager.saveTaskList(taskList, "File1");
            dispose();
        }
        // add your code here
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        RecreateForm dialog = new RecreateForm(- 1);
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
