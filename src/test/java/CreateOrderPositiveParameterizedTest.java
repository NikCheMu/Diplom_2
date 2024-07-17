import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import request.models.CreateOrderModel;
import request.models.LogInUserModel;
import response.models.createOrderPositiveResponse.CreateOrderPositiveResponse;

import java.util.ArrayList;
import java.util.List;

@RunWith(Parameterized.class)
public class CreateOrderPositiveParameterizedTest extends BaseTest {

    private final CreateOrderModel orderModel;

    private final String accessToken;

    public CreateOrderPositiveParameterizedTest(CreateOrderModel orderModel, String accessToken) {
        this.orderModel = orderModel;
        this.accessToken = accessToken;
    }

    @Parameterized.Parameters
    public static Object[][] testData() {
        String token = Helpers.extractAcessToken(new LogInUserModel(Helpers.defaultUser().getEmail(), Helpers.defaultUser().getPassword()));

        List<String> ids = Helpers.getIngredientsIds();

        ArrayList<String> preferredIngredients = new ArrayList<>();

        preferredIngredients.add(ids.get(0));
        preferredIngredients.add(ids.get(1));
        preferredIngredients.add(ids.get(2));

        CreateOrderModel createOrderModel = new CreateOrderModel(preferredIngredients);

        return new Object[][]{
                {createOrderModel, token},
                {createOrderModel, null}
        };
    }

    @Before
    @Step("Prepare user")
    public void setUp() {
        Helpers.registerNewUser(Helpers.defaultUser());
    }

    @After
    @Step("Delete user")
    public void tearDown() {
        String token = Helpers.extractAcessToken(new LogInUserModel(Helpers.defaultUser().getEmail(), Helpers.defaultUser().getPassword()));

        ApiClient.deleteUser(specification.defaultSpecification(), token);
    }

    @Test
    @DisplayName("Check that authorized and unauthorized user can make order")
    public void recieve200andSuccessfullOrderCreatedDespiteAuthorizationStatus() {
        Response response = ApiClient.postCreateOrder(orderModel, specification.defaultSpecification(), accessToken);

        assertions.Status.assertOk(response);

        CreateOrderPositiveResponse createOrderPositiveResponse = (CreateOrderPositiveResponse) assertions.Response.deserialize(response, CreateOrderPositiveResponse.class);

        assertions.Response.assertField(createOrderPositiveResponse.getName());

        assertions.Response.assertField(createOrderPositiveResponse.getOrder().getNumber());

        assertions.Response.assertField(createOrderPositiveResponse.isSuccess(), true, true);


    }


}
