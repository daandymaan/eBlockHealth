package application;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import org.hyperledger.fabric.gateway.Wallet;
import org.hyperledger.fabric.gateway.Wallets;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Authentication {
    private static final Logger LOGGER = Logging.getInstance();
    private String walletDIR = "/opt/tomcat/apache-tomcat-8.5.63/webapps/wallets/";
    // private String walletDIR = "../../wallets";

    public JsonObject authenticateUser(JsonObject user) throws Exception {
        if(userExists(user).equals("success")){
            String passphrase = readPK(user.get("identifier").getAsString());
            if(verifyPK(user, passphrase) == 0){
                user = addPublicKey(user);
                user = addStatus(user);
            } else {
                return null;
            }
        }
        return user;
    }

    public String userExists(JsonObject user) throws IOException {
        Path walletpath = Paths.get(walletDIR);
        Wallet wallet = Wallets.newFileSystemWallet(walletpath);
        if(wallet.get(user.get("identifier").getAsString()) != null){
            LOGGER.info(user.get("identifier").getAsString() + " found!");
            return "success";
        } else {
            LOGGER.info(user.get("identifier").getAsString() + " Not found!");
            return "failure";
        }
    }

    public String readPK(String ppsn){     
        String pathToCredentials = walletDIR + ppsn + ".id";
        LOGGER.info("PATH : " + pathToCredentials);
        try (FileReader reader = new FileReader(pathToCredentials))
        {
            Object obj = JsonParser.parseReader(reader);
            JsonObject usr = (JsonObject) obj;
            JsonObject credentials = usr.getAsJsonObject("credentials");
            String privateKey = credentials.get("privateKey").getAsString();
            return formatPassKey(privateKey);

        } catch (FileNotFoundException e) {
            LOGGER.severe(e.toString());
        } catch (IOException e) {
            LOGGER.severe(e.toString());
        }
        return null;
    }

    public String formatPassKey(String key){
        String formattedKey = "";
        System.out.println(key);
        for(int i = 230; i > 222; i--){
            formattedKey+= key.charAt(i);
        }
        return formattedKey;
    }

    public int verifyPK(JsonObject user, String passphrase){
        for(int i = 0; i < user.get("passcode").getAsString().length()-1; i++){
            int indexOfPassPhrase = Character.getNumericValue(user.get("pattern").getAsString().charAt(i));
            char x = passphrase.charAt(indexOfPassPhrase);
            char y = user.get("passcode").getAsString().charAt(i);
            if(Character.compare(x, y) != 0){
                LOGGER.severe("Incorrect passcode");
                return -1;
            }
        }
        return 0;
    }

    public JsonObject addPublicKey(JsonObject user){
        String ppsn = user.get("identifier").getAsString();
        String pathToCredentials = walletDIR + ppsn + ".id";
        try (FileReader reader = new FileReader(pathToCredentials))
        {
            Object obj = JsonParser.parseReader(reader);
            JsonObject usr = (JsonObject) obj;
            JsonObject credentials = usr.getAsJsonObject("credentials");
            String cert = credentials.get("certificate").getAsString();
            user.addProperty("cert", cert);

        } catch (FileNotFoundException e) {
            LOGGER.severe(e.toString());
        } catch (IOException e) {
            LOGGER.severe(e.toString());
        }
        return user;
    }

    public JsonObject addStatus(JsonObject user){
        JsonObject allUserDetails = JsonParser.parseString(UserRequests.getUserByIdentifier(user, user.get("identifier").getAsString())).getAsJsonObject();
        user.addProperty("status", allUserDetails.get("status").getAsString());
        return user;
    }

    public boolean verifySession(JsonObject userInfo){
        JsonObject correctUserDetails = (JsonObject) JsonParser.parseString(UserRequests.getUserByIdentifier(userInfo, userInfo.get("identifier").getAsString()));
        if(userInfo.get("cert").getAsString().equals(correctUserDetails.get("cert").getAsString()) && userInfo.get("status").getAsString().equals(correctUserDetails.get("status").getAsString())){
            return true;
        } else {
            return false;
        }
    }
}
