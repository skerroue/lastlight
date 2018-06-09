package app.controler;

import java.net.URL;   
import java.util.ArrayList;
import java.util.ResourceBundle;

import app.modele.Game;
import app.vue.FieldView;
import app.vue.InterfaceView;
import app.vue.entity.EntityView;
import app.vue.entity.PlayerView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

public class Controler implements Initializable {
	
	final static int SCROLL_WIDTH = 512;
	final static int SCROLL_HEIGHT = 462;
	final static int PANE_WIDTH = 800;
	final static int PANE_HEIGHT = 800;

	@FXML
	private Pane pausePane;
	//private Button resumeButton;
	//private Button quitButton;
	
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
    
    public Controler() {	
    	
    	this.game = new Game();
    	this.entitiesView = new ArrayList<>();
    	this.hud = new InterfaceView(game.getPlayer());
    	this.field = new FieldView();
    	this.playerView = new PlayerView(game.getPlayer());
        
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		// Creation de la map visuellement
		FieldControler.initializeField(tileContainer, field, interfaceContainer);
		
		// Creation de l'interface et liaison avec les caracteristiques du joueur
		InterfaceControler.initializeInterface(interfaceContainer, hud);
		
		// Liaison des entitiyView avec les entites du modele
		EntityControler.initializeEntities(entityContainer, game, playerView, entitiesView);
		
		DialogControler.initializeDialogContainer(dialogPane, game, dialogContainer);
		
		// Lancement de la gameloop
		this.game.playGameLoop();
		
		// Initialisation de la Scroll Map
		FieldControler.initializeScrollField(playerView, field, game, SCROLL_WIDTH, SCROLL_HEIGHT, PANE_HEIGHT, PANE_WIDTH, tileContainer, entityContainer);
        
        // Animation
        FieldControler.AnimationTransitionMap(1.0);
		
	}

    @FXML
    void onKeyPressed(KeyEvent event) {
    	switch (event.getCode()) {
    	case UP:
    		game.movePlayer(event.getCode());
    		if (entitiesView.get(0).getTranslateY() - SCROLL_HEIGHT / 2 + 1 > 0 && entitiesView.get(0).getTranslateY() + SCROLL_HEIGHT / 2 < PANE_HEIGHT) {
    			setScrollY((int) entitiesView.get(0).getTranslateY() - SCROLL_HEIGHT / 2);
    		}
    		break;
    	case DOWN:
    		game.movePlayer(event.getCode());
    		if (entitiesView.get(0).getTranslateY() - SCROLL_HEIGHT / 2 + 1 > 0 && entitiesView.get(0).getTranslateY() + SCROLL_HEIGHT / 2 < PANE_HEIGHT) {
    			setScrollY((int) entitiesView.get(0).getTranslateY() - SCROLL_HEIGHT / 2);
    		}
    		break;
    	case LEFT:
    		game.movePlayer(event.getCode());
    		if (entitiesView.get(0).getTranslateX() - SCROLL_WIDTH / 2 + 1 > 0 && entitiesView.get(0).getTranslateX() + SCROLL_WIDTH / 2 < PANE_WIDTH) {
    			setScrollX((int) entitiesView.get(0).getTranslateX() - SCROLL_WIDTH / 2);
    		}
    		break;
    	case RIGHT:
    		game.movePlayer(event.getCode());
    		if (entitiesView.get(0).getTranslateX() - SCROLL_WIDTH / 2 + 1 > 0 && entitiesView.get(0).getTranslateX() + SCROLL_WIDTH / 2 < PANE_WIDTH) {
    			setScrollX((int) entitiesView.get(0).getTranslateX() - SCROLL_WIDTH / 2);
    		}
    		break;
    	case D :
    		this.game.getPlayer().loseHP(1);
    		this.game.getPlayer().earnPotion();
    		this.game.getPlayer().earnMoney(1);
    		break;
    	case X :
    		this.game.getPlayer().usePotion();
    		break;
    	case SPACE :
    		if (!this.game.playerInteraction())
    			this.game.getPlayer().attack(game.getEntities());
    		else 
    			if (!(this.dialogContainer.getText() == null))
    				if (!this.dialogContainer.getText().equals(""))
    					this.showText();
    		break;
    	case ESCAPE:
    		showPauseMenu();
    		break;
    	case TAB :
    		this.game.getPlayer().nextWeapon();
    		break;
    	case R :
    		this.game.getPlayer().reload();
    		break;
    	case M :
    		this.game.getPlayer().earnMoney(1);
    		break;
		default:
			break;
    	}
    	
    }

    @FXML
    void onKeyReleased(KeyEvent event) {
    	
    	if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.RIGHT)
    		playerView.resetImage();
    	
    	switch (event.getCode()) {
    	case SPACE :
    		break;
		default :
			break;
    	}
    	
    }
    
    private void setScrollX(int a) {
    	tileContainer.setTranslateX(-a);
		entityContainer.setTranslateX(-a);
    }
    
    private void setScrollY(int a) {
    	tileContainer.setTranslateY(-a);
		entityContainer.setTranslateY(-a);
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
    	
}
