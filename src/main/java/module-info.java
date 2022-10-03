module software1.software1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens Controller_Views to javafx.fxml;
    exports Controller_Views;
    exports Model;
    opens Model to javafx.fxml;
}