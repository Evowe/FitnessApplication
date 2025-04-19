package fitness.app.Social;

import fitness.app.Objects.Account;
import fitness.app.Objects.Databases.AccountsDB;
import fitness.app.Objects.Databases.MessagesDB;
import fitness.app.Objects.Message;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SocialModel {
    private MessagesDB messagesDB;
    private AccountsDB accountsDB;

    private Account receiver;

    public SocialModel() {
        //Create a single instance of MessagesDB
        this.messagesDB = new MessagesDB();
        this.accountsDB = new AccountsDB();
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





}
