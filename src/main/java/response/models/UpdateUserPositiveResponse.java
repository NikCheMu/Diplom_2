package response.models;

import response.models.createUserPositiveResponse.User;

public class UpdateUserPositiveResponse extends GetUserPositiveResponse {
    public UpdateUserPositiveResponse(boolean success, User user) {
        super(success, user);
    }

    public UpdateUserPositiveResponse() {
    }


}
