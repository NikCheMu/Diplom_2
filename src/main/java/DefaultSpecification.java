import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class DefaultSpecification {

    private String URL;

    public DefaultSpecification(String URL) {
        this.URL = URL;
    }

    public RequestSpecification defaultSpecification(){
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBaseUri(this.URL);
        builder.addHeader("Content-Type","application/json");
        return builder.build();
    }
}
