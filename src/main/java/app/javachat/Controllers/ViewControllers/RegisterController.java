package app.javachat.Controllers.ViewControllers;

import app.javachat.Logger.Log;
import app.javachat.ServerConnection;
import app.javachat.SocketNotInitializedException;
import app.javachat.Utilities.Info;
import app.javachat.Utilities.LocalDataManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.regex.Pattern;


public class RegisterController {
    private ServerConnection serverConnection;
    @FXML
    private Button registerButton;
    @FXML
    private Button goLoginButton;
    @FXML
    private TextField usernameInput;
    @FXML
    private TextField passwordInput;
    @FXML
    private TextField emailInput;

    public RegisterController() {
    }
    @FXML
    void initialize(){
        Log.show("Opened register window");
        registerButton.setOnMouseClicked(e->{
            Log.show("Register button pressed");
            String username = usernameInput.getText();
            String password = passwordInput.getText();
            String email = emailInput.getText();
            boolean registerValid = isRegisterValid(username, password,email);
            if (registerValid){
                boolean registerSuccess = false;
                try {
                    registerSuccess = checkRegisterCredentials(username, email,password);
                } catch (SocketNotInitializedException ex) {
                    ex.printStackTrace();
                }
                if (registerSuccess){
                    closeRegisterWindow();
                }else{
                    usernameInput.setStyle("-fx-background-color: red");
                    usernameInput.setText("");
                    usernameInput.setStyle("-fx-text-inner-color: red;");
                }
            }else{
                showInputError();
            }
        });

        goLoginButton.setOnMouseClicked(e->{
            closeRegisterWindow();
        });
    }

    public boolean checkRegisterCredentials(String username,String email,String password) throws SocketNotInitializedException {
        return serverConnection.checkRegisterCredentials(username,email,password);
    }

    public boolean isRegisterValid(String username,String password,String email){
        Pattern emailRegex = Pattern.compile("\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b");
        Pattern passwordRegex = Pattern.compile("\"^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$\"");
        Pattern usernameRegex = Pattern.compile("/^.{3,}$/");

        boolean isUsernameValid = (!username.isEmpty() && usernameRegex.matcher(username).matches());
        boolean isPasswordValid = (!password.isEmpty() && passwordRegex.matcher(password).matches());
        boolean isEmailValid = (!email.isEmpty() && emailRegex.matcher(email).matches());

        return (isPasswordValid && isUsernameValid && isEmailValid);
    }

    private void showInputError() {

    }


    public void setServerConnection(ServerConnection serverConnection) {
        this.serverConnection = serverConnection;
    }

    private void closeRegisterWindow() {
        Stage stage = (Stage) registerButton.getScene().getWindow();
        stage.close();
    }

}
