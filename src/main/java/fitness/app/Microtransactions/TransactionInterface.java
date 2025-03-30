package fitness.app.Microtransactions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;

public class TransactionInterface {
    TransactionInterface ( JFrame frame) {
        // Create a new frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // Create a panel to hold the text boxes
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2)); // Use a grid layout with one column

        // Create and add multiple text boxes to the panel
        JLabel label1 = new JLabel("CardNumber");
        JTextField textBox1 = new JTextField("xxxx xxxx xxxx xxxx");
        JLabel label2 = new JLabel("CardHolder");
        JTextField textBox2 = new JTextField("Name on Card");
        JLabel label3 = new JLabel("Zip Code");
        JTextField textBox3 = new JTextField("xxxxx");
        JLabel label4 = new JLabel("CVV");
        JTextField textBox4 = new JTextField("xxx");
        JLabel label5 = new JLabel("Expir. Date");
        JTextField textBox5 = new JTextField("xx/xx");

        JButton button1 = new JButton("Submit");

        panel.add(label1);
        panel.add(textBox1);
        panel.add(label2);
        panel.add(textBox2);
        panel.add(label3);
        panel.add(textBox3);
        panel.add(label4);
        panel.add(textBox4);
        panel.add(label5);
        panel.add(textBox5);

        panel.add(button1);

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreditCard newCard = new CreditCard();
                newCard.setCardNumber(textBox1.getText());
                newCard.setCardHolder(textBox2.getText());
                newCard.setZipCode(textBox3.getText());
                newCard.setCvv(textBox4.getText());
                newCard.setExpiryDate(textBox5.getText());

                if (newCard.CardValidation(newCard)) {
                    String csvFile = "example.csv";
                    try (FileWriter writer = new FileWriter(csvFile, true)) {
                        writer.append(textBox2.getText() + "," +
                                textBox1.getText() + "," +
                                textBox3.getText() + "," +
                                textBox4.getText() + "," +
                                textBox5.getText() + "\n");
                    } catch (IOException d) {
                        d.printStackTrace();
                    }
                    System.out.println(newCard.CardValidation(newCard));
                }
            }
        });

        // Add the panel to the frame
        frame.add(panel);

        // Make the frame visible
        frame.pack();

        frame.setVisible(true);
    }
}