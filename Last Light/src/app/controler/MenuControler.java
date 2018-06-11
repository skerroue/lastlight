package app.controler;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MenuControler implements Initializable{

	@FXML
    private Pane panemenu;
	
    @FXML
    private Button quitButton;

    @FXML
    private Button playButton;

    @FXML
    private Media sound;
    private MediaPlayer player;
    
    @FXML
    void play(ActionEvent event) {
    	stopMusic();
		setSound("game");
		setPlayer();
    	try {
			FXMLLoader loader = new FXMLLoader();
			URL url = new File("src/app/vue/vueGraphique.fxml").toURI().toURL();
			loader.setLocation(url);
			System.out.println(loader.getLocation());
			Pane root = new Pane(); 
			root=loader.load();
	     	panemenu.getScene().setRoot(root);
			launchMusic();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void quit(ActionEvent event) {
        System.exit(0);
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setSound("menu");
		setPlayer();
		this.player.setCycleCount(Timeline.INDEFINITE);
		launchMusic();
	}
	
	private void launchMusic() {
		this.player.play();
	}
	
	private void stopMusic() {
		this.player.stop();
	}
	
	private void setSound(String i) {
		this.sound = new Media(new File("src/music/" + i +".mp3").toURI().toString());
	}
	
	private void setPlayer() {
		this.player = new MediaPlayer(this.sound);
	}

}
