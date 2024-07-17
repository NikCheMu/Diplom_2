package error.messages;

public enum ErrorMessage {
    UNAUTHORIZED("You should be authorised"),

    USER_TO_UPDATE_EXIST("User with such email already exists"),

    INCORRECT_CREDENTIALS("email or password are incorrect"),

    NOT_ENOUGH_DATA_PROVIDED("Email, password and name are required fields"),

    USER_TO_CREATE_EXIST("User already exists"),

    NO_INGREDIENTS("Ingredient ids must be provided");


    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
