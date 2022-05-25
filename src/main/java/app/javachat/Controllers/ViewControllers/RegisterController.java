package app.javachat.Controllers.ViewControllers;

import app.javachat.Logger.Log;
import app.javachat.ServerConnection;
import app.javachat.SocketNotInitializedException;
import app.javachat.Utilities.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.regex.Pattern;


public class RegisterController {
    private ServerConnection serverConnection;
    private String image;
    @FXML
    private Button registerButton;
    @FXML
    private Button goLoginButton;
    @FXML
    private Button photoButton;
    @FXML
    private TextField usernameInput;
    @FXML
    private TextField passwordInput;
    @FXML
    private TextField emailInput;

    public RegisterController() {
    }

    @FXML
    void initialize() {
        Log.show("Opened register window");
        registerButton.setOnMouseClicked(e -> {
            Log.show("Register button pressed");
            String username = usernameInput.getText();
            String password = passwordInput.getText();
            String email = emailInput.getText();

            boolean registerValid = isRegisterValid(username, password, email);
            if (registerValid) {
                Log.show("Register valid");
                boolean registerSuccess = false;
                try {
                    registerSuccess = checkRegisterCredentials(username, email, password, image);
                    System.out.println("Register> "+registerSuccess);
                } catch (SocketNotInitializedException ex) {
                    ex.printStackTrace();
                }
                if (registerSuccess) {
                    closeRegisterWindow();
                } else {
                    usernameInput.setStyle("-fx-border-color: red");
                    usernameInput.setText("");
                    usernameInput.setStyle("-fx-text-inner-color: red;");
                }
            } else {
                showInputError();
            }
        });

        photoButton.setOnMouseClicked(e -> {
            this.image = selectFile();
        });

        goLoginButton.setOnMouseClicked(e -> {
            closeRegisterWindow();
        });
    }

    private String selectFile() {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select new photo");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"));

        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            return Utils.fileToBase64(file);
        } else return null;
    }

    public boolean checkRegisterCredentials(String username, String email, String password,String photo) throws SocketNotInitializedException {
        return serverConnection.checkRegisterCredentials(username, email, password,photo);
    }

    public boolean isRegisterValid(String username, String password, String email) {

        boolean isUsernameValid = (!username.isEmpty());
        boolean isPasswordValid = (!password.isEmpty());
        boolean isEmailValid = (!email.isEmpty());

        return isEmailValid && isUsernameValid && isPasswordValid;
    }

    private void showInputError() {
        //todo
    }


    public void setServerConnection(ServerConnection serverConnection) {
        this.serverConnection = serverConnection;
    }

    private void closeRegisterWindow() {
        Stage stage = (Stage) registerButton.getScene().getWindow();
        stage.close();
    }

}
