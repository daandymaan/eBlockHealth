package application.service;

import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import application.log.Logging;
import application.requests.Authentication;
import application.requests.UserRequests;

/**
 * Class that contains API URI for user requests
 */
 //Default path
@Path("/userRequestsGateway")
public class UserService {
    private static final Logger LOGGER = Logging.getInstance();

    /**
     * POST request
     * Gets all users stored on the ledger
     * @param String user (JsonObject)
     * @return MediaType.APPLICATION_JSON (JsonObject.toString())
     */
    @POST
    @Path("/getAllUsers")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllUsers(String user){
        JsonObject userInfo = (JsonObject) JsonParser.parseString(user).getAsJsonObject();
        return UserRequests.getUsers(userInfo);
    }

    /**
     * POST request
     * Gets a user's detail by identifier
     * @param String request (JsonObject)
     * @return MediaType.APPLICATION_JSON (JsonObject.toString())
     */
    @POST
    @Path("/getUser")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getUser(String request){
        JsonObject requestJson = (JsonObject) JsonParser.parseString(request).getAsJsonObject();
        JsonObject userInfo = requestJson.get("user").getAsJsonObject();
        String identifier = requestJson.get("identifier").getAsString();
        return UserRequests.getUserByIdentifier(userInfo, identifier);
    }

    /**
     * POST request
     * Updates a user's details 
     * @param String request (JsonObject)
     * @return MediaType.APPLICATION_JSON (JsonObject.toString())
     */
    @POST
    @Path("/updateUserDetails")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String updateUserDetails(String request){
        JsonObject requestJson = (JsonObject) JsonParser.parseString(request).getAsJsonObject();
        JsonObject userInfo = requestJson.get("user").getAsJsonObject();
        JsonObject updatedUser = requestJson.get("updatedUser").getAsJsonObject();
        String identifier = updatedUser.get("identifier").getAsString();
        String title = updatedUser.get("title").getAsString();
        String firstname = updatedUser.get("firstname").getAsString();
        String surname = updatedUser.get("surname").getAsString();
        String address = updatedUser.get("address").getAsString();
        String dob = updatedUser.get("dOB").getAsString();
        String gender = updatedUser.get("gender").getAsString();
        String email  = updatedUser.get("email").getAsString();
        String status = updatedUser.get("status").getAsString();
        String cert = updatedUser.get("cert").getAsString();
        return UserRequests.updateUser(userInfo, identifier, title, firstname, surname, address, dob, gender, email, status, cert);
    }

    /**
     * POST request
     * Creates a user by HTTP request
     * @param String request (JsonObject)
     * @return MediaType.APPLICATION_JSON (JsonObject.toString())
     */
    @POST
    @Path("/createUser")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String createUser(String request){
        JsonObject requestJson = (JsonObject) JsonParser.parseString(request).getAsJsonObject();
        JsonObject userInfo = requestJson.get("user").getAsJsonObject();
        JsonObject newUser = requestJson.get("updatedUser").getAsJsonObject();
        String identifier = newUser.get("identifier").getAsString();
        String title = newUser.get("title").getAsString();
        String firstname = newUser.get("firstname").getAsString();
        String surname = newUser.get("surname").getAsString();
        String address = newUser.get("address").getAsString();
        String dob = newUser.get("dOB").getAsString();
        String gender = newUser.get("gender").getAsString();
        String email  = newUser.get("email").getAsString();
        String status = newUser.get("status").getAsString();
        String cert = newUser.get("cert").getAsString();
        return UserRequests.createUser(userInfo, identifier, title, firstname, surname, address, dob, gender, email, status, cert);
    }

    /**
     * POST request
     * Detects if a user exists by wallet address
     * @param String request (JsonObject)
     * @return MediaType.APPLICATION_JSON (JsonObject.toString())
     */
    @POST
    @Path("/userExists")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String userDetailsExist(String user){
        LOGGER.info(user);
        JsonObject userInfo = (JsonObject) JsonParser.parseString(user).getAsJsonObject();
        Authentication authentication = new Authentication();
        JsonObject msg = new JsonObject();
        try {
            if(authentication.userExists(userInfo)){
                msg.addProperty("msg", "success");
            } else {
                msg.addProperty("msg", "fail");
            }
        } catch (Exception e) {
            LOGGER.severe(e.toString());
            msg.addProperty("msg", "fail");
        }
        return msg.toString();
    }
}
