package application;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.hyperledger.fabric.gateway.Contract;
import org.json.JSONObject;

public class UserRequests {

    public static String getUsers(JsonObject user) {
        System.out.println("Get all users");
        byte result[];
        try {
            Contract contract = Connection.getContract(user, "infocontract");
            result = contract.evaluateTransaction("getAllUsers");
            return new String(result);
        } catch (Exception e) {
            e.printStackTrace();
            JsonObject errorResponse = new JsonObject();
            errorResponse.addProperty("msg", "Users could not be found");
            return errorResponse.toString();
        }
    }

    public static String getUserByIdentifier(JsonObject user, String identifier) {
        System.out.println("Get user by identifier");
        JsonObject userSelected = new JsonObject();
        JsonArray JSONResults = JsonParser.parseString(UserRequests.getUsers(user)).getAsJsonArray();

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
        System.out.println("Create user");
        byte result[];
        try {
            Contract contract = Connection.getContract(user, "infocontract");
            result = contract.submitTransaction("createUser", identifier, title, firstname, surname, address, dob, gender, email, status, cert);
            return new String(result);
        } catch (Exception e) {
            JsonObject errorResponse = new JsonObject();
            errorResponse.addProperty("msg", "User could not be created");
            return errorResponse.toString();
        }
    }
}
