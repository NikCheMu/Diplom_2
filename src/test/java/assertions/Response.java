package assertions;

import io.qameta.allure.Step;
import org.junit.Assert;

public class Response {
    @Step("Deserialize response to object")
    public static Object deserialize(io.restassured.response.Response response, Class model){
        return response.body().as(model);
    }

    @Step("Validate response")
    public static void assertField(Object actual,Object expected,boolean equals){
        if (equals){
            Assert.assertEquals(expected,actual);
        }else {
            Assert.assertNotEquals(expected,actual);
        }

    }

    @Step("Validate response")
    public static void assertField(Object expected){
        Assert.assertNotNull(expected);
    }








}