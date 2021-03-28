/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package application;

import com.google.gson.JsonObject;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/test")
public class App {

    //Needed to run application locally
    static {
		System.setProperty("org.hyperledger.fabric.sdk.service_discovery.as_localhost", "true");
	}

    public String getGreeting() {
        return "Starting Java app";
    }

    public static void main(String[] args) {
        System.out.println(new App().getGreeting());
        JsonObject user = new JsonObject();
        user.addProperty("identifier", "3324784V");
        user.addProperty("pattern", "01234567");
        user.addProperty("passcode", "7q0z1em9");
        user.addProperty("cert", "undefined");

        JsonObject data = new JsonObject();
        data.addProperty("date", "11-02-2021");
        data.addProperty("issuer", "admin");
        data.addProperty("owner", "me");
        data.addProperty("product", "LEXAPRO");
        data.addProperty("productID", "G551");
        data.addProperty("productPackage", "");
        data.addProperty("quantity", "20");
        data.addProperty("doseStrength", "10MG");
        data.addProperty("doseType", "TABS");
        data.addProperty("doseQuantity", "1perday");
        data.addProperty("instruction", "TAKEONEDAILYASDIRECTED");
        data.addProperty("comment", "");

        JsonObject admin = new JsonObject();
        admin.addProperty("identifier", "admin");

        // RegisterUser.enrollAdmin();
        // // RegisterUser.enrollUser(json);
        // try {
        //     Authentication authentication = new Authentication();
        //     // json = authentication.authenticateUser(json);
        //     // if(json.get("cert").getAsString().equals("undefined")){
        //     //     System.out.println("Authentication failed please enter correct details");
        //     // } else {
        //         System.out.println("Authentication succeeded have a nice day");
        //         System.out.println(user.get("cert").getAsString());
        //         PrescriptionRequests.createPrescription(data, user);
        //         PrescriptionRequests.getAllPrescriptions(user);
        //     // }

        // } catch (Exception e) {
        //     e.printStackTrace();
        // }
    }

    @POST
    @Path("/getJSON")
    @Produces(MediaType.APPLICATION_JSON)
    public static String getJSON(/**String user**/){
        // JsonObject json = JsonParser.parseString(user).getAsJsonObject();
        // System.out.println("JSONOBJECT : " + json.toString());
        JsonObject json = new JsonObject();
        json.addProperty("msg", "cheese");
        return json.toString();
    }
}
