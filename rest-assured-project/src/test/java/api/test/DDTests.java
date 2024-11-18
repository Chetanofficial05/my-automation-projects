package api.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import api.endpoints.UserEndPoints;
import api.payload.User;
import api.utilities.DataProviders;
import io.restassured.response.Response;

public class DDTests {
    
    User userPayload;
    
    @Test(dataProvider = "excelData", dataProviderClass = DataProviders.class, priority = 1)
    public void testPostUser(int id, String username, String firstName, String lastName, String email, String password, String phone) {
        
        userPayload = new User();
        userPayload.setId(id); 
        userPayload.setUsername(username);
        userPayload.setFirstName(firstName);
        userPayload.setLastName(lastName);
        userPayload.setEmail(email);
        userPayload.setPassword(password);
        userPayload.setPhone(phone);

        // Call the API endpoint to create the user
        Response response = UserEndPoints.createUser(userPayload);
        Assert.assertEquals(response.getStatusCode(), 200);
    }
}
