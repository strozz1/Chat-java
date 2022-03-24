module app.javachat {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires java.desktop;
    requires socket.io.client;
    requires com.fasterxml.jackson.databind;
    requires engine.io.client;
    requires json;

    opens app.javachat to javafx.fxml;
    exports app.javachat;
    opens app.javachat.Controllers.ViewControllers to javafx.fxml;
    opens app.javachat.Controllers.CustomControllers to javafx.fxml;
    exports app.javachat.Garage;
    opens app.javachat.Garage to javafx.fxml;
    exports app.javachat.Chats;
    opens app.javachat.Chats to javafx.fxml;
    exports app.javachat.Utilities;
    opens app.javachat.Utilities to javafx.fxml;
}