package application;

import java.io.IOException;
import java.security.PrivateKey;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;

import com.google.common.base.CharMatcher;
import com.google.gson.JsonObject;

import org.hyperledger.fabric.gateway.Identities;
import org.hyperledger.fabric.gateway.Identity;
import org.hyperledger.fabric.gateway.Wallet;
import org.hyperledger.fabric.gateway.X509Identity;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.User;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric.sdk.security.CryptoSuiteFactory;
import org.hyperledger.fabric_ca.sdk.EnrollmentRequest;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric_ca.sdk.RegistrationRequest;


public class RegisterUser {

    private static final Logger LOGGER = Logging.getInstance();
    private static String pemLocation = "/home/dan/Docs/fabric-samples/test-network/organizations//peerOrganizations/org1.example.com/ca/ca.org1.example.com-cert.pem";
    private static String url = "https://localhost:7054";

    /**
     * Gets certificate authority from the network
     * @return HFCAClient
     * @throws Exception
     */
    public static HFCAClient createCAClient() throws Exception {
        Properties props = new Properties();
        props.put("pemFile", pemLocation);
        props.put("allowAllHostName", "true");
        HFCAClient caClient = HFCAClient.createNewInstance(url, props);
        CryptoSuite cryptoSuite = CryptoSuiteFactory.getDefault().getCryptoSuite();
        caClient.setCryptoSuite(cryptoSuite);
        return caClient;
    }

    /**
     * Enrolls admin onto the network
     * 
     */ 
    public static void enrollAdmin() {
        try {
            HFCAClient ca = createCAClient();
            Wallet wallet = Connection.getWallet();

            if (wallet.get("admin") != null) {
                LOGGER.severe("This user has already been enrolled");
                return;
            }

            //Enrollment request on the network using the CA 
            EnrollmentRequest enrollmentRequest = new EnrollmentRequest();
            enrollmentRequest.addHost("localhost");
            enrollmentRequest.setProfile("tls");
            Enrollment enrollment = ca.enroll("admin", "adminpw", enrollmentRequest);

            Identity userID = Identities.newX509Identity("Org1MSP", enrollment);
            wallet.put("admin", userID);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.severe(e.toString());
        }
    }

    /**
     * Enrolls user onto the network from JSON data
     * @param JsonObject user
     */
    public static void enrollUser(JsonObject user) {
        try {
            HFCAClient ca = createCAClient();
            Wallet wallet = Connection.getWallet();

            if(wallet.get(user.get("identifier").getAsString()) != null){
                LOGGER.severe("User has already been created");
                return ;
            }

            if(wallet.get("admin") == null){
                LOGGER.severe("Admin does not exist, please create an admin");
            }

            //Creating admin X509 identity 
            X509Identity adminIdentity = (X509Identity)wallet.get("admin");
            User admin = getAdminUser(adminIdentity);

            //Enrollment request on the network using the CA             
            RegistrationRequest registrationRequest = new RegistrationRequest(user.get("identifier").getAsString());
            registrationRequest.setAffiliation("org1.department1");
            registrationRequest.setEnrollmentID(user.get("identifier").getAsString());
            String enrollmentSecret = ca.register(registrationRequest, admin);

            //Creating identity with enrollment request
            Enrollment enrollment = ca.enroll(user.get("identifier").getAsString(), enrollmentSecret);
            Identity newUser = Identities.newX509Identity("Org1MSP", enrollment);
            wallet.put(user.get("identifier").getAsString(), newUser);
            LOGGER.info("Succesfully enrolled " + user + " and imported into wallet");

            //Creating user information on the network. 
            user = getCert(user, wallet);
            String response = UserRequests.createUser(user, user.get("identifier").getAsString(), user.get("title").getAsString(), user.get("firstname").getAsString(), user.get("surname").getAsString(), user.get("address").getAsString(), user.get("dob").getAsString(), user.get("gender").getAsString(), user.get("email").getAsString(), user.get("status").getAsString(), user.get("cert").getAsString());
            LOGGER.info("Created user entry on the ledger" + response);
        } catch (Exception e) {
            LOGGER.severe(e.toString());
        }
    }

    /**
     * Adds the public certificate to the JsonObject passed to the method
     * @param JsonObject user
     * @param Wallet wallet
     * @return JsonObject user
     * @throws IOException
     */
    public static JsonObject getCert(JsonObject user, Wallet wallet) throws IOException{
        X509Identity userIdentity = (X509Identity) wallet.get(user.get("identifier").getAsString());
        String pubKey = CharMatcher.breakingWhitespace().removeFrom(Identities.toPemString(userIdentity.getCertificate()));
        user.addProperty("cert", pubKey);
        return user;
    }

    /**
     * Gets the admin identity user object provided by Hyperledger
     * @param X509Identity adminIdentity
     * @return
     */
    public static User getAdminUser(X509Identity adminIdentity) {
        User adminUser = new User(){

            @Override
            public String getName() {
                return "admin";
            }

            @Override
            public Set<String> getRoles() {
                return null;
            }

            @Override
            public String getAccount() {
                return null;
            }

            @Override
            public String getAffiliation() {
                return "org1.department1";
            }

            @Override
            public Enrollment getEnrollment() {
                return new Enrollment() {

					@Override
					public PrivateKey getKey() {
						return adminIdentity.getPrivateKey();
					}

					@Override
					public String getCert() {
						return Identities.toPemString(adminIdentity.getCertificate());
					}
				};
            }

            @Override
            public String getMspId() {
                return "Org1MSP";
            }
            
        };
        return adminUser;
    }

}
