package api.test;



import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.AssertJUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.endpoints.UserEndPoints2;
import api.payload.User;
import io.restassured.response.Response;

public class UserTests2 {
	
	Faker faker;
	User userPayload;
	
	public Logger logger; 
	
	@BeforeClass
	public void setup() {
		faker=new Faker();
		userPayload =new User();
		
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(5,10));
		userPayload.setPhone(faker.phoneNumber().cellPhone());
		
		//logs
		logger= LogManager.getLogger(this.getClass());
		
		
		
	
	}
	@Test(priority=1)
	public void testPostUser() {
		logger.info("*******-Creating the user----********");
		Response response= UserEndPoints2.CreateUser(userPayload);
		response.then().log().all();
		AssertJUnit.assertEquals(response.getStatusCode(), 200);
		logger.info("*******- user is created ----********");
		
	}
	@Test(priority=2)
	public void testGetUserByName() {
		logger.info("*******-Reading user info----********");
		Response response= UserEndPoints2.readUser(this.userPayload.getUsername());
		response.then().log().all();
		AssertJUnit.assertEquals(response.getStatusCode(), 200);
		logger.info("*******-user info is displayed----********");
	}
	
	@Test(priority=3)
	public void testUpdateUserbyname() {
		//update data using payload
		logger.info("*******-User updating----********");

		
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		
		
		Response response= UserEndPoints.updateUser(this.userPayload.getUsername(),userPayload);
		response.then().log().body();
		AssertJUnit.assertEquals(response.getStatusCode(), 200);
		
		logger.info("*******-User updated----********");
		//checking data after update.
		Response responseAfterUpdate= UserEndPoints2.readUser(this.userPayload.getUsername());
		response.then().log().body();
		AssertJUnit.assertEquals(responseAfterUpdate.getStatusCode(), 200);
		
	}
	
	@Test(priority=4)
	public void testDeleteUserByName() {
		logger.info("*******-User deleting----********");
		Response response=UserEndPoints2.deleteUser(this.userPayload.getUsername());
		AssertJUnit.assertEquals(response.getStatusCode(), 200);
		logger.info("*******-User deleted----********");
		
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
