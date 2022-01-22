package app.javachat.Logger;

import app.javachat.Controllers.ViewControllers.LoggerWindowController;

public class WindowLogType implements LoggerType{

    private static LoggerWindowController controller;

    public WindowLogType(LoggerWindowController controller1){
        controller = controller1;
    }


    public static void setLoggerController(LoggerWindowController controller1){
        controller = controller1;
    }
    public static LoggerWindowController getLoggerController(){
        return controller;
    }
    @Override
    public void showMessage(String msg) {
        controller.addLog(msg);
    }

    @Override
    public void showError(String msg) {
        controller.addLog(msg);

    }

    @Override
    public void showMessage(String msg, String sender) {
        controller.addLog(msg,sender);
    }

    @Override
    public void showError(String msg, String sender) {
        controller.addLog(msg,sender);
    }



}
