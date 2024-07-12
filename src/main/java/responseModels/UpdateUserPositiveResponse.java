package responseModels;

import responseModels.createUserPositiveResponse.User;

public class UpdateUserPositiveResponse extends GetUserPositiveResponse{
    public UpdateUserPositiveResponse(boolean success, User user) {
        super(success, user);
    }

    public UpdateUserPositiveResponse() {
    }


}
