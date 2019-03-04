package TaskManager;

import javax.swing.*;
import java.awt.event.*;

public class AlertForm extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel labelName;
    private JLabel labelDescription;
    private JLabel labelDate;
    private JLabel labelContact;
    private TaskList taskList = new TaskList();
    private RecreateForm recreateForm = new RecreateForm(- 1);
    private int index;


    public AlertForm(int index) {
        if (index > - 1) {
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
            labelDate.setText("Дата события: " + taskList.getTask(index).getDate() + ". Время: " + taskList.getTask(index).getTime());
            labelContact.setText("Привязанные контакты: " + taskList.getTask(index).getContact());
        }
    }

    private void onOK() {
        taskList.getTask(index).setCompleted(true);
        TaskListManager.saveTaskList(taskList, "File1");
        // add your code here
        dispose();
    }

    private void onCancel() {
        recreateForm = new RecreateForm(index);
        recreateForm.setResizable(false);
        recreateForm.setTitle("Отложить");
        recreateForm.pack();
        recreateForm.setVisible(true);
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        AlertForm dialog = new AlertForm(0);
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
