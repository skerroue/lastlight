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
    ImageView i;
    
    public Controler() {	
    	
    	this.game = new Game();
    	this.entitiesView = new ArrayList<>();
    	this.hud = new InterfaceView(game.getPlayer());
    	this.field = new FieldView();
    	
    	i = new ImageView(new Image("file:src/img/0.png"));
    	
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		//Initialisation de la map
		initializeMap();
		
		//Initialisation de l'interface
		initializeInterface();
		
		// Generation des entites (personnage, ennemis, objets)
		initializeEntities();
		
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
    		game.getPlayer().resetIsAttacking();
    		break;
		default :
			break;
    	}
    	
    }
    
    private void initializeMap() {
    	
    	tileContainer.getChildren().addAll(this.field.getFieldView());
    	
    	Game.getMapChanged().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
				field.refreshField();
			}
			
		});
    	
    }
    
    private void initializeInterface() {
    	interfaceContainer.getChildren().addAll(hud.getHearts());
    	interfaceContainer.getChildren().addAll(hud.getPotions());
    	interfaceContainer.getChildren().addAll(hud.getMoney());
    	
    	hud.getPlayer().getHP().addListener(new ChangeListener<Number>() {
    		
    		@Override
    		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
    			
    			if (oldValue.intValue() > newValue.intValue()) {
    				for (int i = hud.getHearts().size()-1 ; i >= 0 ; i--) 
    					if (hud.getHearts().get(i).getFull()) {
    						hud.getHearts().get(i).setEmpty();
    						break;
    					}
    			}
    			
    			else {
    				for (int i = 0 ; i < hud.getHearts().size() ; i++)
    					if (hud.getHearts().get(i).getEmpty() && !hud.getHearts().get(i).getLocked()) {
    						hud.getHearts().get(i).setFull();
    						break;
    					}
    			}
    				
    		}
    		
	    });
		
    	hud.getPlayer().getMaxHP().addListener(new ChangeListener<Number>() {
    		
    		@Override
    		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
    			
    			if (newValue.intValue() > oldValue.intValue()) {
    				for (int i = 0 ; i < hud.getHearts().size() ; i++)
    					if (hud.getHearts().get(i).getLocked()) {
    						hud.getHearts().get(i).setEmpty();
    						break;
    					}
    				
    			}
    			
    		}
    		
	    });
    	
		hud.getPlayer().getPotion().addListener(new ChangeListener<Number>() {
    		
    		@Override
    		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
    			
    			if (oldValue.intValue() > newValue.intValue()) {
    				for (int i = hud.getPotions().size()-1 ; i >= 0 ; i--) 
    					if (hud.getPotions().get(i).getFull()) {
    						hud.getPotions().get(i).setEmpty();
    						break;
    					}
    			}
    			
    			else {
    				for (int i = 0 ; i < hud.getPotions().size() ; i++)
    					if (hud.getPotions().get(i).getEmpty()) {
    						hud.getPotions().get(i).setFull();
    						break;
    					}
    			}
    				
    		}
    		
	    });
		
		hud.getPlayer().getMoney().addListener(new ChangeListener<Number>() {
    		
    		@Override
    		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
    			
    			if (oldValue.intValue() > newValue.intValue()) {
    				for (int i = hud.getMoney().size()-1 ; i >= 0 ; i--) 
    					if (hud.getMoney().get(i).getFull()) {
    						hud.getMoney().get(i).setEmpty();
    						break;
    					}
    			}
    			
    			else {
    				for (int i = 0 ; i < hud.getMoney().size() ; i++)
    					if (hud.getMoney().get(i).getEmpty()) {
    						hud.getMoney().get(i).setFull();
    						break;
    					}
    			}
    				
    		}
    		
	    });
    }
    
    private void initializeEntities() {
    	
    	entitiesView.add(new PlayerView(game.getPlayer()));
    	entityContainer.getChildren().add(entitiesView.get(0));
    	
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
    	
    	game.getPlayer().getIsAttacking().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldValue, Boolean newValue) {
				
				if (newValue.booleanValue()) {
					switch (game.getPlayer().getOrientation().get()) {
					case 0 :
						i.setTranslateX(entitiesView.get(0).getTranslateX() - 32);
						i.setTranslateY(entitiesView.get(0).getTranslateY());
						break;
					case 1 :
						i.setTranslateX(entitiesView.get(0).getTranslateX());
						i.setTranslateY(entitiesView.get(0).getTranslateY() - 32);
						break;
					case 2 : 
						i.setTranslateX(entitiesView.get(0).getTranslateX() + 32);
						i.setTranslateY(entitiesView.get(0).getTranslateY());
						break;
					case 3 :
						i.setTranslateX(entitiesView.get(0).getTranslateX());
						i.setTranslateY(entitiesView.get(0).getTranslateY() + 32);
						break;
					default :
						break;
					}
					
					entityContainer.getChildren().add(i);
				}
				else
					entityContainer.getChildren().remove(i);
				
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
