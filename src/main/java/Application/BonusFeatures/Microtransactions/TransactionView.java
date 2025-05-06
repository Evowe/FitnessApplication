package Application.BonusFeatures.Microtransactions;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.components.FlatButton;
import com.formdev.flatlaf.extras.components.FlatLabel;
import com.formdev.flatlaf.extras.components.FlatPasswordField;
import com.formdev.flatlaf.extras.components.FlatTextField;
import Application.Utility.Objects.Account;
import Application.Utility.Objects.CreditCard;
import Application.BonusFeatures.CurrencyShop.currencyShopModel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.Year;
import java.util.Random;
import java.util.stream.IntStream;

public class TransactionView extends JPanel {
    public static JPanel mainPanel;
    private JSlider tipSlider;
    private JCheckBox agreeTipCheckbox;
    private JDialog parentDialog;
    private currencyShopModel shopModel;
    private String[] tipMessages = {
            "Developers need coffee too!",
            "Help fund my next keyboard!",
            "Every tip brings you better features!",
            "Tip or this app will self-destruct... just kidding!",
            "Your tip helps keep bugs away!",
            "Tip the developer, because debugging at 3 AM deserves compensation!",
            "Feed a developer, improve an app!",
            "Your generosity keeps our servers running and our devs caffeinated!",
            "Tip and receive good karma (and better software)!",
            "Every dollar brings you one step closer to the next cool feature!"
    };

    TransactionView(Account acc) {
        this(acc, null, null);
    }

    TransactionView(Account acc, JDialog dialog, currencyShopModel model) {
        this.parentDialog = dialog;
        this.shopModel = model;

        mainPanel = new JPanel(new GridLayout(1,2));

        JPanel purchaseMenu = new JPanel(new MigLayout("wrap,fillx,insets 30", "fill,275"));
        purchaseMenu.putClientProperty(FlatClientProperties.STYLE, "arc:20;" + "background:lighten(@background,5%)");

        FlatLabel title = new FlatLabel();
        title.setText("Please Enter Card Information");
        title.putClientProperty(FlatClientProperties.STYLE, "font:bold +6");

        // Create and add multiple text boxes to the panel
        FlatTextField cardNumberField = new FlatTextField();
        cardNumberField.setPlaceholderText("xxxx xxxx xxxx xxxx");
        ((AbstractDocument) cardNumberField.getDocument()).setDocumentFilter(new CreditCardDocumentFilter());
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

        JLabel label5 = new JLabel("Expir. Date");
        // Month ComboBox (01 to 12)
        JPanel cardField = new JPanel(new MigLayout("fillx,insets 0", "fill,275"));
        String[] months = IntStream.rangeClosed(1, 12)
                .mapToObj(i -> String.format("%02d", i))
                .toArray(String[]::new);
        JComboBox<String> monthComboBox = new JComboBox<>(months);

        // Year ComboBox (current year to current year + 10)
        int currentYear = Year.now().getValue();
        String[] years = IntStream.range(currentYear, currentYear + 11)
                .mapToObj(Integer::toString)
                .toArray(String[]::new);
        JComboBox<String> yearComboBox = new JComboBox<>(years);

        // Add the tip developer section
        JPanel tipPanel = new JPanel(new MigLayout("wrap,fillx,insets 10", "fill,275"));
        tipPanel.putClientProperty(FlatClientProperties.STYLE, "arc:15;" + "background:lighten(@background,8%)");

        // Random tip message
        Random random = new Random();
        String randomTipMessage = tipMessages[random.nextInt(tipMessages.length)];

        FlatLabel tipTitle = new FlatLabel();
        tipTitle.setText("Tip the Developer");
        tipTitle.putClientProperty(FlatClientProperties.STYLE, "font:bold +2");

        FlatLabel tipDescription = new FlatLabel();
        tipDescription.setText(randomTipMessage);

        // Tip slider
        tipSlider = new JSlider(JSlider.HORIZONTAL, 1, 10, 2);
        tipSlider.setMajorTickSpacing(1);
        tipSlider.setPaintTicks(true);
        tipSlider.setPaintLabels(true);
        tipSlider.setSnapToTicks(true);

        JLabel tipAmountLabel = new JLabel("Tip amount: $2");
        tipSlider.addChangeListener(e -> {
            int value = tipSlider.getValue();
            tipAmountLabel.setText("Tip amount: $" + value);
        });

        // Checkbox for agreeing to tip
        agreeTipCheckbox = new JCheckBox("I agree to tip the hardworking developer");

        // Add components to tip panel
        tipPanel.add(tipTitle);
        tipPanel.add(tipDescription);
        tipPanel.add(tipSlider);
        tipPanel.add(tipAmountLabel);
        tipPanel.add(agreeTipCheckbox);

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
        cardField.add(monthComboBox, "left");
        cardField.add(yearComboBox, "right");

        purchaseMenu.add(cardField);

        // Add tip panel before submit button
        purchaseMenu.add(tipPanel);

        purchaseMenu.add(submitButton);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check if user agreed to tip
                if (!agreeTipCheckbox.isSelected()) {
                    JOptionPane.showMessageDialog(
                            TransactionView.this,
                            "Please agree to tip the developer before proceeding.",
                            "Tip Required",
                            JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }

                CreditCard newCard = new CreditCard();
                newCard.setCardNumber(cardNumberField.getText());
                newCard.setCardHolder(cardHolderField.getText());
                newCard.setZipCode(zipField.getText());
                newCard.setCvv(cvvField.getText());
                String selectedMonth = (String) monthComboBox.getSelectedItem();
                String selectedYear = (String) yearComboBox.getSelectedItem();
                newCard.setExpiryDate(selectedMonth + "/" + selectedYear.substring(2));

                if (newCard.CardValidation(newCard)) {
                    try {
                        // Get the tip amount (but we don't need to do anything with it)
                        int tipAmount = tipSlider.getValue();

                        // Save the credit card information
                        boolean success = acc.saveCreditCard(newCard);

                        if (success) {
                            // Show success message
                            JOptionPane.showMessageDialog(
                                    TransactionView.this,
                                    "Card information saved successfully!\nThank you for your $" + tipAmount + " tip!",
                                    "Success",
                                    JOptionPane.INFORMATION_MESSAGE
                            );

                            // Add the pending currency pack to the cart if we're in the shop
                            if (shopModel != null) {
                                // Use the pending currency pack values from the model
                                int pendingRocketBucks = shopModel.getPendingRocketBucks();
                                double pendingPrice = shopModel.getPendingPrice();

                                // Only add to cart if there's a pending currency pack
                                if (pendingRocketBucks > 0) {
                                    shopModel.addToCart(pendingRocketBucks, pendingPrice);
                                    // Clear the pending pack after adding to cart
                                    shopModel.clearPendingCurrencyPack();
                                }
                            }

                            // Close the dialog if it exists
                            if (parentDialog != null) {
                                parentDialog.dispose();
                            }
                        } else {
                            JOptionPane.showMessageDialog(
                                    TransactionView.this,
                                    "Failed to save card information.",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE
                            );
                        }
                    } catch (SQLException ex) {
                        System.err.println("Error saving credit card: " + ex.getMessage());
                        JOptionPane.showMessageDialog(
                                TransactionView.this,
                                "Error saving card information: " + ex.getMessage(),
                                "Database Error",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                } else {
                    JOptionPane.showMessageDialog(
                            TransactionView.this,
                            "Invalid card information. Please check your entries.",
                            "Validation Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });

        mainPanel.add(purchaseMenu);
    }

    public static JPanel get() {
        return mainPanel;
    }

    // Setter for the parent dialog - in case we need to set it after construction
    public void setParentDialog(JDialog dialog) {
        this.parentDialog = dialog;
    }

    // Setter for the shop model - in case we need to set it after construction
    public void setShopModel(currencyShopModel model) {
        this.shopModel = model;
    }

    static class CreditCardDocumentFilter extends DocumentFilter {
        public static final int MAX_DIGITS = 16;

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
}