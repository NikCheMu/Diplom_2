import assertions.Status;
import error.messages.ErrorMessage;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import request.models.CreateUserModel;
import response.models.CreateUserNegativeResponse;
import response.models.createUserPositiveResponse.CreateUserPositiveResponse;

public class CreateUserTest extends BaseTest {

    public CreateUserModel createUserModel;

    @Before
    @Step("Prepare user for test")
    public void setUp() {
        createUserModel = Helpers.getRandomUser();
    }

    @After
    @Step("Delete user after test")
    public void tearDown() {
        Helpers.tearDown(createUserModel, specification.defaultSpecification());
    }

    @Test
    @DisplayName("Successfull registration returns tokens and user credentials")
    public void recieveAccessTokenAndCredentialsWhenRegistrationSuccess() {
        Response createResponse = ApiClient.postCreateUser(createUserModel, specification.defaultSpecification());

        Status.assertOk(createResponse);

        CreateUserPositiveResponse createUserPositiveResponse = (CreateUserPositiveResponse) assertions.Response.deserialize(createResponse, CreateUserPositiveResponse.class);

        assertions.Response.assertField(createUserPositiveResponse.isSuccess(), true, true);

        assertions.Response.assertField(createUserPositiveResponse.getAccessToken());

        assertions.Response.assertField(createUserPositiveResponse.getRefreshToken());

        assertions.Response.assertField(createUserPositiveResponse.getUser().getName(), createUserModel.getName(), true);

        assertions.Response.assertField(createUserPositiveResponse.getUser().getEmail(), createUserModel.getEmail(), true);

    }

    @Test
    @DisplayName("Check that user cant register twice")
    public void recieve403WhenTryToRegisterTwiceWithSameCredentials() {
        Response createResponse = ApiClient.postCreateUser(createUserModel, specification.defaultSpecification());

        Status.assertOk(createResponse);

        Response createAlreadyRegisteredResponse = ApiClient.postCreateUser(createUserModel, specification.defaultSpecification());

        Status.assertForbidden(createAlreadyRegisteredResponse);

        CreateUserNegativeResponse createUserNegativeResponse = (CreateUserNegativeResponse) assertions.Response.deserialize(createAlreadyRegisteredResponse, CreateUserNegativeResponse.class);

        assertions.Response.assertField(createUserNegativeResponse.isSuccess(), false, true);

        assertions.Response.assertField(createUserNegativeResponse.getMessage(), ErrorMessage.USER_TO_CREATE_EXIST.getMessage(), true);
    }


}
