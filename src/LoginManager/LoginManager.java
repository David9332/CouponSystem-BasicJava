package LoginManager;

import Beans.Company;
import Beans.Customer;
import Exceptions.UserErrorException;
import Facades.AdminFacade;
import Facades.ClientFacade;
import Facades.CompanyFacade;
import Facades.CustomerFacade;

import java.sql.SQLException;

public class LoginManager {
    private static LoginManager instance;

    /**
     * Checks if instance is null. If it is, synchronizes the entire "LoginManager"
     * class, than checks again if its null, creates new instance and return it.
     *
     * @return - Instance, a LoginManager object.
     */
    public static LoginManager getInstance() {
        //before locking critical code
        if (instance == null) {
            synchronized (LoginManager.class) {
                //before creating the code
                if (instance == null) {
                    instance = new LoginManager();
                    return instance;
                }
            }
        }
        return null;
    }

    /**
     * A login method that checks if the user had typed the right login details.
     * For administrator - sends it to adminLogin method at the AdminFacade class to
     * check its details. For company and customer - sends it to the login methods at
     * their facades. For a wrong login - returns null.
     *
     * @param email      - The email of the user.
     * @param password   - The password of the user.
     * @param clientType - The type of client (administrator, company or customer).
     * @return - adminFacade, company or customer objects if the login had succeeded,
     * and null if it had failed.
     * @throws InterruptedException - CouponExpirationDailyJob thread is interrupted.
     * @throws SQLException         - Incorrect sql command.
     * @throws UserErrorException   - Incorrect email or password typed by the user.
     */
    public ClientFacade login(String email, String password, ClientType clientType) throws InterruptedException, SQLException, UserErrorException {
        switch (clientType) {
            case ADMINISTRATOR:
                AdminFacade adminFacade = new AdminFacade();
                if (adminFacade.adminLogin(email, password)) {
                    return new AdminFacade();
                }

            case COMPANY:
                ClientFacade companyFacade = new CompanyFacade();
                Company company = (Company) companyFacade.login(email, password);
                if (company != null) {
                    return new CompanyFacade();
                }

            case CUSTOMER:
                ClientFacade customerFacade = new CustomerFacade();
                Customer customer = (Customer) customerFacade.login(email, password);
                if (customer != null) {
                    return new CustomerFacade();
                }

            default:
                return null;
        }
    }
}