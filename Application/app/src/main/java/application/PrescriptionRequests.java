package application;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.hyperledger.fabric.gateway.Contract;

public class PrescriptionRequests {

    // PrescriptionContext ctx, String date, String issuer, String product, String
    // productID, String productPackage, String quantity, String doseStrength,
    // String doseType, String doseQuantity, String instruction, String comment
    public static String createPrescription(JsonObject user, String date, String issuer, String product, String productID, String productPackage,
    String quantity, String doseStrength, String doseType, String doseQuantity, String instruction, String comment) {
        System.out.println("Creating prescription");
        try {
            Contract contract = Connection.getContract(user, "pc");
            byte[] result = contract.submitTransaction("issue", date, issuer, product, productID, productPackage, quantity, doseStrength, doseType, doseQuantity, instruction, comment );
            return new String(result);
        } catch (Exception e) {
            e.printStackTrace();
            JsonObject errorResponse = new JsonObject();
            errorResponse.addProperty("msg", "Prescriptions could not be created");
            return errorResponse.toString();
        }
    }

    public static String getAllPrescriptions(JsonObject user) {
        System.out.println("Get all prescriptions");
        byte[] result;
        try {
            Contract contract = Connection.getContract(user, "pc");
            result = contract.evaluateTransaction("getAllPrescriptions");
            System.out.println(new String(result));
            return new String(result);
        } catch (Exception e) {
            e.printStackTrace();
            JsonObject errorResponse = new JsonObject();
            errorResponse.addProperty("msg", "Prescriptions could not be found");
            return errorResponse.toString();
        }
    }

    public static String getAllPrescriptionsForUser(JsonObject user){
        System.out.println("Get all prescriptions for user");
        JsonArray userPrescriptions = new JsonArray();
        JsonArray JSONresult = JsonParser.parseString(PrescriptionRequests.getAllPrescriptions(user)).getAsJsonArray();

        for (JsonElement jsonElement : JSONresult) {
            if(user.get("identifier").getAsString().equals(jsonElement.getAsJsonObject().get("owner").getAsString())){
                userPrescriptions.add(jsonElement);
            }
        }
        
        if(userPrescriptions.size() == 0){
            JsonObject errorMsg = new JsonObject();
            errorMsg.addProperty("msg", "No prescriptions found for user");
            return errorMsg.toString();
        }
        return userPrescriptions.toString();
    }

    public static String transferPrescription(JsonObject user, String PID, String newOwner) {
        System.out.println("Transfer prescription");
        byte[] result;
        try {
            Contract contract = Connection.getContract(user, "pc");
            result = contract.submitTransaction("transferPrescription", PID, newOwner);
            return new String(result);

        } catch (Exception e) {
            e.printStackTrace();
            JsonObject errorResponse = new JsonObject();
            errorResponse.addProperty("msg", "Prescriptions could not be transfered");
            return errorResponse.toString();
        }
    }

    public static String updatePrescription(JsonObject user, String PID, String date, String issuer, String owner, String product, 
    String productID, String productPackage, String quantity,String doseStrength, String doseType, String doseQuantity, 
    String instruction, String comment, String status) {
        System.out.println("Updating prescription");
        try {
            Contract contract = Connection.getContract(user, "pc");
            byte[] result = contract.submitTransaction("updatePrescription", PID, date, issuer, owner, product, productID, productPackage, quantity, doseStrength, doseType, doseQuantity, instruction, comment, status);
            System.out.println(new String(result));
            return new String(result);
        } catch (Exception e) {
            e.printStackTrace();
            JsonObject errorResponse = new JsonObject();
            errorResponse.addProperty("msg", "Prescriptions could not be created");
            return errorResponse.toString();
        }

    }

    public static String updateStatus(JsonObject user, String PID, String status) {
        System.out.println("Update prescription status");
        byte[] result;
        Contract contract;
        try {
            contract = Connection.getContract(user, "pc");
            result = contract.submitTransaction("changeStatus", PID, status);
            return new String(result);
        } catch (Exception e) {
            e.printStackTrace();
            JsonObject errorResponse = new JsonObject();
            errorResponse.addProperty("msg", "Prescriptions state could not be changed");
            return errorResponse.toString();
        }

    }
}
