package application.requests;

import java.util.logging.Logger;

import org.hyperledger.fabric.gateway.Identities;
import org.hyperledger.fabric.gateway.Wallet;
import org.hyperledger.fabric.gateway.X509Identity;

import application.log.Logging;

import com.google.common.base.CharMatcher;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Authentication {
    private static final Logger LOGGER = Logging.getInstance();

    public JsonObject authenticateUser(JsonObject user) throws Exception {
        if(userExists(user)){
            if(verifyPassphrase(user)){
                user = addAttr(user);
            } else {
                return null;
            }
        }
        return user;
    }

    /**
     * Checks the wallet of the network for a user
     * 
     * @param JsonObject user
     * @return boolean
     * @throws Exception
     */
    public boolean userExists(JsonObject user) throws Exception {
        Wallet wallet = Connection.getWallet();
        if(wallet.get(user.get("identifier").getAsString()) != null){
            LOGGER.info(user.get("identifier").getAsString() + " found!");
            return true;
        } else {
            LOGGER.info(user.get("identifier").getAsString() + " Not found!");
            return false;
        }
    }

    /**
     * Retrieves the correct passphrase from the identity 
     * @param {String} identifier
     * @return
     */
    public String getPassphrase(String identifier){     
        try {
            X509Identity user = (X509Identity) Connection.getWallet().get(identifier);
            String pk =  CharMatcher.breakingWhitespace().removeFrom(Identities.toPemString(user.getPrivateKey()));
            return pk.substring(217, 225);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return null;
    }

    /**
     * Verifies the passphrase entered by the user
     * @param JsonObject user
     * @return boolean
     */
    public boolean verifyPassphrase(JsonObject user){
        String passphrase = getPassphrase(user.get("identifier").getAsString());
        for(int i = 0; i < user.get("passcode").getAsString().length()-1; i++){
            int indexOfPassPhrase = Character.getNumericValue(user.get("pattern").getAsString().charAt(i));
            char x = passphrase.charAt(indexOfPassPhrase);
            char y = user.get("passcode").getAsString().charAt(i);
            if(Character.compare(x, y) != 0){
                LOGGER.severe("Incorrect passcode");
                return false;
            }
        }
        return true;
    }

    /**
     * Adds the addtional attributes of the user's certificate and status and returns the user
     * @param JsonObject user
     * @return JsonObject user
     */
    public JsonObject addAttr(JsonObject user){
        JsonObject allUserDetails = JsonParser.parseString(UserRequests.getUserByIdentifier(user, user.get("identifier").getAsString())).getAsJsonObject();
        user.addProperty("cert", allUserDetails.get("cert").getAsString());
        user.addProperty("status", allUserDetails.get("status").getAsString());
        return user;
    }

}
