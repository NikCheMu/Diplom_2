import assertions.Status;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import requestModels.CreateUserModel;
import requestModels.LogInUserModel;
import responseModels.LogInUserPositiveResponse;

public class LogInUserTest extends BaseTest {

    public CreateUserModel createUserModel;

    public LogInUserModel logInUserModel;

    @Before
    @Step("Prepare data for tests")
    public void setUp(){
        createUserModel = Helpers.getRandomUser();
        logInUserModel = new LogInUserModel(createUserModel.getEmail(),createUserModel.getPassword());
    }

    @After
    @Step("Clean up after tests")
    public void tearDown(){
        Helpers.tearDown(createUserModel, specification.defaultSpecification());
    }


    @Test
    @DisplayName("Successfull login returns tokens and user credentials")
    public void recieveAccessTokenAndCredentialsWhenLogInSuccess(){
        Response createResponse = ApiClient.postCreateUser(createUserModel, specification.defaultSpecification());

        Status.assertOk(createResponse);

        Response logInResponse = ApiClient.postLogInUser(logInUserModel, specification.defaultSpecification());

        Status.assertOk(logInResponse);

        LogInUserPositiveResponse logInUserPositiveResponse = (LogInUserPositiveResponse) assertions.Response.deserialize(logInResponse, LogInUserPositiveResponse.class);
        assertions.Response.assertField(logInUserPositiveResponse.isSuccess(),true,true);
        assertions.Response.assertField(logInUserPositiveResponse.getAccessToken());
        assertions.Response.assertField(logInUserPositiveResponse.getRefreshToken());
        assertions.Response.assertField(logInUserPositiveResponse.getUser().getName(),createUserModel.getName(),true);
        assertions.Response.assertField(logInUserPositiveResponse.getUser().getEmail(),createUserModel.getEmail(),true);

    }


}
