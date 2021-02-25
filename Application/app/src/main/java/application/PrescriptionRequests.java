package application;

import java.util.concurrent.TimeoutException;

import com.google.gson.JsonObject;

import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.ContractException;

public class PrescriptionRequests {

    // PrescriptionContext ctx, String date, String issuer, String product, String
    // productID, String productPackage, String quantity, String doseStrength,
    // String doseType, String doseQuantity, String instruction, String comment
    public static void createPrescription(JsonObject data, JsonObject user) {
        System.out.println("Creating prescription");

        try {
            Contract contract = Connection.getContract(user, "pc");
            byte[] result = contract.submitTransaction("issue", data.get("date").getAsString(),
                    user.get("cert").getAsString(), data.get("product").getAsString(),
                    data.get("productID").getAsString(), data.get("productPackage").getAsString(),
                    data.get("quantity").getAsString(), data.get("doseStrength").getAsString(),
                    data.get("doseType").getAsString(), data.get("doseQuantity").getAsString(),
                    data.get("instruction").getAsString(), data.get("comment").getAsString());
            System.out.println(new String(result));
        } catch (ContractException | TimeoutException | InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getAllPrescriptions(JsonObject user) {
        System.out.println("Get all prescriptions");
        byte[] result;
        try {
            Contract contract = Connection.getContract(user, "pc");
            result = contract.evaluateTransaction("getAllPrescriptions");
            System.out.println(new String(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            return "Could not transfer prescription";
        }
    }
}
