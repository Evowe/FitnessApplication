package fitness.app.View;

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

        JButton button1 = new JButton("Submit");

        panel.add(label1);
        panel.add(textBox1);
        panel.add(label2);
        panel.add(textBox2);
        panel.add(label3);
        panel.add(textBox3);
        panel.add(label4);
        panel.add(textBox4);

        panel.add(button1);

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Boolean allValid = true;
                if (textBox1.getText().matches("\\d{4} \\d{4} \\d{4} \\d{4}")) {
                    System.out.println(textBox1.getText());
                }
                else {
                    JOptionPane.showMessageDialog(frame, "Invalid Card Number");
                    allValid = false;
                }
                if (textBox3.getText().matches("\\d{5}")) {
                    System.out.println(textBox3.getText());
                }
                else {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid Zip Code");
                    allValid = false;
                }
                if (textBox4.getText().matches("\\d{3}")) {
                    System.out.println(textBox4.getText());
                }
                else {
                    JOptionPane.showMessageDialog(frame, "Invalid CVV");
                    allValid = false;
                }
                if (allValid) {
                    String csvFile = "example.csv";
                    try (FileWriter writer = new FileWriter(csvFile, true)) {
                        writer.append(textBox2.getText() + "," +
                                textBox1.getText() + "," +
                                textBox3.getText() + "," +
                                textBox4.getText() + "\n");
                    } catch (IOException d) {
                        d.printStackTrace();
                    }

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