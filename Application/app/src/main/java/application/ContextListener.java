package application;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

@WebListener("application context listener")
@ApplicationPath("/api")
public class ContextListener extends Application implements ServletContextListener  {
    static {
		System.setProperty("org.hyperledger.fabric.sdk.service_discovery.as_localhost", "true");
	}
    
    private static final Logger LOGGER = Logging.getInstance();
    FileHandler fh;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        LOGGER.info("APPLICATION STARTED");
        RegisterUser.enrollAdmin();
        JsonArray users = getUserJsonArray();
        for (JsonElement user : users) {
            RegisterUser.enrollUser(user.getAsJsonObject());
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        
    }

    public static JsonArray getUserJsonArray(){
        JsonArray users = new JsonArray();
        JsonObject user1 = new JsonObject();
        user1.addProperty("identifier", "3324784V");
        user1.addProperty("title", "Mr");
        user1.addProperty("firstname", "Dan");
        user1.addProperty("surname", "Simons");
        user1.addProperty("address", "52 Strand Street Skerries Dublin");
        user1.addProperty("dob", "01-01-1999");
        user1.addProperty("gender", "M");
        user1.addProperty("email", "sanieldimons@gmail.com");
        user1.addProperty("status", "M");
        users.add(user1);
        return users;
    }   
}

