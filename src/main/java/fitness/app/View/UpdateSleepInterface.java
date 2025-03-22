package fitness.app.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JFrame;
import javax.swing.JPanel;
//import org.jdesktop.swingx.JXDatePicker;
//
//public class DatePickerExample extends JPanel {
//
//    public static void main(String[] args) {
//        JFrame frame = new JFrame("JXPicker Example");
//        JPanel panel = new JPanel();
//
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setBounds(400, 400, 250, 100);
//
//        JXDatePicker picker = new JXDatePicker();
//        picker.setDate(Calendar.getInstance().getTime());
//        picker.setFormats(new SimpleDateFormat("dd.MM.yyyy"));
//
//        panel.add(picker);
//        frame.getContentPane().add(panel);
//
//        frame.setVisible(true);
//    }
//}
public class UpdateSleepInterface {
    UpdateSleepInterface ( JFrame frame) {
        // Create a new frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Create a panel to hold the text boxes
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2)); // Use a grid layout with one column

        // Create and add multiple text boxes to the panel
        JLabel label1 = new JLabel("Choose Day");
        JTextField textBox1 = new JTextField("dd/MM/yyyy");
        JLabel label2 = new JLabel("Enter Sleep");
        JTextField textBox2 = new JTextField("Sleep");

//        JLabel label3 = new JLabel("Zip Code");
//        JTextField textBox3 = new JTextField("xxxxx");
//        JLabel label4 = new JLabel("CVV");
//        JTextField textBox4 = new JTextField("xxx");

        JButton button1 = new JButton("Submit");

        panel.add(label1);
        panel.add(textBox1);
        panel.add(label2);
        panel.add(textBox2);
//        panel.add(label3);
//        panel.add(textBox3);
//        panel.add(label4);
//        panel.add(textBox4);

        panel.add(button1);

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Boolean allValid = true;
                if (textBox1.getText().matches("\\d{4} \\d{4} \\d{4} \\d{4}")) {
                    System.out.println(textBox1.getText());
                }
                else {
                    JOptionPane.showMessageDialog(frame, "Enter A Valid Date");
                    allValid = false;
                }
                if (allValid) {
                    String csvFile = "example.csv";
                    try (FileWriter writer = new FileWriter(csvFile, true)) {
                        writer.append(textBox2.getText() + "," +
                                textBox1.getText() + ",");
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