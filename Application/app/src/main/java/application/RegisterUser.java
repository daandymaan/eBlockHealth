package application;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.text.ParseException;
import java.util.Properties;
import java.util.Set;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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
import org.json.JSONArray;
import org.json.JSONObject;

public class RegisterUser {

    private static String pemLocation = "/home/dan/Docs/fabric-samples/test-network/organizations//peerOrganizations/org1.example.com/ca/ca.org1.example.com-cert.pem";
    private static String url = "https://localhost:7054";
    // "/home/dan/Docs/BlockchainPrescribing/Wallets"

    public static HFCAClient createCAClient() throws Exception {
        Properties props = new Properties();
        props.put("pemFile", pemLocation);
        props.put("allowAllHostName", "true");
        HFCAClient caClient = HFCAClient.createNewInstance(url, props);
        CryptoSuite cryptoSuite = CryptoSuiteFactory.getDefault().getCryptoSuite();
        caClient.setCryptoSuite(cryptoSuite);
        return caClient;
    }

    public static Wallet getWallet() throws IOException {
        // Creating wallet where wallets are saved to the directory
        Wallet wallet = Wallets.newFileSystemWallet(Paths.get("../../wallets"));
        return wallet;
    }

    public static void enrollAdmin() {
        try {
            HFCAClient ca = createCAClient();
            Wallet wallet = getWallet();

            if (wallet.get("admin") != null) {
                System.out.println("This user has already been enrolled");
                return;
            }

            System.out.println("Starting exchanged");
            EnrollmentRequest enrollmentRequest = new EnrollmentRequest();
            System.out.println("After enrollment request");
            enrollmentRequest.addHost("localhost");
            enrollmentRequest.setProfile("tls");
            Enrollment enrollment = ca.enroll("admin", "adminpw", enrollmentRequest);
            System.out.println("*After enrollment*");
            Identity userID = Identities.newX509Identity("Org1MSP", enrollment);
            wallet.put("admin", userID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void enrollUser(JsonObject user) {
        try {
            HFCAClient ca = createCAClient();
            Wallet wallet = getWallet();

            if(wallet.get(user.get("PPSN").getAsString()) != null){
                System.out.println("User already exists");
                return ;
            }

            if(wallet.get("admin") == null){
                System.out.println("Admin does not exist, please create an admin");
            }

            X509Identity adminIdentity = (X509Identity)wallet.get("admin");
            User admin = getAdminUser(adminIdentity);

            RegistrationRequest registrationRequest = new RegistrationRequest(user.get("PPSN").getAsString());
            registrationRequest.setAffiliation("org1.department1");
            registrationRequest.setEnrollmentID(user.get("PPSN").getAsString());
            String enrollmentSecret = ca.register(registrationRequest, admin);
            Enrollment enrollment = ca.enroll(user.get("PPSN").getAsString(), enrollmentSecret);
            Identity newUser = Identities.newX509Identity("Org1MSP", enrollment);
            wallet.put(user.get("PPSN").getAsString(), newUser);
            System.out.println("Succesfully enrolled " + user + " and imported into wallet");

        } catch (Exception e) {
            e.printStackTrace();
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
