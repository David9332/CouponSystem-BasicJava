package Facades;

import Beans.*;
import Exceptions.UserErrorException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminFacade extends ClientFacade {
    private static final String email = "admin@admin.com";
    private static final String password = "admin";

    /**
     * A default constructor that is used in the "LoginManager" class, "login" method.
     * Creates an adminFacade object that allows the use of the method "adminLogin".
     */
    public AdminFacade() {
    }

    /**
     * Checks if the email and password typed by the user matches the admin's.
     *
     * @param email    - The admin's email.
     * @param password - The admin's password.
     * @return - Boolean.
     * @throws UserErrorException - Incorrect email or password typed by the user.
     */
    public boolean adminLogin(String email, String password) throws UserErrorException {
        if (!(email.equals(AdminFacade.email) && password.equals(AdminFacade.password))) {
            throw new UserErrorException("AdminFacade", "adminLogin", "Could either be a typing error, or that the account you are looking for does not exist.");
        }
        return true;
    }

    /**
     * Receives a Company object from the user, checks if it exists at the data
     * base (by name and email) and if it doesn't - adds it to the data base.
     *
     * @param company - A Company object. Given by the user.
     * @throws SQLException       - Incorrect sql command.
     * @throws UserErrorException - There is already a company with the given name and email.
     */
    public void addCompany(Company company) throws SQLException, UserErrorException {
        boolean result = companiesDAO.doesCompanyExist(company.getName(), company.getEmail());
        if (!result) {
            companiesDAO.addCompany(company);
        } else {
            throw new UserErrorException("'CompaniesDBDAO'", "'doesCompanyExists'", "There is already a company with these name or email in the  DB. The company was NOT added.");
        }
    }

    /**
     * Receives a Company object from the user, checks if it exists at the data
     * base (by name and email) and if it does - updates the company at he DB
     * by the given Company object.
     *
     * @param company - A Company object. Given by the user.
     * @throws SQLException       - Incorrect sql command.
     * @throws UserErrorException - There is no company with the given name and email.
     */
    public void updateCompany(Company company) throws SQLException, UserErrorException {
        boolean result = companiesDAO.doesCompanyExistByNameAndId(company.getName(), company.getId());
        if (result) {
            companiesDAO.updateCompany(company);
        } else {
            throw new UserErrorException("'CompaniesDBDAO'", "'doesCompanyExists'", "There is no company with these details in the DB.");
        }
    }

    /**
     * Checks if a company exists at the data base (by id), and if it does - deletes it.
     *
     * @param companyID - The id of the company.
     * @throws SQLException       - Incorrect sql command.
     * @throws UserErrorException - There is no company with that id.
     */
    public void deleteCompany(int companyID) throws SQLException, UserErrorException {
        boolean result = companiesDAO.doesCompanyExistByID(companyID);
        if (result) {
            companiesDAO.deleteCompany(companyID);
        } else {
            throw new UserErrorException("'CompaniesDBDAO'", "'doesCompanyExistsByID'", "There is no company with this id in the DB.");
        }
    }

    /**
     * Gets all the companies in the data base into a list, and prints their details.
     *
     * @return - A list of Company objects.
     * @throws SQLException - Incorrect sql command.
     */
    public List<Company> getAllCompanies() throws SQLException {
        return companiesDAO.getAllCompanies();
    }

    /**
     * Gets one specific company (by id) and prints its details.
     *
     * @param companyID - The id of the company.
     * @return - A Company object.
     * @throws SQLException       - Incorrect sql command.
     * @throws UserErrorException - There is no company with that id.
     */
    public Company getOneCompany(int companyID) throws SQLException, UserErrorException {
        return companiesDAO.getOneCompany(companyID);
    }

    /**
     * Checks if a customer, given by the user ,exists in the data base, and if it
     * doesn't - adds it.
     *
     * @param customer - A Customer object given by the user.
     * @throws SQLException         - Incorrect sql command.
     * @throws InterruptedException - The couponExpirationDailyJob thread was interrupted.
     * @throws UserErrorException   - The customer already exist in the data base.
     */
    public void addCustomer(Customer customer) throws SQLException, UserErrorException {
        boolean result = customersDAO.doesCustomerExist(customer.getEmail());
        if (!result) {
            customersDAO.addCustomer(customer);
        }else {
            throw new UserErrorException("'CustomerDBDAO'","'doesCustomerExist'","There is already a customer with" +
                    " that email. Customer was NOT added.");
        }
    }

    /**
     * Receives a Customer object from the user, Checks if the customer exists in the data base
     * (by email and password), and if it does - updates it by the given customer object.
     *
     * @param customer - A Customer object. Given by the user.
     * @throws SQLException       - Incorrect sql command.
     * @throws UserErrorException - The customer does not exists in the data base.
     */
    public void updateCustomer(Customer customer) throws SQLException, UserErrorException {
        boolean result = customersDAO.doesCustomerExistsByID(customer.getId());
        if (result) {
            customersDAO.updateCustomer(customer);
        } else {
            throw new UserErrorException("CustomerDBDAO", "doesCustomerExists", "You can't update customer id.");
        }
    }

    /**
     * Checks if a customer exists at the data base (by id), and if it does - deletes it.
     *
     * @param customerID - The id of the customer.
     * @throws SQLException       - Incorrect sql command.
     * @throws UserErrorException - There is no customer with that id.
     */
    public void deleteCustomer(int customerID) throws SQLException, UserErrorException {
        boolean result = customersDAO.doesCustomerExistsByID(customerID);
        if (result) {
            customersDAO.deleteCustomer(customerID);
        } else {
            throw new UserErrorException("AdminFacade", "deleteCustomer", "Customer does not exist, there is nothing to delete.");
        }
    }

    /**
     * Gets all the customers in the data base into a list, and prints their details.
     *
     * @return - A list of Customer objects.
     * @throws SQLException - Incorrect sql command.
     */
    public List<Customer> getAllCustomers() throws SQLException {
        return customersDAO.getAllCustomers();
    }

    /**
     * Gets a specific customer (by id) and prints his details.
     *
     * @param customerID - The id of the customer.
     * @return - A Customer object.
     * @throws SQLException       - Incorrect sql command.
     * @throws UserErrorException - There is no customer with that id.
     */
    public Customer getOneCustomer(int customerID) throws SQLException, UserErrorException {
        return customersDAO.getOneCustomer(customerID);
    }
}
