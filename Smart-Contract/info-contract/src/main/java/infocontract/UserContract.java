package infocontract;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

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
import org.hyperledger.fabric.shim.ledger.KeyValue;
import org.hyperledger.fabric.shim.ledger.QueryResultsIterator;

@Contract(
        name = "infocontract",
        info = @Info(
                title = "User contract",
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
public class UserContract implements ContractInterface{
    private final Genson genson = new Genson();

    /**
     * Checks the existence of the prescription on the ledger
     *
     * @param ctx the transaction context
     * @param identifier the ID of the user
     * @return boolean indicating the existence of the asset
     */
    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public boolean userExists(final Context ctx, final String identifier) {
        ChaincodeStub stub = ctx.getStub();
        String userJSON = stub.getStringState(identifier);
        return (userJSON != null && !userJSON.isEmpty());
    }

    /**
     * Creates a new user
     * 
     * @param ctx
     * @param identifier
     * @param title
     * @param firstname
     * @param surname
     * @param address
     * @param dob
     * @param gender
     * @param email
     * @param status
     * @param cert 
     * @return The string with the json value of the entry added to the ledger
     */
    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public String createUser(final Context ctx, String identifier, String title, String firstname, String surname, String address, String dob, String gender, String email, String status, String cert){
        ChaincodeStub stub = ctx.getStub();
        String JSON = "";
        if(!userExists(ctx, identifier)){
            User user = new User(identifier, title, firstname, surname, address, dob, gender, email, status, cert);
            JSON = genson.serialize(user);
            stub.putStringState(identifier, JSON);
        }
        return JSON;
    }

    /**
     * Updates a users information
     * @param ctx
     * @param identifier
     * @param title
     * @param firstname
     * @param surname
     * @param address
     * @param dob
     * @param gender
     * @param email
     * @param status
     * @param cert
     * @return
     */
    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public String updateUser(final Context ctx, String identifier, String title, String firstname, String surname, String address, String dob, String gender, String email, String status, String cert) {
        ChaincodeStub stub = ctx.getStub();
        String JSON = "";
        if (userExists(ctx, identifier)) {
            User user = new User(identifier, title, firstname, surname, address, dob, gender, email, status, cert);
            JSON = genson.serialize(user);
            stub.putStringState(identifier, JSON); 
        }
        return JSON;
    }

    /**
     * This method retrieves all users from the blockchain
     * @param ctx The given context 
     * @return A string of the combined entries in the database
     */
    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public String getAllUsers(final Context ctx){
        ChaincodeStub stub = ctx.getStub();
        List<User> queryResults = new ArrayList<User>();
        QueryResultsIterator<KeyValue> results = stub.getStateByRange("", "");
        for (KeyValue result: results) {
            User user = User.deserialize(result.getStringValue());
            queryResults.add(user);
        }
        final String response = genson.serialize(queryResults);
        return response;
    }
}
