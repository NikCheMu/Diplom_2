package request.models;

public class LogInUserModel {
    private String email;
    private String password;

    public LogInUserModel(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public LogInUserModel() {
    }

    public String getEmail() {
        return email;
    }

    public LogInUserModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public LogInUserModel setPassword(String password) {
        this.password = password;
        return this;
    }
}
