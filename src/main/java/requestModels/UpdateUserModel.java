package requestModels;

public class UpdateUserModel extends CreateUserModel {

    public UpdateUserModel(String email, String password, String name) {
        super(email, password, name);
    }

    public UpdateUserModel() {
    }
}
