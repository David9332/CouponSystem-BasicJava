package DB;


import Beans.*;
import Beans.Config;
import DAO.CouponsDAO;
import DBDAO.CouponsDBDAO;
import Exceptions.UserErrorException;
import Facades.AdminFacade;
import Facades.CompanyFacade;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseManager {
    //mysql:mysql-connector-java:8.0.23 : file->project structure->Libreries->+->maven-> link
    //conection string for connection to the mysql/mariadb server
    public static String url = "jdbc:mysql://localhost:3306/couponsdb?createDatabaseIfNotExist=true&serverTimezone=UTC"; //Asia/Jerusalem
    public static String username = "root";
    public static String password = "12345678";

    private static String CREATE_DB = "CREATE DATABASE couponsdb";
    private static String DROP_DB = "DROP DATABASE couponsdb";

    private static final String CREATE_TABLE_CATEGORIES = "CREATE TABLE IF NOT EXISTS `couponsdb`.`categories` (`ID` INT(25) NOT NULL AUTO_INCREMENT PRIMARY KEY, `NAME` VARCHAR(30) NOT NULL)";
    private static final String CREATE_TABLE_CATEGORIES_CONTENT = "INSERT INTO `couponsdb`.`categories` (`ID`,`NAME`) VALUES (?,?)";
    private static final String CREATE_TABLE_COUPONS = "CREATE TABLE IF NOT EXISTS `couponsdb`.`coupons` (`ID` INT(25) NOT NULL AUTO_INCREMENT PRIMARY KEY, `COMPANY_ID` INT(25) NOT NULL, `CATEGORY_ID` INT(25) NOT NULL, `TITLE` VARCHAR(30) NOT NULL, `DESCRIPTION` VARCHAR(150) NOT NULL, `START_DATE` DATE NOT NULL, `END_DATE` DATE NOT NULL, `AMOUNT` INT(11) NOT NULL, `PRICE` DOUBLE NOT NULL, `IMAGE` VARCHAR(50) NOT NULL, FOREIGN KEY (COMPANY_ID) REFERENCES companies(ID) ON DELETE CASCADE, FOREIGN KEY (CATEGORY_ID) REFERENCES categories(ID) ON DELETE CASCADE) ";
    private static final String CREATE_TABLE_COMPANIES = "CREATE TABLE IF NOT EXISTS `couponsdb`.`companies` (`ID` INT(25) NOT NULL AUTO_INCREMENT PRIMARY KEY, `NAME` VARCHAR(30) NOT NULL, `EMAIL` VARCHAR(50) NOT NULL, `PASSWORD` VARCHAR(50) NOT NULL)";
    private static final String CREATE_TABLE_CUSTOMERS = "CREATE TABLE IF NOT EXISTS `couponsdb`.`customers` (`ID` int(25) NOT NULL AUTO_INCREMENT PRIMARY KEY, `FIRST_NAME` VARCHAR(30) NOT NULL, LAST_NAME VARCHAR(30) NOT NULL, `EMAIL` VARCHAR(30) NOT NULL, `PASSWORD` VARCHAR(30) NOT NULL)";
    private static final String CREATE_TABLE_CUSTOMERS_VS_COUPONS = "CREATE TABLE couponsdb.`customers_vs_coupons` (`CUSTOMER_ID` INT(25) NOT NULL, `COUPON_ID` INT(25) NOT NULL , PRIMARY KEY (`CUSTOMER_ID`,`COUPON_ID`), FOREIGN KEY (CUSTOMER_ID) REFERENCES customers(ID) ON DELETE CASCADE, FOREIGN KEY (COUPON_ID) REFERENCES coupons(ID) ON DELETE CASCADE)";

    private static final String DROP_TABLE_CATEGORIES_FOREIGN_KEY = "ALTER TABLE `couponsdb`.`coupons` DROP FOREIGN KEY `coupons_ibfk_2`";
    private static final String DROP_TABLE_CATEGORIES = "DROP TABLE `couponsdb`.`categories`";
    private static final String DROP_TABLE_COMPANIES_FOREIGN_KEY = "ALTER TABLE `couponsdb`.`coupons` DROP FOREIGN KEY `coupons_ibfk_1`;";
    private static final String DROP_TABLE_COMPANIES = "DROP TABLE `couponsdb`.`companies`";
    private static final String DROP_TABLE_COUPONS_FOREIGN_KEYS = "ALTER TABLE `couponsdb`.`customers_vs_coupons` DROP FOREIGN KEY `customers_vs_coupons_ibfk_2`;";
    private static final String DROP_TABLE_COUPONS = " DROP TABLE `couponsdb`.`coupons`;";
    private static final String DROP_TABLE_CUSTOMERS_FOREIGN_KEY = "ALTER TABLE `couponsdb`.`customers_vs_coupons` DROP FOREIGN KEY `customers_vs_coupons_ibfk_1`;";
    private static final String DROP_TABLE_CUSTOMERS = "DROP TABLE `couponsdb`.`customers`";
    private static final String DROP_TABLE_CUSTOMERS_VS_COUPONS = "DROP TABLE `couponsdb`.`customers_vs_coupons`";


    public static void getConfiguration() {
        Config config = Config.readConfig();
        //"jdbc:mysql://localhost:3306?createDatabaseIfNotExist=FALSE&useTimezone=TRUE&serverTimezone=Asia/Jerusalem"
        url = "jdbc:mysql://" + config.getSqlConnectionString() +
                "?createDatabaseIfNotExist" + (config.isCreateIfNotExists() ? "TRUE" : "FALSE") +
                "&useTimezone=" + (config.isUseTimeZone() ? "TRUE" : "FALSE") +
                "&serverTimezone=" + config.getServerTimeZone();
        username = config.getUserName();
        password = config.getUserPassword();
        CREATE_DB = "CREATE DATABASE " + config.getDBname();
        DROP_DB = "DROP DATABASE " + config.getDBname();
    }

    /**
     * Creates a data base "couponsdb" at MariaDB.
     */
    public static void createDataBase() {
        DBUtils.runQuery(CREATE_DB);
    }

    /**
     * Deletes the database "couponsdb" from MariaDB.
     */
    public static void dropDataBase() {
        DBUtils.runQuery(DROP_DB);
    }

    /**
     * Creates the table "categories" in the "couponsdb" database.
     */
    public static void createTableCategories() {
        DBUtils.runQuery(CREATE_TABLE_CATEGORIES);
        Map<Integer, Object> params1 = new HashMap<>();
        params1.put(1, Category.ELECTRICITY.getCategoryId());
        params1.put(2, Category.ELECTRICITY);
        DBUtils.runQuery(CREATE_TABLE_CATEGORIES_CONTENT, params1);
        Map<Integer, Object> params2 = new HashMap<>();
        params2.put(1, Category.FOOD.getCategoryId());
        params2.put(2, Category.FOOD);
        DBUtils.runQuery(CREATE_TABLE_CATEGORIES_CONTENT, params2);
        Map<Integer, Object> params3 = new HashMap<>();
        params3.put(1, Category.RESTAURANT.getCategoryId());
        params3.put(2, Category.RESTAURANT);
        DBUtils.runQuery(CREATE_TABLE_CATEGORIES_CONTENT, params3);
        Map<Integer, Object> params4 = new HashMap<>();
        params4.put(1, Category.VACATION.getCategoryId());
        params4.put(2, Category.VACATION);
        DBUtils.runQuery(CREATE_TABLE_CATEGORIES_CONTENT, params4);
    }

    /**
     * Creates the table "companies" in the "couponsdb" database.
     */
    public static void createTableCompanies() {
        DBUtils.runQuery(CREATE_TABLE_COMPANIES);
    }

    /**
     * Creates the table "coupons" in the "couponsdb" database.
     */
    public static void createTableCoupons() {
        DBUtils.runQuery(CREATE_TABLE_COUPONS);
    }

    /**
     * Creates the table "customers" in the "couponsdb" database.
     */
    public static void createTableCustomers() {
        DBUtils.runQuery(CREATE_TABLE_CUSTOMERS);
    }

    /**
     * Creates the table "customers_vs_coupons" in the "couponsdb" database.
     */
    public static void createTableCustomersVsCoupons() {
        DBUtils.runQuery(CREATE_TABLE_CUSTOMERS_VS_COUPONS);
    }


    /**
     * Deletes the foreign key of the table "categories", and then deletes the table
     * itself from the "couponsdb" database.
     */
    public static void dropTableCategories() {
        DBUtils.runQuery(DROP_TABLE_CATEGORIES_FOREIGN_KEY);
        DBUtils.runQuery(DROP_TABLE_CATEGORIES);
    }

    /**
     * Deletes the foreign key of the table "companies", and then deletes the table
     * itself from the "couponsdb" database.
     */
    public static void dropTableCompanies() {
        DBUtils.runQuery(DROP_TABLE_COMPANIES_FOREIGN_KEY);
        DBUtils.runQuery(DROP_TABLE_COMPANIES);
    }

    /**
     * Deletes the foreign key of the table "coupons", and then deletes the table
     * itself from the "couponsdb" database.
     */
    public static void dropTableCoupons() {
        DBUtils.runQuery(DROP_TABLE_COUPONS_FOREIGN_KEYS);
        DBUtils.runQuery(DROP_TABLE_COUPONS);
    }

    /**
     * Deletes the foreign key of the table "customers", and then deletes the table
     * itself from the "couponsdb" database.
     */
    public static void dropTableCustomers() {
        DBUtils.runQuery(DROP_TABLE_CUSTOMERS_FOREIGN_KEY);
        DBUtils.runQuery(DROP_TABLE_CUSTOMERS);
    }

    /**
     * Deletes the table "customers_vs_coupons" from the "couponsdb" database.
     */
    public static void dropTableCustomersVsCoupons() {
        DBUtils.runQuery(DROP_TABLE_CUSTOMERS_VS_COUPONS);
    }

    /**
     * Creates the "couponsdb" database with all of its 5 tables.
     */
    public static void createDBWithTables() {
        DBUtils.runQuery(CREATE_DB);
        createTableCategories();
        DBUtils.runQuery(CREATE_TABLE_COMPANIES);
        DBUtils.runQuery(CREATE_TABLE_CUSTOMERS);
        DBUtils.runQuery(CREATE_TABLE_COUPONS);
        DBUtils.runQuery(CREATE_TABLE_CUSTOMERS_VS_COUPONS);
    }

    /**
     * Creates the "couponsdb" database, with all of its 5 tables,
     * with full data inside them.
     *
     * @throws SQLException         - Incorrect SQL command.
     * @throws InterruptedException - The couponExpirationDailyJob thread is interrupted.
     * @throws UserErrorException   - Problematic information typed by the user.
     */
    public static void createDBWithContent() throws SQLException, InterruptedException, UserErrorException {
        dropDataBase();
        createDBWithTables();
        AdminFacade adminFacade = new AdminFacade();
        CompanyFacade companyFacade = new CompanyFacade();

        CouponsDAO couponsDAO = new CouponsDBDAO();

        Company company1 = new Company("Microsoft", "microsoft@gmail.com", "1234");
        Company company2 = new Company("Apple", "apple@gmail.com", "3221");
        Company company3 = new Company("Amazon", "amazon@gmail.com", "7777");
        Company company4 = new Company("John bryce", "jb@gmail.com", "1594");
        Company company5 = new Company("Intel", "intel@gmail.com", "8888");

        adminFacade.addCompany(company1);
        adminFacade.addCompany(company2);
        adminFacade.addCompany(company3);
        adminFacade.addCompany(company4);
        adminFacade.addCompany(company5);


        Customer customer1 = new Customer("David", "Birger", "david@gmail.com", "qasw");
        Customer customer2 = new Customer("Tom", "Hacarmeli", "tom@gmail.com", "tyygy");
        Customer customer3 = new Customer("Yossi", "Cohen", "yossi@gmail.com", "fgdf");
        Customer customer4 = new Customer("Avi", "Levi", "avi@gmail.com", "cvv");
        Customer customer5 = new Customer("Israel", "Israeli", "israel@gmail.com", "3444");
        Customer customer6 = new Customer("Ron", "Alon", "ron@gmail.com", "k43yy");
        Customer customer7 = new Customer("Benjamin", "Netanyahu", "benjamin@gmail.com", "mrlve");
        Customer customer8 = new Customer("Sara", "Netanyahu", "sara@gmail.com", "kjjhkj");
        Customer customer9 = new Customer("Benny", "Gantz", "benny@gmail.com", "cdfd");
        Customer customer10 = new Customer("Naftali", "Bennet", "naftali@gmail.com", "yhbd");
        adminFacade.addCustomer(customer1);
        adminFacade.addCustomer(customer2);
        adminFacade.addCustomer(customer3);
        adminFacade.addCustomer(customer4);
        adminFacade.addCustomer(customer5);
        adminFacade.addCustomer(customer6);
        adminFacade.addCustomer(customer7);
        adminFacade.addCustomer(customer8);
        adminFacade.addCustomer(customer9);
        adminFacade.addCustomer(customer10);

        Coupon coupon1 = new Coupon(1, 1, "ACE", "BOSCH washing machine, model WAN28160BY for only 1,000 NIS", couponsDAO.getDate(2020, 4, 4), couponsDAO.getDate(2022, 8, 8), 50, 1000.00, "image");
        Coupon coupon2 = new Coupon(1, 2, "Osher Ad", "1 kg fresh veal sirloin - 79.90 NIS", couponsDAO.getDate(2020, 4, 4), couponsDAO.getDate(2021, 3, 29), 50, 79.90, "image");
        Coupon coupon3 = new Coupon(1, 3, "Caffe caffe", "Israeli breakfast for 57 NIS", couponsDAO.getDate(2020, 4, 4), couponsDAO.getDate(2021, 3, 29), 50, 57.00, "image");
        Coupon coupon4 = new Coupon(1, 4, "Club hotel", "Double or Twin Room – Breakfast includedFREE cancellation • No prepayment needed", couponsDAO.getDate(2020, 4, 4), couponsDAO.getDate(2021, 3, 29), 50, 1500.00, "image");
        Coupon coupon5 = new Coupon(2, 1, "Electric Shop", "75″ Class TU8000 Crystal UHD 4K Smart TV (2020)", couponsDAO.getDate(2020, 4, 4), couponsDAO.getDate(2022, 8, 8), 50, 2000.00, "image");
        Coupon coupon6 = new Coupon(2, 2, "SoGood night store", "Buy at 200 and get products at 300", couponsDAO.getDate(2020, 4, 4), couponsDAO.getDate(2022, 8, 8), 50, 200.00, "image");
        Coupon coupon7 = new Coupon(2, 3, "Pizza boom", "1 plus 1 on buying a family pizza", couponsDAO.getDate(2020, 4, 4), couponsDAO.getDate(2022, 8, 8), 50, 50.00, "image");
        Coupon coupon8 = new Coupon(2, 4, "Royal Beach", "2 rooms Double bed with balcony For two nights", couponsDAO.getDate(2020, 4, 4), couponsDAO.getDate(2022, 8, 8), 50, 1000.00, "image");
        Coupon coupon9 = new Coupon(3, 1, "Big Electric", "LG 70 Class 4K UHD 2160P Smart TV with HDR - 70UN7070PUA 2020 Model", couponsDAO.getDate(2020, 4, 4), couponsDAO.getDate(2022, 8, 8), 50, 2500.00, "image");
        Coupon coupon10 = new Coupon(3, 2, "Home store", "Olive oil for 10 NIS", couponsDAO.getDate(2020, 4, 4), couponsDAO.getDate(2022, 8, 8), 50, 10.00, "image");
        Coupon coupon11 = new Coupon(3, 3, "Meat meat", "The house sandwich includes drink and dessert", couponsDAO.getDate(2020, 4, 4), couponsDAO.getDate(2022, 8, 8), 50, 49.90, "image");
        Coupon coupon12 = new Coupon(3, 4, "Jerusalem tours", "A tour in Jerusalem in the holy places includes lunch", couponsDAO.getDate(2020, 4, 4), couponsDAO.getDate(2022, 8, 8), 50, 100.00, "image");
        Coupon coupon13 = new Coupon(4, 1, "Electric city ", "S3821w-C0 38-Inch Sound Bar with Bluetooth Wireless Sub", couponsDAO.getDate(2020, 4, 4), couponsDAO.getDate(2022, 8, 8), 50, 500.00, "image");
        Coupon coupon14 = new Coupon(4, 2, "Bast big", "Buy at 100 and get products at 200", couponsDAO.getDate(2020, 4, 4), couponsDAO.getDate(2022, 8, 8), 50, 100.00, "image");
        Coupon coupon15 = new Coupon(4, 3, "Italiano", "A meal for two that includes a main course and dessert", couponsDAO.getDate(2020, 4, 4), couponsDAO.getDate(2022, 8, 8), 50, 99.90, "image");
        Coupon coupon16 = new Coupon(4, 4, "Gil hotel", "2 nights per couple including breakfast", couponsDAO.getDate(2020, 4, 4), couponsDAO.getDate(2021, 4, 12), 50, 500.00, "image");
        Coupon coupon17 = new Coupon(5, 1, "David Electric", "SAMSUNG 70 Class LED 4K (2160P) LED Smart TV QN70Q60 2021", couponsDAO.getDate(2020, 4, 4), couponsDAO.getDate(2022, 8, 8), 50, 1500.00, "image");
        Coupon coupon18 = new Coupon(5, 2, "Super pharma ", "Chicken for 5 NIS", couponsDAO.getDate(2020, 4, 4), couponsDAO.getDate(2022, 8, 8), 50, 5.00, "image");
        Coupon coupon19 = new Coupon(5, 3, "Burger burger", "400g hamburger with fries and a drink", couponsDAO.getDate(2020, 4, 4), couponsDAO.getDate(2022, 8, 8), 50, 49.90, "image");
        Coupon coupon20 = new Coupon(5, 4, "Dolphin Reef", "A tour on the Dolphin Reef includes all the attractions", couponsDAO.getDate(2020, 4, 4), couponsDAO.getDate(2022, 8, 8), 50, 50.00, "image");

        companyFacade.addCoupon(coupon1);
        companyFacade.addCoupon(coupon2);
        companyFacade.addCoupon(coupon3);
        companyFacade.addCoupon(coupon4);
        companyFacade.addCoupon(coupon5);
        companyFacade.addCoupon(coupon6);
        companyFacade.addCoupon(coupon7);
        companyFacade.addCoupon(coupon8);
        companyFacade.addCoupon(coupon9);
        companyFacade.addCoupon(coupon10);
        companyFacade.addCoupon(coupon11);
        companyFacade.addCoupon(coupon12);
        companyFacade.addCoupon(coupon13);
        companyFacade.addCoupon(coupon14);
        companyFacade.addCoupon(coupon15);
        companyFacade.addCoupon(coupon16);
        companyFacade.addCoupon(coupon17);
        companyFacade.addCoupon(coupon18);
        companyFacade.addCoupon(coupon19);
        companyFacade.addCoupon(coupon20);

        couponsDAO.addCouponPurchase(1, 10);
        couponsDAO.addCouponPurchase(2, 9);
        couponsDAO.addCouponPurchase(3, 8);
        couponsDAO.addCouponPurchase(4, 15);
        couponsDAO.addCouponPurchase(4, 16);
        couponsDAO.addCouponPurchase(4, 17);
        couponsDAO.addCouponPurchase(4, 18);
        couponsDAO.addCouponPurchase(4, 19);
        couponsDAO.addCouponPurchase(5, 6);
        couponsDAO.addCouponPurchase(6, 5);
        couponsDAO.addCouponPurchase(7, 2);
        couponsDAO.addCouponPurchase(7, 4);
        couponsDAO.addCouponPurchase(7, 6);
        couponsDAO.addCouponPurchase(7, 9);
        couponsDAO.addCouponPurchase(7, 10);
        couponsDAO.addCouponPurchase(8, 3);
        couponsDAO.addCouponPurchase(9, 2);
        couponsDAO.addCouponPurchase(10, 1);

    }
}
