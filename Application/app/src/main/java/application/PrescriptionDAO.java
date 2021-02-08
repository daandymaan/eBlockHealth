package application;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Identities;
import org.hyperledger.fabric.gateway.Wallet;
import org.hyperledger.fabric.gateway.Wallets;

public class PrescriptionDAO {
    

    public Gateway connect(String user) throws Exception{
        Path walletpath = Paths.get("../../wallets");
        Wallet wallet = Wallets.newFileSystemWallet(walletpath);
        Path networkConfigPath = Paths.get("../Orgs/Org1/gatewayconnection-org1.yaml");
        Gateway.Builder builder = Gateway.createBuilder();
        builder.identity(wallet, user).networkConfig(networkConfigPath).discovery(true);
        return builder.connect();
    }
    public void CreatePrescription(Prescription p){

    }

    public void GetPrescription(String key){

    }

    public void GetAllPrescriptions(){

    }

    public void UpdatePrescription(String key, Prescription p){

    }

}
