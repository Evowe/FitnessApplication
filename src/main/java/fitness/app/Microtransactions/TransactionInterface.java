package fitness.app.Microtransactions;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.components.FlatButton;
import com.formdev.flatlaf.extras.components.FlatLabel;
import com.formdev.flatlaf.extras.components.FlatPasswordField;
import com.formdev.flatlaf.extras.components.FlatTextField;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;

public class TransactionInterface extends JPanel {
    private static JPanel mainPanel;
    TransactionInterface () {
        mainPanel = new JPanel(new GridLayout(1,2));

        JPanel purchaseMenu = new JPanel(new MigLayout("wrap,fillx,insets 30", "fill,275"));
        purchaseMenu.putClientProperty(FlatClientProperties.STYLE, "arc:20;" + "background:lighten(@background,5%)");

        FlatLabel title = new FlatLabel();
        title.setText("Please Enter Card Information");
        title.putClientProperty(FlatClientProperties.STYLE, "font:bold +6");

        // Create and add multiple text boxes to the panel
        FlatTextField cardNumberField = new FlatTextField();
        cardNumberField.setPlaceholderText("xxxx xxxx xxxx xxxx");
        JLabel label1 = new JLabel("CardNumber");

        FlatTextField cardHolderField = new FlatTextField();
        cardHolderField.setPlaceholderText("Name");
        JLabel label2 = new JLabel("CardHolder");

        FlatTextField zipField = new FlatTextField();
        zipField.setPlaceholderText("xxxxx");
        JLabel label3 = new JLabel("Zip Code");

        FlatPasswordField cvvField = new FlatPasswordField();
        cvvField.setPlaceholderText("Enter cvv");
        cvvField.putClientProperty(FlatClientProperties.STYLE, "showRevealButton:true");
        JLabel label4 = new JLabel("CVV");

        FlatTextField expirField = new FlatTextField();
        expirField.setPlaceholderText("mm/dd");
        JLabel label5 = new JLabel("Expir. Date");

        FlatButton submitButton = new FlatButton();
        submitButton.setText("Submit Details");
        submitButton.putClientProperty(FlatClientProperties.STYLE, "background:lighten(@background,10%);");

        purchaseMenu.add(label1);
        purchaseMenu.add(cardNumberField);
        purchaseMenu.add(label2);
        purchaseMenu.add(cardHolderField);
        purchaseMenu.add(label3);
        purchaseMenu.add(zipField);
        purchaseMenu.add(label4);
        purchaseMenu.add(cvvField);
        purchaseMenu.add(label5);
        purchaseMenu.add(expirField);

        purchaseMenu.add(submitButton);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreditCard newCard = new CreditCard();
                newCard.setCardNumber(cardNumberField.getText());
                newCard.setCardHolder(cardHolderField.getText());
                newCard.setZipCode(zipField.getText());
                newCard.setCvv(cvvField.getText());
                newCard.setExpiryDate(expirField.getText());

                if (newCard.CardValidation(newCard)) {
                    String csvFile = "example.csv";
                    try (FileWriter writer = new FileWriter(csvFile, true)) {
                        writer.append(cardHolderField.getText() + "," +
                                cardNumberField.getText() + "," +
                                zipField.getText() + "," +
                                cvvField.getText() + "," +
                                expirField.getText() + "\n");
                    } catch (IOException d) {
                        d.printStackTrace();
                    }
                    System.out.println(newCard.CardValidation(newCard));
                }
            }
        });

        mainPanel.add(purchaseMenu);
    }

    public static JPanel get() {return mainPanel;}

}