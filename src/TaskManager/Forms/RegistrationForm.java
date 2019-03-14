package TaskManager.Forms;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RegistrationForm extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textFieldLogin;
    private JTextField textFieldPassword;
    private JTextField textFieldPassword2;

    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Socket socket;

    public RegistrationForm() {
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
    }

    private void onOK() {
        // add your code here
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            Document doc = factory.newDocumentBuilder().newDocument();


            if (textFieldPassword.getText().equals(textFieldPassword2.getText())) {
                //создание DOM xml
                Element root = doc.createElement("action");
                root.setAttribute("id", "registration");
                root.setAttribute("login", textFieldLogin.getText());
                root.setAttribute("password", textFieldPassword.getText());
                doc.appendChild(root);
                try {
                    try {
                        //установка соединения
                        socket = new Socket("Localhost", 1024);

                        out = new ObjectOutputStream(socket.getOutputStream());
                        in = new ObjectInputStream(socket.getInputStream());

                        //отправка данных
                        out.writeObject(doc);
                        out.flush();
                        boolean resp = (boolean) in.readObject();

                        //данные получены
                        if (resp){
                            JOptionPane.showMessageDialog(null, "Вы успешно зарегистрировались");
                            dispose();
                        }
                        else {
                            JOptionPane.showMessageDialog(null, "Такой пользователь уже существует");
                        }
                    } finally {
                        socket.close();
                        in.close();
                        out.close();
                    }
                } catch (IOException er) {
                    er.printStackTrace();
                }
                catch (ClassNotFoundException er) {
                    er.printStackTrace();
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "Пароли не совпадают");
            }

        } catch (ParserConfigurationException er) {
            System.out.println(er.fillInStackTrace());
        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        RegistrationForm dialog = new RegistrationForm();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
