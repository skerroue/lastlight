package app.controler;

import java.io.File;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

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
    		Media sound = new Media(new File("src/music/menu.mp3").toURI().toString());
    		MediaPlayer player = new MediaPlayer(sound);
    		player.play();
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
