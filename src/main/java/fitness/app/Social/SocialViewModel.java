package fitness.app.Social;

import fitness.app.Objects.Account;
import fitness.app.Objects.Message;

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

    public Object[] getMessageColumns() {
        return socialModel.getMessageColumns();
        //return null;
    }

    public Object[][] getUserData() {
        return socialModel.getUserData();
        //return null;
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


}
