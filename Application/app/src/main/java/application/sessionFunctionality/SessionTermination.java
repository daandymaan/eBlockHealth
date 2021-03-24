package application.sessionFunctionality;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.JsonObject;

import application.log.Logging;

@WebServlet("/userRequestsGateway/logout")
public class SessionTermination extends HttpServlet{

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logging.getInstance();

    public void doGet(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession(false);
        if(session != null){
            session.invalidate();
            JsonObject msg = new JsonObject();
            msg.addProperty("msg", "success");

            try {
                PrintWriter writer = response.getWriter();
                writer.append(msg.toString());
                LOGGER.info(msg.toString());
                writer.close();
            } catch (IOException e) {
                LOGGER.severe(e.toString());
            }
        }
    } 
    
}
