package infocontract;

import static java.nio.charset.StandardCharsets.UTF_8;
import org.hyperledger.fabric.contract.annotation.Property;
import org.json.JSONObject;

public class User {

    @Property()
    private String identifier;
    @Property()
    private String title;
    @Property()
    private String firstname;
    @Property()
    private String surname;
    @Property()
    private String address;
    @Property()
    private String dob;
    @Property()
    private String gender;
    @Property()
    private String email;
    @Property()
    private String status;
    @Property()
    private String cert;

    //Getters and Setters
    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCert() {
        return cert;
    }

    public void setCert(String cert) {
        this.cert = cert;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
         /**
      * Deserialize a state data to user
      *
      * @param {Buffer} data to form back into the object
      */
    public static User deserialize(String string){
        JSONObject json = new JSONObject(string);
        String identifier = json.getString("identifier");
        String title = json.getString("title");
        String firstname = json.getString("firstname");
        String surname = json.getString("surname");
        String address = json.getString("address");
        String dob = json.getString("dob");
        String gender = json.getString("gender");
        String email = json.getString("email");
        String status = json.getString("status");
        String cert = json.getString("cert");
        return new User(identifier, title, firstname, surname, address, dob, gender, email, status, cert);
    }

    public static byte[] serialize(User user){
        String jsonStr = new JSONObject(user).toString();
        return jsonStr.getBytes(UTF_8);
    }

    public User(String identifier, String title, String firstname, String surname, String address, String dob,
            String gender, String email, String status, String cert) {
        this.identifier = identifier;
        this.title = title;
        this.firstname = firstname;
        this.surname = surname;
        this.address = address;
        this.dob = dob;
        this.gender = gender;
        this.email = email;
        this.status = status;
        this.cert = cert;
    }

    @Override
    public String toString() {
        return "User [address=" + address + ", cert=" + cert + ", dob=" + dob + ", email=" + email + ", firstname="
                + firstname + ", gender=" + gender + ", identifier=" + identifier + ", status=" + status + ", surname="
                + surname + ", title=" + title + "]";
    }
}
