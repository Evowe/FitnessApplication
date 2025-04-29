package Application.Utility.Objects;

import Application.Databases.DatabaseManager;
import Application.Databases.MessagesDB;

import java.sql.SQLException;
import java.util.Objects;

public class Message {
    private String message;
    private Account sender;
    private Account receiver;
    private Type type;

    private String response;
    private Type responseType;

    private static MessagesDB getMessagesDB() {
        return DatabaseManager.getMessagesDB();
    }

    public enum Type {CHALLENGE, FRIEND_REQUEST, ACCEPT_FRIEND, REJECT_FRIEND, ACCEPT_CHALLENGE, REJECT_CHALLENGE, NOT_READ};

    public Message(String message, Account sender, Account receiver, Type type) {
        this.message = message;
        this.sender = sender;
        this.receiver = receiver;
        this.type = type;

        response = "";
        responseType = Type.NOT_READ;
    }

    public Message(String message, Account sender, Account receiver, Type type, String response, Type responseType) {
        this.message = message;
        this.sender = sender;
        this.receiver = receiver;
        this.type = type;
        this.response = response;
        this.responseType = responseType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Account getSender() {
        return sender;
    }

    public void setSender(Account sender) {
        this.sender = sender;
    }

    public Account getReceiver() {
        return receiver;
    }

    public void setReceiver(Account receiver) {
        this.receiver = receiver;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public static Type getType(String type) {
        if (type.equals("CHALLENGE")) {
            return Type.CHALLENGE;
        }
        else if (type.equals("FRIEND_REQUEST")) {
            return Type.FRIEND_REQUEST;
        }
        else if (type.equals("ACCEPT_FRIEND")) {
            return Type.ACCEPT_FRIEND;
        }
        else if (type.equals("REJECT_FRIEND")) {
            return Type.REJECT_FRIEND;
        }
        else if (type.equals("ACCEPT_CHALLENGE")) {
            return Type.ACCEPT_CHALLENGE;
        }
        else if (type.equals("NOT_READ")) {
            return Type.NOT_READ;
        }
        return null;
    }


    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Type getResponseType() {
        return responseType;
    }

    public void setResponseType(Type responseType) {
        this.responseType = responseType;
    }

    public String getSenderUsername() {
        return sender.getUsername();
    }

    public String getReceiverUsername() {
        return receiver.getUsername();
    }

    public String getTypeString() {
        if (type == Type.CHALLENGE) {
            return "CHALLENGE";
        }
        if (type == Type.FRIEND_REQUEST) {
            return "FRIEND_REQUEST";
        }
        if (type == Type.ACCEPT_FRIEND) {
            return "ACCEPT_FRIEND";
        }
        if (type == Type.REJECT_FRIEND) {
            return "REJECT_FRIEND";
        }
        if (type == Type.ACCEPT_CHALLENGE) {
            return "ACCEPT_CHALLENGE";
        }
        if (type == Type.REJECT_CHALLENGE) {
            return "REJECT_CHALLENGE";
        }
        if (type == Type.NOT_READ) {
            return "NOT_READ";
        }

        return "";
    }

    public String getResponseTypeString() {
        if (responseType == Type.CHALLENGE) {
            return "CHALLENGE";
        }
        if (responseType == Type.FRIEND_REQUEST) {
            return "FRIEND_REQUEST";
        }
        if (responseType == Type.ACCEPT_FRIEND) {
            return "ACCEPT_FRIEND";
        }
        if (responseType == Type.REJECT_FRIEND) {
            return "REJECT_FRIEND";
        }
        if (responseType == Type.ACCEPT_CHALLENGE) {
            return "ACCEPT_CHALLENGE";
        }
        if (responseType == Type.REJECT_CHALLENGE) {
            return "REJECT_CHALLENGE";
        }
        if (responseType == Type.NOT_READ) {
            return "NOT_READ";
        }

        return "";
    }

    public void addMessage() {
        try{
            getMessagesDB().addMessage(this);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public Object [] getString(){
        Object [] str = new Object [6];
        str[0] = this.message;
        str[1] = sender.getUsername();
        str[2] = receiver.getUsername();
        str[3] = this.getTypeString();
        str[4] = this.response;
        str[5] = this.responseType;

        return str;
    }



    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Message message1)) return false;
        return Objects.equals(message, message1.message) && Objects.equals(sender, message1.sender) && Objects.equals(receiver, message1.receiver) && type == message1.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, sender, receiver, type);
    }
}
