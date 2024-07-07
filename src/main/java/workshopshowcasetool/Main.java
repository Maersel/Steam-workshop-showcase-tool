package workshopshowcasetool;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    Stage stage;
    Scene mainScene;
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/Main.fxml"));
        mainScene = new Scene(fxmlLoader.load());
        this.stage = stage;
        stage.setTitle("Workshop showcase tool");
        stage.setScene(mainScene);
        stage.show();
    }

    public static void main(String[] args) throws IOException {
        launch();
    }
}