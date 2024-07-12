import assertions.Status;
import errorMessages.ErrorMessage;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import requestModels.LogInUserModel;
import responseModels.LogInUserNegativeResponse;

@RunWith(Parameterized.class)
public class LogInUserNullCredentialsParameterizedTest extends BaseTest{

    private final LogInUserModel logInUserModel;

    private final String errorMessage;

    public LogInUserNullCredentialsParameterizedTest(LogInUserModel logInUserModel, String errorMessage) {
        this.logInUserModel = logInUserModel;
        this.errorMessage = errorMessage;
    }

    @Parameterized.Parameters
    public static Object[][] testData(){
        return new Object[][]{
                {new LogInUserModel(Helpers.getRandomUser().getEmail(), Helpers.getRandomUser().getPassword()).setEmail(null), ErrorMessage.INCORRECT_CREDENTIALS.getMessage()},
                {new LogInUserModel(Helpers.getRandomUser().getEmail(), Helpers.getRandomUser().getPassword()).setPassword(null), ErrorMessage.INCORRECT_CREDENTIALS.getMessage()}
        };
    }



    @Test
    @DisplayName("Check password and email are required for authorization")
    public void recieve401WhenLoginWithoutAllCredentialsNedeed(){
        Response logInResponse = ApiClient.postLogInUser(logInUserModel, specification.defaultSpecification());

        Status.assertUnauthorized(logInResponse);

        LogInUserNegativeResponse deserialized = (LogInUserNegativeResponse) assertions.Response.deserialize(logInResponse, LogInUserNegativeResponse.class);
        assertions.Response.assertField(deserialized.isSuccess(),false,true);
        assertions.Response.assertField(deserialized.getMessage(),errorMessage,true);
    }
}
