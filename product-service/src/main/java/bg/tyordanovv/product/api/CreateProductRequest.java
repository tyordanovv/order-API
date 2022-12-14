package bg.tyordanovv.product.api;

public record CreateProductRequest
        (String name, String description, double price, String type){}
