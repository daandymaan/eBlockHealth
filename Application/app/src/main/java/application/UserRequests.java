package application;

import java.util.logging.Logger;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.hyperledger.fabric.gateway.Contract;

public class UserRequests {
    private static final Logger LOGGER = Logging.getInstance();

    public static String getUsers(JsonObject user) {
        LOGGER.info("getUsers");
        byte result[];
        try {
            Contract contract = Connection.getContract(user, "infocontract");
            result = contract.evaluateTransaction("getAllUsers");
            LOGGER.info(new String(result));
            return new String(result);
        } catch (Exception e) {
            LOGGER.info(e.toString());
            JsonObject errorResponse = new JsonObject();
            errorResponse.addProperty("msg", "Users could not be found");
            LOGGER.info(new String(errorResponse.toString()));
            return errorResponse.toString();
        }
    }

    public static String getUserByIdentifier(JsonObject user, String identifier) {
        LOGGER.info("getUserByIdentifier");
        JsonObject userSelected = new JsonObject();
        JsonArray JSONResults = JsonParser.parseString(UserRequests.getUsers(user)).getAsJsonArray();
        LOGGER.info(JSONResults.toString());
        for (JsonElement jsonElement : JSONResults) {
            if(jsonElement.getAsJsonObject().get("identifier").getAsString().equals(identifier)){
                userSelected = jsonElement.getAsJsonObject();
            }
        }

        if(!userSelected.entrySet().isEmpty()){
            return userSelected.toString();
        } else {
            JsonObject errorResponse = new JsonObject();
            errorResponse.addProperty("msg", "User could not be found");
            return errorResponse.toString();
        }

    }

    public static String updateUser(JsonObject user, String identifier, String title, String firstname, 
    String surname, String address, String dob, String gender, String email, String status, String cert) {
        System.out.println("Update user");
        byte result[];
        try {
            Contract contract = Connection.getContract(user, "infocontract");
            result = contract.submitTransaction("updateUser", identifier, title, firstname, surname, address, dob, gender, email, status, cert);
            return new String(result);
        } catch (Exception e) {
            e.printStackTrace();
            JsonObject errorResponse = new JsonObject();
            errorResponse.addProperty("msg", "User could not be updated");
            return errorResponse.toString();
        }

    }

    public static String createUser(JsonObject user, String identifier, String title, String firstname, 
    String surname, String address, String dob, String gender, String email, String status, String cert) {
        LOGGER.info(user.toString());
        byte result[];
        try {
            Contract contract = Connection.getContract(user, "infocontract");
            LOGGER.info("CONTRACT FOUND");
            result = contract.submitTransaction("createUser", identifier, title, firstname, surname, address, dob, gender, email, status, cert);
            LOGGER.info(new String(result));
            return new String(result);
        } catch (Exception e) {
            LOGGER.severe(e.toString());
            JsonObject errorResponse = new JsonObject();
            errorResponse.addProperty("msg", "User could not be created");
            return errorResponse.toString();
        }
    }
}
