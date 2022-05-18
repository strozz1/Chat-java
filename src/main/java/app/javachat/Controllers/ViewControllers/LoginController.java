package app.javachat.Controllers.ViewControllers;

import app.javachat.Logger.Log;
import app.javachat.ServerConnection;
import app.javachat.SocketNotInitializedException;
import app.javachat.Utilities.Info;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.util.regex.Pattern;

public class LoginController {
    private ServerConnection serverConnection;

    @FXML
    private Button loginButton;
    @FXML
    private Button goRegisterButton;
    @FXML
    private TextField inputUsername;
    @FXML
    private TextField inputPassword;

    public LoginController() {

    }

    @FXML
    void initialize(){
        Log.show("Opened login window");
        loginButton.setOnMouseClicked(e->{
            Log.show("Login button pressed");
            String username = inputUsername.getText();
            String password = inputPassword.getText();
            boolean loginValid = isLoginValid(username, password);
           if (loginValid){
               boolean loginSucces = false;
               try {
                   loginSucces = checkLoginCredentials(username, password);
               } catch (SocketNotInitializedException ex) {
                   ex.printStackTrace();
               }
               if (loginSucces){
                   Info.userIsLogged=true;
                   Info.setUsername(username);
                   Info.setPassword(password);
                   closeLoginWindow();
               }else{
                   // TODO: 18/05/2022
               }
           }else{
               showInputError();
           }
        });
        
        goRegisterButton.setOnMouseClicked(e->{
            openRegisterWindow();
        });

    }

    private void closeLoginWindow() {
        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.close();
    }

    private void showInputError() {
        // TODO: 18/05/2022
    }

    private void openRegisterWindow() {
        // TODO: 18/05/2022
    }

    // Verificacion input usuario correcto

    public boolean isLoginValid(String username,String password){
        boolean isUsernameValid = (!username.isEmpty());
        boolean isPasswordValid = (!password.isEmpty());
        return (isPasswordValid && isUsernameValid);
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
    // Verificar login y registro del usuario
    public boolean checkLoginCredentials(String username,String password) throws SocketNotInitializedException {
        return serverConnection.checkLoginCredentials(username,password);
    }

//    public boolean checkRegisterCredentials(String username,String email){
//        return x.checkRegisterCredentials(username,email);
//    }
//    // Controller method for register
//    boolean isValid = isRegisterValid(username, password);
//    if(isValid){
//        boolean isRegisterCorrect = checkRegisterCredentials(username, email);
//        if(isRegisterCorrect){
//            registerSuccesful();
//        }else{
//            registerFailed();
//        }
//    }

    public void setServerConnection(ServerConnection serverConnection) {
        this.serverConnection = serverConnection;
    }

    public void onKeyPressed(KeyEvent event) {
    }
}
