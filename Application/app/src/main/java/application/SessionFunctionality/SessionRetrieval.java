package application.SessionFunctionality;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.JsonObject;

import application.Logging;

@WebServlet("/userRequestsGateway/verifySession")
public class SessionRetrieval extends HttpServlet{

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logging.getInstance();

    public void doGet(HttpServletRequest request, HttpServletResponse response){
        
        HttpSession session = request.getSession(false);
        response.setContentType("application/json");
        JsonObject msg = new JsonObject();
        PrintWriter writer;
        if(session == null){
            msg.addProperty("msg", "error");
        } else {
            msg.addProperty("msg", "success");
            msg.addProperty("identifier", session.getAttribute("identifier").toString());
            msg.addProperty("cert", session.getAttribute("cert").toString());
            msg.addProperty("status", session.getAttribute("status").toString());
        }

        try {
            writer = response.getWriter();
            writer.append(msg.toString());
            LOGGER.info(msg.toString());
            writer.close();
        } catch (IOException e) {
            LOGGER.severe(e.toString());
        }
    }
}
