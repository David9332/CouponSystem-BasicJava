package DAO;

import Beans.Company;
import Beans.Coupon;
import Exceptions.UserErrorException;

import java.sql.SQLException;
import java.util.*;

public interface CouponsDAO {
    /**
     * Checks if a coupon exist in the data base (by coupon id and company id).
     *
     * @param couponID  - Name of the company.
     * @param companyID - Email of the company.
     * @return boolean (true or false).
     * @throws SQLException - Incorrect SQL command.
     */
    boolean doesCouponExist(int couponID, int companyID) throws SQLException, UserErrorException;

    /**
     * Checks if a coupon of a company exists in the data base (by company id and coupon title).
     *
     * @param coupon - A coupon object.
     * @return - Boolean (true or false).
     * @throws SQLException - Incorrect SQL command.
     */
    boolean doesCompanyCouponsExistByCompanyIdAndTitle(int companyId, String title) throws SQLException, UserErrorException;

    /**
     * Adds a coupon to the data base.
     *
     * @param coupon - A Coupon object.
     */
    void addCoupon(Coupon coupon);

    /**
     * Updates a coupon in the data base.
     *
     * @param coupon - A Coupon object.
     */
    void updateCoupon(Coupon coupon);

    /**
     * Updates a coupon in the data base.
     *
     * @param coupon - A Coupon object.
     */
    void updateCouponAmount(int couponID);

    /**
     * Delete a coupon from the data base (by id).
     *
     * @param couponID - The id of the coupon.
     */
    void deleteCoupon(int couponID);

    /**
     * Gets all the coupons in the data base into a list, and prints their details.
     *
     * @return - A list of Coupon objects.
     * @throws SQLException - Incorrect SQL command.
     */
    List<Coupon> getAllCoupons() throws SQLException;

    /**
     * Gets all of a specific company coupons into a list, and prints their details.
     *
     * @param companyID - The id of the company.
     * @return - A list of company objects.
     * @throws SQLException - Incorrect SQL command.
     */
    List<Coupon> getCouponsByCompany(int companyID) throws SQLException;


    /**
     * Gets all of a specific company coupons into a list.
     *
     * @param companyID - The id of the company.
     * @return - A list of company objects.
     * @throws SQLException - Incorrect SQL command.
     */
    List<Coupon> getCompanyCoupons(int companyID) throws SQLException;

    /**
     * Gets all coupon of a specific company, that are under a given price or equal to it,
     * puts them into a list and prints their details.
     *
     * @param companyID - The id of the company.
     * @param maxPrice  - The upper price limit of the coupons.
     * @return - A list of Coupon objects.
     * @throws SQLException - Incorrect SQL command.
     */
    List<Coupon> getCompanyCouponsByMaxPrice(int companyID, double maxPrice) throws SQLException;

    /**
     * Gets coupons of a company by a specific category.
     *
     * @param companyID  - The id of the company.
     * @param categoryID - The id of the category.
     * @return - A list of Coupon objects.
     * @throws SQLException       - Incorrect SQL command.
     * @throws UserErrorException - Category does not exists.
     */
    List<Coupon> getCompanyCouponsByCategory(int companyID, int categoryID) throws SQLException, UserErrorException;

    /**
     * Gets a specific coupon from the data base and prints it.
     *
     * @param couponID - The id of the coupon.
     * @return - A Coupon object.
     * @throws SQLException       - Incorrect SQL command.
     * @throws UserErrorException - Coupon does not exist.
     */
    Coupon getOneCoupon(int couponID) throws SQLException, UserErrorException;

    /**
     * Checks if a coupon was already purchased by a customer.
     *
     * @param customerID - The id of the customer.
     * @param couponID   - The id of the coupon.
     * @return - Boolean
     * @throws SQLException       - Incorrect SQL command.
     * @throws UserErrorException - This coupon was already purchased by this customer.
     */
    boolean wasCouponPurchasedByCustomer(int customerID, int couponID) throws SQLException, UserErrorException;

    /**
     * Creates a purchase of a coupon by a customer.
     *
     * @param customerID - The id of the customer.
     * @param couponID   - The id of the coupon.
     */
    void addCouponPurchase(int customerID, int couponID);

    /**
     * Deletes a purchase of a coupon (by customer id and coupon id).
     *
     * @param customerID - The id of the customer.
     * @param couponID   - The id of the coupon.
     */
    void deleteCouponPurchase(int customerID, int couponID);

    /**
     * Uses 3 parameters, typed by the user at the Coupon constructor,
     * to create a date of a Date-class type. Returns the result to the Coupon constructor.
     *
     * @param year  - Year typed by the user.
     * @param month - Month typed by the user.
     * @param day   - Day typed by the user.
     * @return - Date-class date.
     */
    Date getDate(int year, int month, int day);

    /**
     * Delete coupons with an expired end-date from the data base.
     */
    void deleteExpiredCoupons() throws SQLException;
}