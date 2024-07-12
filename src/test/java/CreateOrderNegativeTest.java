import errorMessages.ErrorMessage;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import requestModels.CreateOrderModel;
import responseModels.CreateOrderNegativeResponse;
import responseModels.CreateUserNegativeResponse;

import java.util.ArrayList;
import java.util.UUID;


public class CreateOrderNegativeTest extends  BaseTest{


    @Test
    @DisplayName("Check status code should be 400 and error message when ingredients no provided to order")
    public void recieve400andErrorMessageWhenCreateOrderWithoutIngredientIds(){
        ArrayList<String> noIds = new ArrayList<>();
        CreateOrderModel order = new CreateOrderModel(noIds);

        Response response = ApiClient.postCreateOrder(order,specification.defaultSpecification(),null);
        assertions.Status.assertBadRequest(response);

        CreateOrderNegativeResponse createUserNegativeResponse  = (CreateOrderNegativeResponse) assertions.Response.deserialize(response,CreateOrderNegativeResponse.class);

        assertions.Response.assertField(createUserNegativeResponse.isSuccess(),false,true);
        assertions.Response.assertField(createUserNegativeResponse.getMessage(), ErrorMessage.NO_INGREDIENTS.getMessage(),true);

    }

    @Test
    @DisplayName("Check status code should be 500 when unknown ingredients provided to order")
    public void recieve500WhenCreateOrderWithUnknownIngredientIds(){
       String  ingredientFirst = UUID.randomUUID().toString();
       String  ingredientSecond = UUID.randomUUID().toString();
       ArrayList<String> ids = new ArrayList<>();
       ids.add(ingredientFirst);
       ids.add(ingredientSecond);

       CreateOrderModel order = new CreateOrderModel(ids);

       Response response = ApiClient.postCreateOrder(order,specification.defaultSpecification(),null);
       assertions.Status.assertInternalServerError(response);





    }



}
