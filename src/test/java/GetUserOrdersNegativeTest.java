import errorMessages.ErrorMessage;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import responseModels.GetUserOrdersNegativeResponse;

public class GetUserOrdersNegativeTest extends BaseTest {

    @Test
    @DisplayName("Check status code and error message when get user orders unauthorized")
    public void recieve401AndErrorMessageWhenTryToRecieveUserOrdersUnauthorized(){
        Response response = ApiClient.getUserOrders(specification.defaultSpecification(), null);

        assertions.Status.assertUnauthorized(response);

        GetUserOrdersNegativeResponse getUserNegativeResponse = (GetUserOrdersNegativeResponse) assertions.Response.deserialize(response,GetUserOrdersNegativeResponse.class);
        assertions.Response.assertField(getUserNegativeResponse.isSuccess(),false,true);
        assertions.Response.assertField(getUserNegativeResponse.getMessage(), ErrorMessage.UNAUTHORIZED.getMessage(),true);
    }
}
