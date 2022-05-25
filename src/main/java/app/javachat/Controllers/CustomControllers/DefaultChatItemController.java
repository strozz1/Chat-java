package app.javachat.Controllers.CustomControllers;

import app.javachat.Logger.Log;
import app.javachat.Models.GroupRoom;
import app.javachat.Models.Message;
import app.javachat.Models.Room;
import app.javachat.Utilities.Info;
import app.javachat.Utilities.MessageSenderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.HashMap;

import static app.javachat.Utilities.JSONBuilder.parseMessageToJSON;


public class DefaultChatItemController {


    public DefaultChatItemController() {
    }

}