package application;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Path("/prescriptionRequests")
public class PrescriptionRequestsGateway {

    @POST
    @Path("/getAllPrescriptions")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllPrescriptions(String user){
        JsonObject userInfo = (JsonObject) JsonParser.parseString(user).getAsJsonObject();
        return PrescriptionRequests.getAllPrescriptions(userInfo);
    }

    @POST
    @Path("/getPrescriptionsForUser")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getPrescriptionsForUser(String user){
        JsonObject userInfo = (JsonObject) JsonParser.parseString(user).getAsJsonObject();
        return PrescriptionRequests.getAllPrescriptionsForUser(userInfo);
    }

    @POST
    @Path("/updatePrescriptionStatus")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String updatePrescriptionStatus(String request){
        JsonObject requestJson = (JsonObject) JsonParser.parseString(request).getAsJsonObject();
        JsonObject userInfo = requestJson.get("user").getAsJsonObject();
        String PID = requestJson.get("pID").getAsString();
        String status = requestJson.get("status").getAsString();
        return PrescriptionRequests.updateStatus(userInfo, PID, status);
    }

    @POST
    @Path("/updatePrescriptionOwner")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String updatePrescriptionOwner(String request){
        JsonObject requestJson = (JsonObject) JsonParser.parseString(request).getAsJsonObject();
        JsonObject userInfo = requestJson.get("user").getAsJsonObject();
        String PID = requestJson.get("pID").getAsString();
        String newOwner = requestJson.get("newOwner").getAsString();
        return PrescriptionRequests.transferPrescription(userInfo, PID, newOwner);
    }

    @POST
    @Path("/updatePrescription")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String updatePrescription(String request){
        JsonObject requestJson = (JsonObject) JsonParser.parseString(request).getAsJsonObject();
        JsonObject userInfo = requestJson.get("user").getAsJsonObject();
        String PID = requestJson.get("pID").getAsString();
        String date = requestJson.get("date").getAsString();
        String issuer = requestJson.get("issuer").getAsString();
        String owner = requestJson.get("owner").getAsString();
        String product = requestJson.get("product").getAsString();
        String productID = requestJson.get("productID").getAsString();
        String productPackage = requestJson.get("productPackage").getAsString();
        String quantity = requestJson.get("quantity").getAsString();
        String doseStrength = requestJson.get("doseStrength").getAsString();
        String doseType = requestJson.get("doseType").getAsString();
        String doseQuantity = requestJson.get("doseQuantity").getAsString();
        String instruction = requestJson.get("instruction").getAsString();
        String comment = requestJson.get("comment").getAsString();
        String status = requestJson.get("status").getAsString();
        return PrescriptionRequests.updatePrescription(userInfo, PID, date, issuer, owner, product, productID, productPackage, quantity, doseStrength, doseType, doseQuantity, instruction, comment, status);
    }

    @POST
    @Path("/createPrescription")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String createPrescription(String request){
        JsonObject requestJson = (JsonObject) JsonParser.parseString(request).getAsJsonObject();
        JsonObject userInfo = requestJson.get("user").getAsJsonObject();
        String date = requestJson.get("date").getAsString();
        String issuer = requestJson.get("issuer").getAsString();
        String product = requestJson.get("product").getAsString();
        String productID = requestJson.get("productID").getAsString();
        String productPackage = requestJson.get("productPackage").getAsString();
        String quantity = requestJson.get("quantity").getAsString();
        String doseStrength = requestJson.get("doseStrength").getAsString();
        String doseType = requestJson.get("doseType").getAsString();
        String doseQuantity = requestJson.get("doseQuantity").getAsString();
        String instruction = requestJson.get("instruction").getAsString();
        String comment = requestJson.get("comment").getAsString();
        return PrescriptionRequests.createPrescription(userInfo, date, issuer, product, productID, productPackage, quantity, doseStrength, doseType, doseQuantity, instruction, comment);
    }

}
