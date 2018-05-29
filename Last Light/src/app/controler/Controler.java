package app.controler;

import java.net.URL;  
import java.util.ArrayList;
import java.util.ResourceBundle;

import app.modele.Game;
import app.modele.entity.Enemy;
import app.modele.entity.Entity;
import app.vue.EnemyView;
import app.vue.EntityView;
import app.vue.FieldView;
import app.vue.InterfaceView;
import app.vue.PlayerView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

public class Controler implements Initializable {

    @FXML
    private Pane tileContainer;
    private FieldView field;

    @FXML
    private Pane entityContainer;
	
    @FXML
    private Pane interfaceContainer;
   
    private ArrayList<EntityView> entitiesView;
    private InterfaceView hud;
    
    private Game game;
    
    public Controler() {	
    	
    	this.game = new Game();
    	this.entitiesView = new ArrayList<>();
    	this.hud = new InterfaceView(game.getPlayer());
    	this.field = new FieldView();
    	
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Generation des entites (personnage, ennemis, objets, interface, map)
		entityLoading();
		
		this.game.playGameLoop();
		
	}

    @FXML
    void onKeyPressed(KeyEvent event) {
    	
    	if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.RIGHT)
    		game.movePlayer(event.getCode());
    	else {
	    	switch (event.getCode()) {
	    	case S:
	    		this.game.addEnnemy(384, 384);
	    		break;
	    	case E:
	    		this.game.getPlayer().loseHP(1);
	    		this.game.getPlayer().earnPotion();
	    		this.game.getPlayer().earnMoney(1);
	    		break;
	    	case X:
	    		this.game.getPlayer().usePotion();
	    		break;
	    	case SPACE :
	    		this.game.getPlayer().attack(game.getEntities());
	    		break;
			default:
				break;
	    	}
    	}
    	
    }

    @FXML
    void onKeyReleased(KeyEvent event) {
    	
    	if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.RIGHT)
    		entitiesView.get(0).resetImage();
    	
    	switch (event.getCode()) {
    	case SPACE :
    		break;
		default :
			break;
    	}
    	
    }
    
    private void entityLoading() {
    	
    	entitiesView.add(new PlayerView(game.getPlayer()));
    	entityContainer.getChildren().add(entitiesView.get(0));
    	
    	interfaceContainer.getChildren().addAll(hud.getHearts());
    	interfaceContainer.getChildren().addAll(hud.getPotions());
    	interfaceContainer.getChildren().addAll(hud.getMoney());
    	
    	tileContainer.getChildren().addAll(this.field.getFieldView());
    	
    	game.getEntities().addListener(new ListChangeListener<Entity>() {

			@Override
			public void onChanged(Change<? extends Entity> c) {
				
				while (c.next()) {
					if (c.wasAdded()) {
						entitiesView.add(new EnemyView(game.getEntities().get(game.getEntities().size()-1)));
						entityContainer.getChildren().add(entitiesView.get(entitiesView.size()-1));
					}
				}
				
			}
    		
    	});
    	
    	this.game.addKeyFrame(e -> {
			for (int k = 0; k < entitiesView.size(); k++)
				if (entitiesView.get(k).getIsDead()) {
					entityContainer.getChildren().remove(entitiesView.get(k));
					entitiesView.remove(entitiesView.get(k));
				}
			for (int k = 0; k < game.getEntities().size(); k++)
				if (game.getEntities().get(k).getIsDead().get()) {
					game.getEntities().remove(game.getEntities().get(k));
				}
			for (int k = 0; k < game.getEntities().size(); k++) {
				if (game.getEntities().get(k).getHP().get() == 0)
					game.getEntities().get(k).die();
			}
		}, 0.017);
    	
    }
    
}
