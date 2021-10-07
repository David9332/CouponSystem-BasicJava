package Beans;

import java.util.Calendar;
import java.util.Date;

public class Coupon {
    private int id;
    private int companyID;
    private int categoryID;
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private int amount;
    private double price;
    private String image;

    /**
     * A full class constructor for the creation of a full-field Coupon object.
     *
     * @param id          - The id of the coupon. Primary key.
     * @param companyID   - The id of the company to which the coupon belongs.
     *                    Foreign key to the id in the companies table.
     * @param categoryID  - The id of the category to which the coupon belongs.
     *                    Foreign key to the id in the categories table.
     * @param title       - Title of the coupon.
     * @param description - Description of the coupon.
     * @param startDate   - The date in which the coupon becomes active.
     * @param endDate     - The date in which the coupons cease to be active.
     * @param amount      - The amount of coupons in stock.
     * @param price       - The price of the coupon.
     * @param image       - The name of the coupon's image file.
     */
    public Coupon(int id, int companyID, int categoryID, String title, String description, Date startDate, Date endDate, int amount, double price, String image) {
        this.id = id;
        this.companyID = companyID;
        this.categoryID = categoryID;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.price = price;
        this.image = image;
    }

    /**
     * A partial class constructor for the creation of a Coupon object with auto incrementing
     * id at the data base.
     *
     * @param companyID   - The id of the company to which the coupon belongs.
     *                    Foreign key to the id in the companies table.
     * @param categoryID  - The id of the category to which the coupon belongs.
     *                    Foreign key to the id in the categories table.
     * @param title       - Title of the coupon.
     * @param description - Description of the coupon.
     * @param startDate   - The date in which the coupon becomes active.
     * @param endDate     - The date in which the coupons cease to be active.
     * @param amount      - The amount of coupons in stock.
     * @param price       - The price of the coupon.
     * @param image       - The name of the coupon's image file.
     */
    public Coupon(int companyID, int categoryID, String title, String description, Date startDate, Date endDate, int amount, double price, String image) {
        this(0, companyID, categoryID, title, description, startDate, endDate, amount, price, image);
    }

    /**
     * A default constructor for receiving Coupon objects from the data base.
     */
    public Coupon() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCompanyID() {
        return companyID;
    }

    public void setCompanyID(int companyID) {
        this.companyID = companyID;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "\nCoupon {" +
                "id=" + id +
                ", companyID=" + companyID +
                ", categoryID=" + categoryID +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", amount=" + amount +
                ", price=" + price +
                ", image='" + image + '\'' +
                '}';
    }
}
