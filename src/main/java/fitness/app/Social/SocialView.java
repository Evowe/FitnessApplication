package fitness.app.Social;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.components.FlatButton;
import com.formdev.flatlaf.extras.components.FlatLabel;
import fitness.app.Login.LoginModel;
import fitness.app.Main;
import fitness.app.Objects.Account;
import fitness.app.Widgets.SideMenu.SideMenuView;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        centerPanel.setLayout(new GridLayout(1, 2));

                //Setup myFriendsBlock
        JPanel myFriendsPanel = new JPanel();
        myFriendsPanel.setLayout(new BorderLayout());

        FlatLabel myFriendsTitle = new FlatLabel();
        myFriendsTitle.setText("My Friends");
        myFriendsTitle.putClientProperty(FlatClientProperties.STYLE, "" + "font:regular +6");
        myFriendsPanel.add(myFriendsTitle, BorderLayout.NORTH);

        //Need to initalize with data from friends database
        JTable friendsTable = new JTable();

        JScrollPane friendsScrollPane = new JScrollPane(friendsTable);
        myFriendsPanel.add(friendsScrollPane, BorderLayout.CENTER);


                //Setup myMessagesBlock
        JPanel messagesPanel = new JPanel();
        messagesPanel.setLayout(new BorderLayout());

        FlatLabel messagesTitle = new FlatLabel();
        messagesTitle.setText("My Messages");
        messagesTitle.putClientProperty(FlatClientProperties.STYLE, "" + "font:regular +6");
        messagesPanel.add(messagesTitle, BorderLayout.NORTH);

        //Need to initalize with data from friends database
        JTable messagesTable = new JTable(viewModel.getMessageData2(Main.getCurrentUser()), viewModel.getMessageColumns());
        messagesTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        messagesTable.getColumnModel().getColumn(0).setPreferredWidth(350);
        messagesTable.getColumnModel().getColumn(1).setPreferredWidth(120);
        messagesTable.getColumnModel().getColumn(2).setPreferredWidth(130);

        for(int i = 1; i < messagesTable.getColumnCount(); i++) {
            TableColumn col = messagesTable.getColumnModel().getColumn(i);
            DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
            dtcr.setHorizontalAlignment(SwingConstants.CENTER);
            col.setCellRenderer(dtcr);
        }




        JScrollPane messagesScrollPane = new JScrollPane(messagesTable);
        messagesPanel.add(messagesScrollPane, BorderLayout.CENTER);


        centerPanel.add(myFriendsPanel);
        centerPanel.add(messagesPanel);


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
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel, "growy, pushy");
    }


}
