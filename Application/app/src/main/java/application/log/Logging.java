package application.log;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import application.ContextListener;
/**
 * SINGLETON LOG INIT
 */
public class Logging {
    private static Logging single_instance = null;
    private static final Logger LOGGER = Logger.getLogger(ContextListener.class.getName());
    private static FileHandler fh;
    private static String logPath = "/opt/tomcat/apache-tomcat-8.5.63/webapps/logs/blockchainPrescribing.txt";

    /**
     * Creates or gets instance of logger for application
     * @return
     */
    public static Logger getInstance(){
        if(single_instance == null){
            try {
                fh = new FileHandler(logPath);
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
