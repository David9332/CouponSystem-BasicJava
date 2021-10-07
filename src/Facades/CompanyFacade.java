package Facades;

import Beans.Category;
import Beans.Company;
import Beans.Coupon;
import Beans.Customer;
import DB.DBUtils;
import Exceptions.UserErrorException;

import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.InputMismatchException;
import java.util.List;

public class CompanyFacade extends ClientFacade {
    private static Company companyLoggedIn;


    /**
     * A default constructor that is used in the "LoginManager" class, "login" method.
     * Creates a companyFacade object that allows the use of the method "login".
     */
    public CompanyFacade() {
    }

    /**
     * Checks if the email and password, that were typed by the user, matches the user's details
     * in the data base.
     *
     * @param email    - The company's email.
     * @param password - The company's password.
     * @return - A Company object.
     * @throws UserErrorException - Incorrect email or password typed by the user.
     * @throws SQLException       - Incorrect sql command.
     */
    public Company login(String email, String password) throws SQLException, UserErrorException {
        companyLoggedIn = companiesDAO.login(email, password);
        return companyLoggedIn;
    }

    /**
     * Receives a Coupon object from the user, checks if the coupon already exists in the
     * data base, and if it doesn't - adds it to the data base and to the company's
     * coupons list.
     *
     * @param coupon - A Coupon object. Given by the user.
     * @throws SQLException       - Incorrect sql command.
     * @throws UserErrorException - The coupon already exists in the data base.
     */
    public void addCoupon(Coupon coupon) throws SQLException, UserErrorException {
        boolean result = couponsDAO.doesCompanyCouponsExistByCompanyIdAndTitle(coupon.getCompanyID(), coupon.getTitle());
        if (result) {
            throw new UserErrorException("'CouponsDBDAO'", "'doesCompanyCouponsExistByCompanyIdAndTitle'", "There is already a coupon with this title in the DB. The coupon was NOT added.");
        }
        couponsDAO.addCoupon(coupon);
    }

    /**
     * Receives a Coupon object from the user, checks if the coupon already exists in the
     * data base, and if it does - updates it by the coupon object.
     *
     * @param coupon - A coupon object. Given by the user.
     * @throws SQLException       - Incorrect sql command.
     * @throws UserErrorException - The coupon does not exist.
     */
    public void updateCoupon(Coupon coupon) throws SQLException, UserErrorException {
        boolean result = couponsDAO.doesCouponExist(coupon.getId(), coupon.getCompanyID());
        if (!result) {
            throw new UserErrorException("'CouponsDBDAO'", "'doesCouponExist'", "Coupon does not exist.");
        }
        couponsDAO.updateCoupon(coupon);
    }

    /**
     * Checks if a coupon exists in the data base (by id), and if it does - deletes it.
     *
     * @param couponID - The id of the coupon.
     * @throws SQLException       - Incorrect sql command.
     * @throws UserErrorException - The coupon does not exist in the data base.
     */
    public void deleteCoupon(int couponID) throws SQLException, UserErrorException {
        boolean result = couponsDAO.doesCouponExist(couponID, companyLoggedIn.getId());
        if (result) {
            couponsDAO.deleteCoupon(couponID);
        } else {
            throw new UserErrorException("'CouponDBDAO'", "'doesCouponExist'", "Coupon does not exist for this company. There is nothing to delete.");
        }
    }

    /**
     * Gets all the coupons of a specific company from the data base (by company id), puts them
     * into a list, and prints their details.
     *
     * @return - A list of Coupon objects.
     * @throws SQLException - Incorrect sql command.
     */
    public List<Coupon> getCouponsByCompany() throws SQLException {
        return couponsDAO.getCouponsByCompany(companyLoggedIn.getId());
    }

    public Coupon getOneCoupon(int couponId) throws SQLException, UserErrorException {
        return couponsDAO.getOneCoupon(couponId);
    }

    /**
     * Gets all the coupons of a specific company and a specific category from the data base
     * (by company id and category id), puts them into a list, and prints their details.
     *
     * @return - A list of Coupon objects.
     * @throws SQLException - Incorrect sql command.
     */
    public List<Coupon> getCompanyCouponsByCategory(int categoryID) throws SQLException, UserErrorException {
        return couponsDAO.getCompanyCouponsByCategory(companyLoggedIn.getId(), categoryID);
    }

    /**
     * Gets all the coupons of a specific company, under or equal to a given price, from the data
     * base. Then it puts them into a list, and prints their details.
     *
     * @param maxPrice  - The price's upper limit.
     * @return - A list of Coupon objects.
     * @throws SQLException - Incorrect sql command.
     */
    public List<Coupon> getCompanyCouponsByMaxPrice(double maxPrice) throws SQLException {
        return couponsDAO.getCompanyCouponsByMaxPrice(companyLoggedIn.getId(), maxPrice);
    }

    /**
     * Receive a Company object from the data base(by id).
     *
     * @throws SQLException       - Incorrect sql command.
     * @throws UserErrorException - There is no company with this id.
     */
    public void getCompanyDetails() throws SQLException, UserErrorException {
        companiesDAO.getOneCompany(companyLoggedIn.getId());
    }
}

