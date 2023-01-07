package bg.tyordanovv.requests.product;

import bg.tyordanovv.core.product.ProductType;

public record CreateProductRequest
        (String name, String description, double price, ProductType type, double weight, int quantity){}