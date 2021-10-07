package Beans;

import DAO.CouponsDAO;
import DBDAO.CouponsDBDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Company {
    private int id;
    private String name;
    private String email;
    private String password;
    private List<Coupon> coupons;

    /**
     * A full class constructor for the creation of a full-field Company object.
     *
     * @param id       - The id of the company.
     * @param name     - The name of the company.
     * @param email    - The email of the company.
     * @param password - The password of the company.
     */
    public Company(int id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        coupons = new ArrayList<>();
    }

    /**
     * A partial class constructor for the creation of a Company object with an auto incrementing
     * id at the data base.
     *
     * @param name     - The name of the company.
     * @param email    - The email of the company.
     * @param password - The password of the company.
     */
    public Company(String name, String email, String password) {
        this(0, name, email, password);
        coupons = new ArrayList<>();
    }

    /**
     * A default constructor for receiving Company objects from the data base.
     */
    public Company() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        CouponsDBDAO couponsDBDAO = new CouponsDBDAO();
        try {
            return "Company details {" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", email='" + email + '\'' +
                    ", password='" + password + '\'' +
                    ", coupons=" + couponsDBDAO.getCompanyCoupons(this.id) +
                    '}'+"\n";
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}