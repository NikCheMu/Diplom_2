package responseModels;

public class LogInUserNegativeResponse extends BaseNegativeResponse {
    public LogInUserNegativeResponse(boolean success, String message) {
        super(success, message);
    }

    public LogInUserNegativeResponse() {
    }
}
