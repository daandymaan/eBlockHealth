package application;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Identities;
import org.hyperledger.fabric.gateway.Wallet;
import org.hyperledger.fabric.gateway.Wallets;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Authentication {
    
    
    public Gateway connect(JsonObject user) throws Exception{
        Path walletpath = Paths.get("../../wallets");
        Wallet wallet = Wallets.newFileSystemWallet(walletpath);

        if(wallet.get(user.get("PPSN").getAsString()) == null){
            System.out.println(user.get("PPSN").getAsString() + " does not exists");
        } else {
            System.out.println(user.get("PPSN").getAsString() + " found!");
            if(authenticateUser(user) != 0){
                System.out.println("Authentication failed please try again");
            } else {
                System.out.println("Authentication sucessfull!");
                Path networkConfigPath = Paths.get("../../Orgs/Org1/gateway/connection-org1.yaml");
                Gateway.Builder builder = Gateway.createBuilder();
                builder.identity(wallet, user.get("PPSN").getAsString()).networkConfig(networkConfigPath).discovery(true);
                return builder.connect();
            }
        }
        return null;
    }

    public int authenticateUser(JsonObject user){
        String passphrase = readCredentials(user.get("PPSN").getAsString());

        for(int i = 0; i < user.get("passcode").getAsString().length()-1; i++){
            int indexOfPassPhrase = Character.getNumericValue(user.get("pattern").getAsString().charAt(i));
            char x = passphrase.charAt(indexOfPassPhrase);
            char y = user.get("passcode").getAsString().charAt(i);
            if(Character.compare(x,y ) != 0){
                System.out.println("Incorrect passcode");
                return 1;
            }
        }
        return 0;
    }

    public String readCredentials(String ppsn){     
        
        String pathToCredentials = "../../wallets/" + ppsn + ".id";
        System.out.println("PATH:" + pathToCredentials);
        try (FileReader reader = new FileReader(pathToCredentials))
        {
            Object obj = JsonParser.parseReader(reader);
            JsonObject usr = (JsonObject) obj;
            JsonObject credentials = usr.getAsJsonObject("credentials");
            String privateKey = credentials.get("privateKey").getAsString();
            return formatPrivateKey(privateKey);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String formatPrivateKey(String key){
        String formattedKey = "";
        System.out.println(key);
        for(int i = 230; i > 222; i--){
            // System.out.println("index:" + i +"\tchar:" + key.charAt(i));
            formattedKey+= key.charAt(i);
        }

        System.out.println(formattedKey);
        return formattedKey;
    }
}
