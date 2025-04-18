package fitness.app.Social;

import fitness.app.Objects.Account;

public class SocialViewModel {
    private SocialModel socialModel;

    public SocialViewModel() {
        socialModel = new SocialModel();
    }

    public Object[][] getMessageData(Account account) {
        return socialModel.getMessageData(account);
        //return null;
    }

    public Object[] getMessageColumns() {
        return socialModel.getMessageColumns();
        //return null;
    }



}
