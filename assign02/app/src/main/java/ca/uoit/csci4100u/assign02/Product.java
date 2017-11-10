package ca.uoit.csci4100u.assign02;

/**
 * A product class to store product related data
 */
public class Product {

    /**
     * Member variables
     */
    private int productId;
    private String name;
    private String description;
    private float price;

    /**
     * A constructor to quickly create a product
     * @param productId The unique product identifier
     * @param name The name of the product
     * @param description The description of the product
     * @param price The price of the product in Canadian dollars
     */
    public Product(int productId, String name, String description, float price) {
        setProductId(productId);
        setName(name);
        setDescription(description);
        setPrice(price);
    }

    /**
     * A getter for the product id
     * @return The product id
     */
    public int getProductId() {
        return productId;
    }

    /**
     * A setter for the product id
     * @param productId The product id
     */
    public void setProductId(int productId) {
        this.productId = productId;
    }

    /**
     * A getter for the product name
     * @return The product name
     */
    public String getName() {
        return name;
    }

    /**
     * A setter for the product name
     * @param name The product name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * A getter for the product description
     * @return The product description
     */
    public String getDescription() {
        return description;
    }

    /**
     * A setter for the product description
     * @param description The product description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * A getter for the product's price in Canadian
     * @return The product's price in Canadian
     */
    public float getPrice() {
        return price;
    }

    /**
     * A setter for the product's price in Canadian
     * @param price The product's price in Canadian
     */
    public void setPrice(float price) {
        this.price = price;
    }
}
