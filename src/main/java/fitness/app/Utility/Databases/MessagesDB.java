package fitness.app.Utility.Databases;

import fitness.app.Utility.Objects.Account;
import fitness.app.Utility.Objects.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MessagesDB extends DBTemplate{

    public MessagesDB() { super("messages"); }

    @Override
    protected void createTables() throws SQLException{
        //Create Messages Table
        String [] columns = {
                "message TEXT NOT NULL",
                "sender TEXT NOT NULL",
                "receiver TEXT NOT NULL",
                "type TEXT NOT NULL",
                "response TEXT NOT NULL",
                "responseType TEXT NOT NULL"
        };
        createTable("messages", columns);
    }

    public void addMessage(Message message) throws SQLException{
        String sql = "INSERT INTO messages (message, sender, receiver, type, response, responseType) VALUES (?, ?, ?, ?, ?, ?)";

        try(Connection con = getConnection();
        PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, message.getMessage());
            pstmt.setString(2, message.getSenderUsername());
            pstmt.setString(3, message.getReceiverUsername());
            pstmt.setString(4, message.getTypeString());
            pstmt.setString(5, message.getResponse());
            pstmt.setString(6, message.getResponseTypeString());

            pstmt.executeUpdate();

            System.out.println("Message added successfully");
        } catch (SQLException e) {
            System.out.println("Error adding message");
            throw e; //Throw e again for caller to handle
        }

    }

    public void deleteMessage(Message message) throws SQLException{
        String sql = "DELETE FROM messages (message, sender, receiver, type, response, responseType) VALUES (?, ?, ?, ?, ?, ?)";

        try(Connection con = getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, message.getMessage());
            pstmt.setString(2, message.getSenderUsername());
            pstmt.setString(3, message.getReceiverUsername());
            pstmt.setString(4, message.getTypeString());
            pstmt.setString(5, message.getResponse());
            pstmt.setString(6, message.getResponseTypeString());

            pstmt.executeUpdate();

            System.out.println("Message deleted successfully");
        } catch (SQLException e) {
            System.out.println("Error deleting message");
            throw e; //Throw e again for caller to handle
        }

    }

    public void respond(Message message) throws SQLException{
        String sql = "UPDATE messages SET response=?, responseType=? WHERE message=? AND sender=? AND receiver=? AND type =?";

        try(Connection con = getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, message.getResponse());
            pstmt.setString(2, message.getResponseTypeString());
            pstmt.setString(3, message.getMessage());
            pstmt.setString(4, message.getSenderUsername());
            pstmt.setString(5, message.getReceiverUsername());
            pstmt.setString(6, message.getTypeString());

            pstmt.executeUpdate();

            pstmt.close();
            con.close();
            System.out.println("Message edited successfully");
        } catch (SQLException e) {
            System.out.println("Error editing message");
            throw e; //Throw e again for caller to handle
        }

    }



    public Message getMessage(String message, String senderUsername, String receiverUsername, String type) throws SQLException{
        String sql = "SELECT * FROM messages WHERE message = ? AND sender = ? AND reciever = ? AND type = ?";

        try(Connection con = getConnection();
        PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, message);
            pstmt.setString(2, senderUsername);
            pstmt.setString(3, receiverUsername);
            pstmt.setString(4, type);

            ResultSet rs = pstmt.executeQuery();

            if(rs.next()){
                Message m = new Message(
                        rs.getString("message"),
                        Account.getAccount(rs.getString("sender")),
                        Account.getAccount(rs.getString("reciever")),
                        Message.getType(rs.getString("type"))
                );


                return m;
            }

            rs.close();

        } catch (SQLException e) {
            System.out.println("Error getting message");
            throw e;
        }

        return null;
    }

    public void deleteFriendRequestMessages(String username, String receiver) throws SQLException{
        String sql = "UPDATE messages SET responseType = ?, response = ? WHERE sender = ? AND receiver = ? AND type =?";
        try(Connection con = getConnection();
        PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, "REJECT_FRIEND");
            String response = receiver + " declined your friend request";
            pstmt.setString(2, response);
            pstmt.setString(3, receiver);
            pstmt.setString(4, username);
            pstmt.setString(5, "FRIEND_REQUEST");

            pstmt.executeUpdate();

            pstmt.close();
            con.close();
            System.out.println("Message edited successfully");
        } catch (SQLException e) {
            System.out.println("Error editing message");
            throw e;
        }
    }

    public void acceptFriendRequestMessages(String username, String receiver) throws SQLException{
        String sql = "UPDATE messages SET responseType = ?, response = ? WHERE sender = ? AND receiver = ? AND type =?";
        try(Connection con = getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, "ACCEPT_FRIEND");
            String response = receiver + " accepted your friend request";
            pstmt.setString(2, response);
            pstmt.setString(3, receiver);
            pstmt.setString(4, username);
            pstmt.setString(5, "FRIEND_REQUEST");

            pstmt.executeUpdate();

            pstmt.close();
            con.close();
            System.out.println("Message edited successfully");
        } catch (SQLException e) {
            System.out.println("Error editing message");
            throw e;
        }
    }

    public List<Message> getAllIncomingMessages(String receiverUsername) throws SQLException{
        //db query to select the user
        String sql = "SELECT * FROM messages WHERE receiver = ?";
        List<Message> incomingMessages = new ArrayList<>();

        try(Connection con = getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)){

            pstmt.setString(1, receiverUsername);

            ResultSet rs = pstmt.executeQuery();

            incomingMessages = new ArrayList<>();

            //Process all query results and creates new message object for each row
            while(rs.next()){
                String msgText = rs.getString("message");
                String sender = rs.getString("sender");
                String receiver = rs.getString("receiver");
                String type = rs.getString("type");
                String response = rs.getString("response");
                String responseType = rs.getString("responseType");

                Account senderAccount = Account.getAccountNoClose(sender);
                Account receiverAccount = Account.getAccountNoClose(receiver);

                Message m = new Message(
                        msgText,
                        senderAccount,
                        receiverAccount,
                        Message.getType(type),
                        response,
                        Message.getType(responseType)
                );


                incomingMessages.add(m);

            }

        } catch (SQLException e){
            System.out.println("Error getting messages");
            throw e;
        }

        return incomingMessages;
    }

    public List<Object []> getAllIncoming(String receiverUsername) throws SQLException{
        String sql = "SELECT * FROM messages WHERE receiver = ? AND responseType=?";
        List<Object []> incomingMessages = new ArrayList<>();

        try(Connection con = getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)){

            pstmt.setString(1, receiverUsername);
            pstmt.setString(2, "NOT_READ");

            ResultSet rs = pstmt.executeQuery();

            incomingMessages = new ArrayList<>();

            //Process all query results and creates new message object for each row
            while(rs.next()){
                Object[] row = new Object[3];
                row[0] = rs.getString("message");
                row[1] = rs.getString("sender");
                //row[2] = rs.getString("receiver");
                row[2] = rs.getString("type");
                //row[4] = rs.getString("response");
                //row[5] = rs.getString("responseType");

                incomingMessages.add(row);
            }

        } catch (SQLException e){
            System.out.println("Error getting messages");
            throw e;
        }

        return incomingMessages;
    }


    public List<Object []> getAllResponses(String senderUsername) throws SQLException{
        String sql = "SELECT * FROM messages WHERE sender = ? AND responseType != ?";
        List<Object []> responses = new ArrayList<>();

        try(Connection con = getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)){

            pstmt.setString(1, senderUsername);
            pstmt.setString(2, "NOT_READ");

            ResultSet rs = pstmt.executeQuery();

            responses = new ArrayList<>();

            //Process all query results and creates new message object for each row
            while(rs.next()){
                Object[] row = new Object[3];
                row[0] = rs.getString("response");
                row[1] = rs.getString("receiver");
                //row[2] = rs.getString("receiver");
                row[2] = rs.getString("responseType");
                //row[4] = rs.getString("response");
                //row[5] = rs.getString("responseType");

                responses.add(row);
            }

        } catch (SQLException e){
            System.out.println("Error getting messages");
            throw e;
        }

        return responses;
    }

    public List<Message> getAllSentMessages(String senderUsername) throws SQLException{
        //db query to select the user
        String sql = "SELECT * FROM messages WHERE sender = ?";
        List<Message> sentMessages = new ArrayList<>();

        try(Connection con = getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, senderUsername);

            ResultSet rs = pstmt.executeQuery();

            sentMessages = new ArrayList<>();

            //Process all query results and creates new message object for each row
            while(rs.next()){
                Message m = new Message(
                        rs.getString("message"),
                        Account.getAccount(rs.getString("sender")),
                        Account.getAccount(rs.getString("reciever")),
                        Message.getType(rs.getString("type")),
                        rs.getString("response"),
                        Message.getType(rs.getString("responseType"))
                );

                sentMessages.add(m);

            }

            rs.close();
        } catch (SQLException e){
            System.out.println("Error getting messages");
            throw e;
        }

        return sentMessages;
    }



}
