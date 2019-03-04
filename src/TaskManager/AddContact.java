package TaskManager;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class AddContact extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textFieldName;
    private JTextField textFieldPhone;
    private JTextField textFieldEmail;
    private JComboBox comboBoxEndOfEmail;
    private JLabel labelEmailError;
    private JLabel labelPhoneError;
    private ArrayList<Contact> contacts = new ArrayList<>();
    String[] mails = {"@mail.ru", "@yandex.ru", "@google.com"};

    public AddContact() {
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
        for (int i = 0; i < mails.length; i++){
            comboBoxEndOfEmail.addItem(mails[i]);
        }
    }

    private void onOK() {
        // add your code here
        boolean flagPhone = false;
        boolean flagEmail = true;
        int count = 0;
        contacts = Contact.load("Contacts");
        if (textFieldPhone.getText().length() == 11) {
            if (textFieldPhone.getText().toCharArray()[0] == "8".toCharArray()[0]) {
                count = 0;
                for (int i = 1; i < 11; i++) {
                    if (Character.isDigit(textFieldPhone.getText().toCharArray()[i])) {
                        count++;
                    }
                }
                if (count == 10) {
                    flagPhone = true;
                }
            }
        } else {
            if (textFieldPhone.getText().length() == 12) {
                if ((textFieldPhone.getText().toCharArray()[0] == "+".toCharArray()[0]) && (textFieldPhone.getText().toCharArray()[1] == "7".toCharArray()[0])) {
                    count = 0;
                    for (int i = 2; i < 12; i++) {
                        if (Character.isDigit(textFieldPhone.getText().toCharArray()[i])) {
                            count++;
                        }
                    }
                    if (count == 10) {
                        flagPhone = true;
                    }
                }
            }
        }
        char[] badCharacters = {'!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '+', '=', '?', ':', ';', '№', '"', '`', '~', '<', '>', '/', ','};
        for (int i = 0; i < badCharacters.length; i++) {
            if (textFieldEmail.getText().contains(Character.toString(badCharacters[i]))) {
                flagEmail = false;
                i = badCharacters.length;
            }
        }
        if (flagEmail && flagPhone) {
            Contact contact = new Contact(textFieldName.getText(), textFieldPhone.getText(), textFieldEmail.getText() + mails[comboBoxEndOfEmail.getSelectedIndex() - 1]);
            contacts.add(contact);
            Contact.save(contacts, "Contacts");
            dispose();
        } else {
            if (!flagPhone) labelPhoneError.setText("Ошибка ввода");
            else labelPhoneError.setText("");
            if (!flagEmail) labelEmailError.setText("Недопустимые символы");
            else labelEmailError.setText("");
        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        AddContact dialog = new AddContact();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
