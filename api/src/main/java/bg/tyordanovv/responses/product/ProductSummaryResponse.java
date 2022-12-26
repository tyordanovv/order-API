package bg.tyordanovv.responses.product;

public record ProductSummaryResponse
        (Long id, String name, double price, String description, String type, double review, int orderedQuantity){}
