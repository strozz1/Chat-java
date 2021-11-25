module app.javachat {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires java.desktop;


    opens app.javachat to javafx.fxml;
    exports app.javachat;
    exports app.javachat.Controllers;
    opens app.javachat.Controllers to javafx.fxml;
}