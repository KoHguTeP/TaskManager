package TaskManager.Forms;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.UnknownHostException;

public class LoginForm {
    private JTextField textField1;
    private JTextField textField2;
    private JButton buttonLogin;
    private JButton buttonRegistration;
    private JPanel panel1;

    private static final String LOGIN = "";
    private static final String PASSWORD = "";

    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Socket socket;

    private Document document;

    public JPanel getPanel1() {
        return panel1;
    }

    public LoginForm() {

        buttonLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                    factory.setNamespaceAware(true);
                    Document doc = factory.newDocumentBuilder().newDocument();

                    //создание DOM xml
                    Element root = doc.createElement("action");
                    root.setAttribute("id", "login");
                    root.setAttribute("login", textField1.getText());
                    root.setAttribute("password", textField2.getText());
                    doc.appendChild(root);
                    try {
                        try {
                            socket = new Socket("Localhost", 1024);

                            System.out.println("Connection established");

                            out = new ObjectOutputStream(socket.getOutputStream());
                            in = new ObjectInputStream(socket.getInputStream());
                            out.writeObject(doc);
                            out.flush();

                            System.out.println("Received response from server");

                            JOptionPane.showMessageDialog(null, in.readObject());
                        }
                        finally {
                            socket.close();
                            in.close();
                            out.close();
                        }
                    }
                    catch (IOException er) {
                        er.printStackTrace();
                    } catch (ClassNotFoundException er) {
                        er.printStackTrace();
                    }
                    System.out.println("done");

                } catch (ParserConfigurationException er) {
                    System.out.println(er.fillInStackTrace());
                }
            }
        });
    }
}
