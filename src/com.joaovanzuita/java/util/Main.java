package util;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;

public class Main extends Application {


    @Override
    public void start(Stage stage) throws IOException {

        URL url = getClass().getResource("/view/telasGlobais/Login.fxml");

        FXMLLoader fxmlLoader = new FXMLLoader(url);


        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root);
        stage.setTitle("PROJETO PPO");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {

        launch(args);

    }

}
