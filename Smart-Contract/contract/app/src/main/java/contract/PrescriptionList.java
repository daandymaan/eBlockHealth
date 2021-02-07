package contract;

import java.util.List;

import org.hyperledger.fabric.contract.Context;

import contract.ledgerapi.StateList;

public class PrescriptionList {
    
    private StateList stateList;

    public PrescriptionList(Context ctx){
        this.stateList = StateList.getStateList(ctx, PrescriptionList.class.getSimpleName(), Prescription::deserialize);
    }
    
    public PrescriptionList addPrescription(Prescription prescription){
        stateList.addState(prescription);
        return this;
    }

    public Prescription getPrescription(String key){
        return(Prescription) this.stateList.getState(key);
    }

    public PrescriptionList updatePrescription(Prescription prescription){
        this.stateList.updateState(prescription);
        return this;
    }

}
