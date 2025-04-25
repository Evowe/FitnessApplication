package fitness.app.BonusFeatures.Social;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.components.FlatButton;
import com.formdev.flatlaf.extras.components.FlatLabel;
import com.formdev.flatlaf.extras.components.FlatTextField;
import fitness.app.Main;
import fitness.app.Utility.Objects.Account;
import fitness.app.Utility.Objects.Message;
import fitness.app.Utility.Widgets.SideMenu.SideMenuView;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateMessageView extends JPanel {
    SocialViewModel viewModel = new SocialViewModel();


    public CreateMessageView() {
        //Setup Main Panel Layout
        setLayout(new MigLayout("insets 20", "left", "top"));
        putClientProperty(FlatClientProperties.STYLE, "background:@background");

        add(new SideMenuView(), "growy, pushy");

        JPanel main = new JPanel();
        main.putClientProperty(FlatClientProperties.STYLE, "background:@background");
        main.setLayout(new BorderLayout());
        main.setMinimumSize(new Dimension(1250, 500));

        //North Panel Setup - Title
        JPanel titlePanel = new JPanel();
        titlePanel.putClientProperty(FlatClientProperties.STYLE, "background:@background");
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        FlatLabel title = new FlatLabel();
        title.setText("New Message");
        title.putClientProperty(FlatClientProperties.STYLE, "font:bold +25");
        titlePanel.add(title);

        main.add(titlePanel, BorderLayout.NORTH);



        //CenterPanel Setup
        JPanel centerPanel = new JPanel();
        centerPanel.putClientProperty(FlatClientProperties.STYLE, "background:@background");
        centerPanel.setLayout(new GridLayout(4, 1, 0, 20));


        JPanel userPanel = new JPanel();
        userPanel.putClientProperty(FlatClientProperties.STYLE, "background:@background");
        userPanel.setLayout(new BorderLayout());

        FlatLabel tableTitle = new FlatLabel();
        tableTitle.setText("Users");
        tableTitle.putClientProperty(FlatClientProperties.STYLE, "font:regular +6");
        userPanel.add(tableTitle, BorderLayout.NORTH);

        //Create Table with all users
        JTable userTable = new JTable(viewModel.getUserData(), viewModel.getUserColumns());
        userTable.setMinimumSize(new Dimension(1200, 100));
        JScrollPane userTableScrollPane = new JScrollPane(userTable);
        userPanel.add(userTableScrollPane, BorderLayout.CENTER);
        userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        FlatButton selectUser = new FlatButton();
        selectUser.setText("Select User");

        selectUser.addActionListener(e -> {
            int selectedRow = userTable.getSelectedRow();
            Account account = viewModel.selectUser(selectedRow);
            viewModel.setReceiver(account);
        });
        userPanel.add(selectUser, BorderLayout.SOUTH);

        centerPanel.add(userPanel);


        //Message Type Title & Field
        JPanel messageTypePanel = new JPanel();
        messageTypePanel.putClientProperty(FlatClientProperties.STYLE, "background:@background");
        messageTypePanel.setLayout(new BorderLayout());

        FlatLabel messageTypeTitle = new FlatLabel();
        messageTypeTitle.setText("Message Type");
        messageTypeTitle.putClientProperty(FlatClientProperties.STYLE, "font:regular +6");
        messageTypePanel.add(messageTypeTitle, BorderLayout.NORTH);

        String[] options = {"Select Type", "Challenge", "Friend Request"};
        JComboBox<String> messageTypeDropdown = new JComboBox<>(options);
        messageTypeDropdown.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Select Type");
        messageTypeDropdown.setBackground(new Color(200,200,200));
        messageTypeDropdown.setForeground(new Color(120, 120, 120));
        messageTypePanel.add(messageTypeDropdown, BorderLayout.CENTER);
        centerPanel.add(messageTypePanel);

        //Message Title & Text Field
        JPanel messagePanel = new JPanel();
        messagePanel.putClientProperty(FlatClientProperties.STYLE, "background:@background");
        messagePanel.setLayout(new BorderLayout());

        FlatLabel messageTitle = new FlatLabel();
        messageTitle.setText("Message");
        messageTitle.putClientProperty(FlatClientProperties.STYLE, "font:regular +6");
        messagePanel.add(messageTitle, BorderLayout.NORTH);

        FlatTextField messageField = new FlatTextField();
        //messageField.setMinimumSize(new Dimension(200, 12));
        //messageField.setMaximumSize(new Dimension(200, 12));
        messageField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter Message Here");
        messagePanel.add(messageField, BorderLayout.CENTER);
        centerPanel.add(messagePanel);

        FlatButton sendMessageButton = new FlatButton();
        sendMessageButton.setMinimumHeight(35);
        sendMessageButton.setMinimumWidth(1250);
        sendMessageButton.setText("Send Message");
        sendMessageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                //Account receiver = viewModel.selectUser(selectedRow);
                int index = messageTypeDropdown.getSelectedIndex();
                Message.Type type = null;
                if (index == 1) {
                    type = Message.Type.CHALLENGE;
                } else if (index == 2) {
                    type = Message.Type.FRIEND_REQUEST;
                }
                System.out.println("this is the index:" + index);

                Message message = new Message(messageField.getText(), Main.getCurrentUser(), viewModel.getReceiver(), type);
                message.addMessage();
                if(type == Message.Type.FRIEND_REQUEST) {
                    viewModel.requestFriend(Main.getCurrentUser().getUsername(), viewModel.getReceiver().getUsername());
                }
                //viewModel.sendMessage(messageField.getText(), Main.getCurrentUser(), viewModel.getReceiver(), type);
                Main.setWindow("SocialView");
                //viewModel.selectUser(selectedRow);
            }
        });

        main.add(sendMessageButton, BorderLayout.SOUTH);








        main.add(centerPanel, BorderLayout.CENTER);




        add(main, "growy, pushy");

    }



}
