package responseModels;

public class GetUserOrdersNegativeResponse extends BaseNegativeResponse{
    public GetUserOrdersNegativeResponse(boolean success, String message) {
        super(success, message);
    }

    public GetUserOrdersNegativeResponse() {
    }
}
