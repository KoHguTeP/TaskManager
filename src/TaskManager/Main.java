package TaskManager;

import javax.swing.*;

public class Main {

    public static void main(String[] args)
    {
        JFrame jFrame = new JFrame("Планировщик задач");
        jFrame.setContentPane(new MainForm().getPanel1());
        jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setVisible(true);
    }
}