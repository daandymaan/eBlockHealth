package application.requests;

import java.util.logging.Logger;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.hyperledger.fabric.gateway.Contract;

import application.log.Logging;
import application.util.Connection;


public class PrescriptionRequests {

    private static final Logger LOGGER = Logging.getInstance();
    private static final String contractName = "pc";

    /**
     * Adds a prescription to the ledger using the pc contract, the contract then returns a string with the prescription that was added to the ledger
     * @param JsonObject user
     * @param String date
     * @param String issuer
     * @param String product
     * @param String productID
     * @param String productPackage
     * @param String quantity
     * @param String doseStrength
     * @param String doseType
     * @param String doseQuantity
     * @param String instruction
     * @param String comment
     * @return String 
     */
    public static String createPrescription(JsonObject user, String date, String issuer, String product, String productID, String productPackage,
    String quantity, String doseStrength, String doseType, String doseQuantity, String instruction, String comment) {
        try {
            Contract contract = Connection.getContract(user, contractName);
            byte[] result = contract.submitTransaction("issue", date, issuer, product, productID, productPackage, quantity, doseStrength, doseType, doseQuantity, instruction, comment );
            return new String(result);
        } catch (Exception e) {
            LOGGER.severe(e.toString());
            JsonObject errorResponse = new JsonObject();
            errorResponse.addProperty("msg", "Prescriptions could not be created");
            return errorResponse.toString();
        }
    }

    /**
     * This method retrieves all prescriptions from the ledger using the pc contract
     * @param JsonObject user
     * @return String
     */
    public static String getAllPrescriptions(JsonObject user) {
        byte[] result;
        try {
            Contract contract = Connection.getContract(user, contractName);
            result = contract.evaluateTransaction("getAllPrescriptions");
            return new String(result);
        } catch (Exception e) {
            e.printStackTrace();
            JsonObject errorResponse = new JsonObject();
            errorResponse.addProperty("msg", "Prescriptions could not be found");
            return errorResponse.toString();
        }
    }

    /**
     * This method retrieves all prescriptions from the ledger associated with a user
     * @param JsonObject user
     * @return
     */
    public static String getAllPrescriptionsForUser(JsonObject user){
        JsonArray userPrescriptions = new JsonArray();
        JsonArray JSONresult = JsonParser.parseString(PrescriptionRequests.getAllPrescriptions(user)).getAsJsonArray();

        for (JsonElement jsonElement : JSONresult) {
            if(user.get("cert").getAsString().equals(jsonElement.getAsJsonObject().get("owner").getAsString())){
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

    /**
     * This method transfers ownership of a prescription to another user
     * @param JsonObject user
     * @param String PID
     * @param String owner
     * @param String newOwner
     * @return String 
     */
    public static String transferPrescription(JsonObject user, String PID, String owner, String newOwner) {
        byte[] result;
        JsonObject errorResponse = new JsonObject();
        try {
            if(getPrescriptionByPID(user, PID) == null){
                errorResponse.addProperty("msg", "Prescription does not exist");
                return errorResponse.toString();
            }

            JsonObject prescriptionChosen = JsonParser.parseString(getPrescriptionByPID(user, PID)).getAsJsonObject();
            if(!prescriptionChosen.get("owner").getAsString().equals(owner)){
                errorResponse.addProperty("msg", "This prescription is owner by another user");
                return errorResponse.toString();
            }

            if(UserRequests.userExistsByCert(user, newOwner)){
                Contract contract = Connection.getContract(user, contractName);
                result = contract.submitTransaction("transferPrescription", PID, owner, newOwner);
                return new String(result);
            } else {
                errorResponse.addProperty("msg", "User does not exist");
                return errorResponse.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            errorResponse.addProperty("msg", "Prescriptions could not be transfered");
            return errorResponse.toString();
        }
    }

    /**
     * Updates a prescription on the ledger
     * @param JsonObject user
     * @param String PID
     * @param String date
     * @param String issuer
     * @param String owner
     * @param String product
     * @param String productID
     * @param String productPackage
     * @param String quantity
     * @param String doseStrength
     * @param String doseType
     * @param String doseQuantity
     * @param String instruction
     * @param String comment
     * @param String status
     * @return String 
     */
    public static String updatePrescription(JsonObject user, String PID, String date, String issuer, String owner, String product, 
    String productID, String productPackage, String quantity,String doseStrength, String doseType, String doseQuantity, 
    String instruction, String comment, String status) {
        try {
            Contract contract = Connection.getContract(user, contractName);
            byte[] result = contract.submitTransaction("updatePrescription", PID, date, issuer, owner, product, productID, productPackage, quantity, doseStrength, doseType, doseQuantity, instruction, comment, status);
            return new String(result);
        } catch (Exception e) {
            LOGGER.severe(e.toString());
            JsonObject errorResponse = new JsonObject();
            errorResponse.addProperty("msg", "Prescriptions could not be created");
            return errorResponse.toString();
        }

    }

    /**
     * Updates a prescription status on the ledger.
     * @param JsonObject user
     * @param String PID
     * @param String status
     * @return
     */
    public static String updateStatus(JsonObject user, String PID, String status) {
        byte[] result;
        Contract contract;
        try {
            contract = Connection.getContract(user, contractName);
            result = contract.submitTransaction("changeStatus", PID, status);
            return new String(result);
        } catch (Exception e) {
            LOGGER.severe(e.toString());
            JsonObject errorResponse = new JsonObject();
            errorResponse.addProperty("msg", "Prescriptions state could not be changed");
            return errorResponse.toString();
        }

    }

    /**
     * Gets the transaction history and general history for a specified key 
     * @param JsonObject user
     * @param String PID
     * @return String 
     */
    public static String getHistoryForKey(JsonObject user, String PID){
        byte[] result;
        Contract contract;
        try {
            contract = Connection.getContract(user, contractName);
            result = contract.evaluateTransaction("getHistoryForKey", PID);
            return new String(result);
        } catch (Exception e) {
            LOGGER.severe(e.toString());
            JsonObject errorResponse = new JsonObject();
            errorResponse.addProperty("msg", "History could not be gathered");
            return errorResponse.toString();
        }
    }

    /**
     * Gets the prescriptions associated with a user and then gets the history for each of the keys 
     * @param JsonObject user
     * @return String
     */
    public static String getPrescriptionHistoryForUser(JsonObject user){
        JsonObject prescriptionsObject = JsonParser.parseString(getAllPrescriptionsForUser(user)).getAsJsonObject();
        if(prescriptionsObject.isJsonArray()){
            JsonArray prescriptions = prescriptionsObject.getAsJsonArray();
            JsonArray prescriptionsHistory = new JsonArray();
            for (JsonElement p : prescriptions) {
                prescriptionsHistory.add(JsonParser.parseString(getHistoryForKey(user, p.getAsJsonObject().get("pID").getAsString())).getAsJsonArray());
            }
            LOGGER.info(prescriptionsHistory.toString());
            return prescriptionsHistory.toString();
        } else {
            JsonObject errorMsg = new JsonObject();
            errorMsg.addProperty("msg", "No prescriptions currently owned");
            return errorMsg.toString();
        }
    }

    public static String getPrescriptionByPID(JsonObject user, String PID){
        JsonArray JSONresult = JsonParser.parseString(PrescriptionRequests.getAllPrescriptions(user)).getAsJsonArray();
        for (JsonElement jsonElement : JSONresult) {
            if(PID.equals(jsonElement.getAsJsonObject().get("pID").getAsString())){
                return jsonElement.getAsJsonObject().toString();
            }
        }
        return null;
    }
}
