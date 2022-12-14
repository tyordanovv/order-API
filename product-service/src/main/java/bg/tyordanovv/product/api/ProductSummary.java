package bg.tyordanovv.product.api;

import bg.tyordanovv.product.persistence.ProductType;

public record ProductSummary
        (Long id, String name, double price, String description, String type, double rate, int rateNumbers){}
