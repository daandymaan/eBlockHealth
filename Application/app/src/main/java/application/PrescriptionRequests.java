package application;

import java.util.concurrent.TimeoutException;

import com.google.gson.JsonObject;

import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.ContractException;

public class PrescriptionRequests {

    // PrescriptionContext ctx, String date, String issuer, String product, String
    // productID, String productPackage, String quantity, String doseStrength,
    // String doseType, String doseQuantity, String instruction, String comment
    public static void createPrescription(Contract contract, JsonObject data, JsonObject user) {
        System.out.println("Creating prescription");
        try {
            contract.submitTransaction("issue", 
            data.get("date").getAsString(), 
            user.get("cert").getAsString(),
            data.get("product").getAsString(), 
            data.get("productID").getAsString(),
            data.get("productPackage").getAsString(), 
            data.get("quantity").getAsString(),
            data.get("doseStrength").getAsString(),
            data.get("doseType").getAsString(),
            data.get("doseQuantity").getAsString(),
            data.get("instruction").getAsString(),
            data.get("comment").getAsString());
        } catch (ContractException | TimeoutException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void getAllPrescriptions(Contract contract){
        System.out.println("Get all prescriptions");
        byte[] result;
        try {
            result = contract.evaluateTransaction("GetAllAssets");
            System.out.println(new String(result));
        } catch (ContractException e) {
            e.printStackTrace();
        }
    }

    public static void transferPrescription(){
        System.out.println("Get all prescriptions");
    }
}
