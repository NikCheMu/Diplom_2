package response.models;

public class CreateUserNegativeResponse extends BaseNegativeResponse {
    public CreateUserNegativeResponse(boolean success, String message) {
        super(success, message);
    }

    public CreateUserNegativeResponse() {
    }
}
