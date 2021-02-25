package application;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.hyperledger.fabric.gateway.Wallet;
import org.hyperledger.fabric.gateway.Wallets;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@javax.ws.rs.Path("/authentication")
public class Authentication {

    @javax.ws.rs.Path("/validateUser")
    public JsonObject authenticateUser(JsonObject user) throws Exception {
        if(userExists(user).equals("success")){
            System.out.println(user.get("identifier").getAsString() + " found!");
            String passphrase = readPK(user.get("identifier").getAsString());
            if(verifyPK(user, passphrase) == 0){
                user = addPublicKey(user);
            }
        }
        return user;
    }

    @javax.ws.rs.Path("/verifyUser")
    public String userExists(JsonObject user) throws IOException {
        Path walletpath = Paths.get("../../wallets");
        Wallet wallet = Wallets.newFileSystemWallet(walletpath);
        if(wallet.get(user.get("identifier").getAsString()) != null){
            return "success";
        } else {
            return "failure";
        }
    }

    public String readPK(String ppsn){     
        String pathToCredentials = "../../wallets/" + ppsn + ".id";
        System.out.println("PATH:" + pathToCredentials);
        try (FileReader reader = new FileReader(pathToCredentials))
        {
            Object obj = JsonParser.parseReader(reader);
            JsonObject usr = (JsonObject) obj;
            JsonObject credentials = usr.getAsJsonObject("credentials");
            String privateKey = credentials.get("privateKey").getAsString();
            return formatPassKey(privateKey);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String formatPassKey(String key){
        String formattedKey = "";
        System.out.println(key);
        for(int i = 230; i > 222; i--){
            formattedKey+= key.charAt(i);
        }

        System.out.println(formattedKey);
        return formattedKey;
    }

    public int verifyPK(JsonObject user, String passphrase){
        for(int i = 0; i < user.get("passcode").getAsString().length()-1; i++){
            int indexOfPassPhrase = Character.getNumericValue(user.get("pattern").getAsString().charAt(i));
            char x = passphrase.charAt(indexOfPassPhrase);
            char y = user.get("passcode").getAsString().charAt(i);
            if(Character.compare(x,y ) != 0){
                System.out.println("Incorrect passcode");
                return -1;
            }
        }
        return 0;
    }

    public JsonObject addPublicKey(JsonObject user){
        String ppsn = user.get("PPSN").getAsString();
        String pathToCredentials = "../../wallets/" + ppsn + ".id";
        try (FileReader reader = new FileReader(pathToCredentials))
        {
            Object obj = JsonParser.parseReader(reader);
            JsonObject usr = (JsonObject) obj;
            JsonObject credentials = usr.getAsJsonObject("credentials");
            String cert = credentials.get("certificate").getAsString();
            user.addProperty("cert", cert);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }
}
