package prescriptioncontract;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import com.owlike.genson.Genson;

import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contact;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Default;
import org.hyperledger.fabric.contract.annotation.Info;
import org.hyperledger.fabric.contract.annotation.License;
import org.hyperledger.fabric.contract.annotation.Transaction;
import org.hyperledger.fabric.shim.ChaincodeStub;
import org.hyperledger.fabric.shim.ledger.KeyModification;
import org.hyperledger.fabric.shim.ledger.KeyValue;
import org.hyperledger.fabric.shim.ledger.QueryResultsIterator;

@Contract(
        name = "pc",
        info = @Info(
                title = "Prescription contract",
                description = "Allows the creation and transfer of prescriptions",
                version = "0.0.1-SNAPSHOT",
                license = @License(
                        name = "Apache 2.0 License",
                        url = "http://www.apache.org/licenses/LICENSE-2.0.html"),
                contact = @Contact(
                        email = "sanieldimons@gmail.com",
                        name = "Daniel Simons",
                        url = "https://github.com/daandymaan")))
@Default
public final class PrescriptionContract implements ContractInterface {
    private final Genson genson = new Genson();

    /**
     * Generate an ID for the prescription
     * @param date The datetime the prescription was created
     * @param issuer The current issuer of the prescription
     * @param product The product title
     * @param productID The ID related to the product 
     * @return The ID for the prescription
     */
    public String generatePID(String date, String issuer, String product, String productID){
        return Integer.toString( Math.abs(Objects.hash(date, issuer, product, productID))); 
    }

    /**
     * Issue a prescription, creates a new prescription to be used by a GP
     * String date, String issuer, String owner, String product, String productID, String productPackage, String quantity, String doseStrength, String doseType, String doseQuantity, String instruction, String comment
     * @param ctx The transaction context
     * @param date The date when the prescription was issued
     * @param issuer The issuer of the prescription (e.g. GP)
     * @param product Medical product title  
     * @param productID Product ID which relates to the medicine 
     * @param productPackage Type of package the product is in 
     * @param quantity Quantity of the product needed to complete prescription
     * @param doseStrength Dose strength (e.g. 200mg)
     * @param doseType The type of medication (Tablets, dropper)
     * @param doseQuantity Dose quantity how many insances of the medical product 
     * @param instruction Instruction in how to consume the medication
     * @param comments Comments in addition to the instructions
     * @return The string json value of the entry added to the ledger
     */
    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public String issue(final Context ctx, String date, String issuer, String product, String productID, String productPackage, String quantity, String doseStrength, String doseType, String doseQuantity, String instruction, String comment){
        ChaincodeStub stub = ctx.getStub();
        String JSON = "";
        String PID = generatePID(date, issuer, product, productID);
        String status = "ACTIVE";
        if(!prescriptionExists(ctx, PID)){
            //Create instance of prescription
            Prescription prescription = new Prescription(PID, date, issuer, "", product, productID, productPackage, quantity, doseStrength, doseType, doseQuantity, instruction, comment, status);
            //Prescription is owned by the issuer
            prescription.setOwner(issuer);
            JSON = genson.serialize(prescription);
            stub.putStringState(PID, JSON);
        }
        return JSON;
    }
    /**
     *  Updates the properties of an prescription on the ledger.
     * @param ctx The transaction context
     * @param PID The PID of the prescription to be updated
     * @param date The date when the prescription was issued
     * @param issuer The issuer of the prescription (e.g. GP)
     * @param owner The current owner of the prescription
     * @param product Medical product title  
     * @param productID Product ID which relates to the medicine 
     * @param productPackage Type of package the product is in 
     * @param quantity Quantity of the product needed to complete prescription
     * @param doseStrength Dose strength (e.g. 200mg)
     * @param doseType The type of medication (Tablets, dropper)
     * @param doseQuantity Dose quantity how many insances of the medical product 
     * @param instruction Instruction in how to consume the medication
     * @param comments Comments in addition to the instructions
     * @return A string containing the updated prescription
     */
    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public String updatePrescription(final Context ctx,String PID, String date, String issuer, String owner, String product, String productID, String productPackage, String quantity, String doseStrength, String doseType, String doseQuantity, String instruction, String comment, String status) {
        ChaincodeStub stub = ctx.getStub();
        String JSON = "";
        if (prescriptionExists(ctx, PID)) {
            Prescription prescription = new Prescription(PID, date, issuer,  owner,  product,  productID,  productPackage,  quantity,  doseStrength,  doseType,  doseQuantity,  instruction,  comment, status);
            JSON = genson.serialize(prescription);
            stub.putStringState(PID, JSON);    
        }
        return JSON;
    }

    /**
     * This method retrieves all prescriptions from the blockchain
     * @param ctx The given context 
     * @return A string of the combined entries in the database
     */
    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public String getAllPrescriptions(final Context ctx){
        ChaincodeStub stub = ctx.getStub();
        List<Prescription> queryResults = new ArrayList<Prescription>();
        QueryResultsIterator<KeyValue> results = stub.getStateByRange("", "");
        for (KeyValue result: results) {
            Prescription prescription = Prescription.deserialize(result.getStringValue());
            queryResults.add(prescription);
        }
        final String response = genson.serialize(queryResults);
        return response;
    }

    /**
    * This method changes ownership of the prescription
    * @param ctx The given context 
    * @param PID The PID of the prescription
    * @param newOwner The new owner of the prescription
    * @return A string containing the updated prescription
    */
    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public String transferPrescription(final Context ctx, final String PID, final String owner, final String newOwner) {
        ChaincodeStub stub = ctx.getStub();
        String JSON = stub.getStringState(PID);
        if (JSON != null ) {
            Prescription prescription = Prescription.deserialize(JSON);
            if(owner.equals(prescription.getOwner())){
                prescription.setOwner(newOwner);
                JSON = genson.serialize(prescription);
                stub.putStringState(PID, JSON);
            }
        }
        return JSON;
    }

    /**
    * This method changes status of the prescription
    * @param ctx The given context 
    * @param PID The PID of the prescription
    * @param status The new owner of the prescription
    * @return A string containing the updated prescription
    */
    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public String changeStatus(final Context ctx, String PID, String status) {
        ChaincodeStub stub = ctx.getStub();
        String JSON = stub.getStringState(PID);
        if (JSON != null ) {
            Prescription prescription = Prescription.deserialize(JSON);
            prescription.setStatus(status);
            JSON = genson.serialize(prescription);
            stub.putStringState(PID, JSON);
        }
        return JSON;
    }

    /**
     * This method is still in progress (IT WILL RETURN ALL TRANSACTION HISTORY OF A KEY)
     * @param ctx
     * @param PID
     * @return
     */
    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public String getHistoryForKey(final Context ctx, String PID){
        ChaincodeStub stub = ctx.getStub();
        ArrayList<String> results  = new ArrayList<String>();
        QueryResultsIterator<KeyModification> history =  stub.getHistoryForKey(PID);
        Iterator<KeyModification> iter = history.iterator();
        while(iter.hasNext()){
            results.add(iter.next().getStringValue());
        }
        return results.toString();
    }

    /**
     * Checks the existence of the prescription on the ledger
     *
     * @param ctx the transaction context
     * @param PID the ID of the prescription
     * @return boolean indicating the existence of the asset
     */
    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public boolean prescriptionExists(final Context ctx, final String PID) {
        ChaincodeStub stub = ctx.getStub();
        String prescriptionJSON = stub.getStringState(PID);
        return (prescriptionJSON != null && !prescriptionJSON.isEmpty());
    }
}
