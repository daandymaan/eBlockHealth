package application;

import static java.nio.charset.StandardCharsets.UTF_8;

import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;
import org.json.JSONObject;
import org.json.JSONPropertyIgnore;

@DataType()
public class Prescription{

    public final static String ISSUED = "ISSUED";
    public final static String OWNED = "OWNED";
    public final static String REDEEMED = "REDEEMED";

    @Property()
    private String state="";

    public String getState() {
        return state;
    }

    public Prescription setState(String state) {
        this.state = state;
        return this;
    }

    @JSONPropertyIgnore()
    public boolean isIssued() {
        return this.state.equals(Prescription.ISSUED);
    }

    @JSONPropertyIgnore()
    public boolean isOwned() {
        return this.state.equals(Prescription.OWNED);
    }

    @JSONPropertyIgnore()
    public boolean isRedeemed() {
        return this.state.equals(Prescription.REDEEMED);
    }

    public Prescription setIssued() {
        this.state = Prescription.ISSUED;
        return this;
    }

    public Prescription setOwnership() {
        this.state = Prescription.OWNED;
        return this;
    }

    public Prescription setRedeemed() {
        this.state = Prescription.REDEEMED;
        return this;
    }

    //Values to be stored on the blockchain
    @Property()
    private String PID;

    @Property()
    private String date;
    
    @Property()
    private String issuer;
    
    @Property()
    private String owner;

    @Property()
    private String product;

    @Property()
    private String productID;

    @Property()
    private String productPackage;

    @Property()
    private String quantity;

    @Property()
    private String doseStrength;

    @Property()
    private String doseType;

    @Property()
    private String doseQuantity;

    @Property()
    private String instruction;

    @Property()
    private String comment;

    public Prescription() {
        super();
    }

    //Getters and setters for values
    public String getPID() {
        return PID;
    }

    public void setPID(String pID) {
        PID = pID;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getOwner() {
        return owner;
    }

    public Prescription setOwner(String owner) {
        this.owner = owner;
        return this;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductPackage() {
        return productPackage;
    }

    public void setProductPackage(String productPackage) {
        this.productPackage = productPackage;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDoseStrength() {
        return doseStrength;
    }

    public void setDoseStrength(String doseStrength) {
        this.doseStrength = doseStrength;
    }

    public String getDoseType() {
        return doseType;
    }

    public void setDoseType(String doseType) {
        this.doseType = doseType;
    }

    public String getDoseQuantity() {
        return doseQuantity;
    }

    public void setDoseQuantity(String doseQuantity) {
        this.doseQuantity = doseQuantity;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    /**
     * Deserialize a state data to prescription
     *
     * @param {Buffer} data to form back into the object
     */
    //String date, String issuer, String owner, String product, String productID, String productPackage, String quantity, String doseStrength, String doseType, String doseQuantity, String instruction, String comment
    //Method to retreive a JSON object and convert the data into Strings that the class can use
    public static Prescription deserialize(byte[] data){
        JSONObject json = new JSONObject(new String(data, UTF_8));

        String state = json.getString("state");        
        String PID = json.getString("PID");
        String date = json.getString("date");
        String issuer = json.getString("issuer");
        String owner = json.getString("owner");
        String product = json.getString("product");
        String productID = json.getString("productID");
        String productPackage = json.getString("productPackage");
        String quantity = json.getString("quantity");
        String doseStrength = json.getString("doseStrength");
        String doseType = json.getString("doseType");
        String doseQuantity = json.getString("doseQuantity");
        String instruction = json.getString("instruction");
        String comment = json.getString("comment");
        
        return new Prescription(PID, date, issuer,  owner,  product,  productID,  productPackage,  quantity,  doseStrength,  doseType,  doseQuantity,  instruction,  comment, state);
    }

    // public static byte[] serialize(Prescription prescription) {
    //     return State.serialize(prescription);
    // }

    //Without state
    public Prescription(String PID, String date, String issuer, String owner, String product, String productID,
            String productPackage, String quantity, String doseStrength, String doseType, String doseQuantity,
            String instruction, String comment) {

        this.PID = PID;
        this.date = date;
        this.issuer = issuer;
        this.owner = owner;
        this.product = product;
        this.productID = productID;
        this.productPackage = productPackage;
        this.quantity = quantity;
        this.doseStrength = doseStrength;
        this.doseType = doseType;
        this.doseQuantity = doseQuantity;
        this.instruction = instruction;
        this.comment = comment;
    }

    //With state
    public Prescription(String PID, String date, String issuer, String owner, String product, String productID,
            String productPackage, String quantity, String doseStrength, String doseType, String doseQuantity,
            String instruction, String comment, String state) {

        this.PID = PID;
        this.state = state;
        this.date = date;
        this.issuer = issuer;
        this.owner = owner;
        this.product = product;
        this.productID = productID;
        this.productPackage = productPackage;
        this.quantity = quantity;
        this.doseStrength = doseStrength;
        this.doseType = doseType;
        this.doseQuantity = doseQuantity;
        this.instruction = instruction;
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Prescription [PID=" + PID + ", comment=" + comment + ", date=" + date + ", doseQuantity=" + doseQuantity
                + ", doseStrength=" + doseStrength + ", doseType=" + doseType + ", instruction=" + instruction
                + ", issuer=" + issuer + ", owner=" + owner + ", product=" + product + ", productID=" + productID
                + ", productPackage=" + productPackage + ", quantity=" + quantity + ", state=" + state + "]";
    }
}
