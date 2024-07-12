package responseModels;

import responseModels.createUserPositiveResponse.CreateUserPositiveResponse;
import responseModels.createUserPositiveResponse.User;

public class LogInUserPositiveResponse extends CreateUserPositiveResponse {
    public LogInUserPositiveResponse(boolean success, User user, String accessToken, String refreshToken) {
        super(success, user, accessToken, refreshToken);
    }

    public LogInUserPositiveResponse() {
    }
}
