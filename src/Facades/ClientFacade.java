package Facades;

import DAO.CompaniesDAO;
import DAO.CouponsDAO;
import DAO.CustomersDAO;
import DBDAO.CompaniesDBDAO;
import DBDAO.CouponsDBDAO;
import DBDAO.CustomersDBDAO;
import Exceptions.UserErrorException;

import java.sql.SQLException;

public abstract class ClientFacade {
    protected CustomersDAO customersDAO = new CustomersDBDAO();
    protected CompaniesDAO companiesDAO = new CompaniesDBDAO();
    protected CouponsDAO couponsDAO = new CouponsDBDAO();
    private Object Object;

    //האיבר המוחזר הוא ממחלקת העל Object, שמתאימה גם לחברה וגם למשתמש.

    /**
     * A login method for customers and companies users. Checks if the email and
     * password typed by the user matches the ones in the data base.
     *
     * @param email    - The email of the customer/company.
     * @param password - The password of the customer/company.
     * @return - An object (companyFacade for companies, customerFacade for customers).
     * @throws SQLException       - Incorrect sql command.
     * @throws UserErrorException - Incorrect email or password typed by the user.
     */
    public Object login(String email, String password) throws SQLException, UserErrorException {
        return Object;
    }

    //בגלל שאין אובייקט שנקרא אדמין (ולכן אי אפשר להחזירו), היינו חייבים ליצור לו שיטת חיבור ייחודית לו

    /**
     * A login method for the admin. Checks if the email and
     * password typed by the user matches the admin's.
     *
     * @param email    - The email of the admin.
     * @param password - The password of the admin.
     * @return - Boolean.
     * @throws UserErrorException - Incorrect email or password typed by the user.
     */
    public boolean adminLogin(String email, String password) throws UserErrorException {
        return false;
    }
}
