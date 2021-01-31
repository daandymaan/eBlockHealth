package application;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Paths;
import java.util.Properties;

import org.hyperledger.fabric.gateway.Identities;
import org.hyperledger.fabric.gateway.Identity;
import org.hyperledger.fabric.gateway.Wallet;
import org.hyperledger.fabric.gateway.Wallets;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric.sdk.security.CryptoSuiteFactory;
import org.hyperledger.fabric_ca.sdk.EnrollmentRequest;
import org.hyperledger.fabric_ca.sdk.HFCAClient;

public class RegisterUser {

    private static String pemLocation = "/home/dan/Docs/fabric-samples/test-network/organizations/peerOrganizations/org2.example.com/ca/ca.org2.example.com-cert.pem";
    private static String url = "https://localhost:7054";
    // "/home/dan/Docs/BlockchainPrescribing/Wallets"

    public HFCAClient createCAClient() throws Exception {
        Properties props = new Properties();
        props.put("pemFile", pemLocation);
        props.put("allowAllHostName", "true");
        HFCAClient caClient = HFCAClient.createNewInstance(url, props);
        CryptoSuite cryptoSuite = CryptoSuiteFactory.getDefault().getCryptoSuite();
        caClient.setCryptoSuite(cryptoSuite);
        return caClient;
    }

    public Wallet getWallet() throws IOException {
        // Creating wallet where wallets are saved to the directory
        Wallet wallet = Wallets.newFileSystemWallet(Paths.get("home", "dan", "BlockchainPrescribing", "Wallets"));
        return wallet;
    }

    public void enrollUser(String user) {
        try {
            HFCAClient ca = createCAClient();
            Wallet wallet = getWallet();

            if(wallet.get(user) != null){
                System.out.println("This user has already been enrolled");
                return;
            }

            EnrollmentRequest enrollmentRequest = new EnrollmentRequest();
            enrollmentRequest.addHost("localhost");
            enrollmentRequest.setProfile("tls");
            Enrollment enrollment = ca.enroll(user, "password", enrollmentRequest);
            Identity userID = Identities.newX509Identity("Org2MSP", enrollment);
            wallet.put(user, userID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
    }

}
