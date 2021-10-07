
package Facades;

import Beans.Category;
import Beans.Company;
import Beans.Coupon;
import Beans.Customer;
import Exceptions.UserErrorException;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class CustomerFacade extends ClientFacade {
    private static Customer customerLoggedIn;


    /**
     * A default constructor that is used in the "LoginManager" class, "login" method.
     * Creates a customerFacade object that allows the use of the method "login".
     */
    public CustomerFacade() {
    }

    /**
     * Checks if the email and password, that were typed by the user, matches the user's details
     * in the data base.
     *
     * @param email    - The customer's email.
     * @param password - The customer's password.
     * @return - A Customer object.
     * @throws UserErrorException - Incorrect email or password typed by the user.
     * @throws SQLException       - Incorrect sql command.
     */
    public Customer login(String email, String password) throws SQLException, UserErrorException {
        customerLoggedIn = customersDAO.login(email, password);
        return customerLoggedIn;
    }

    /**
     * Gets a coupon from the data base (by id), checks that:
     * 1. It wasn't already purchased by the customer.
     * 2. That there are more the 0 coupons of that kind.
     * 3. That its end-date had not already passed.
     * If all the above are ture, it adds a purchase and decreases the amount of coupons by 1.
     *
     * @param couponID - The id of the coupon.
     * @throws SQLException       - Incorrect sql command.
     * @throws UserErrorException - zero amount of that coupon, passed end-date,
     *                            coupon already purchased by that customer.
     */
    public void purchaseCoupon(int couponID) throws SQLException, UserErrorException {
        Date date = new Date();
        Coupon coupon = couponsDAO.getOneCoupon(couponID);
        boolean Result = couponsDAO.wasCouponPurchasedByCustomer(customerLoggedIn.getId(), couponID);
        if (coupon != null && !Result && coupon.getAmount() > 0 &&
                date.before(coupon.getEndDate())) {
            couponsDAO.addCouponPurchase(customerLoggedIn.getId(), couponID);
            couponsDAO.updateCouponAmount(couponID);
        } else {
            throw new UserErrorException("'CustomerFacade'", "'purchaseCoupon'", "Either the end date of the coupon has already passed," +
                    " the customer had already purchased that coupon or there are no more coupons of that kind.");
        }
    }

    /**
     * Gets from the data base all the coupons that were purchased by a specific customer,
     * puts them into a list, and prints their details.
     *
     * @return - A list of Coupon objects.
     * @throws SQLException - Incorrect sql command.
     */
    public List<Coupon> getCustomerPurchasedCoupons() throws SQLException {
        List<Coupon> coupons = customersDAO.getCustomerPurchasedCoupons(customerLoggedIn.getId());
        return coupons;
    }

    /**
     * Gets from the data base all the coupons that were purchased by a specific customer
     * that belongs to a specific category. Then it puts them into a list, and prints
     * their details.
     *
     * @param category - A Category enum.
     * @return - A list of Coupon objects.
     * @throws SQLException - Incorrect sql command.
     */
    public List<Coupon> getCustomerCouponsByCategory(Category category) throws SQLException {
        return customersDAO.getCustomerCouponsByCategory(customerLoggedIn.getId(), category);
    }

    /**
     * Gets all the coupons of a specific customer, under or equal to a given price, from the data base.
     * Then it puts them into a list, and prints their details.
     *
     * @param maxPrice - The price's upper limit.
     * @return - A list of Customer objects.
     * @throws SQLException - Incorrect sql command.
     */
    public List<Coupon> getCustomerCouponsByMaxPrice(double maxPrice) throws SQLException {
        List<Coupon> couponsByMaxPrice = customersDAO.getCustomerCouponsByMaxPrice(customerLoggedIn.getId(), maxPrice);
        return couponsByMaxPrice;
    }

    /**
     * Gets a customer from the data base, and prints its details.
     *
     * @throws SQLException - Incorrect sql command.
     */
    public void showCustomerDetails() throws SQLException, UserErrorException {
        customersDAO.showCustomerDetails(customerLoggedIn.getId());
    }
}