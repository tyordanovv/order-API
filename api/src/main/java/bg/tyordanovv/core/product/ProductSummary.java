package bg.tyordanovv.core.product;


public class ProductSummary {
    private final Long productId;
    private final String name;
    private double weight;
    private int quantity;
//    private double review;

    public ProductSummary(){
        this.productId = 0L;
        this.name = "no name";
        this.weight = 0;
        this.quantity = 0;
    }

    public ProductSummary(
            Long productId,
            String name,
            double weight,
            int quantity
            ){
        this.productId = productId;
        this.name = name;
        this.weight = weight;
        this.quantity = quantity;
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

}
