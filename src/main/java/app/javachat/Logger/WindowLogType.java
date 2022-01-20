package app.javachat.Logger;

import app.javachat.Controllers.ViewControllers.LoggerController;

public class WindowLogType implements LoggerType{

    private static LoggerController controller;

    public WindowLogType(LoggerController controller1){
        controller = controller1;
    }


    public static void setLoggerController(LoggerController controller1){
        controller = controller1;
    }
    public static LoggerController getLoggerController(){
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
