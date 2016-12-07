package urlShort.model;

import urlShort.utils.PasswordGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Created by admin on 12/4/2016.
 */
@Entity
@Table(name = "ACCOUNT")
public class Account {

    @Id
    @Column(name = "ID")
    private String accountId = null;

    @Column(name = "PASSWORD")
    private String password = null;

    public Account(AccountData accountData) {
        this.accountId = accountData.getAccountId();
        this.password = PasswordGenerator.generate();
    }

    public String getAccountId() {
        return accountId;
    }

    public String getPassword() {
        return password;
    }

}
