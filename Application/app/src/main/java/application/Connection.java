package application;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.gson.JsonObject;

import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Network;
import org.hyperledger.fabric.gateway.Wallet;
import org.hyperledger.fabric.gateway.Wallets;

public class Connection {
    
    public static Contract connect(JsonObject user) throws Exception{
        Path walletpath = Paths.get("../../wallets");
        Wallet wallet = Wallets.newFileSystemWallet(walletpath);
        Path networkConfigPath = Paths.get("../../Orgs/Org1/gateway/connection-org1.yaml");
        Gateway.Builder builder = Gateway.createBuilder();
        builder.identity(wallet, user.get("PPSN").getAsString()).networkConfig(networkConfigPath).discovery(true);
        Gateway gateway = builder.connect();
        Network network = gateway.getNetwork("mychannel");
        Contract contract = network.getContract("org.blockprescription.prescription");
        return contract;
    }
}
