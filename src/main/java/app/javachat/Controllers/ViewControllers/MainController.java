package app.javachat.Controllers.ViewControllers;

import app.javachat.Chats.ChatListener;
import app.javachat.Controllers.CustomControllers.LeftChatItem;
import app.javachat.Utilities.Info;
import app.javachat.MainApplication;
import app.javachat.Models.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainController {
    private List<LeftChatItem> chatObjectList;
    private User localUser;

    @FXML
    private VBox lateralMenu;
    @FXML
    private Label usernameLeftLabel;
    @FXML
    private BorderPane parent;

    public MainController(){

    }

    @FXML
    void initialize() {
        chatObjectList = new ArrayList<>();
        ChatListener chatListener = new ChatListener(this);
        chatListener.setDaemon(true);
        chatListener.start();
        usernameLeftLabel.textProperty().bind(Info.username);
    }



    public void openAddChatView() throws IOException {
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("add-server-view.fxml"));
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(loader.load());
        stage.initStyle(StageStyle.UTILITY);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void onAddChat(MouseEvent mouseEvent) throws IOException {
        openAddChatView();

    }

    public VBox getLateralMenu() {
        return lateralMenu;
    }
    public BorderPane getParent() {
        return parent;
    }
}
