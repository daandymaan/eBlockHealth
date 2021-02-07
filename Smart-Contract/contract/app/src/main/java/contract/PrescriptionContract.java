package contract;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contact;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Default;
import org.hyperledger.fabric.contract.annotation.Info;
import org.hyperledger.fabric.contract.annotation.License;
import org.hyperledger.fabric.contract.annotation.Transaction;
import org.hyperledger.fabric.shim.ChaincodeStub;
import org.hyperledger.fabric.shim.ledger.KeyValue;
import org.hyperledger.fabric.shim.ledger.QueryResultsIterator;

import contract.ledgerapi.State;

@Contract(name = "org.blockprescription.prescription", info = @Info(title = "Prescription contract", description = "Contract", version = "0.0.1", license = @License(name = "SPDX-License-Identifier: Apache-2.0", url = ""),contact = @Contact (email = "java-contract@example.com", name = "java-contract", url = "http://java-contract.me") ))
@Default
public class PrescriptionContract implements ContractInterface{
    
    @Override
    public Context createContext(ChaincodeStub stub){
        return new PrescriptionContext(stub);
    }

    public PrescriptionContract(){

    }

    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public boolean PrescriptionExists(PrescriptionContext ctx, String PID){
        Prescription p = ctx.prescriptionList.getPrescription(PID);
        if(p == null){
            System.out.println("Prescription does not exist");
            return false;
        } 
        return true;

    }

    //Data migration if needed
    @Transaction
    public void instantiate(PrescriptionContext ctx) {

    }

    /**
     * Issue a prescription
     * String date, String issuer, String owner, String product, String productID, String productPackage, String quantity, String doseStrength, String doseType, String doseQuantity, String instruction, String comment
     * @param {Context} ctx the transaction context
     * @param {String} date of when the prescription was issued
     * @param {String} issuer of the prescription (e.g. GP)
     * @param {String} Medical product title  
     * @param {String} Product ID which relates to the medicine 
     * @param {String} Quantity of the product needed to complete prescription
     * @param {String} Dose strength (e.g. 200mg)
     * @param {String} Dose quantity how many insances of the medical product 
     * @param {String} Instruction in how to consume the medication
     * @param {String} Comments in addition to the instructions
     */
    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public Prescription issue(PrescriptionContext ctx, String date, String issuer, String product, String productID, String productPackage, String quantity, String doseStrength, String doseType, String doseQuantity, String instruction, String comment){
        
        String PID = generatePID(date, issuer, product, productID);
        //Create instance of prescription
        Prescription prescription = new Prescription(PID, date, issuer, "", product, productID, productPackage, quantity, doseStrength, doseType, doseQuantity, instruction, comment, "");

        //Set status or state of the prescription to issued
        prescription.setIssued();

        //Prescription is owned by the issuer
        prescription.setOwner(issuer);

        System.out.println(prescription.toString());

        //Add prescription to simliar state prescriptions in the ledger
        ctx.prescriptionList.addPrescription(prescription);

        return prescription;
    }

    /**
     * Send a prescription
     * String date, String issuer, String owner, String product, String productID, String productPackage, String quantity, String doseStrength, String doseType, String doseQuantity, String instruction, String comment
     * @param {Context} ctx the transaction context
     * @param {String} PID of the prescription
     * @param {String} date of when the prescription was issued
     * @param {String} issuer of the prescription (e.g. GP)
     * @param {String} owner of the prescription (e.g. patient)
     */
    //KEY GENERATION SHOULD BE A BIT MORE COMPLEX
    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public Prescription send(PrescriptionContext ctx, String PID,  String date, String currentOwner, String recipient){
        String key = State.makeKey(new String[] { PID });
        Prescription prescription = ctx.prescriptionList.getPrescription(key);

        //Validate current owner
        if (!prescription.getOwner().equals(currentOwner)) {
            throw new RuntimeException("Prescription " + key + " is not owned by " + currentOwner);
        }

         // First send moves state from ISSUED to TRADING
         if (prescription.isIssued()) {
            prescription.setOwnership();
        }

        // Check Prescription is not already REDEEMED
        if (prescription.isOwned()) {
            prescription.setOwner(recipient);
        } else {
            throw new RuntimeException("Prescription " + key + " is not trading. Current state = " + prescription.getState());
        }

        // Update the Prescription
        ctx.prescriptionList.updatePrescription(prescription);
        return prescription;
    }

    /**
     * Redeem a prescription
     * String date, String issuer, String owner, String product, String productID, String productPackage, String quantity, String doseStrength, String doseType, String doseQuantity, String instruction, String comment
     * @param {Context} ctx the transaction context
     * @param {String} PID of prescription
     * @param {String} date of when the prescription was issued
     * @param {String} owner of the prescription (e.g. patient)
     */
    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public Prescription redeem(PrescriptionContext ctx, String PID,  String date, String owner){
        String key = Prescription.makeKey(new String[] { PID });

        Prescription prescription = ctx.prescriptionList.getPrescription(key);

        // Check prescription is not REDEEMED
        if (prescription.isRedeemed()) {
            throw new RuntimeException("Prescription " + key + " already redeemed");
        }

        // Verify that the redeemer owns the commercial prescription before redeeming it
        if (prescription.getOwner().equals(owner)) {
            prescription.setOwner(prescription.getIssuer());
            prescription.setRedeemed();
        } else {
            throw new RuntimeException("Redeeming owner does not own prescription" + key);
        }

        ctx.prescriptionList.updatePrescription(prescription);
        return prescription;
    }

    public String generatePID(String date, String issuer, String product, String productID){
       return Integer.toString(Objects.hash(date, issuer, productID));
    }

    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public List<Prescription> getAllPrescriptions(PrescriptionContext ctx){
        ChaincodeStub stub = ctx.getStub();
        List<Prescription> prescriptionList = new ArrayList<Prescription>();

        QueryResultsIterator<KeyValue> results = stub.getStateByRange("", "");

        for(KeyValue result : results){
            Prescription prescription = Prescription.deserialize(result.getValue());
            prescriptionList.add(prescription);
            System.out.println(prescription.toString());
        }
        //This might have to be to string
        return prescriptionList;
    }
}
