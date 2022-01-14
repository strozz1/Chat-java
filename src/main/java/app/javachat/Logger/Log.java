package app.javachat.Logger;

public class Log {
    private static LoggerType loggerType = new ConsoleType();

    private Log(){

    }
    public static void show(String message) {

        loggerType.showMessage(message);
    }

    public static void error(String message) {

        loggerType.showError(message);
    }

    public static void show(String message, String sender) {
        loggerType.showMessage(message, sender);
    }

    public static void error(String message, String sender) {

        loggerType.showError(message, sender);
    }


    public static void setLoggerType(LoggerType loggerType) {
        Log.loggerType = loggerType;
    }


    public static LoggerType getLoggerType() {
        return loggerType;
    }
}
