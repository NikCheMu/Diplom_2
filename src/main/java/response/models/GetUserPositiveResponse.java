package response.models;

import response.models.createUserPositiveResponse.User;

public class GetUserPositiveResponse {

    private boolean success;

    private User user;

    public GetUserPositiveResponse(boolean success, User user) {
        this.success = success;
        this.user = user;
    }

    public GetUserPositiveResponse() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
