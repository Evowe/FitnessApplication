package fitness.app.Social;

import fitness.app.Objects.Account;
import fitness.app.Objects.Databases.MessagesDB;
import fitness.app.Objects.Message;

import java.sql.SQLException;
import java.util.List;

public class SocialModel {
    private MessagesDB messagesDB;

    public SocialModel() {
        //Create a single instance of MessagesDB
        this.messagesDB = new MessagesDB();
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

    public Object[] getMessageColumns() {
        Object[] columns = new Object[6];
        columns[0] = "Message";
        columns[1] = "Sender";
        columns[2] = "Reciever";
        columns[3] = "Type";
        columns[4] = "Response";
        columns[5] = "Response Type";

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
