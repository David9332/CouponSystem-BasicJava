package Tester;


import Beans.Category;
import Beans.Company;
import Beans.Coupon;
import Beans.Customer;
import DAO.CompaniesDAO;
import DAO.CouponsDAO;
import DAO.CustomersDAO;
import DB.ConnectionPool;
import DB.DatabaseManager;
import DBDAO.CompaniesDBDAO;
import DBDAO.CouponsDBDAO;
import DBDAO.CustomersDBDAO;
import Exceptions.UserErrorException;
import Facades.AdminFacade;
import Facades.CompanyFacade;
import Facades.CustomerFacade;
import LoginManager.LoginManager;
import LoginManager.ClientType;
import Threads.CouponExpirationDailyJob;
import Utills.Art;

import java.sql.SQLException;

public class Test {
    public static void testAll() {
        CouponsDAO couponsDAO = new CouponsDBDAO();
        CompaniesDAO companiesDAO = new CompaniesDBDAO();
        CustomersDAO customersDAO = new CustomersDBDAO();

        try {
            System.out.println();
            System.out.println(Art.INIT_DATABASE);
            System.out.println(Art.sperator);
            DatabaseManager.createDBWithContent();
            System.out.println();
            System.out.println(Art.DATABASE_READY);
            CouponExpirationDailyJob dailyJob = new CouponExpirationDailyJob();
            Thread thread = new Thread(dailyJob);
            thread.start();
            dailyJob.stop();

            System.out.println(Art.sperator);
            System.out.println();
            System.out.println(Art.ADMIN_METHODS);
            AdminFacade adminFacade = (AdminFacade) LoginManager.getInstance().login("admin@admin.com", "admin", ClientType.ADMINISTRATOR);

            System.out.println(Art.sperator);
            System.out.println("Successful company add to the DB:");
            System.out.println("Before:");
            adminFacade.getAllCompanies().forEach(System.out::println);
            Company company = new Company("Samsung", "Samsung@gmail.com", "4321");
            adminFacade.addCompany(company);
            System.out.println("After:");
            adminFacade.getAllCompanies().forEach(System.out::println);
            System.out.println();
            System.out.println(Art.sperator);

            System.out.println();
            System.out.println("Successful update of company number 6:");
            Company companyForUpdate = new Company(6, "Samsung", "xxxxxxxxxxxxxx@gmail.com", "xxxxxxxxxxxxxxxxx");
            System.out.println("Before:");
            adminFacade.getOneCompany(6);
            adminFacade.updateCompany(companyForUpdate);
            System.out.println("After:");
            adminFacade.getOneCompany(6);
            System.out.println();
            System.out.println(Art.sperator);

            System.out.println();
            System.out.println("Successful delete of company number 4:");
            System.out.println("In the database: please notice that all of the company coupons were deleted from the coupon table.");
            System.out.println("In the database: please notice that all of the clients purchase history of that company coupons were deleted from the customer-VS-coupons table.");
            System.out.println();
            System.out.println("Before:");
            adminFacade.getAllCompanies().forEach(System.out::println);
            adminFacade.deleteCompany(4);
            System.out.println("After:");
            adminFacade.getAllCompanies().forEach(System.out::println);
            System.out.println();
            System.out.println(Art.sperator);

            System.out.println();
            System.out.println("Successful receive of all companies from the DB:");
            adminFacade.getAllCompanies().forEach(System.out::println);
            System.out.println();
            System.out.println(Art.sperator);

            System.out.println();
            System.out.println("Successful receive of company number 5 from the DB:");
            adminFacade.getOneCompany(5);
            System.out.println();
            System.out.println(Art.sperator);

            System.out.println();
            System.out.println("Successful customer add to the DB:");
            System.out.println("Before:");
            adminFacade.getAllCustomers().forEach(System.out::println);
            Customer addCustomer = new Customer("Ronny", "Levy", "ronny@gmail.com", "1234");
            adminFacade.addCustomer(addCustomer);
            System.out.println("After:");
            adminFacade.getAllCustomers().forEach(System.out::println);
            System.out.println();
            System.out.println(Art.sperator);

            System.out.println();
            System.out.println("Successful update of customer number 3:");
            System.out.println("Before:");
            adminFacade.getOneCustomer(3);
            Customer customerForUpdate = new Customer(3, "xxxxxxxxxxxxx", "xxxxxxxxxx", "xxxxxxxxxxxx@gmail.com", "xxxxxxxxxxxx");
            adminFacade.updateCustomer(customerForUpdate);
            System.out.println("After:");
            System.out.println(customerForUpdate);
            System.out.println();
            System.out.println(Art.sperator);

            System.out.println();
            System.out.println("Successful customer delete of customer number 4:");
            System.out.println("In the database: please notice that all of the clients purchase history was deleted from the customers-VS-coupons table.");
            System.out.println();
            System.out.println("Before:");
            adminFacade.getAllCustomers().forEach(System.out::println);
            adminFacade.deleteCustomer(4);
            System.out.println();
            System.out.println("After:");
            adminFacade.getAllCustomers().forEach(System.out::println);
            System.out.println();
            System.out.println(Art.sperator);


            System.out.println();
            System.out.println("Successful receive of all customers:");
            System.out.println();
            adminFacade.getAllCustomers().forEach(System.out::println);
            System.out.println();
            System.out.println(Art.sperator);


            System.out.println();
            System.out.println("Successful receive of customer number 9:");
            System.out.println();
            adminFacade.getOneCustomer(9);
            System.out.println();
            System.out.println(Art.sperator);

            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


            System.out.println();
            System.out.println(Art.COMPANIES_METHODS);
//            CompanyFacade companyFacade = (CompanyFacade) LoginManager.getInstance().login("apple@gmail.com", "3221", ClientType.COMPANY);
//
//            System.out.println(Art.sperator);
//            System.out.println("Successful add of a coupon of logged-in company (number 2) to the DB:");
//            System.out.println("Before:");
//            companyFacade.getCouponsByCompany().forEach(System.out::println);
//            Coupon addedCoupon = new Coupon(2, Category.ELECTRICITY.getCategoryId(), "yyyyyyyyyyyyyyyyyyy", "yyyyyyyyyyyyyyyyyyy", couponsDAO.getDate(1955, 12, 31), couponsDAO.getDate(2023, 1, 1), 10, 22, "image");
//            companyFacade.addCoupon(addedCoupon);
//            System.out.println();
//            System.out.println("After:");
//            companyFacade.getCouponsByCompany().forEach(System.out::println);
//            System.out.println();
//            System.out.println(Art.sperator);
//
//            System.out.println();
//            Coupon couponForUpdate = new Coupon(5, 2, 1, "xxxxxxxxxxxxxxxxxxx", "xxxxxxxxxxxxxxxxxxx", couponsDAO.getDate(2020, 4, 4), couponsDAO.getDate(2022, 8, 8), 40, 13.4, "image");
//            System.out.println("Successful update of coupon number 5:");
//            System.out.println("Before:");
//            System.out.println(companyFacade.getOneCoupon(5));
//            companyFacade.updateCoupon(couponForUpdate);
//            System.out.println("After:");
//            System.out.println(companyFacade.getOneCoupon(5));
//            System.out.println();
//            System.out.println(Art.sperator);
//
//            System.out.println();
//            System.out.println("Successful receive of logged-in company coupons:");
//            System.out.println(companyFacade.getCouponsByCompany());
//            System.out.println();
//            System.out.println(Art.sperator);
//
//            System.out.println();
//            System.out.println("Successful receive of logged-in company coupons by category (category number 1):");
//            System.out.println(companyFacade.getCompanyCouponsByCategory(1));
//            System.out.println();
//            System.out.println(Art.sperator);
//
//
//            System.out.println();
//            System.out.println("Successful receive of logged-in company coupons with a price equal or lower then 100:");
//            System.out.println(companyFacade.getCompanyCouponsByMaxPrice(100));
//            System.out.println();
//            System.out.println(Art.sperator);
//
//            System.out.println("Successful delete of coupon number 5 of the logged-in company from the DB:");
//            System.out.println("In the DB: notice that the coupon is deleted from the coupon table and the customers-VS-coupons");
//            System.out.println("Before:");
//            companyFacade.getCouponsByCompany().forEach(System.out::println);
//            companyFacade.deleteCoupon(5);
//            System.out.println();
//            System.out.println("After:");
//            companyFacade.getCouponsByCompany().forEach(System.out::println);
//            System.out.println();
//            System.out.println(Art.sperator);
//
//            System.out.println();
//            System.out.println("Successful receive of logged-in company details:");
//            companyFacade.getCompanyDetails();
//            System.out.println();
//            System.out.println(Art.sperator);

            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


            System.out.println();
            System.out.println(Art.CUSTOMERS_METHODS);

//            CustomerFacade customerFacade = (CustomerFacade) LoginManager.getInstance().login("avi@gmail.com", "cvv", ClientType.CUSTOMER);
//
//            System.out.println("Successful purchase of coupon number 9 by the logged-in customer (number 4):");
//            System.out.println("Customer's coupons before:");
//            customerFacade.getCustomerPurchasedCoupons();
//            customerFacade.purchaseCoupon(9);
//            System.out.println();
//            System.out.println("Customer's coupons after:");
//            customerFacade.getCustomerPurchasedCoupons();
//            System.out.println();
//            System.out.println(Art.sperator);
//
//            System.out.println("Successful receive of the coupons of the logged-in customer (number 4):");
//            customerFacade.getCustomerPurchasedCoupons();
//            System.out.println();
//            System.out.println(Art.sperator);
//
//            System.out.println("Successful receive of the coupons of the logged-in customer by category ('ELECTRICITY'):");
//            customerFacade.getCustomerCouponsByCategory(Category.ELECTRICITY);
//            System.out.println();
//            System.out.println(Art.sperator);
//
//            System.out.println("Successful receive of the coupons of the logged-in customer, equal or under the price 100:");
//            customerFacade.getCustomerCouponsByMaxPrice(100);
//            System.out.println();
//            System.out.println(Art.sperator);
//
//            System.out.println("Details of the logged-in customer (number 4):");
//            customerFacade.showCustomerDetails();
//            System.out.println();
//            System.out.println(Art.sperator);

            // ConnectionPool.getInstance().closeAllConnection();


        } catch (Exception err) {
            System.out.println(err.getMessage());
        }

    }
}
