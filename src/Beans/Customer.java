package Beans;

import DBDAO.CouponsDBDAO;
import DBDAO.CustomersDBDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Customer {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<Coupon> coupons;

    /**
     * A full class constructor for the creation of a full-field Customer object.
     *
     * @param id        - Id of the customer. Primary key.
     * @param firstName - First name of the customer.
     * @param lastName  - Last name of the customer.
     * @param email     - Email of the customer.
     * @param password  - Password of the customer.
     */
    public Customer(int id, String firstName, String lastName, String email, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.coupons = new ArrayList<>();
    }

    /**
     * A partial class constructor for the creation of a Customer object with auto incrementing
     * id at the data base.
     *
     * @param firstName - First name of the customer.
     * @param lastName  - Last name of the customer.
     * @param email     - Email of the customer.
     * @param password  - Password of the customer.
     */
    public Customer(String firstName, String lastName, String email, String password) {
        this(0, firstName, lastName, email, password);
        this.coupons = new ArrayList<>();
    }

    /**
     * A default constructor for receiving Customer objects from the data base.
     */
    public Customer() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public List<Coupon> getCoupons() {
        return coupons;
    }

    public void setCoupons(List<Coupon> coupons) {
        this.coupons = coupons;
    }

    @Override
    public String toString() {
        CustomersDBDAO customersDBDAO = new CustomersDBDAO();
        try {
            return "Customer{" +
                    "id=" + id +
                    ", firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    ", email='" + email + '\'' +
                    ", password='" + password + '\'' +
                    ", coupons=" + customersDBDAO.getCustomerCoupons(this.id) +
                    '}'+"\n";
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
