package application.requests;

import java.util.logging.Logger;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.hyperledger.fabric.gateway.Contract;

import application.log.Logging;


public class UserRequests {
    private static final Logger LOGGER = Logging.getInstance();

    /**
     * Gets all users from the ledger using the infocontract contract
     * @param JsonObject user
     * @return String
     */
    public static String getUsers(JsonObject user) {
        byte result[];
        try {
            Contract contract = Connection.getContract(user, "infocontract");
            result = contract.evaluateTransaction("getAllUsers");
            return new String(result);
        } catch (Exception e) {
            LOGGER.info(e.toString());
            JsonObject errorResponse = new JsonObject();
            errorResponse.addProperty("msg", "Users could not be found");
            return errorResponse.toString();
        }
    }

    /**
     * Gets a users details by their identifier from the infocontract contract on the ledger
     * @param JsonObject user
     * @param String identifier
     * @return String
     */
    public static String getUserByIdentifier(JsonObject user, String identifier) {
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

    /**
     * Updates a user's details on the ledger using the contract infocontract
     * @param JsonObject user
     * @param String identifier
     * @param String title
     * @param String firstname
     * @param String surname
     * @param String address
     * @param String dob
     * @param String gender
     * @param String email
     * @param String status
     * @param String cert
     * @return String 
     */
    public static String updateUser(JsonObject user, String identifier, String title, String firstname, 
    String surname, String address, String dob, String gender, String email, String status, String cert) {
        byte result[];
        try {
            Contract contract = Connection.getContract(user, "infocontract");
            result = contract.submitTransaction("updateUser", identifier, title, firstname, surname, address, dob, gender, email, status, cert);
            LOGGER.info(new String(result));
            return new String(result);
        } catch (Exception e) {
            e.printStackTrace();
            JsonObject errorResponse = new JsonObject();
            errorResponse.addProperty("msg", "User could not be updated");
            return errorResponse.toString();
        }

    }

    /**
     * Creates a new user on the ledger using the contract infocontract
     * @param user
     * @param String identifier
     * @param String title
     * @param String firstname
     * @param String surname
     * @param String address
     * @param String dob
     * @param String gender
     * @param String email
     * @param String status
     * @param String cert
     * @return String 
     */
    public static String createUser(JsonObject user, String identifier, String title, String firstname, 
    String surname, String address, String dob, String gender, String email, String status, String cert) {
        byte result[];
        try {
            Contract contract = Connection.getContract(user, "infocontract");
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
