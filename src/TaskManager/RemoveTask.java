package TaskManager;

import javax.swing.*;
import java.awt.event.*;

public class RemoveTask extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel labelName;
    private JLabel labelDescription;
    private TaskList taskList = new TaskList();
    private int index;

    public JPanel getPanel1(){
        return contentPane;
    }

    public RemoveTask(int index) {
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
            labelName.setText(taskList.getTask(index).getName());
            labelDescription.setText(taskList.getTask(index).getDescription());
        }
    }

    private void onOK() {
        TaskListManager.removeTask(taskList, index);
        TaskListManager.saveTaskList(taskList, "File1");
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        RemoveTask dialog = new RemoveTask(- 1);
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
