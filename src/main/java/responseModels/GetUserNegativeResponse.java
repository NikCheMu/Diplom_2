package responseModels;

public class GetUserNegativeResponse extends BaseNegativeResponse {
    public GetUserNegativeResponse(boolean success, String message) {
        super(success, message);
    }

    public GetUserNegativeResponse() {
    }
}
