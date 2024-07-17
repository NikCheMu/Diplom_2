import error.messages.ErrorMessage;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import request.models.CreateUserModel;
import request.models.LogInUserModel;
import request.models.UpdateUserModel;
import response.models.UpdateUserNegativeResponse;


public class UpdateUserErrorTest extends BaseTest {

    public CreateUserModel defaultUser;

    public CreateUserModel userUnderTest;

    public String defaultUserToken;

    public String userUnderTestToken;


    @Before
    @Step("Prepare users for test")
    public void setUp() {
        defaultUser = Helpers.registerNewUser(Helpers.defaultUser());

        LogInUserModel defaultCredentials = new LogInUserModel(defaultUser.getEmail(), defaultUser.getPassword());

        defaultUserToken = Helpers.extractAcessToken(defaultCredentials);

        userUnderTest = Helpers.getRandomUser();

        Helpers.registerNewUser(userUnderTest);

        LogInUserModel userUnderTestCredentials = new LogInUserModel(userUnderTest.getEmail(), userUnderTest.getPassword());

        userUnderTestToken = Helpers.extractAcessToken(userUnderTestCredentials);
    }


    @After
    @Step("Delete users after test")
    public void tearDown() {
        ApiClient.deleteUser(specification.defaultSpecification(), defaultUserToken);

        ApiClient.deleteUser(specification.defaultSpecification(), userUnderTestToken);
    }


    @Test
    @DisplayName("Request without token should return 401 and errormessage")
    public void recieve401andErrorMessageWhenUpdateUserWithoutAuthorization() {
        UpdateUserModel updateUserModel = new UpdateUserModel(Helpers.getRandomString(), Helpers.getRandomString(), Helpers.getRandomString());

        Response response = ApiClient.updateUser(specification.defaultSpecification(), null, updateUserModel);

        UpdateUserNegativeResponse updateUserActualResponse = (UpdateUserNegativeResponse) assertions.Response.deserialize(response, UpdateUserNegativeResponse.class);

        assertions.Status.assertUnauthorized(response);

        assertions.Response.assertField(updateUserActualResponse.isSuccess(), false, true);
        assertions.Response.assertField(updateUserActualResponse.getMessage(), ErrorMessage.UNAUTHORIZED.getMessage(), true);
    }

    @Test
    @DisplayName("Update user email to existing email should return 403 and error message")
    public void receive403andErrorMessageWhenUpdateUserToExistingEmail() {

        UpdateUserModel updateUserModel = new UpdateUserModel(defaultUser.getEmail(), null, null);

        Response updateResponse = ApiClient.updateUser(specification.defaultSpecification(), userUnderTestToken, updateUserModel);

        assertions.Status.assertForbidden(updateResponse);

        UpdateUserNegativeResponse actualResponse = (UpdateUserNegativeResponse) assertions.Response.deserialize(updateResponse, UpdateUserNegativeResponse.class);

        assertions.Response.assertField(actualResponse.isSuccess(), false, true);
        assertions.Response.assertField(actualResponse.getMessage(), ErrorMessage.USER_TO_UPDATE_EXIST.getMessage(), true);
    }

}
