import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import requestModels.CreateUserModel;
import requestModels.LogInUserModel;
import requestModels.UpdateUserModel;
import responseModels.UpdateUserPositiveResponse;
import responseModels.createUserPositiveResponse.User;

@RunWith(Parameterized.class)
public class UpdateUserParameterizedTest extends BaseTest {

    private final UpdateUserModel updateUserModel;

    private final UpdateUserPositiveResponse expectedResponse;
    private String token;

    private CreateUserModel user;


    public UpdateUserParameterizedTest(UpdateUserModel updateUserModel, UpdateUserPositiveResponse expectedResponse) {
        super();
        this.updateUserModel = updateUserModel;
        this.expectedResponse = expectedResponse;
    }

    @Before
    @Step("Prepare user before test")
    public void setUp(){
        user = Helpers.registerNewUser(Helpers.defaultUser());
        LogInUserModel credentials = new LogInUserModel(user.getEmail(), user.getPassword());
        token = Helpers.extractAcessToken(credentials);
    }

    @After
    @Step("Delete user after test")
    public void tearDown(){
        ApiClient.deleteUser(specification.defaultSpecification(),token);
    }

    @Parameterized.Parameters
    public static Object[][] testData(){
        CreateUserModel defaultUser = Helpers.defaultUser();
        String randomName = Helpers.getRandomString();
        String randomEmail = Helpers.getRandomString().toLowerCase();
        String randomPassword = Helpers.getRandomString();
        return new Object[][]{
                {new UpdateUserModel(null,null,randomName),
                        new UpdateUserPositiveResponse(true,new User(defaultUser.getEmail(),randomName))},
                {new UpdateUserModel(randomEmail,null,null),
                        new UpdateUserPositiveResponse(true,new User(randomEmail,defaultUser.getName()))},
                {new UpdateUserModel(null,randomPassword,null),
                        new UpdateUserPositiveResponse(true,new User(defaultUser.getEmail(),defaultUser.getName()))},
                {new UpdateUserModel(randomEmail,randomPassword,randomName),
                        new UpdateUserPositiveResponse(true,new User(randomEmail,randomName))}

        };
    }

    @Test
    @DisplayName("Check password,email and name can be updated")
    public void recieve200WhenUpdateUserCredentialsWithValidData(){
        Response updateResponse =  ApiClient.updateUser(specification.defaultSpecification(),token,updateUserModel);
        assertions.Status.assertOk(updateResponse);

        UpdateUserPositiveResponse updateUserActualPositiveResponse = (UpdateUserPositiveResponse) assertions.Response.deserialize(updateResponse, UpdateUserPositiveResponse.class);

        assertions.Response.assertField(updateUserActualPositiveResponse.getUser().getName(),expectedResponse.getUser().getName(),true);
        assertions.Response.assertField(updateUserActualPositiveResponse.getUser().getEmail(),expectedResponse.getUser().getEmail(),true);
    }
}
