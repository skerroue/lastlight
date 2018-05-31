package app.controler;

import java.io.File;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class MenuControler {

	@FXML
    private Pane panemenu;
	
    @FXML
    private Button quitButton;

    @FXML
    private Button playButton;

    @FXML
    void play(ActionEvent event) {

    	try {
			FXMLLoader loader = new FXMLLoader();
			URL url = new File("src/app/vue/vueGraphique.fxml").toURI().toURL();
			loader.setLocation(url);
			System.out.println(loader.getLocation());
			Pane root = new Pane(); 
			root=loader.load();
	     	panemenu.getScene().setRoot(root);
	     	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void quit(ActionEvent event) {
        System.exit(0);
    }

}
