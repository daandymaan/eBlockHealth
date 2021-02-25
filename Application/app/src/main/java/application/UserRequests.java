package application;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.hyperledger.fabric.gateway.Contract;
import org.json.JSONObject;

public class UserRequests {

    public String getUsers(JsonObject user) {
        System.out.println("Get all users");
        byte result[];
        try {
            Contract contract = Connection.getContract(user, "infocontract");
            result = contract.evaluateTransaction("getAllUsers");
            return new String(result);
        } catch (Exception e) {
            e.printStackTrace();
            return "No user information found";
        }
    }

    public String getUserByIdentifier(JsonObject user, String identifier) {
        System.out.println("Get user by identifier");
        ObjectMapper mapper = new ObjectMapper();
        String results = getUsers(user);
        try {
            List<User> users = Arrays.asList(mapper.readValue(results, User[].class));
            for(User current : users){
                if(current.getIdentifier().equals(identifier)){
                    return new String(User.serialize(current));
                }
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "Could not find user";
    }

    public String updateUser(JsonObject user) {
        System.out.println("Update user");
        byte result[];
        try {
            Contract contract = Connection.getContract(user, "infocontract");
            result = contract.submitTransaction("updateUser", user.get("identifier").getAsString(),
                    user.get("title").getAsString(), user.get("firstname").getAsString(),
                    user.get("surname").getAsString(), user.get("address").getAsString(), user.get("dob").getAsString(),
                    user.get("gender").getAsString(), user.get("email").getAsString(), user.get("status").getAsString(),
                    user.get("cert").getAsString());
            return new String(result);
        } catch (Exception e) {
            e.printStackTrace();
            return "Could not update user";
        }

    }

    public String createUser(JsonObject user) {
        System.out.println("Create user");
        byte result[];
        try {
            Contract contract = Connection.getContract(user, "infocontract");
            result = contract.submitTransaction("createUser", user.get("identifier").getAsString(),
            user.get("title").getAsString(), user.get("firstname").getAsString(),
            user.get("surname").getAsString(), user.get("address").getAsString(), user.get("dob").getAsString(),
            user.get("gender").getAsString(), user.get("email").getAsString(), user.get("status").getAsString(),
            user.get("cert").getAsString());
            return new String(result);
        } catch (Exception e) {
            e.printStackTrace();
            return "Could not create user";
        }
    }
}
