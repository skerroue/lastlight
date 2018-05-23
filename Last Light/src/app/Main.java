package app;

import java.io.File;
import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

public class Main extends Application {


	@Override
	public void start(Stage primaryStage) {
		try {

		FXMLLoader loader = new FXMLLoader();
		URL url = new File("src/app/vue/vueGraphique.fxml").
		toURI().toURL();
		loader.setLocation(url);
		System.out.println(loader.getLocation());
		Pane root = new Pane();
		root=loader.load();
     	Scene scene = new Scene(root,800,800);
     	primaryStage.setTitle("Last Light");
     	primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}