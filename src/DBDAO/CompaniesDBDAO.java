package DBDAO;

import Beans.Company;
import Beans.Coupon;
import Beans.Customer;
import DAO.CompaniesDAO;
import DB.ConnectionPool;
import DB.DBUtils;
import Exceptions.UserErrorException;
import LoginManager.LoginManager;
import org.w3c.dom.ls.LSOutput;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompaniesDBDAO implements CompaniesDAO {
    public static final String DOES_COMPANY_EXIST = "SELECT * FROM `couponsdb`.`companies` WHERE `NAME`=? OR `EMAIL`=?";
    public static final String DOES_COMPANY_EXIST_BY_ID = "SELECT * FROM `couponsdb`.`companies` WHERE `ID`=?";
    public static final String DOES_COMPANY_EXIST_BY_NAME_AND_ID = "SELECT * FROM `couponsdb`.`companies` WHERE `NAME`=? AND `ID`=?";
    public static final String ADD_COMPANY = "INSERT INTO `couponsdb`.`companies` (`ID`,`NAME`,`EMAIL`,`PASSWORD`) VALUES (?,?,?,?)";
    private static final String UPDATE_COMPANY = "UPDATE `couponsdb`.`companies` SET EMAIL=?, PASSWORD=? WHERE NAME=? AND ID=?";
    private static final String DELETE_COMPANY = "DELETE FROM `couponsdb`.`companies` WHERE ID=?";
    private static final String GET_ALL_COMPANIES = "SELECT * FROM `couponsdb`.`companies`";
    private static final String GET_ONE_COMPANY = "SELECT * FROM `couponsdb`.`companies` WHERE ID=?";
    private static final String LOGIN = "SELECT * FROM `couponsdb`.`companies` WHERE EMAIL=? AND PASSWORD=?";

    /**
     * A login method for allowing a specific company to work with the program.
     *
     * @param email    - The email of the company.
     * @param password - The password of the company.
     * @return The Company object.
     * @throws SQLException       - Incorrect SQL command.
     * @throws UserErrorException - Incorrect email or password typed by the user.
     */
    @Override
    public Company login(String email, String password) throws SQLException, UserErrorException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, email);
        params.put(2, password);
        ResultSet resultSet = DBUtils.runQueryWithResult(LOGIN, params);
        if (resultSet.next()) {
            Company companyLoggedIn = new Company(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            );
            return companyLoggedIn;
        }
        throw new UserErrorException("'CompaniesDBDAO'", "'login'", "Could either be a typing" +
                " error, or that the account you are looking for does not exist.");
    }

    /**
     * Checks if a company already exist in the data base by the name and email typed by the user.
     *
     * @param name  - Name of the company.
     * @param email - Email of the company.
     * @return boolean (true or false).
     * @throws SQLException - Incorrect SQL command.
     */
    @Override
    public boolean doesCompanyExist(String name, String email) throws SQLException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, name);
        params.put(2, email);
        ResultSet resultSet = DBUtils.runQueryWithResult(DOES_COMPANY_EXIST, params);
        if (resultSet.next()) {
            return true;
        }
        return false;
    }

    /**
     * Checks by id if a company already exist in the data base.
     *
     * @param companyID - The id of the company.
     * @return Boolean (true or false).
     * @throws SQLException - Incorrect SQL command.
     */
    public boolean doesCompanyExistByID(int companyID) throws SQLException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, companyID);
        ResultSet resultSet = DBUtils.runQueryWithResult(DOES_COMPANY_EXIST_BY_ID, params);
        if (resultSet.next()) {
            return true;
        }
        return false;
    }

    /**
     * Checks by name if a company already exist in the data base.
     *
     * @return Boolean (true or false).
     * @throws SQLException - Incorrect SQL command.
     */
    public boolean doesCompanyExistByNameAndId(String name, int id) throws SQLException, UserErrorException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, name);
        params.put(2, id);
        ResultSet resultSet = DBUtils.runQueryWithResult(DOES_COMPANY_EXIST_BY_NAME_AND_ID, params);
        if (resultSet.next()) {
            return true;
        }
        throw new UserErrorException("'CompaniesDBDAO'", "'doesCompanyExistByNameAndId'", "You can't update the company's name and id.");
    }

    /**
     * Adds a company to the data base.
     *
     * @param company - A Company object.
     */
    @Override
    public void addCompany(Company company) {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, company.getId());
        params.put(2, company.getName());
        params.put(3, company.getEmail());
        params.put(4, company.getPassword());
        DBUtils.runQuery(ADD_COMPANY, params);
    }

    /**
     * Updates company details in the data base.
     *
     * @param company - A Company object.
     */
    @Override
    public void updateCompany(Company company) {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, company.getEmail());
        params.put(2, company.getPassword());
        params.put(3, company.getName());
        params.put(4, company.getId());
        DBUtils.runQuery(UPDATE_COMPANY, params);
    }

    /**
     * Deletes a company from the data base.
     *
     * @param companyID - The id of the company.
     */
    @Override
    public void deleteCompany(int companyID) {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, companyID);
        DBUtils.runQuery(DELETE_COMPANY, params);
    }

    /**
     * Receiving all the companies from the data base, puts them in a list
     * and prints their details.
     *
     * @return - A list of Company objects.
     * @throws SQLException - Incorrect SQL command.
     */
    @Override
    public List<Company> getAllCompanies() throws SQLException {
        List<Company> companies = new ArrayList<>();
        ResultSet resultSet = DBUtils.runQueryWithResult(GET_ALL_COMPANIES);
        while (resultSet.next()) {
            Company company = new Company(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            );
            companies.add(company);
//            System.out.println(company);
        }
        return companies;
    }

    /**
     * Receiving and printing the details of a specific company in the data base (by id).
     *
     * @param companyID - The id of the company.
     * @return - A specific company from the Data base.
     * @throws SQLException - Incorrect SQL command.
     */
    @Override
    public Company getOneCompany(int companyID) throws SQLException, UserErrorException {
        Map<Integer, Object> params = new HashMap<>();
        Company company = new Company();
        params.put(1, companyID);
        ResultSet resultSet = DBUtils.runQueryWithResult(GET_ONE_COMPANY, params);
        if (resultSet.next()) {
            company = new Company(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            );
            System.out.println(company);
            return company;
        } else {
            throw new UserErrorException("'CompaniesDBDAO'", "'getOneCompany'", "There is no company with this id.");
        }
    }
}
