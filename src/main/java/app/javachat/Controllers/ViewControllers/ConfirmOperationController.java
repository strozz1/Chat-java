package app.javachat.Controllers.ViewControllers;

import app.javachat.Controllers.CustomControllers.DefaultChatItem;
import app.javachat.Utilities.Info;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ConfirmOperationController {



    @FXML
    private Button yesButton,noButton;
    private MainController controller;

    @FXML
    void initialize(){
        yesButton.setOnMouseClicked(e->{
            controller.getLateralMenu().getChildren().clear();
            Info.rooms.clear();
            controller.getParent().setCenter(new DefaultChatItem());
            close(e);
        });
        noButton.setOnMouseClicked(this::close);
    }

    private void close(MouseEvent e) {
        Node node = (Node) e.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();
        thisStage.close();
    }

    public void setMainController(MainController controller) {
        this.controller= controller;
    }
}
