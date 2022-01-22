package app.javachat.Controllers.ViewControllers;

import app.javachat.ChatListener;
import app.javachat.Controllers.CustomControllers.LeftChatItem;
import app.javachat.Info;
import app.javachat.MainApplication;
import app.javachat.Models.SalaModel;
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
        chatObjectList = new ArrayList<>();
        ChatListener chatListener = new ChatListener(this);
        chatListener.start();
    }

    @FXML
    void initialize() {
        usernameLeftLabel.setText(Info.localUser.getUsername());
    }



    public SalaModel changeViewToAddOrJoinServer() throws IOException {
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("add-server-view.fxml"));
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(loader.load());
        AddServerController addServerController = loader.getController();
        stage.setScene(scene);
        stage.showAndWait();
        localUser = addServerController.getUser();
        SalaModel salaModel = addServerController.getSalaModel();
        return salaModel;
    }

    public void onAddChat(MouseEvent mouseEvent) throws IOException {
//        SalaModel salaModel = changeViewToAddOrJoinServer();
//        sala = new SalaCliente(salaModel, localUser);
//        recibirMensajes(paneChat);
//        UserChatItemControl userChatItemControl = new UserChatItemControl();
//        chatObjectList.add(userChatItemControl);
//        lateralMenu.getChildren().addAll(chatObjectList);

    }

    public VBox getLateralMenu() {
        return lateralMenu;
    }
    public BorderPane getParent() {
        return parent;
    }
}
