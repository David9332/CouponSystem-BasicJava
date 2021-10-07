package DBDAO;

import Beans.Category;
import Beans.Coupon;
import Beans.Customer;
import DAO.CustomersDAO;
import DB.DBUtils;
import Exceptions.UserErrorException;

import java.sql.*;
import java.util.*;

public class CustomersDBDAO implements CustomersDAO {
    private static final String DOES_CUSTOMER_EXIST = "SELECT * FROM `couponsdb`.`customers` WHERE `EMAIL`=?";
    private static final String DOES_CUSTOMER_EXIST_BY_ID = "SELECT * FROM `couponsdb`.`customers` WHERE `ID`=?";
    private static final String ADD_CUSTOMER = "INSERT INTO `couponsdb`.`customers` (`ID`,`FIRST_NAME`,`LAST_NAME`,`EMAIL`,`PASSWORD`) VALUES (?,?,?,?,?)";
    private static final String UPDATE_CUSTOMER = "UPDATE `couponsdb`.`customers` SET FIRST_NAME=? , LAST_NAME=? , EMAIL=?, PASSWORD=? WHERE ID=?";
    private static final String DELETE_CUSTOMER = "DELETE FROM `couponsdb`.`customers` WHERE ID=?";
    private static final String GET_ALL_CUSTOMERS = "SELECT * FROM `couponsdb`.`customers`";
    private static final String GET_ONE_CUSTOMER = "SELECT * FROM `couponsdb`.`customers` WHERE ID=?";
    private static final String GET_ALL_CUSTOMER_PURCHASED_COUPONS = "SELECT * FROM couponsdb.customers_vs_coupons t1 INNER JOIN couponsdb.coupons t2 ON t1.COUPON_ID = t2.ID WHERE CUSTOMER_ID = ?";
    private static final String GET_CUSTOMER_PURCHASED_COUPONS_BY_CATEGORY = "SELECT * FROM couponsdb.customers_vs_coupons t1 INNER JOIN couponsdb.coupons t2 ON t1.COUPON_ID = t2.ID WHERE CUSTOMER_ID=? AND CATEGORY_ID=?";
    private static final String GET_CUSTOMER_PURCHASED_COUPONS_BY_PRICE = "SELECT * FROM couponsdb.customers_vs_coupons t1 INNER JOIN couponsdb.coupons t2 ON t1.COUPON_ID = t2.ID WHERE CUSTOMER_ID=? AND PRICE<=?";
    private static final String DELETE_COUPON_PURCHASE = "DELETE FROM `couponsdb`.`customers_vs_coupons` WHERE `CUSTOMER_ID` = ?";
    private static final String LOGIN = "SELECT * FROM `couponsdb`.`customers` WHERE EMAIL=? AND PASSWORD=?";

    /**
     * A login method for allowing a certain specific to work with the program.
     *
     * @param email    - The email of the customer.
     * @param password - The password of the customer.
     * @return - A Customer object.
     * @throws SQLException       - Incorrect SQL command.
     * @throws UserErrorException - User's typing error, or the account does not exist.
     */
    @Override
    public Customer login(String email, String password) throws SQLException, UserErrorException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, email);
        params.put(2, password);
        ResultSet resultSet = DBUtils.runQueryWithResult(LOGIN, params);
        if (resultSet.next()) {
            Customer customer = new Customer(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            );
            return customer;
        }
        throw new UserErrorException("'CustomersDBDAO'", "'login'", "Could either be a typing" +
                " error, or that the account you are looking for does not exist.");
    }

    /**
     * Checks if the customer exists in the data base (by email and password).
     *
     * @param email - The email of the customer.
     * @return - Boolean (true or false).
     * @throws SQLException - Incorrect SQL command.
     */
    @Override
    public boolean doesCustomerExist(String email) throws SQLException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, email);
        ResultSet resultSet = DBUtils.runQueryWithResult(DOES_CUSTOMER_EXIST, params);
        if (resultSet.next()) {
            return true;
        }
        return false;
    }

    /**
     * Checks if the customer exists in the data base (by id).
     *
     * @param customerID - The id of the customer.
     * @return - Boolean (true or false).
     * @throws SQLException - Incorrect SQL command.
     */
    @Override
    public boolean doesCustomerExistsByID(int customerID) throws SQLException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customerID);
        ResultSet resultSet = DBUtils.runQueryWithResult(DOES_CUSTOMER_EXIST_BY_ID, params);
        if (resultSet.next()) {
            return true;
        }
        return false;
    }

    /**
     * Adds a customer to the data base.
     *
     * @param customer - A Customer object.
     */
    public void addCustomer(Customer customer) {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customer.getId());
        params.put(2, customer.getFirstName());
        params.put(3, customer.getLastName());
        params.put(4, customer.getEmail());
        params.put(5, customer.getPassword());
        DBUtils.runQuery(ADD_CUSTOMER, params);
    }

    /**
     * Updates a customer in the data base.
     *
     * @param customer - A Customer object.
     */
    @Override
    public void updateCustomer(Customer customer) {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customer.getFirstName());
        params.put(2, customer.getLastName());
        params.put(3, customer.getEmail());
        params.put(4, customer.getPassword());
        params.put(5, customer.getId());
        DBUtils.runQuery(UPDATE_CUSTOMER, params);
    }

    /**
     * Deletes a customer from the data base.
     *
     * @param customerID - The id of the customer.
     */
    @Override
    public void deleteCustomer(int customerID) {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customerID);
        DBUtils.runQuery(DELETE_CUSTOMER, params);
    }

    /**
     * Receive all customers from the data base to a list, and prints their details.
     *
     * @return - A list of Customer objects.
     * @throws SQLException - Incorrect SQL command.
     */
    @Override
    public List<Customer> getAllCustomers() throws SQLException {
        List<Customer> customers = new ArrayList<>();
        ResultSet resultSet = DBUtils.runQueryWithResult(GET_ALL_CUSTOMERS);
        while (resultSet.next()) {
            Customer customer = new Customer(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            );
            customers.add(customer);
//            System.out.println(customer);
        }
        return customers;
    }

    /**
     * Receives all the coupons, that were purchased by the customer, from the data base
     * to a list, and prints their details.
     *
     * @param customerID - The id of the customer.
     * @return - A list of Coupon objects.
     * @throws SQLException - Incorrect SQL command.
     */
    @Override
    public List<Coupon> getCustomerPurchasedCoupons(int customerID) throws SQLException {
        Map<Integer, Object> params = new HashMap<>();
        List<Coupon> coupons = new ArrayList<>();
        params.put(1, customerID);
        ResultSet resultSet = DBUtils.runQueryWithResult(GET_ALL_CUSTOMER_PURCHASED_COUPONS, params);
        while (resultSet.next()) {
            Coupon coupon = new Coupon(
                    resultSet.getInt(3),
                    resultSet.getInt(4),
                    resultSet.getInt(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getDate(8),
                    resultSet.getDate(9),
                    resultSet.getInt(10),
                    resultSet.getDouble(11),
                    resultSet.getString(12)
            );
            coupons.add(coupon);
            System.out.println(coupon);
        }
        return coupons;
    }

    /**
     * Receives all the coupons, that were purchased by the customer, from the data base
     * to a list.
     *
     * @param customerID - The id of the customer.
     * @return - A list of Coupon objects.
     * @throws SQLException - Incorrect SQL command.
     */
    @Override
    public List<Coupon> getCustomerCoupons(int customerID) throws SQLException {
        Map<Integer, Object> params = new HashMap<>();
        List<Coupon> coupons = new ArrayList<>();
        params.put(1, customerID);
        ResultSet resultSet = DBUtils.runQueryWithResult(GET_ALL_CUSTOMER_PURCHASED_COUPONS, params);
        while (resultSet.next()) {
            Coupon coupon = new Coupon(
                    resultSet.getInt(3),
                    resultSet.getInt(4),
                    resultSet.getInt(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getDate(8),
                    resultSet.getDate(9),
                    resultSet.getInt(10),
                    resultSet.getDouble(11),
                    resultSet.getString(12)
            );
            coupons.add(coupon);
        }
        return coupons;
    }

    /**
     * Receives all the coupons that were purchased by the customer, that belongs to a
     * specific category, from the data base to a list. Prints their details.
     *
     * @param customerID - The id of the customer.
     * @param category   - A specific category.
     * @return - A list of Coupon objects.
     * @throws SQLException - Incorrect SQL command.
     */
    @Override
    public List<Coupon> getCustomerCouponsByCategory(int customerID, Category category) throws SQLException {
        Map<Integer, Object> params = new HashMap<>();
        List<Coupon> couponsByCategory = new ArrayList<>();
        params.put(1, customerID);
        params.put(2, category.getCategoryId());
        ResultSet resultSet = DBUtils.runQueryWithResult(GET_CUSTOMER_PURCHASED_COUPONS_BY_CATEGORY, params);
        while (resultSet.next()) {
            Coupon coupon = new Coupon(
                    resultSet.getInt(3),
                    resultSet.getInt(4),
                    resultSet.getInt(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getDate(8),
                    resultSet.getDate(9),
                    resultSet.getInt(10),
                    resultSet.getDouble(11),
                    resultSet.getString(12)
            );
            couponsByCategory.add(coupon);
            System.out.println(coupon);
        }
        return couponsByCategory;
    }

    /**
     * Gets all coupons of a specific customer, that are under or equal to a given price,
     * puts them into a list and prints their details.
     *
     * @param customerID - The id of the customer.
     * @param maxPrice   - The upper price limit of the coupons.
     * @return - A list of Coupon objects.
     * @throws SQLException - Incorrect SQL command.
     */
    @Override
    public List<Coupon> getCustomerCouponsByMaxPrice(int customerID, double maxPrice) throws SQLException {
        Map<Integer, Object> params = new HashMap<>();
        List<Coupon> couponsByMaxPrice = new ArrayList<>();
        params.put(1, customerID);
        params.put(2, maxPrice);
        ResultSet resultSet = DBUtils.runQueryWithResult(GET_CUSTOMER_PURCHASED_COUPONS_BY_PRICE, params);
        while (resultSet.next()) {
            Coupon coupon = new Coupon(
                    resultSet.getInt(3),
                    resultSet.getInt(4),
                    resultSet.getInt(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getDate(8),
                    resultSet.getDate(9),
                    resultSet.getInt(10),
                    resultSet.getDouble(11),
                    resultSet.getString(12)
            );
            couponsByMaxPrice.add(coupon);
            System.out.println(coupon);
        }
        return couponsByMaxPrice;
    }

    /**
     * Receive one customer (by id) and prints his details.
     *
     * @param customerID - The id of the customer.
     * @return - A Customer object.
     * @throws SQLException - Incorrect SQL command.
     */
    @Override
    public Customer getOneCustomer(int customerID) throws SQLException, UserErrorException {
        Customer customer = new Customer();
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customerID);
        ResultSet resultSet = DBUtils.runQueryWithResult(GET_ONE_CUSTOMER, params);
        if (resultSet.next()) {
            customer = new Customer(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            );
            System.out.println(customer);
            return customer;
        } else {
            throw new UserErrorException("'CustomerDBDAO'", "'getOneCustomer'", "There is no customer with that ID.");
        }
    }

    /**
     * Prints a customer's details.
     *
     * @param customerID - The id of the customer.
     * @throws SQLException - Incorrect SQL command.
     */
    @Override
    public void showCustomerDetails(int customerID) throws SQLException, UserErrorException {
        getOneCustomer(customerID);
    }
}
