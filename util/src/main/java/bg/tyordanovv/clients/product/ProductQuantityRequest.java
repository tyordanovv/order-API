package bg.tyordanovv.clients.product;

import lombok.AllArgsConstructor;

public record ProductQuantityRequest
        (Long id, String name, int quantity){}
