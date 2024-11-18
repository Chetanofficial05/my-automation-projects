package api.test;


import org.testng.Assert;
import org.testng.annotations.*;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.payload.User;
import io.restassured.response.Response;

public class UserTests {
	
	Faker faker;
	User userPayload;
	
	@BeforeClass
	public void setupData() {
		
		faker = new Faker();
		userPayload = new User();
		
        userPayload.setId(faker.number().randomDigitNotZero()); // Random non-zero digit for ID
        userPayload.setUsername(faker.name().username());        // Generate a username
        userPayload.setFirstName(faker.name().firstName());      // Generate a first name
        userPayload.setLastName(faker.name().lastName());        // Generate a last name
        userPayload.setEmail(faker.internet().emailAddress());   // Generate an email address
        userPayload.setPassword(faker.internet().password());    // Generate a password
        userPayload.setPhone(faker.phoneNumber().cellPhone());   // Generate a phone number

	}
	
	@Test(priority=1)
	public void testPostUser() {
		
		Response response = UserEndPoints.createUser(userPayload);
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);	
	}
	
	
	@Test(priority=2)
	public void testGetUserByName() {
		Response response = UserEndPoints.readUser(this.userPayload.getUsername());
		
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);	
	}
	
	@Test(priority=3)
	public void testupdateUserByName() {
		
        userPayload.setFirstName(faker.name().firstName());      // Generate a first name
        userPayload.setLastName(faker.name().lastName());        // Generate a last name
        userPayload.setEmail(faker.internet().emailAddress());   // Generate an email address
		
		Response response = UserEndPoints.updateUser(userPayload, this.userPayload.getUsername() );
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);	
		
		//Checking data after update
		Response responseafterupdate = UserEndPoints.readUser(this.userPayload.getUsername());
		Assert.assertEquals(responseafterupdate.getStatusCode(), 200);	
	}
	
	@Test(priority=4)
	public void testDeleteUserByName() {
		
		Response response = UserEndPoints.deleteUser(this.userPayload.getUsername());
		Assert.assertEquals(response.getStatusCode(), 200);	
		
	}
	
	

}
