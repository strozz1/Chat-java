package app.javachat.Logger;

import app.javachat.Controllers.LoggerController;
import app.javachat.Controllers.LoggerControllerRetriever;

public class WindowLogType implements LoggerType{

    @Override
    public void showMessage(String msg) {
        LoggerControllerRetriever.getLoggerController().addLog(msg);
    }

    @Override
    public void showError(String msg) {
        LoggerControllerRetriever.getLoggerController().addLog(msg);

    }

    @Override
    public void showMessage(String msg, String sender) {
        LoggerControllerRetriever.getLoggerController().addLog(msg,sender);
    }

    @Override
    public void showError(String msg, String sender) {
        LoggerControllerRetriever.getLoggerController().addLog(msg,sender);
    }


}
