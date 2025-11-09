import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;

public class HttpRequests {
	
	int userId = 1;
	
	@Test(priority=1)
	public void getUsers() {
		given()
		.when()
			.get("https://jsonplaceholder.typicode.com/posts")
		.then()
			.statusCode(200)
			.body("userId[0]", notNullValue())
			.body("id[0]", notNullValue())
          	.body("title[0]", notNullValue())
         	.body("body[0]", notNullValue())
			.log().all();
	}
	
	@Test(priority=2)
	public void createUser() {
		
		HashMap requestBody = new HashMap<>();
        requestBody.put("title", "foo");
        requestBody.put("body", "bar");
        requestBody.put("userId", userId);

        
        given()
            .contentType("application/json")
            .body(requestBody)
        .when()
            .post("https://jsonplaceholder.typicode.com/posts")
            .jsonPath().getInt("id");
        
        
        
//        .then()
//            
//            .statusCode(201)
            
            //.body("title", equalTo("foo"))
            //.body("body", equalTo("bar"))
            //.body("userId", equalTo(1))
            //.log().all();
	}
	
	@Test(priority=3, dependsOnMethods= {"createUser"})
	public void updateUser() {
		HashMap data = new HashMap();
		data.put("title", "foo 2");
		data.put("body","bar 2");
		data.put("userId", 1);
		
		given()
			.contentType("application/json")
			.body(data)
		.when()
			.put("https://jsonplaceholder.typicode.com/posts/"+userId)
		.then()
			.statusCode(200)
			.log().all();
	}
	
	@Test(priority=4)
	public void deleteUser() {
		given()
		.when()
			.delete("https://jsonplaceholder.typicode.com/posts/"+userId)
		.then()
			.statusCode(200);
	}
	
}
