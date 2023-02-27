import apiEngine.model.requests.AuthRequest;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

public class Users {
    private static final String USER_ID = "9b5f49ab-eea9-45f4-9d66-bcf56a531b85";
    private static final String BASE_URL = "http://restapi.adequateshop.com";
private static String token;
private static Response response;
    private static String jsonString;
    private static String bookId;

    JsonPath jsonPathEvaluator;
    @BeforeTest
   //@Test
    public void tokenGenerate(){

        AuthRequest credentials= new AuthRequest("Developer5@gmail.com","123456");
        RestAssured.baseURI= BASE_URL;
        RequestSpecification request=RestAssured.given();
        request.header("Content-Type","application/json");
        response=request.body(credentials).post("/api/authaccount/login");
       jsonPathEvaluator= response.jsonPath();
 token= jsonPathEvaluator.get("data.Token");
 System.out.println("Test>>>>>"+ token);
    }


    @Test
    public void getUsers(){
        RequestSpecification request=RestAssured.given();
        System.out.println("Token text:>>"+token);
        request.header("Authorization","Bearer "+ token);
        response=request.get("/api/users?page=1");
int statusCode= response.statusCode();
//Print status code
System.out.println(statusCode);
        jsonPathEvaluator=response.jsonPath();
        List<String> resJsonUser=jsonPathEvaluator.getList("data.name");

        for(String name : resJsonUser)
        {
            System.out.println("Name: " + name);
        }

    }



}
