package Application.BonusFeatures.Social;

import Application.Main;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.components.FlatButton;
import com.formdev.flatlaf.extras.components.FlatLabel;
import com.formdev.flatlaf.extras.components.FlatTextField;
import Application.Utility.Objects.Account;
import Application.Utility.Objects.Message;
import Application.Utility.Widgets.SideMenu.SideMenuView;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class SendResponseView extends JPanel {
    SocialViewModel viewModel = new SocialViewModel();

    public SendResponseView() {
        //Setup Main Panel Layout
        //setLayout(new MigLayout("fill, insets 20", "center", "center"));
        setLayout(new MigLayout("fill, insets 20", "[]20[]", "center"));

        putClientProperty(FlatClientProperties.STYLE, "background:@background");

        add(new SideMenuView(), "growy, pushy");

        //Setup Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.putClientProperty(FlatClientProperties.STYLE, "background:@background");


        //North Panel Setup
        JPanel titlePanel = new JPanel();
        titlePanel.putClientProperty(FlatClientProperties.STYLE, "background:@background");
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        FlatLabel title = new FlatLabel();
        title.setText("New Response");
        title.putClientProperty(FlatClientProperties.STYLE, "font:bold +25");
        titlePanel.add(title);

        mainPanel.add(titlePanel, BorderLayout.NORTH);


        //Make Center Panel
        JPanel center = new JPanel();
        center.putClientProperty(FlatClientProperties.STYLE, "background:@background");
        center.setLayout(new GridLayout(4, 1));
        center.setMinimumSize(new Dimension(1250, 100));

        //Setup myMessagesBlock
        JPanel messagesPanel = new JPanel();
        messagesPanel.setLayout(new BorderLayout());
        messagesPanel.setBackground(Color.BLACK);

        FlatLabel messagesTitle = new FlatLabel();
        messagesTitle.setText("My Messages");
        messagesTitle.putClientProperty(FlatClientProperties.STYLE, "" + "font:regular +6");
        messagesTitle.setBackground(Color.BLACK);
        //messagesTitle.putClientProperty(FlatClientProperties.STYLE, "background:@background");
        messagesPanel.add(messagesTitle, BorderLayout.NORTH);

        //Need to initalize with data from friends database
        JTable messagesTable = new JTable(viewModel.getMessageData2(Main.getCurrentUser()), viewModel.getMessageColumns());
        messagesTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        messagesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        messagesTable.getColumnModel().getColumn(0).setPreferredWidth(950);
        messagesTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        messagesTable.getColumnModel().getColumn(2).setPreferredWidth(150);

        for(int i = 1; i < messagesTable.getColumnCount(); i++) {
            TableColumn col = messagesTable.getColumnModel().getColumn(i);
            DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
            dtcr.setHorizontalAlignment(SwingConstants.CENTER);
            col.setCellRenderer(dtcr);
        }


        JScrollPane messagesScrollPane = new JScrollPane(messagesTable);
        messagesPanel.add(messagesScrollPane, BorderLayout.CENTER);

        FlatButton selectMessage = new FlatButton();
        selectMessage.setText("Select Message");


        selectMessage.addActionListener(e -> {
            int selectedRow = messagesTable.getSelectedRow();
            /*
            String message = messagesTable.getValueAt(selectedRow, 0).toString();
            String sender = messagesTable.getValueAt(selectedRow, 1).toString();
            String receiver = Main.getCurrentUser().getUsername();
            String type = messagesTable.getValueAt(selectedRow, 3).toString();

             */
            viewModel.setSelectedRow(selectedRow);
        });
        messagesPanel.add(selectMessage, BorderLayout.SOUTH);


        //Response Type Title & Field
        JPanel responseTypePanel = new JPanel();
        responseTypePanel.putClientProperty(FlatClientProperties.STYLE, "background:@background");
        responseTypePanel.setLayout(new BorderLayout());

        FlatLabel responseTypeTitle = new FlatLabel();
        responseTypeTitle.setText("Response Type");
        responseTypeTitle.putClientProperty(FlatClientProperties.STYLE, "font:regular +6");
        responseTypePanel.add(responseTypeTitle, BorderLayout.NORTH);

        String[] options = {"Select Type", "Accept Friend", "Reject Friend", "Accept Challenge", "Reject Challenge"};
        JComboBox<String> responseTypeDropdown = new JComboBox<>(options);
        responseTypeDropdown.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Select Type");
        responseTypeDropdown.setBackground(new Color(200,200,200));
        responseTypeDropdown.setForeground(new Color(120, 120, 120));
        responseTypePanel.add(responseTypeDropdown, BorderLayout.CENTER);


        center.add(messagesPanel);
        center.add(responseTypePanel);

        //Message Title & Text Field
        JPanel responsePanel = new JPanel();
        responsePanel.putClientProperty(FlatClientProperties.STYLE, "background:@background");
        responsePanel.setLayout(new BorderLayout());

        FlatLabel responseTitle = new FlatLabel();
        responseTitle.setText("Response");
        responseTitle.putClientProperty(FlatClientProperties.STYLE, "font:regular +6");
        responsePanel.add(responseTitle, BorderLayout.NORTH);

        FlatTextField responseField = new FlatTextField();
        responseField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter Response Here");
        responsePanel.add(responseField, BorderLayout.CENTER);
        center.add(responsePanel);

        FlatButton sendResponseButton = new FlatButton();
        sendResponseButton.setMinimumHeight(35);
        sendResponseButton.setMinimumWidth(1250);
        sendResponseButton.setText("Send Response");
        sendResponseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                if (responseTypeDropdown.getSelectedItem().equals("Select Type") || responseField.getText().isEmpty()
                        || responseTypeDropdown.getSelectedItem().equals("Select Type")) {
                    JOptionPane.showMessageDialog(null,
                            "All fields must be filled.");
                } else if (responseField.getText().length() > 100) {
                    JOptionPane.showMessageDialog(null,
                            "No field can exceed 100 characters.");
                } else {
                    //Account receiver = viewModel.selectUser(selectedRow);
                    Boolean correspondingType = true;
                    int index = responseTypeDropdown.getSelectedIndex();
                    Object[][] data = viewModel.getMessageData2(Main.getCurrentUser());
                    Message.Type type = Message.getType(data[viewModel.getSelectedRow()][2].toString());
                    Message.Type responseType = null;
                    if (index == 1 && type == Message.Type.FRIEND_REQUEST) {
                        responseType = Message.Type.ACCEPT_FRIEND;
                    } else if (index == 2 && type == Message.Type.FRIEND_REQUEST) {
                        responseType = Message.Type.REJECT_FRIEND;
                    } else if (index == 3 && type == Message.Type.CHALLENGE) {
                        responseType = Message.Type.ACCEPT_CHALLENGE;
                    } else if (index == 4 && type == Message.Type.CHALLENGE) {
                        responseType = Message.Type.REJECT_CHALLENGE;
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "You must select a corresponding type to the message type.");
                        correspondingType = false;

                    }

                    if(correspondingType){
                        String message = data[viewModel.getSelectedRow()][0].toString();
                        Account sender = null;
                        try {
                            sender = Account.getAccount(data[viewModel.getSelectedRow()][1].toString());
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        Account recipient = Main.getCurrentUser();
                        String response = responseField.getText();
                        //Message.Type type = Message.getType(data[viewModel.getSelectedRow()][2].toString());
                        Message m = new Message(message, sender, recipient, type, response, responseType);

                        viewModel.respondToMessage(m);

                        if (responseType == Message.Type.ACCEPT_FRIEND) {
                            viewModel.acceptFriendRequest(Main.getCurrentUser().getUsername(), messagesTable.getValueAt(viewModel.getSelectedRow(), 1).toString());
                        } else if (responseType == Message.Type.REJECT_FRIEND) {
                            viewModel.declineFriendRequest(Main.getCurrentUser().getUsername(), messagesTable.getValueAt(viewModel.getSelectedRow(), 1).toString());
                            //viewModel.deleteFriendRequestMessages(Main.getCurrentUser().getUsername(), messagesTable.getValueAt(viewModel.getSelectedRow(), 1).toString());
                        }

                        //viewModel.sendMessage(resField.getText(), Main.getCurrentUser(), viewModel.getReceiver(), type);
                        Main.setWindow("SocialView");
                        //viewModel.selectUser(selectedRow);
                    }

                }
            }

        });



        mainPanel.add(center, BorderLayout.CENTER);
        mainPanel.add(sendResponseButton, BorderLayout.SOUTH);


        add(mainPanel, "growy, pushy");


    }
}
