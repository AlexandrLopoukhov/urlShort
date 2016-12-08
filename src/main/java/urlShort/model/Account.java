package urlShort.model;

import urlShort.utils.PasswordGenerator;


/**
 * Created by admin on 12/4/2016.
 */

public class Account {

    private int accountId;
    private String password = null;

    public Account(AccountData accountData) {
        this.accountId = accountData.getAccountId();
        this.password = PasswordGenerator.generate();
    }

    public int getAccountId() {
        return accountId;
    }

    public String getPassword() {
        return password;
    }

}
