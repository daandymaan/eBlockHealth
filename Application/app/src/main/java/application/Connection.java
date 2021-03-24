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
    private static String walletDIR = "/opt/tomcat/apache-tomcat-8.5.63/webapps/wallets/";
    private static String connectionDIR = "/opt/tomcat/apache-tomcat-8.5.63/webapps/app/WEB-INF/classes/connection-org1.yaml";

    //Needed to run application locally
    static {
		System.setProperty("org.hyperledger.fabric.sdk.service_discovery.as_localhost", "true");
	}
    /**
     * Gets Hyperledger network connection to the blockchain using a user's credentials
     * 
     * @param JsonObject user
     * @return Network network
     * @throws Exception
     */
    public static Network connect(JsonObject user) throws Exception{
        Wallet wallet = getWallet();
        Path networkConfigPath = Paths.get(connectionDIR);
        Gateway.Builder builder = Gateway.createBuilder();
        builder.identity(wallet, user.get("identifier").getAsString()).networkConfig(networkConfigPath).discovery(true);
        Gateway gateway = builder.connect();
        System.out.println("Gateway created");
        Network network = gateway.getNetwork("mychannel");
        System.out.println("Network created");
        return network;

    }

    /**
     * Retrieves a specified contract from the network.
     * @param JsonObject user
     * @param String contractName
     * @return Contract contract
     * @throws Exception
     */
    public static Contract getContract(JsonObject user, String contractName) throws Exception {
        Network network = connect(user);
        Contract contract = network.getContract(contractName);
        System.out.println("Contract found");
        return contract;
    }

    /**
     * Retrieves the wallet with credentials 
     * @return Wallet wallet
     * @throws Exception
     */
    public static Wallet getWallet() throws Exception{
        Path walletpath = Paths.get(walletDIR);
        Wallet wallet = Wallets.newFileSystemWallet(walletpath);
        return wallet;
    }
}
