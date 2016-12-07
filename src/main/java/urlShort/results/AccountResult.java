package urlShort.results;

import urlShort.model.Account;

/**
 * Created by admin on 12/4/2016.
 */
public class AccountResult {
    private boolean isSuccess;
    private String description;
    private String password;

    public AccountResult(Account account) {
        this.isSuccess = true;
        this.description = "Your account is opened";
        this.password = account.getPassword();

    }

    public AccountResult(Exception e) {
        this.isSuccess = false;
        this.description = "account with that ID already exists";
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getDescription() {
        return description;
    }

    public String getPassword() {
        return password;
    }
}
