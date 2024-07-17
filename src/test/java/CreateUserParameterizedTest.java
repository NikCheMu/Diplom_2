import assertions.Status;
import error.messages.ErrorMessage;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import request.models.CreateUserModel;
import response.models.CreateUserNegativeResponse;

@RunWith(Parameterized.class)
public class CreateUserParameterizedTest extends BaseTest {
    private final CreateUserModel createUserModel;

    private final String errorMessage;

    public CreateUserParameterizedTest(CreateUserModel createUserModel, String errorMessage) {
        this.createUserModel = createUserModel;
        this.errorMessage = errorMessage;
    }

    @Parameterized.Parameters
    public static Object[][] testData() {
        return new Object[][]{
                {Helpers.getRandomUser().setEmail(null), ErrorMessage.NOT_ENOUGH_DATA_PROVIDED.getMessage()},
                {Helpers.getRandomUser().setName(null), ErrorMessage.NOT_ENOUGH_DATA_PROVIDED.getMessage()},
                {Helpers.getRandomUser().setPassword(null), ErrorMessage.NOT_ENOUGH_DATA_PROVIDED.getMessage()},
        };
    }

    @Test
    @DisplayName("Check name,password and email are required for registration")
    public void recieve403andErrorMessageWhenCredentialsNotProvided() {
        Response createResponse = ApiClient.postCreateUser(createUserModel, specification.defaultSpecification());

        Status.assertForbidden(createResponse);

        CreateUserNegativeResponse deserialized = (CreateUserNegativeResponse) assertions.Response.deserialize(createResponse, CreateUserNegativeResponse.class);

        assertions.Response.assertField(deserialized.isSuccess(), false, true);
        assertions.Response.assertField(deserialized.getMessage(), errorMessage, true);
    }
}
