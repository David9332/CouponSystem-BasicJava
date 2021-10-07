package LoginManager;

public enum ClientType {
    ADMINISTRATOR("admin@admin.com", "admin"),
    COMPANY(),
    CUSTOMER();

    private String email;
    private String password;

    /**
     * A constructor for the ADMINISTRATOR field of this class.
     *
     * @param email    - The email of the administrator.
     * @param password - The password of the administrator.
     */
    ClientType(String email, String password) {
        this.email = email;
        this.password = password;
    }

    /**
     * A default constructor for the COMPANY and CUSTOMER fields of this class.
     */
    ClientType() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
