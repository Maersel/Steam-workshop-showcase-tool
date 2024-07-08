package workshopshowcasetool;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    Stage stage;
    Scene mainScene;
    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Main.fxml"));
            Parent root = loader.load();

            // Create and set the scene
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Steam Workshop Showcase Tool");

            // Show the stage
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        launch();
    }
}