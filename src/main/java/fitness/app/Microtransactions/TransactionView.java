package fitness.app.Microtransactions;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.components.FlatButton;
import com.formdev.flatlaf.extras.components.FlatLabel;
import com.formdev.flatlaf.extras.components.FlatPasswordField;
import com.formdev.flatlaf.extras.components.FlatTextField;
import fitness.app.Objects.Account;
import fitness.app.Objects.CreditCard;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;

public class TransactionView extends JPanel {
    private static JPanel mainPanel;
    TransactionView (Account acc) {
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
        expirField.setPlaceholderText("mm/yr");
        JLabel label5 = new JLabel("Expir. Date");

        FlatButton submitButton = new FlatButton();
        submitButton.setText("Submit Details");
        submitButton.putClientProperty(FlatClientProperties.STYLE, "background:lighten(@background,10%);");

        purchaseMenu.add(title);

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

                acc.setCard(newCard);

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
/*
    static class CreditCardDocumentFilter extends DocumentFilter {
        private static final int MAX_DIGITS = 16;

        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            if (string != null) {
                replace(fb, offset, 0, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            Document doc = fb.getDocument();
            StringBuilder currentText = new StringBuilder(doc.getText(0, doc.getLength()));
            currentText.replace(offset, offset + length, text);

            // Remove all non-digits
            String digitsOnly = currentText.toString().replaceAll("\\D", "");
            if (digitsOnly.length() > MAX_DIGITS) {
                digitsOnly = digitsOnly.substring(0, MAX_DIGITS);
            }

            // Format into groups of 4
            StringBuilder formatted = new StringBuilder();
            for (int i = 0; i < digitsOnly.length(); i++) {
                if (i > 0 && i % 4 == 0) {
                    formatted.append(" ");
                }
                formatted.append(digitsOnly.charAt(i));
            }

            fb.replace(0, doc.getLength(), formatted.toString(), attrs);
        }

        @Override
        public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
            replace(fb, offset, length, "", null);
        }
    }

 */

}