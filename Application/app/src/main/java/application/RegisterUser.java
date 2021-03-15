package application;

import java.io.IOException;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;

import com.google.gson.JsonObject;

import org.hyperledger.fabric.gateway.Identities;
import org.hyperledger.fabric.gateway.Identity;
import org.hyperledger.fabric.gateway.Wallet;
import org.hyperledger.fabric.gateway.Wallets;
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
    private static String walletDIR = "/opt/tomcat/apache-tomcat-8.5.63/webapps/wallets";
    // private static String walletDIR = "../../wallets";

    public static HFCAClient createCAClient() throws Exception {
        LOGGER.info("CAClient : Started");
        Properties props = new Properties();
        props.put("pemFile", pemLocation);
        props.put("allowAllHostName", "true");
        HFCAClient caClient = HFCAClient.createNewInstance(url, props);
        CryptoSuite cryptoSuite = CryptoSuiteFactory.getDefault().getCryptoSuite();
        caClient.setCryptoSuite(cryptoSuite);
        return caClient;
    }

    public static Wallet getWallet() throws IOException {
        LOGGER.info("GET WALLET : Started");
        // Creating wallet where wallets are saved to the directory
        Wallet wallet = Wallets.newFileSystemWallet(Paths.get(walletDIR));
        return wallet;
    }

    public static void enrollAdmin() {
        try {
            HFCAClient ca = createCAClient();
            LOGGER.info("CAClient : Finished");
            Wallet wallet = getWallet();
            LOGGER.info("GET WALLET : Finished");

            if (wallet.get("admin") != null) {
                LOGGER.severe("This user has already been enrolled");
                return;
            }

            LOGGER.info("enrollmentRequest : Started");
            EnrollmentRequest enrollmentRequest = new EnrollmentRequest();
            enrollmentRequest.addHost("localhost");
            enrollmentRequest.setProfile("tls");
            Enrollment enrollment = ca.enroll("admin", "adminpw", enrollmentRequest);
            LOGGER.info("enrollmentRequest : Completed");

            Identity userID = Identities.newX509Identity("Org1MSP", enrollment);
            wallet.put("admin", userID);
            LOGGER.info("ADMIN GENERATED");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.severe(e.toString());
        }
    }

    public static void enrollUser(JsonObject user) {
        try {
            HFCAClient ca = createCAClient();
            LOGGER.info("CAClient : Finished");
            Wallet wallet = getWallet();
            LOGGER.info("GET WALLET : Finished");

            if(wallet.get(user.get("identifier").getAsString()) != null){
                LOGGER.severe("User has already been created");
                return ;
            }

            if(wallet.get("admin") == null){
                LOGGER.severe("Admin does not exist, please create an admin");
            }

            X509Identity adminIdentity = (X509Identity)wallet.get("admin");
            User admin = getAdminUser(adminIdentity);

            RegistrationRequest registrationRequest = new RegistrationRequest(user.get("identifier").getAsString());
            registrationRequest.setAffiliation("org1.department1");
            registrationRequest.setEnrollmentID(user.get("identifier").getAsString());
            String enrollmentSecret = ca.register(registrationRequest, admin);
            Enrollment enrollment = ca.enroll(user.get("identifier").getAsString(), enrollmentSecret);
            Identity newUser = Identities.newX509Identity("Org1MSP", enrollment);
            wallet.put(user.get("identifier").getAsString(), newUser);
            LOGGER.info("Succesfully enrolled " + user + " and imported into wallet");
            Authentication authentication = new Authentication();
            user = authentication.addPublicKey(user);
            LOGGER.info(user.toString());
            String response = UserRequests.createUser(user, user.get("identifier").getAsString(), user.get("title").getAsString(), user.get("firstname").getAsString(), user.get("surname").getAsString(), user.get("address").getAsString(), user.get("dob").getAsString(), user.get("gender").getAsString(), user.get("email").getAsString(), user.get("status").getAsString(), user.get("cert").getAsString());
            LOGGER.info(response);
            LOGGER.info(PrescriptionRequests.getAllPrescriptions(user));
        } catch (Exception e) {
            LOGGER.severe(e.toString());
        }

    }

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
