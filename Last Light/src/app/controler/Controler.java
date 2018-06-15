package app.controler;

import java.io.File;
import java.net.URL;   
import java.util.ArrayList;
import java.util.ResourceBundle;

import app.modele.Game;
import app.vue.FieldView;
import app.vue.InterfaceView;
import app.vue.entity.EntityView;
import app.vue.entity.PlayerView;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Controler implements Initializable {
	
	final static int SCROLL_WIDTH = 512;
	final static int SCROLL_HEIGHT = 462;
	final static int PANE_WIDTH = 800;
	final static int PANE_HEIGHT = 800;

	@FXML
	private Pane pausePane;
	
    @FXML
    private Pane tileContainer;
    private FieldView field;

    @FXML
    private Pane entityContainer;
	
    @FXML
    private Pane interfaceContainer;
    private InterfaceView hud;
    
    private ArrayList<EntityView> entitiesView;
    private PlayerView playerView;
    
    private Game game;
    
    @FXML
    private Pane dialogPane;
    
    @FXML
    private ImageView dialogBackground;
    
    @FXML
    private Label dialogContainer;
    
    
    private Media sound;
   
    private MediaPlayer player;
    
    public Controler() {	
    	
    	this.game = new Game();
    	this.entitiesView = new ArrayList<>();
    	this.hud = new InterfaceView(Game.getPlayer());
    	this.field = new FieldView();
    	this.playerView = new PlayerView(Game.getPlayer());
        
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		// Initialisation de la map et du scrolling
		FieldControler.initializeScrollField(playerView, field, game, SCROLL_WIDTH, SCROLL_HEIGHT, PANE_HEIGHT, PANE_WIDTH, tileContainer, entityContainer, interfaceContainer);
		
		// Creation de l'interface et liaison avec les caracteristiques du joueur
		InterfaceControler.initializeInterface(interfaceContainer, hud);
		
		// Liaison des entitiyView avec les entites du modele
		EntityControler.initializeEntities(entityContainer, game, playerView, entitiesView);
		
		DialogControler.initializeDialogContainer(dialogPane, game, dialogContainer);
		
		// Lancement de la gameloop
		this.game.playGameLoop();
        
        // Animation de début de jeu
        FieldControler.AnimationTransitionMap(1.0);
        
        // Musiques
        launchMusic();
		
	}

    @FXML
    void onKeyPressed(KeyEvent event) {
    	
    	if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.RIGHT)
    		game.movePlayer(event.getCode());
    	else 
	    	switch (event.getCode()) {
	    	case D :
	    		Game.getPlayer().loseHP(1);
	    		Game.getPlayer().earnPotion();
	    		Game.getPlayer().earnMoney(1);
	    		break;
	    	case SHIFT :
	    		Game.getPlayer().usePotion();
	    		break;
	    	case E :
	    		if (!this.game.playerInteraction())
	    			Game.getPlayer().attack();
	    		else 
	    			if (!(this.dialogContainer.getText() == null))
	    				if (!this.dialogContainer.getText().equals(""))
	    					this.showText();
	    		break;
	    	case ESCAPE :
	    		showPauseMenu();
	    		break;
	    	case TAB :
	    		Game.getPlayer().nextWeapon();
	    		break;
	    	case R :
	    		Game.getPlayer().reload();
	    		break;
	    	case M :
	    		Game.getPlayer().earnMoney(1);
	    		break;
	    	case A :
	    		Game.getPlayer().useNecklace();
	    		break;
	    	case SPACE :
	    		Game.getPlayer().useBoots();
	    		break;
			default:
				break;
	    	}
    	
    }

    @FXML
    void onKeyReleased(KeyEvent event) {
    	
    	if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.RIGHT)
    		playerView.resetImage();
    	
    }
    
    @FXML
    void quit(ActionEvent event) {	
    	System.exit(0);
    }

    @FXML
    void resume(ActionEvent event) {
    	this.pausePane.setVisible(false);
    	this.game.playGameLoop();
    }
    
    private void showPauseMenu() {
    	this.pausePane.setVisible(true);
    	this.game.pauseGameLoop();
    	this.pausePane.requestFocus();
    }
    
    @FXML
    void closeText(KeyEvent event) {
    	if (event.getCode() == KeyCode.SPACE) {
    		this.dialogPane.setVisible(false);
        	this.game.playGameLoop();
    	}
    }	
    
    private void showText() {
    	this.dialogPane.setVisible(true);
    	this.game.pauseGameLoop();
    	this.dialogPane.requestFocus();
    }
    
    private void launchMusic() {
    	this.sound = new Media(new File("src/music/game.mp3").toURI().toString());
    	this.player = new MediaPlayer(this.sound);
    	this.player.setCycleCount(Timeline.INDEFINITE);
    	this.player.play();
    }
    	
}
