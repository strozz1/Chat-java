module app.javachat {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires java.desktop;


    opens app.javachat to javafx.fxml;
    exports app.javachat;
    exports app.javachat.Controllers;
    opens app.javachat.Controllers to javafx.fxml;
    exports app.javachat.Controllers.ViewControllers;
    opens app.javachat.Controllers.ViewControllers to javafx.fxml;
    opens app.javachat.Controllers.CustomControllers to javafx.fxml;
}