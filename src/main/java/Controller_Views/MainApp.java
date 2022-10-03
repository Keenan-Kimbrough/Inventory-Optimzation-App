package Controller_Views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This is the Main App Class.
 * This class Extends Applications
 */
public class MainApp extends Application {
    /**
     * This is the Start Method.
     * This method starts the application
     *
     * @param stage - this is the stage
     * @throws IOException - this handles the exception
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1750, 1000);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * This is Main class method.
     * This method calls launch.
     * @param args -  the args.
     */
    public static void main(String[] args) {
        launch();
    }
}