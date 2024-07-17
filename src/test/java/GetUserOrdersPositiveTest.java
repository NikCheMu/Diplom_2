import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import request.models.CreateOrderModel;
import request.models.CreateUserModel;
import request.models.LogInUserModel;
import response.models.getUserOrdersPositiveResponse.GetUserOrdersPositiveResponse;

import java.util.ArrayList;
import java.util.List;

public class GetUserOrdersPositiveTest extends BaseTest {

    public CreateUserModel user;

    public String token;

    public CreateOrderModel firstOrder;

    public CreateOrderModel secondOrder;

    @Before
    @Step("Prepare user and data for test")
    public void setUp() {
        user = Helpers.registerNewUser(Helpers.defaultUser());

        LogInUserModel credentials = new LogInUserModel(user.getEmail(), user.getPassword());

        token = Helpers.extractAcessToken(credentials);

        List<String> ids = Helpers.getIngredientsIds();

        ArrayList<String> firstOrderIngredients = new ArrayList<>();

        firstOrderIngredients.add(ids.get(0));
        firstOrderIngredients.add(ids.get(1));
        firstOrderIngredients.add(ids.get(2));

        firstOrder = new CreateOrderModel(firstOrderIngredients);

        ArrayList<String> secondOrderIngredients = new ArrayList<>();

        secondOrderIngredients.add(ids.get(3));
        secondOrderIngredients.add(ids.get(4));
        secondOrderIngredients.add(ids.get(5));

        firstOrder = new CreateOrderModel(firstOrderIngredients);

        secondOrder = new CreateOrderModel(secondOrderIngredients);

        ApiClient.postCreateOrder(firstOrder, specification.defaultSpecification(), token).then().statusCode(200);

        ApiClient.postCreateOrder(secondOrder, specification.defaultSpecification(), token).then().statusCode(200);


    }

    @After
    @Step("Delete user after test")
    public void tearDown() {
        ApiClient.deleteUser(specification.defaultSpecification(), token);
    }

    @Test
    @DisplayName("Check that authorized user can get his orders")
    public void recieve200andOrdersListWhenGetOrdersAuthorized() {
        Response response = ApiClient.getUserOrders(specification.defaultSpecification(), token);

        assertions.Status.assertOk(response);

        GetUserOrdersPositiveResponse getUserOrdersPositiveResponse = (GetUserOrdersPositiveResponse) assertions.
                Response.
                deserialize(response, GetUserOrdersPositiveResponse.class);

        assertions.Response.assertField(getUserOrdersPositiveResponse.isSuccess(), true, true);
        assertions.Response.assertField(getUserOrdersPositiveResponse.getOrders().size(), 2, true);
        assertions.Response.assertField(getUserOrdersPositiveResponse.getOrders().get(0).getIngredients().containsAll(firstOrder.getIngredients()), true, true);
        assertions.Response.assertField(getUserOrdersPositiveResponse.getOrders().get(1).getIngredients().containsAll(secondOrder.getIngredients()), true, true);

    }

}
