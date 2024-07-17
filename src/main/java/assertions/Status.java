package assertions;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static org.apache.http.HttpStatus.*;

public class Status {

    @Step("Validate status code is 200 OK")
    public static void assertOk(Response response) {
        response.then().statusCode(SC_OK);
    }

    @Step("Validate status code is 400 BADREQUEST")
    public static void assertBadRequest(Response response) {
        response.then().statusCode(SC_BAD_REQUEST);
    }

    @Step("Validate status code is 403 FORBIDDEN")
    public static void assertForbidden(Response response) {
        response.then().statusCode(SC_FORBIDDEN);
    }

    @Step("Validate status code is 403 FORBIDDEN")
    public static void assertUnauthorized(Response response) {
        response.then().statusCode(SC_UNAUTHORIZED);
    }

    @Step("Validate status code is 500 INTERNAL_SERVER_ERROR")
    public static void assertInternalServerError(Response response) {
        response.then().statusCode(SC_INTERNAL_SERVER_ERROR);
    }


}