package responseModels;

public class UpdateUserNegativeResponse extends BaseNegativeResponse{
    public UpdateUserNegativeResponse(boolean success, String message) {
        super(success, message);
    }

    public UpdateUserNegativeResponse() {
    }
}
