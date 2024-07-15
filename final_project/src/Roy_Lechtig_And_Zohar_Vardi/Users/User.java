package Roy_Lechtig_And_Zohar_Vardi.Users;

public abstract class User {
    protected String username;
    protected String password;

    public User( String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User() {
        this.username = "";
        this.password = "";
    }

    public boolean setUserName(String username) {
        this.username = username;
        return true;
    }
    public boolean setPassword(String password) {
        this.password = password;
        return true;
    }

    public String getUserName() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
