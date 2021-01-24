package contract;

import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.shim.ChaincodeStub;

public class PrescriptionContext extends Context{
    
    public PrescriptionContext(ChaincodeStub stub){
        super(stub);
        this.prescriptionList = new PrescriptionList(this);
    }

    public PrescriptionList prescriptionList;
}
