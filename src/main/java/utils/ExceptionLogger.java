package utils;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class ExceptionLogger {
	
	private static Logger logger;

    static {
        try {
          
            logger = Logger.getLogger(ExceptionLogger.class.getName());
            FileHandler fileHandler = new FileHandler("exception.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void logException(Exception e) {
        if (logger != null) {
            logger.log(Level.SEVERE, "Exception occurred: ", e);
        }
    }
    
    public static void logInfo(String message) {
        if (logger != null) {
            logger.log(Level.INFO, message);
        }
    }

}
