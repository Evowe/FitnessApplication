package fitness.app.BonusFeatures.Social;

import fitness.app.BadProjectStructureSection.Objects.Account;
import fitness.app.BadProjectStructureSection.Objects.Message;

import java.util.List;

public class SocialViewModel {
    private SocialModel socialModel;

    public SocialViewModel() {
        socialModel = new SocialModel();
    }

    public Object[][] getMessageData(Account account) {
        return socialModel.getMessageData(account);
        //return null;
    }

    public Object[][] getMessageData2(Account account) {
        return socialModel.getMessageData2(account);
        //return null;
    }

    public Object[][] getResponseData(Account account) {
        return socialModel.getResponseData(account);
        //return null;
    }

    public void deleteFriendRequestMessages(String username, String requesterUsername) {
        socialModel.deleteFriendRequestMessages(username, requesterUsername);
    }

    public void acceptFriendRequestMessages(String username, String requesterUsername) {
        socialModel.acceptFriendRequestMessages(username, requesterUsername);
    }

    public Object[] getMessageColumns() {
        return socialModel.getMessageColumns();
    }

    public Object[] getResponseColumns() {
        return socialModel.getResponseColumns();
    }

    public Object[][] getUserData() {
        return socialModel.getUserData();
    }

    public void setReceiver(Account account) {
        socialModel.setReceiver(account);
    }

    public Account getReceiver() {
        return socialModel.getReceiver();
    }

    public Object[] getUserColumns() {
        return socialModel.getUserColumns();
        //return null;
    }

    public Account selectUser(int row) {
        return socialModel.selectUser(row);
    }

    public void sendMessage(String message, Account sender, Account receiver, Message.Type type) {
        socialModel.createMessage(message, sender, receiver, type);
    }

    public void requestFriend(String senderUsername, String receiverUsername) {
        socialModel.requestFriend(senderUsername, receiverUsername);
    }

    public Object[][] getFriendData(Account account) {
        return socialModel.getFriendData(account.getUsername());
    }

    public Object[] getFriendColumns(){
        return socialModel.getFriendColumns();
    }

    public void acceptFriendRequest(String senderUsername, String receiverUsername) {
        socialModel.acceptFriendRequest(senderUsername, receiverUsername);
    }

    public int getSelectedRow(){
        return socialModel.getSelectedRow();
    }

    public void setSelectedRow(int row) {
        socialModel.setSelectedRow(row);
    }

    public int getAccountCount(){
        return socialModel.getAccountCount();
    }

    public List<String> getPendingRequests(String username){
        return socialModel.getPendingRequests(username);
    }

    public void setFriendRequestUsername(String username){
        socialModel.setFriendRequestUsername(username);
    }

    public String getFriendRequestUsername(){
        return socialModel.getFriendRequestUsername();
    }

    public void declineFriendRequest(String senderUsername, String receiverUsername) {
        socialModel.declineFriendRequest(senderUsername, receiverUsername);
    }

    public void respondToMessage(Message message) {
        socialModel.respondToMessage(message);
    }

    public void deleteMessage(Message message) {
        socialModel.deleteMessage(message);
    }
}
