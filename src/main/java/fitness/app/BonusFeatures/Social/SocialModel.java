package fitness.app.BonusFeatures.Social;

import fitness.app.Utility.Objects.Account;
import fitness.app.Utility.Databases.AccountsDB;
import fitness.app.Utility.Databases.FriendsDB;
import fitness.app.Utility.Databases.MessagesDB;
import fitness.app.Utility.Objects.Message;

import java.sql.SQLException;
import java.util.List;

public class SocialModel {
    private MessagesDB messagesDB;
    private AccountsDB accountsDB;
    private FriendsDB friendsDB;

    private Account receiver;
    private int selectedRow;
    private String friendRequestUsername;

    public SocialModel() {
        //Create a single instance of MessagesDB
        this.messagesDB = new MessagesDB();
        this.accountsDB = new AccountsDB();
        this.friendsDB = new FriendsDB();
    }

    public Object[][] getUserData() {
        List<Account> users = null;

        try{
            users = accountsDB.getAllUsers();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Object[][] data = new Object[users.size()][2];
        for(int i = 0; i < users.size(); i++){
            data[i] = users.get(i).getString();
        }

        return data;
    }

    public void setReceiver(Account receiver) {
        this.receiver = receiver;
    }

    public Object[] getUserColumns() {
        Object[] columns = new Object[2];
        columns[0] = "Username";
        columns[1] = "Role";

        return columns;
    }

    public Account selectUser(int row){
        List<Account> users = null;

        try{
            users = accountsDB.getAllUsers();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return users.get(row);
    }

    public Account getReceiver() {
        return receiver;
    }


    public Object[][] getMessageData(Account account) {
        List<Message> messages = null;

        try{
            messages = messagesDB.getAllIncomingMessages(account.getUsername());
        } catch(SQLException e){
            throw new RuntimeException(e);
        }

        Object[][] data = new Object[messages.size()][6];
        for(int i = 0; i < messages.size(); i++){
            data[i] = messages.get(i).getString();
        }

        return data;
    }

    public Object[][] getMessageData2(Account account) {
        List<Object []> messages = null;
        try{
            messages = messagesDB.getAllIncoming(account.getUsername());
        } catch(SQLException e){
            throw new RuntimeException(e);
        }

        Object[][] data = new Object[messages.size()][6];
        for(int i = 0; i < messages.size(); i++){
            data[i] = messages.get(i);
        }

        return data;
    }

    public Object[][] getResponseData(Account account) {
        List<Object []> messages = null;
        try{
            messages = messagesDB.getAllResponses(account.getUsername());
        } catch(SQLException e){
            throw new RuntimeException(e);
        }

        Object[][] data = new Object[messages.size()][6];
        for(int i = 0; i < messages.size(); i++){
            data[i] = messages.get(i);
        }

        return data;
    }

    public void deleteFriendRequestMessages(String username, String requesterUsername){
        try{
            messagesDB.deleteFriendRequestMessages(username, requesterUsername);
        } catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void acceptFriendRequestMessages(String username, String requesterUsername){
        try{
            messagesDB.acceptFriendRequestMessages(username, requesterUsername);
        } catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    public int getSelectedRow() {
        return selectedRow;
    }


    public Object[] getMessageColumns() {
        Object[] columns = new Object[3];
        columns[0] = "Message";
        columns[1] = "Sent From";
        //columns[2] = "Reciever";
        columns[2] = "Type";
        //columns[4] = "Response";
        //columns[5] = "Response Type";

        return columns;
    }

    public Object[] getResponseColumns() {
        Object[] columns = new Object[3];
        columns[0] = "Response";
        columns[1] = "From";
        //columns[2] = "Reciever";
        columns[2] = "Response Type";
        //columns[4] = "Response";
        //columns[5] = "Response Type";

        return columns;
    }

    public Message createMessage(String message, Account sender, Account receiver, Message.Type type){
        Message m = new Message(message, sender, receiver, type);

        try {
            messagesDB.addMessage(m);
            System.out.println("Message succesfully added to the database");
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            e.printStackTrace();
            return null;
        }

        return m;
    }

    public void respondToMessage(Message message, String response, String responseType){
        message.setResponse(response);
        message.setResponseType(Message.getType(responseType));

        try {
            messagesDB.respond(message);
            System.out.println("Message succesfully edited in the database");
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public void requestFriend(String username, String friend){
        try {
            friendsDB.sendFriendRequest(username, friend);
            System.out.println("Friend Request succesfully added to the database");
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Object [][] getFriendData(String username){
        List<Object []> friends = null;

        try {
            friends = friendsDB.getFriends(username);
            System.out.println("Successfully got friends from the database");
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            e.printStackTrace();
        }

        Object[][] data = new Object[friends.size()][1];

        for(int i = 0; i < friends.size(); i++){
            data[i] = friends.get(i);
        }

        return data;
    }

    public Object[] getFriendColumns(){
        Object[] columns = new Object[1];
        columns[0] = "Username";

        return columns;
    }

    public void acceptFriendRequest(String username, String friend){
        try {
            friendsDB.acceptFriendRequest(username, friend);
            System.out.println("Successfully accepted friend request");
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void setSelectedRow(int row){
        selectedRow = row;
    }

    public int getAccountCount(){
        try {
            return accountsDB.getCount();
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    public List<String> getPendingRequests(String username){
        List<String> pendingRequests = null;

        try {
            pendingRequests = friendsDB.getPendingRequests(username);
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            e.printStackTrace();
        }

        return pendingRequests;

    }

    public String getFriendRequestUsername(){
        return this.friendRequestUsername;
    }

    public void setFriendRequestUsername(String username){
        this.friendRequestUsername = username;
    }

    public void declineFriendRequest(String username, String friend){
        try {
            friendsDB.declineFriendRequest(username, friend);
            System.out.println("Successfully declined friend request");
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void respondToMessage(Message message){
        try {
            messagesDB.respond(message);
            System.out.println("Successfully responded to message");
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteMessage(Message message){
        try {
            messagesDB.deleteMessage(message);
            System.out.println("Successfully deleted message");
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
