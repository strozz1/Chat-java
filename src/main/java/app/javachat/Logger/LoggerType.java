package app.javachat.Logger;

import app.javachat.Controllers.LoggerController;

public interface LoggerType {
    void showMessage(String msg);
    void showError(String msg);

    void showMessage(String msg,String sender);
    void showError(String msg,String sender);

}
