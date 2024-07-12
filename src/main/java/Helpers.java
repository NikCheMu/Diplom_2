import com.github.javafaker.Faker;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import requestModels.CreateUserModel;
import requestModels.LogInUserModel;
import responseModels.LogInUserPositiveResponse;
import responseModels.getIngredientsPositiveResponse.GetIngredientPositiveResponse;
import responseModels.getIngredientsPositiveResponse.Ingredient;

import java.util.ArrayList;
import java.util.List;

import static org.apache.http.HttpStatus.*;

public class Helpers {

    static CreateUserModel getRandomUser(){
        Faker faker = new Faker();
        String email = faker.internet().emailAddress();
        String name = faker.name().firstName();
        String password = name+name + faker.number().randomDigitNotZero()+"@!";
        return new CreateUserModel(email,password,name);
    }

    static void tearDown(CreateUserModel randomUser, RequestSpecification specification){
        LogInUserModel logInUserModel = new LogInUserModel(randomUser.getEmail(), randomUser.getPassword());
        Response logInResponse = ApiClient.postLogInUser(logInUserModel,specification);
        if (logInResponse.statusCode()==SC_ACCEPTED){
          String accessToken = logInResponse.body().as(LogInUserPositiveResponse.class).getAccessToken();
          ApiClient.deleteUser(specification,accessToken).then().statusCode(SC_OK);
        }

    }

    static String extractAcessToken(LogInUserModel logInUserModel){
        DefaultSpecification specification = new DefaultSpecification("https://stellarburgers.nomoreparties.site");
        Response logInResponse = ApiClient.postLogInUser(logInUserModel,specification.defaultSpecification());
        return logInResponse.body().as(LogInUserPositiveResponse.class).getAccessToken();
    }

    static String getRandomString() {
        Faker faker = new Faker();
        return faker.rickAndMorty().toString();
    }

   static CreateUserModel registerNewUser(CreateUserModel createUserModel){
       DefaultSpecification specification = new DefaultSpecification("https://stellarburgers.nomoreparties.site");
       Response response = ApiClient.postCreateUser(createUserModel,specification.defaultSpecification());
       return createUserModel;
   }

   static CreateUserModel defaultUser(){
        return new CreateUserModel("la_ca_pu_sta_1@mail.com","password1","Bloger1");
   }


   static List<String> getIngredientsIds(){
       ArrayList<String> ids = new ArrayList<String>();
       DefaultSpecification specification = new DefaultSpecification("https://stellarburgers.nomoreparties.site");
       Response response = ApiClient.getIngredients(specification.defaultSpecification());
       GetIngredientPositiveResponse deserialized = response.body().as(GetIngredientPositiveResponse.class);
       for (Ingredient ingredient:deserialized.getData()){
           ids.add(ingredient.get_id());
       }
       return  ids;


   }




}



