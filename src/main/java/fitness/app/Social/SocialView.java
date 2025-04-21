package fitness.app.Social;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.components.FlatButton;
import com.formdev.flatlaf.extras.components.FlatLabel;
import fitness.app.Main;
import fitness.app.Objects.Message;
import fitness.app.Widgets.SideMenu.SideMenuView;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SocialView extends JPanel {
    SocialViewModel viewModel = new SocialViewModel();

    public SocialView() {
        //Setup Main Panel
        setLayout(new MigLayout("insets 20", "left", "top"));
        putClientProperty(FlatClientProperties.STYLE, "background:@background");

        //Add navigation bar
        add(new SideMenuView(), "growy, pushy");


        //Setup Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.putClientProperty(FlatClientProperties.STYLE, "background:@background");

        //North Panel Setup
        JPanel titlePanel = new JPanel();
        titlePanel.putClientProperty(FlatClientProperties.STYLE, "background:@background");
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        FlatLabel title = new FlatLabel();
        title.setText("Social");
        title.putClientProperty(FlatClientProperties.STYLE, "font:bold +25");
        titlePanel.add(title);

        mainPanel.add(titlePanel, BorderLayout.NORTH);

        //Center Panel Setup
        JPanel centerPanel = new JPanel();
        centerPanel.setMinimumSize(new Dimension(1200, 500));
        centerPanel.putClientProperty(FlatClientProperties.STYLE, "background:@background");
        centerPanel.setLayout(new GridLayout(1, 2, 10, 0));

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(2, 1, 0, 10));
        leftPanel.putClientProperty(FlatClientProperties.STYLE, "background:@background");
                //Setup myFriendsBlock
        JPanel myFriendsPanel = new JPanel();
        myFriendsPanel.setLayout(new BorderLayout());

        FlatLabel myFriendsTitle = new FlatLabel();
        myFriendsTitle.setText("My Friends");
        myFriendsTitle.setHorizontalAlignment(SwingConstants.CENTER);
        myFriendsTitle.setForeground(new Color(193, 18, 31));
        myFriendsTitle.putClientProperty(FlatClientProperties.STYLE, "" + "font:bold +6");
        myFriendsPanel.add(myFriendsTitle, BorderLayout.NORTH);

        //Need to initalize with data from friends database

        Object [][] data = null;
        if(viewModel.getFriendData(Main.getCurrentUser()) == null){
            data = new Object[1][viewModel.getFriendColumns().length];
            Object [] empty = new Object[1];
            empty[0] = "";
            data[0] = empty;
        } else{
            data = viewModel.getFriendData(Main.getCurrentUser());
        }



        JTable friendsTable = new JTable(data, viewModel.getFriendColumns());
        friendsTable.setRowHeight(50);
        friendsTable.setShowGrid(true);
        friendsTable.setMaximumSize(new Dimension(0, 200));
        friendsTable.putClientProperty(FlatClientProperties.STYLE,  "" + "font:bold +6");
        friendsTable.setDefaultEditor(Object.class, null);

        JScrollPane friendsScrollPane = new JScrollPane(friendsTable);
        myFriendsPanel.add(friendsScrollPane, BorderLayout.CENTER);
        leftPanel.add(myFriendsPanel);



        //Friend Requests Panel
        List<String> friendRequests = viewModel.getPendingRequests(Main.getCurrentUser().getUsername());

        JPanel friendPanel = new JPanel(new BorderLayout());

        FlatLabel friendRequestsTitle = new FlatLabel();
        friendRequestsTitle.setText("Friend Requests");
        friendRequestsTitle.setHorizontalAlignment(SwingConstants.CENTER);
        friendRequestsTitle.setForeground(new Color(193, 18, 31));
        friendRequestsTitle.putClientProperty(FlatClientProperties.STYLE, "" + "font:bold +6");
        friendPanel.add(friendRequestsTitle, BorderLayout.NORTH);

        JPanel friendsRequestsPanel = new JPanel();
        friendsRequestsPanel.setLayout(new GridLayout(8,1, 0, 5));
        //friendsRequestsPanel.setMaximumSize(new Dimension(0, 10));

        for(int i = 0; i < friendRequests.size(); i++){
            //friendPanel = new JPanel();
            //friendsRequestsPanel.setLayout(new GridLayout(data.length, 3));
            JPanel friendRequest = new JPanel();
            friendRequest.setBorder(BorderFactory.createLineBorder(friendsTable.getGridColor(), 1));
            friendRequest.setLayout(new GridLayout(1, 3, 5, 5));
            FlatLabel friendName = new FlatLabel();
            friendName.setText(friendRequests.get(i));
            viewModel.setFriendRequestUsername(friendRequests.get(i));
            friendName.putClientProperty(FlatClientProperties.STYLE, "" + "font:regular +6");
            friendRequest.add(friendName);

            FlatButton acceptFriend = new FlatButton();
            //acceptFriend.setMinimumHeight(10);
            //acceptFriend.setMinimumWidth(200);
            acceptFriend.setText("Accept");
            acceptFriend.setBackground(new Color(20, 100, 31));
            acceptFriend.setBorder(BorderFactory.createRaisedBevelBorder());
            friendRequest.add(acceptFriend);
            acceptFriend.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {

                    viewModel.acceptFriendRequest(Main.getCurrentUser().getUsername(), viewModel.getFriendRequestUsername());
                    viewModel.acceptFriendRequestMessages(Main.getCurrentUser().getUsername(), viewModel.getFriendRequestUsername());

                    Main.setWindow("SocialView");
                }
            });


            FlatButton declineRequest = new FlatButton();
            //declineRequest.setMinimumHeight(35);
            //declineRequest.setMinimumWidth(200);
            declineRequest.setText("Decline");
            friendRequest.add(declineRequest);
            declineRequest.setBorder(BorderFactory.createRaisedBevelBorder());
            declineRequest.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    viewModel.declineFriendRequest(Main.getCurrentUser().getUsername(), viewModel.getFriendRequestUsername());
                    viewModel.deleteFriendRequestMessages(Main.getCurrentUser().getUsername(), viewModel.getFriendRequestUsername());
                    Main.setWindow("SocialView");
                }
            });

            friendsRequestsPanel.add(friendRequest);
        }

        friendPanel.add(friendsRequestsPanel, BorderLayout.CENTER);

        leftPanel.add(friendPanel);

        centerPanel.add(leftPanel);




        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(2, 1, 0, 10));
        rightPanel.putClientProperty(FlatClientProperties.STYLE, "background:@background");


                //Setup myMessagesBlock
        JPanel messagesPanel = new JPanel();
        messagesPanel.setLayout(new BorderLayout());

        FlatLabel messagesTitle = new FlatLabel();
        messagesTitle.setText("My Messages");
        messagesTitle.setHorizontalAlignment(SwingConstants.CENTER);
        messagesTitle.setForeground(new Color(193, 18, 31));
        messagesTitle.putClientProperty(FlatClientProperties.STYLE, "" + "font:bold +6");
        messagesPanel.add(messagesTitle, BorderLayout.NORTH);

        //Need to initalize with data from friends database
        JTable messagesTable = new JTable(viewModel.getMessageData2(Main.getCurrentUser()), viewModel.getMessageColumns());
        messagesTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        messagesTable.putClientProperty(FlatClientProperties.STYLE,  "" + "font:regular +0");
        messagesTable.setRowHeight(50);
        messagesTable.setShowGrid(true);

        messagesTable.getColumnModel().getColumn(0).setPreferredWidth(313);
        messagesTable.getColumnModel().getColumn(1).setPreferredWidth(120);
        messagesTable.getColumnModel().getColumn(2).setPreferredWidth(160);
        messagesTable.setDefaultEditor(Object.class, null);

        for(int i = 1; i < messagesTable.getColumnCount(); i++) {
            TableColumn col = messagesTable.getColumnModel().getColumn(i);
            DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
            dtcr.setHorizontalAlignment(SwingConstants.CENTER);
            col.setCellRenderer(dtcr);
        }


        JScrollPane messagesScrollPane = new JScrollPane(messagesTable);
        messagesPanel.add(messagesScrollPane, BorderLayout.CENTER);

        rightPanel.add(messagesPanel);


        //Setup Responses Panel
        JPanel responsesPanel = new JPanel();
        responsesPanel.setLayout(new BorderLayout());

        FlatLabel responsesTitle = new FlatLabel();
        responsesTitle.setText("Responses to Sent Messages");
        responsesTitle.setHorizontalAlignment(SwingConstants.CENTER);
        responsesTitle.setForeground(new Color(193, 18, 31));
        responsesTitle.putClientProperty(FlatClientProperties.STYLE, "" + "font:bold +6");
        responsesPanel.add(responsesTitle, BorderLayout.NORTH);

        //Need to initalize with data from friends database
        JTable responsesTable = new JTable(viewModel.getResponseData(Main.getCurrentUser()), viewModel.getResponseColumns());
        responsesTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        responsesTable.putClientProperty(FlatClientProperties.STYLE,  "" + "font:regular +0");
        responsesTable.setRowHeight(50);
        responsesTable.setShowGrid(true);
        responsesTable.getColumnModel().getColumn(0).setPreferredWidth(313);
        responsesTable.getColumnModel().getColumn(1).setPreferredWidth(120);
        responsesTable.getColumnModel().getColumn(2).setPreferredWidth(160);
        responsesTable.setDefaultEditor(Object.class, null);

        for(int i = 0; i < responsesTable.getColumnCount(); i++) {
            TableColumn col = responsesTable.getColumnModel().getColumn(i);
            DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
            dtcr.setHorizontalAlignment(SwingConstants.CENTER);
            col.setCellRenderer(dtcr);
        }


        JScrollPane responsesScrollPane = new JScrollPane(responsesTable);
        responsesPanel.add(responsesScrollPane, BorderLayout.CENTER);

        rightPanel.add(responsesPanel);






        //centerPanel.add(myFriendsPanel);
        centerPanel.add(rightPanel);


        mainPanel.add(centerPanel, BorderLayout.CENTER);


        //South Panel Setup
        JPanel buttonPanel = new JPanel();
        buttonPanel.putClientProperty(FlatClientProperties.STYLE, "background:@background");
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        FlatButton newMessage = new FlatButton();
        newMessage.setMinimumHeight(35);
        newMessage.setMinimumWidth(200);
        newMessage.setText("+ New Message");
        buttonPanel.add(newMessage);
        newMessage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                Main.setWindow("CreateMessage" );
            }
        });

        FlatButton sendResponse = new FlatButton();
        sendResponse.setMinimumHeight(35);
        sendResponse.setMinimumWidth(200);
        sendResponse.setText("Send Response");
        buttonPanel.add(sendResponse);
        sendResponse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                Main.setWindow("SendResponse" );
            }
        });




        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel, "growy, pushy");
    }


}
