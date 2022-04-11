//package app.javachat.Controllers.ViewControllers;
//
//import app.javachat.Logger.Log;
//import app.javachat.Models.User;
//import app.javachat.Utilities.Info;
//import javafx.application.Platform;
//import javafx.beans.property.SimpleStringProperty;
//import javafx.beans.property.StringProperty;
//import javafx.fxml.FXML;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.stage.Stage;
//
//import java.time.LocalTime;
//
//public class CallWindowController {
//    private User localUser, otherUser;
//    @FXML
//    private Button btnEndCall;
//    @FXML
//    private Label otherUserLabel;
//    @FXML
//    private Label labelTiempoLlamada;
//    public static StringProperty timer=new SimpleStringProperty("00:00:00");
//    private boolean isCallOnline=false;
//
//
//    public CallWindowController() {
//        Info.Call.getLocalCall().setController(this);
//        Info.Call.getLocalCall().startCall();
//        otherUser = Info.Call.getLocalCall().getOtherUser();
//    }
//
//    @FXML
//    void initialize() {
//        startCallTimer();
//        otherUserLabel.setText(otherUser.getUsername());
//
//        labelTiempoLlamada.textProperty().bind(timer);
//
//        btnEndCall.setOnMouseClicked(event -> {
//            endCall();
//        });
//    }
//
//    private void endCall() {
//        Info.Call.getLocalCall().endCall();
//        cerrarVentana();
//        stopTimer();
//    }
//
//
//    public void cerrarVentana() {
//        Info.Call.setInCall(false);
//        Platform.runLater(() -> {
//            Stage stage = (Stage) labelTiempoLlamada.getScene().getWindow();
//            stage.close();
//        });
//    }
//    void startCallTimer() {
//        isCallOnline=true;
//       Thread thread= new Thread(this::timer);
//       thread.start();
//    }
//
//    void stopTimer(){
//        isCallOnline=false;
//    }
//
//    void timer() {
//                LocalTime of = LocalTime.of(00, 00, 00);
//        while(isCallOnline) {
//            try {
//                String info = "Tiempo de llamada  " + of.toString();
//                Platform.runLater(()->timer.setValue(info));
//                Thread.sleep(1000);
//                of = of.plusSeconds(1);
//            } catch (InterruptedException e) {
//                Log.error(e.getMessage());
//            }
//        }
//    }
//}
