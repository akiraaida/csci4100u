package ca.uoit.csci4100u.assign02;

public class Product {

    private int productId;
    private String name;
    private String description;
    private float price;

    public Product(int productId, String name, String description, float price) {
        setProductId(productId);
        setName(name);
        setDescription(description);
        setPrice(price);
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
