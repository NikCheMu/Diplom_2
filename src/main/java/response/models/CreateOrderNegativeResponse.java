package response.models;

public class CreateOrderNegativeResponse extends BaseNegativeResponse {
    public CreateOrderNegativeResponse(boolean success, String message) {
        super(success, message);
    }

    public CreateOrderNegativeResponse() {
    }
}
