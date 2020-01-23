package my.edu.unikl.placeitright;

public class ProductInfo {
    private String productName, productImage, productPrice;
    private Long ProductId;

    public ProductInfo() {
        //empty constructor needed
    }

    public ProductInfo(String productName, String productImage, String productPrice, Long productId) {
        this.productName = productName;
        this.productImage = productImage;
        this.productPrice = productPrice;
        this.ProductId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductPrice() {
        return "RM " + productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public Long getProductId() { return ProductId; }

    public void setProductId(Long productId) { ProductId = productId; }
}
