package DAO;

import Beans.Company;
import Beans.Coupon;
import Exceptions.UserErrorException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface CompaniesDAO {

    /**
     * A login method for allowing a specific company to work with the program.
     *
     * @param email    - The email of the company.
     * @param password - The password of the company.
     * @return The Company object.
     * @throws SQLException       - Incorrect SQL command.
     * @throws UserErrorException - Incorrect email or password typed by the user.
     */
    Company login(String email, String password) throws SQLException, UserErrorException;

    /**
     * Checks if a company already exist in the database by the name and email typed by the user.
     *
     * @param name  - Name of the company.
     * @param email - Email of the company.
     * @return boolean (true or false).
     * @throws SQLException - Incorrect SQL command.
     */
    boolean doesCompanyExist(String name, String email) throws SQLException, UserErrorException;

    /**
     * Checks by id if a company already exist in the database.
     *
     * @param companyID - The id of the company.
     * @return Boolean (true or false).
     * @throws SQLException - Incorrect SQL command.
     */
    boolean doesCompanyExistByID(int companyID) throws SQLException, UserErrorException;

    /**
     * Checks by name and id if a company already exist in the database.
     *
     * @param name - The name of the company.
     * @param id - The id of the company.
     * @return Boolean (true or false).
     * @throws SQLException - Incorrect SQL command.
     */
    boolean doesCompanyExistByNameAndId(String name, int id) throws SQLException, UserErrorException;

    /**
     * Adds a company to the data base.
     *
     * @param company - A Company object.
     */
    void addCompany(Company company);

    /**
     * Updates company details in the data base.
     *
     * @param company - A Company object.
     */
    void updateCompany(Company company);

    /**
     * Deletes a company from the data base.
     *
     * @param companyID - The id of the company.
     */
    void deleteCompany(int companyID);

    /**
     * Receiving all the companies from the data base, puts them in a list
     * and prints their details.
     *
     * @return - A list of Company objects.
     * @throws SQLException - Incorrect SQL command.
     */
    List<Company> getAllCompanies() throws SQLException;

    /**
     * Receiving and printing the details of a specific company in the data base (by id).
     *
     * @param companyID - The id of the company.
     * @return - A specific company from the Data base.
     * @throws SQLException - Incorrect SQL command.
     */
    Company getOneCompany(int companyID) throws SQLException, UserErrorException;


}