package application.SessionFunctionality;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import application.Authentication;
import application.Logging;

@WebServlet("/userRequestsGateway/authenticateUser")
public class SessionGeneration extends HttpServlet{
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logging.getInstance();

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession(true);
        Authentication authentication = new Authentication();
        JsonObject msg = new JsonObject();
        response.setContentType("application/json");
        PrintWriter writer;
        try {
            writer = response.getWriter();
            JsonObject  userInfo = readJsonRequest(request.getReader());
            JsonObject verifiedUser = authentication.authenticateUser(userInfo);
            if(verifiedUser == null){
                LOGGER.info("Incorrect credentials");
                msg.addProperty("msg", "fail");
                writer.append(msg.toString());
                writer.close();
            } else {
                msg.addProperty("msg", "success");
                msg.addProperty("cert", verifiedUser.getAsJsonObject().get("cert").getAsString());
                msg.addProperty("identifier", verifiedUser.getAsJsonObject().get("identifier").getAsString());
                msg.addProperty("status", verifiedUser.getAsJsonObject().get("status").getAsString());
                session.setAttribute("identifier", verifiedUser.getAsJsonObject().get("identifier").getAsString());
                session.setAttribute("cert", verifiedUser.getAsJsonObject().get("cert").getAsString());
                session.setAttribute("status", verifiedUser.getAsJsonObject().get("status").getAsString());
                session.setAttribute("username", verifiedUser.getAsJsonObject().get("identifier").getAsString());
                writer.append(msg.toString());
                writer.close();
            }
        } catch (Exception e) {
            LOGGER.severe(e.toString());
        }
    }

    public JsonObject readJsonRequest(BufferedReader reader) throws IOException{
        StringBuffer jb = new StringBuffer();
        String line = null;
        while((line = reader.readLine()) != null ){
            jb.append(line);
        }
        JsonObject json = JsonParser.parseString(jb.toString()).getAsJsonObject();
        return json;
    }
    
}
