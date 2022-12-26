package bg.tyordanovv.core.product;


public class ProductEntity {
    private final Long productId;
    private final String name;
    private double weight;
    private int quantity;
    private double review;

    public ProductEntity(){
        this.productId = 0l;
        this.name = "no name";
        this.weight = 0;
        this.quantity = 0;
        this.review = 0;
    }

    public ProductEntity(
            Long productId,
            String name,
            double weight,
            int quantity
            ){
        this.productId = productId;
        this.name = name;
        this.weight = weight;
        this.quantity = quantity;
        this.review = 0;
    }

    public Long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setReview(double review) {
        this.review = review;
    }
}
