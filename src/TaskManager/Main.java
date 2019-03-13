package TaskManager;

import TaskManager.Forms.LoginForm;
import TaskManager.Forms.MainForm;

import javax.swing.*;

public class Main {

    public static void main(String[] args)
    {
        /* JFrame jFrame = new JFrame("Планировщик задач");
        jFrame.setContentPane(new MainForm().getPanel1());
        jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setVisible(true);*/

        JFrame jFrame = new JFrame("LOGIN");
        jFrame.setContentPane(new LoginForm().getPanel1());
        jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setVisible(true);
    }
}
