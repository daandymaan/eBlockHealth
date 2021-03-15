package application;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Logging {
    private static Logging single_instance = null;
    private static final Logger LOGGER = Logger.getLogger(ContextListener.class.getName());
    private static FileHandler fh;

    public static Logger getInstance(){
        if(single_instance == null){
            try {
                fh = new FileHandler("/opt/tomcat/apache-tomcat-8.5.63/webapps/logs/blockchainPrescribing.txt");
                LOGGER.addHandler(fh);
                SimpleFormatter formatter = new SimpleFormatter();
                fh.setFormatter(formatter);
                LOGGER.info("LOGGER INTIIALIZED");
                single_instance = new Logging();
                return LOGGER;
            } catch (SecurityException | IOException e) {
                e.printStackTrace();
            }
        } 
        return LOGGER;

    }
}
