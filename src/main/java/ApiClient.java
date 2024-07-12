import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import requestModels.CreateOrderModel;
import requestModels.CreateUserModel;
import requestModels.LogInUserModel;
import requestModels.UpdateUserModel;

import static io.restassured.RestAssured.given;

public class ApiClient {

    @Step("Send POST/api/auth/register")
    public static Response postCreateUser(CreateUserModel createUserModel, RequestSpecification specification){
        return given()
                .spec(specification)
                .body(createUserModel)
                .when()
                .post("/api/auth/register");
    }

    @Step("Send POST/api/auth/login")
    public static Response postLogInUser(LogInUserModel logInUserModel, RequestSpecification specification){
        return given()
                .spec(specification)
                .body(logInUserModel)
                .when()
                .post("/api/auth/login");
    }


    @Step("Send DELETE/api/auth/user")
    public static Response deleteUser(RequestSpecification specification,String accessToken){
        if(accessToken!=null){
            specification.header("Authorization",accessToken);
        }
        return given()
                .spec(specification)
                .delete("/api/auth/user");

    }

    @Step("Send GET/api/auth/user")
    public static Response getUser(RequestSpecification specification,String accessToken){
        if(accessToken!=null){
            specification.header("Authorization",accessToken);
        }
        return given()
                .spec(specification)
                .get("/api/auth/user");

    }
    @Step("Send PATCH/api/auth/user")
    public static Response updateUser(RequestSpecification specification, String accessToken, UpdateUserModel updateUserModel){
        if(accessToken!=null){
            specification.header("Authorization",accessToken);
        }
        return given()
                .spec(specification)
                .body(updateUserModel)
                .patch("/api/auth/user");
    }

    @Step("Send POST/api/orders")
    public static Response postCreateOrder(CreateOrderModel createOrderModel, RequestSpecification specification,String accessToken){
        if(accessToken!=null){
            specification.header("Authorization",accessToken);
        }
        return given()
                .spec(specification)
                .body(createOrderModel)
                .when()
                .post("/api/orders");
    }


    @Step("Send GET/api/ingredients")
    public static Response getIngredients(RequestSpecification specification){
        return given()
                .spec(specification)
                .get("/api/ingredients");

    }

    @Step("Send GET/api/orders")
    public static Response getUserOrders(RequestSpecification specification,String accessToken){
        if(accessToken!=null){
            specification.header("Authorization",accessToken);
        }
        return given()
                .spec(specification)
                .get("/api/orders");
    }




}
