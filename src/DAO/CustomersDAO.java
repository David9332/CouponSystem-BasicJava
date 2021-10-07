package DAO;

import Beans.Category;
import Beans.Coupon;
import Beans.Customer;
import Exceptions.UserErrorException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface CustomersDAO {

    /**
     * A login method for allowing a specific customer to work with the program.
     *
     * @param email    - The email of the customer.
     * @param password - The password of the customer.
     * @return - A Customer object.
     * @throws SQLException       - Incorrect SQL command.
     * @throws UserErrorException - User's typing error, or the account does not exist.
     */
    Customer login(String email, String password) throws SQLException, UserErrorException;

    /**
     * Checks if the customer exists in the data base (by email and password).
     *
     * @param email    - The email of the customer.
     * @return - Boolean (true or false).
     * @throws SQLException - Incorrect SQL command.
     */
    boolean doesCustomerExist(String email) throws SQLException;

    /**
     * Checks if the customer exists in the data base (by id).
     *
     * @param customerID - The id of the customer.
     * @return - Boolean (true or false).
     * @throws SQLException - Incorrect SQL command.
     */
    boolean doesCustomerExistsByID(int customerID) throws SQLException;

    /**
     * Adds a customer to the data base.
     *
     * @param customer - A Customer object.
     */
    void addCustomer(Customer customer);

    /**
     * Updates a customer in the data base.
     *
     * @param customer - A Customer object.
     */
    void updateCustomer(Customer customer);

    /**
     * Deletes a customer from the data base.
     *
     * @param customerID - The id of the customer.
     */
    void deleteCustomer(int customerID);

    /**
     * Receive all customers from the data base to a list, and prints their details.
     *
     * @return - A list of Customer objects.
     * @throws SQLException - Incorrect SQL command.
     */
    List<Customer> getAllCustomers() throws SQLException;

    /**
     * Receive one customer (by id) and prints his details.
     *
     * @param customerID - The id of the customer.
     * @return - A Customer object.
     * @throws SQLException - Incorrect SQL command.
     */
    Customer getOneCustomer(int customerID) throws SQLException, UserErrorException;

    /**
     * Receives all the coupons, that were purchased by the customer, from the data base
     * to a list, and prints their details.
     *
     * @param customerID - The id of the customer.
     * @return - A list of Coupon objects.
     * @throws SQLException - Incorrect SQL command.
     */
    List<Coupon> getCustomerPurchasedCoupons(int customerID) throws SQLException;

    /**
     * Receives all the coupons, that were purchased by the customer, from the data base
     * to a list.
     *
     * @param customerID - The id of the customer.
     * @return - A list of Coupon objects.
     * @throws SQLException - Incorrect SQL command.
     */
    List<Coupon> getCustomerCoupons(int customerID) throws SQLException;

    /**
     * Receives all the coupons that were purchased by the customer, that belongs to a
     * specific category, from the data base to a list. Prints their details.
     *
     * @param customerID - The id of the customer.
     * @param category   - A specific category.
     * @return - A list of Coupon objects.
     * @throws SQLException - Incorrect SQL command.
     */
    List<Coupon> getCustomerCouponsByCategory(int customerID, Category category) throws SQLException;

    /**
     * Gets all coupons of a specific customer, that are under or equal to a given price,
     * puts them into a list and prints their details.
     *
     * @param customerID - The id of the customer.
     * @param maxPrice   - The upper price limit of the coupons.
     * @return - A list of Coupon objects.
     * @throws SQLException - Incorrect SQL command.
     */
    List<Coupon> getCustomerCouponsByMaxPrice(int customerID, double maxPrice) throws SQLException;

    /**
     * Prints a customer's details.
     *
     * @param customerID - The id of the customer.
     * @throws SQLException - Incorrect SQL command.
     */
    void showCustomerDetails(int customerID) throws SQLException, UserErrorException;
}
