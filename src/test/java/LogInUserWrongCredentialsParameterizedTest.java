import assertions.Status;
import error.messages.ErrorMessage;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import request.models.CreateUserModel;
import request.models.LogInUserModel;
import response.models.LogInUserNegativeResponse;

@RunWith(Parameterized.class)
public class LogInUserWrongCredentialsParameterizedTest extends BaseTest {
    private final LogInUserModel logInUserModel;

    private final String errorMessage;

    private String token;

    public LogInUserWrongCredentialsParameterizedTest(LogInUserModel logInUserModel, String errorMessage) {
        this.logInUserModel = logInUserModel;
        this.errorMessage = errorMessage;
    }

    @Parameterized.Parameters
    public static Object[][] testData() {
        CreateUserModel defaultUser = Helpers.defaultUser();

        return new Object[][]{
                {new LogInUserModel(Helpers.getRandomString(), defaultUser.getPassword()),
                        ErrorMessage.INCORRECT_CREDENTIALS.getMessage()},
                {new LogInUserModel(defaultUser.getEmail(), Helpers.getRandomString()),
                        ErrorMessage.INCORRECT_CREDENTIALS.getMessage()}
        };
    }

    @Before
    @Step("Prepare user before test")
    public void setUp() {
        CreateUserModel user = Helpers.registerNewUser(Helpers.defaultUser());

        LogInUserModel credentials = new LogInUserModel(user.getEmail(), user.getPassword());

        token = Helpers.extractAcessToken(credentials);
    }

    @After
    @Step("Delete user after test")
    public void tearDown() {
        ApiClient.deleteUser(specification.defaultSpecification(), token);
    }

    @Test
    @DisplayName("Check password and email combination should be registered for login")
    public void recieve401AndErrorMessageWhenWrongCredentialsProvidedAtLogIn() {
        Response logInResponse = ApiClient.postLogInUser(logInUserModel, specification.defaultSpecification());

        Status.assertUnauthorized(logInResponse);

        LogInUserNegativeResponse deserialized = (LogInUserNegativeResponse) assertions.Response.deserialize(logInResponse, LogInUserNegativeResponse.class);

        assertions.Response.assertField(deserialized.isSuccess(), false, true);

        assertions.Response.assertField(deserialized.getMessage(), errorMessage, true);
    }


}
