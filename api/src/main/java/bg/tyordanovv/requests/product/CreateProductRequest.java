package bg.tyordanovv.requests.product;

import bg.tyordanovv.core.product.ProductType;

public record CreateProductRequest
        (String name,
         String description,
         ProductType type,
         double price,
         double weight,
         int quantity){}