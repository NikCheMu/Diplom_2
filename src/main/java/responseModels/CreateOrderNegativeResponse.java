package responseModels;

public class CreateOrderNegativeResponse extends BaseNegativeResponse {
    public CreateOrderNegativeResponse(boolean success, String message) {
        super(success, message);
    }

    public CreateOrderNegativeResponse() {
    }
}
