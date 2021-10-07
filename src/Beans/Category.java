package Beans;

public enum Category {

    ELECTRICITY(1),
    FOOD(2),
    RESTAURANT(3),
    VACATION(4);

    private int categoryId;

    /**
     * Category constructor.
     *
     * @param categoryId - The id number of the category.
     */
    Category(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getCategoryId() {
        return categoryId;
    }
}
