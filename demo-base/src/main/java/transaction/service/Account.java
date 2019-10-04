package transaction.service;


/**
 * 
 * 
 * 
 * @author yuezh2   2019年10月4日 下午11:25:21
 *
 */
public class Account  implements java.io.Serializable {
    private int id;
    private String accountName;
    private String user;
    private String money;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", accountName='" + accountName + '\'' +
                ", user='" + user + '\'' +
                ", money='" + money + '\'' +
                '}';
    }
}
