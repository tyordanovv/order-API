package bg.tyordanovv.requests.product;

//@AllArgsConstructor
public record ProductQuantityRequest
        (Long id, String name, int quantity){}
