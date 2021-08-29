package archivos.interfaz;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static Stage stage;
    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("../archivos/server/udp/cliente.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("cliente.fxml"));
        primaryStage.setTitle("Sistema de Hospitales Distribuidos");
        primaryStage.setResizable(false);
        Scene scene = new Scene(root, 800, 550);
        scene.getStylesheets().add("archivos/style/style.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}