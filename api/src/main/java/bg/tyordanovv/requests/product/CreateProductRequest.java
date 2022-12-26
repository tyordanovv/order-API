package bg.tyordanovv.requests.product;

public record CreateProductRequest
        (String name, String description, double price, String type){}
