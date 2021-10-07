package DBDAO;

import Beans.Company;
import Beans.Coupon;
import Beans.Customer;
import DAO.CouponsDAO;
import DB.ConnectionPool;
import DB.DBUtils;
import Exceptions.UserErrorException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CouponsDBDAO implements CouponsDAO {
    private static final String DOES_COUPONS_EXISTS_BY_COMPANY_ID_AND_TITLE = "SELECT * FROM `couponsdb`.`coupons` WHERE COMPANY_ID=? AND TITLE=?";
    private static final String DOES_COUPON_EXISTS_BY_COUPON_ID_AND_COMPANY_ID = "SELECT * FROM `couponsdb`.`coupons` WHERE ID=? AND COMPANY_ID=?";
    private static final String ADD_COUPON = "INSERT INTO `couponsdb`.`coupons` (`ID`,`COMPANY_ID`,`CATEGORY_ID`,`TITLE`,`DESCRIPTION`,`START_DATE`,`END_DATE`,`AMOUNT`,`PRICE`,`IMAGE`) VALUES (?,?,?,?,?,?,?,?,?,?)";
    private static final String UPDATE_COUPON = "UPDATE `couponsdb`.`coupons` SET CATEGORY_ID=?, TITLE=?, DESCRIPTION=?, START_DATE=?, END_DATE=?, AMOUNT=?, PRICE=?, IMAGE=? WHERE ID=? AND COMPANY_ID=?";
    private static final String UPDATE_COUPON_AMOUNT = "UPDATE `couponsdb`.`coupons` SET AMOUNT=AMOUNT-1 WHERE ID=?";
    private static final String DELETE_COUPON = "DELETE FROM `couponsdb`.`coupons` WHERE ID=?";
    private static final String GET_ALL_COUPONS = "SELECT * FROM `couponsdb`.`coupons`";
    private static final String GET_COUPONS_BY_COMPANY = "SELECT * FROM `couponsdb`.`coupons` WHERE COMPANY_ID=?";
    private static final String GET_COUPONS_BY_COMPANY_AND_CATEGORY = "SELECT * FROM `couponsdb`.`coupons` WHERE COMPANY_ID=? AND CATEGORY_ID=?";
    private static final String GET_COMPANY_COUPONS_LOWER_THEN_MAX_PRICE = "SELECT * FROM `couponsdb`.`coupons` WHERE COMPANY_ID=? AND PRICE<?";
    private static final String GET_ONE_COUPON = "SELECT * FROM `couponsdb`.`coupons` WHERE ID=?";
    private static final String WAS_COUPON_PURCHASED_BY_CUSTOMER = "SELECT * FROM `couponsdb`.`customers_vs_coupons` WHERE CUSTOMER_ID=? AND COUPON_ID=?";
    private static final String ADD_COUPON_PURCHASE = "INSERT INTO `couponsdb`.`customers_vs_coupons`(`CUSTOMER_ID`, `COUPON_ID`) VALUES(?,?)";
    private static final String DELETE_COUPON_PURCHASE = "DELETE FROM `couponsdb`.`customers_vs_coupons` WHERE `CUSTOMER_ID` = ? AND `COUPON_ID` = ?";
    private static final String DELETE_EXPIRED_COUPONS = "DELETE FROM `couponsdb`.`coupons` WHERE END_DATE<?";

    /**
     * Checks if a coupon exist in the data base (by coupon id and company id).
     *
     * @param couponID  - Name of the company.
     * @param companyID - Email of the company.
     * @return boolean (true or false).
     * @throws SQLException - Incorrect SQL command.
     */
    @Override
    public boolean doesCouponExist(int couponID, int companyID) throws SQLException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, couponID);
        params.put(2, companyID);
        ResultSet resultSet = DBUtils.runQueryWithResult(DOES_COUPON_EXISTS_BY_COUPON_ID_AND_COMPANY_ID, params);
        if (resultSet.next()) {
            return true;
        }
        return false;
    }

    /**
     * Checks if a coupon of a company exists in the data (by company id and coupon title) .
     *
     * @param coupon - A coupon object.
     * @return - Boolean (true or false).
     * @throws SQLException - Incorrect SQL command.
     */
    @Override
    public boolean doesCompanyCouponsExistByCompanyIdAndTitle(int companyId, String title) throws SQLException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, companyId);
        params.put(2, title);
        ResultSet resultSet = DBUtils.runQueryWithResult(DOES_COUPONS_EXISTS_BY_COMPANY_ID_AND_TITLE, params);
        if (resultSet.next()) {
            return true;
        }
        return false;
    }

    /**
     * Adds a coupon to the data base.
     *
     * @param coupon - A Coupon object.
     */
    @Override
    public void addCoupon(Coupon coupon) {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, coupon.getId());
        params.put(2, coupon.getCompanyID());
        params.put(3, coupon.getCategoryID());
        params.put(4, coupon.getTitle());
        params.put(5, coupon.getDescription());
        params.put(6, coupon.getStartDate());
        params.put(7, coupon.getEndDate());
        params.put(8, coupon.getAmount());
        params.put(9, coupon.getPrice());
        params.put(10, coupon.getImage());
        DBUtils.runQuery(ADD_COUPON, params);
    }

    /**
     * Updates a coupon in the data base.
     *
     * @param coupon - A Coupon object.
     */
    @Override
    public void updateCoupon(Coupon coupon) {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, coupon.getCategoryID());
        params.put(2, coupon.getTitle());
        params.put(3, coupon.getDescription());
        params.put(4, coupon.getStartDate());
        params.put(5, coupon.getEndDate());
        params.put(6, coupon.getAmount());
        params.put(7, coupon.getPrice());
        params.put(8, coupon.getImage());
        params.put(9, coupon.getId());
        params.put(10, coupon.getCompanyID());
        DBUtils.runQuery(UPDATE_COUPON, params);
    }

    /**
     * Updates the amount of a specific coupon in the data base.
     *
     * @param couponsID - The id of the coupon.
     */
    @Override
    public void updateCouponAmount(int couponsID) {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, couponsID);
        DBUtils.runQuery(UPDATE_COUPON_AMOUNT, params);
    }

    /**
     * Delete a coupon from the data base (by id).
     *
     * @param couponID - The id of the coupon.
     */
    @Override
    public void deleteCoupon(int couponID) {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, couponID);
        DBUtils.runQuery(DELETE_COUPON, params);
    }

    /**
     * Gets all the coupons in the data base into a list, and prints their details.
     *
     * @return - A list of Coupon objects.
     * @throws SQLException - Incorrect SQL command.
     */
    @Override
    public List<Coupon> getAllCoupons() throws SQLException {
        List<Coupon> coupons = new ArrayList<>();
        ResultSet resultSet = DBUtils.runQueryWithResult(GET_ALL_COUPONS);
        while (resultSet.next()) {
            Coupon coupon = new Coupon(
                    resultSet.getInt(1),
                    resultSet.getInt(2),
                    resultSet.getInt(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getDate(6),
                    resultSet.getDate(7),
                    resultSet.getInt(8),
                    resultSet.getDouble(9),
                    resultSet.getString(10)
            );
            coupons.add(coupon);
            System.out.println(coupon);
        }
        return coupons;
    }

    /**
     * Gets all of a specific company coupons into a list, and prints their details.
     *
     * @param companyID - The id of the company.
     * @return - A list of company objects.
     * @throws SQLException - Incorrect SQL command.
     */
    @Override
    public List<Coupon> getCouponsByCompany(int companyID) throws SQLException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, companyID);
        List<Coupon> coupons = new ArrayList<>();
        ResultSet resultSet = DBUtils.runQueryWithResult(GET_COUPONS_BY_COMPANY, params);
        while (resultSet.next()) {
            Coupon coupon = new Coupon(
                    resultSet.getInt(1),
                    resultSet.getInt(2),
                    resultSet.getInt(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getDate(6),
                    resultSet.getDate(7),
                    resultSet.getInt(8),
                    resultSet.getDouble(9),
                    resultSet.getString(10)
            );
            coupons.add(coupon);
//            System.out.println(coupon);
        }
        return coupons;
    }

    /**
     * Gets all of a specific company coupons into a list.
     *
     * @param companyID - The id of the company.
     * @return - A list of company objects.
     * @throws SQLException - Incorrect SQL command.
     */
    @Override
    public List<Coupon> getCompanyCoupons(int companyID) throws SQLException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, companyID);
        List<Coupon> coupons = new ArrayList<>();
        ResultSet resultSet = DBUtils.runQueryWithResult(GET_COUPONS_BY_COMPANY, params);
        while (resultSet.next()) {
            Coupon coupon = new Coupon(
                    resultSet.getInt(1),
                    resultSet.getInt(2),
                    resultSet.getInt(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getDate(6),
                    resultSet.getDate(7),
                    resultSet.getInt(8),
                    resultSet.getDouble(9),
                    resultSet.getString(10)
            );
            coupons.add(coupon);
        }
        return coupons;
    }

    /**
     * Gets all coupon of a specific company, that are under a given price or equal to it,
     * puts them into a list and prints their details.
     *
     * @param companyID - The id of the company.
     * @param maxPrice  - The upper price limit of the coupons.
     * @return - A list of Coupon objects.
     * @throws SQLException - Incorrect SQL command.
     */
    @Override
    public List<Coupon> getCompanyCouponsByMaxPrice(int companyID, double maxPrice) throws SQLException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, companyID);
        params.put(2, maxPrice);
        List<Coupon> coupons = new ArrayList<>();
        ResultSet resultSet = DBUtils.runQueryWithResult(GET_COMPANY_COUPONS_LOWER_THEN_MAX_PRICE, params);
        while (resultSet.next()) {
            Coupon coupon = new Coupon(
                    resultSet.getInt(1),
                    resultSet.getInt(2),
                    resultSet.getInt(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getDate(6),
                    resultSet.getDate(7),
                    resultSet.getInt(8),
                    resultSet.getDouble(9),
                    resultSet.getString(10)
            );
            coupons.add(coupon);
//            System.out.println(coupon);
        }
        return coupons;
    }

    /**
     * Gets coupons of a company by a specific category.
     *
     * @param companyID  - The id of the company.
     * @param categoryID - The id of the category.
     * @return - A list of Coupon objects.
     * @throws SQLException       - Incorrect SQL command.
     * @throws UserErrorException - Category does not exists.
     */
    @Override
    public List<Coupon> getCompanyCouponsByCategory(int companyID, int categoryID) throws SQLException, UserErrorException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, companyID);
        params.put(2, categoryID);
        List<Coupon> coupons = new ArrayList<>();
        ResultSet resultSet = DBUtils.runQueryWithResult(GET_COUPONS_BY_COMPANY_AND_CATEGORY, params);
        while (resultSet.next()) {
            Coupon coupon = new Coupon(
                    resultSet.getInt(1),
                    resultSet.getInt(2),
                    resultSet.getInt(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getDate(6),
                    resultSet.getDate(7),
                    resultSet.getInt(8),
                    resultSet.getDouble(9),
                    resultSet.getString(10)
            );
            coupons.add(coupon);
//            System.out.println(coupon);
        }
        if (categoryID < 1 || categoryID > 4) {
            throw new UserErrorException("CouponsDBDAO", "getCouponsByCompanyAndCategory", "This category does not exist.");
        }
        return coupons;
    }

    /**
     * Gets a specific coupon from the data base and prints it.
     *
     * @param couponID - The id of the coupon.
     * @return - A Coupon object.
     * @throws SQLException       - Incorrect SQL command.
     * @throws UserErrorException - Coupon does not exist.
     */
    @Override
    public Coupon getOneCoupon(int couponID) throws SQLException, UserErrorException {
        Coupon coupon = new Coupon();
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, couponID);
        ResultSet resultSet = DBUtils.runQueryWithResult(GET_ONE_COUPON, params);
        if (resultSet.next()) {
            coupon = new Coupon(
                    resultSet.getInt(1),
                    resultSet.getInt(2),
                    resultSet.getInt(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getDate(6),
                    resultSet.getDate(7),
                    resultSet.getInt(8),
                    resultSet.getDouble(9),
                    resultSet.getString(10)
            );
            return coupon;
        }
        throw new UserErrorException("CouponsDBDAO", "getOneCoupon", "Coupon doesn't exist.");
    }


    /**
     * Checks if a coupon was already purchased by a customer.
     *
     * @param customerID - The id of the customer.
     * @param couponID   - The id of the coupon.
     * @return - Boolean
     * @throws SQLException       - Incorrect SQL command.
     * @throws UserErrorException - This coupon was already purchased by this customer.
     */
    @Override
    public boolean wasCouponPurchasedByCustomer(int customerID, int couponID) throws SQLException, UserErrorException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customerID);
        params.put(2, couponID);
        ResultSet resultSet = DBUtils.runQueryWithResult(WAS_COUPON_PURCHASED_BY_CUSTOMER, params);
        if (resultSet.next()) {
            throw new UserErrorException("CouponsDBDAO", "wasCouponPurchasedByCustomer", "This coupon was already purchased by this customer.");
        }
        return false;
    }

    /**
     * Creates a purchase of a coupon by a customer.
     *
     * @param customerID - The id of the customer.
     * @param couponID   - The id of the coupon.
     */
    @Override
    public void addCouponPurchase(int customerID, int couponID) {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customerID);
        params.put(2, couponID);
        DBUtils.runQuery(ADD_COUPON_PURCHASE, params);
    }

    /**
     * Deletes a purchase of a coupon (by customer id and coupon id).
     *
     * @param customerID - The id of the customer.
     * @param couponID   - The id of the coupon.
     */
    @Override
    public void deleteCouponPurchase(int customerID, int couponID) {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customerID);
        params.put(2, couponID);
        DBUtils.runQuery(DELETE_COUPON_PURCHASE, params);
    }

    /**
     * Uses 3 parameters, typed by the user at the Coupon constructor,
     * to create a date of a Date-class type. Returns the result to the Coupon constructor.
     *
     * @param year  - Year typed by the user.
     * @param month - Month typed by the user.
     * @param day   - Day typed by the user.
     * @return - Date-class date.
     */
    @Override
    public Date getDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, day);
        java.util.Date utilDate = cal.getTime();
        return utilDate;
    }

    /**
     * Delete coupons with an expired end-date from the data base.
     */
    @Override
    public void deleteExpiredCoupons() {
        Date date = new Date();
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, date);
        DBUtils.runQuery(DELETE_EXPIRED_COUPONS, params);
    }
}