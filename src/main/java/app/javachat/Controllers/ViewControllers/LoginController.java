package app.javachat.Controllers.ViewControllers;

import app.javachat.Logger.Log;
import app.javachat.MainApplication;
import app.javachat.ServerConnection;
import app.javachat.SocketNotInitializedException;
import app.javachat.Utilities.Info;
import app.javachat.Utilities.LocalDataManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Border;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
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
    private PasswordField inputPassword;

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
               boolean loginSuccess = false;
               try {
                   loginSuccess = checkLoginCredentials(username, password);
               } catch (SocketNotInitializedException ex) {
                   Log.error("Error login");
               }
               if (loginSuccess){
                   Info.userIsLogged=true;
                   try {
                       LocalDataManager.saveUserCredentials(username,password,null);
                       } catch (Exception ex) {

                   }
                   closeLoginWindow();
               }else{
                   badChatInput();
               }
           }else{
               showInputError();
           }
        });
        
        goRegisterButton.setOnMouseClicked(e->{
            try {
                openRegisterWindow();
            } catch (IOException ex) {
                Log.error(ex.getMessage());
            }
        });

    }

    private void closeLoginWindow() {
        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.close();
    }

    private void showInputError() {
        // TODO: 18/05/2022
    }
    private void badChatInput() {
        inputUsername.setStyle("-fx-border-color: red");
        inputPassword.setStyle("-fx-border-color: red");
    }

    private void openRegisterWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("register-view.fxml"));
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        RegisterController controller = new RegisterController();
        controller.setServerConnection(serverConnection);
        loader.setController(controller);
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().clear();

        scene.getStylesheets().add(Info.theme);
        stage.initStyle(StageStyle.UTILITY);
        stage.setScene(scene);

        stage.setOnCloseRequest(close -> {
            Platform.exit();
        });
        stage.showAndWait();
    }



    public boolean isLoginValid(String username,String password){
        boolean isUsernameValid = (!username.isEmpty());
        boolean isPasswordValid = (!password.isEmpty());
        return (isPasswordValid && isUsernameValid);
    }



    public boolean checkLoginCredentials(String username,String password) throws SocketNotInitializedException {
        return serverConnection.checkLoginCredentials(username,password);
    }
    public void setServerConnection(ServerConnection serverConnection) {
        this.serverConnection = serverConnection;
    }

}
