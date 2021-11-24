module app.javachat {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;


    opens app.javachat to javafx.fxml;
    exports app.javachat;
    exports app.javachat.Controllers;
    opens app.javachat.Controllers to javafx.fxml;
}