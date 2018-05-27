package app.controler;

import java.net.URL; 
import java.util.ArrayList;
import java.util.ResourceBundle;

import app.modele.Game;
import app.modele.entity.Enemy;
import app.modele.entity.Entity;
import app.vue.EnemyView;
import app.vue.EntityView;
import app.vue.ItemView;
import app.vue.PlayerView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

public class Controler implements Initializable {

    @FXML
    private Pane tileContainer;
    private Image tileset;

    @FXML
    private Pane entityContainer;
	
    @FXML
    private Pane interfaceContainer;
   
    private ArrayList<EntityView> entitiesView;
    
    private Game game;
    
    public Controler() {	
    	
    	this.game = new Game();
    	this.tileset = new Image("file:src/img/tilesetLastLight.png");
    	this.entitiesView = new ArrayList<>();
    	
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {	
		
		// Generation de la map
		mapGeneration();
		this.game.mapOnChange();
		
		// Generation des entites
		entityLoading();
		
		// Generation de l'interface
		interfaceGeneration();
		
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
				if (game.getEntities().get(k).getPv().get() == 0)
					game.getEntities().get(k).die();
			}
		}, 0.017);
		
		this.game.playGameLoop();
		
	}

    @FXML
    void onKeyPressed(KeyEvent event) {
    	
    	int rightLimit = 0, leftLimit = 768;
    	
    	switch (event.getCode()) {
    	case UP:
    		this.game.getPlayer().setOrientation(event.getCode());
    		if (this.game.getPlayer().getY().getValue() == rightLimit) {
    			if (this.game.loadField(2))
    				this.game.getPlayer().setY(leftLimit);
    		}
    		else 
    			this.game.getPlayer().moveUp(this.game.getEntities());
    		break;
    	case DOWN:
    		this.game.getPlayer().setOrientation(event.getCode());
    		if (this.game.getPlayer().getY().getValue() == leftLimit) {
    			if (this.game.loadField(4))
    				this.game.getPlayer().setY(rightLimit);
    		}
    		else
    			this.game.getPlayer().moveDown(this.game.getEntities());
    		break;
    	case LEFT:
    		this.game.getPlayer().setOrientation(event.getCode());
    		if (this.game.getPlayer().getX().getValue() == rightLimit) {
    			if (this.game.loadField(1))
    				this.game.getPlayer().setX(leftLimit);
    		}
    		else
    			this.game.getPlayer().moveLeft(this.game.getEntities());
    		break;
    	case RIGHT:
    		this.game.getPlayer().setOrientation(event.getCode());
    		if (this.game.getPlayer().getX().getValue() == leftLimit) {
    			if (this.game.loadField(3))
    				this.game.getPlayer().setX(rightLimit);
    		}
    		else
    			this.game.getPlayer().moveRight(this.game.getEntities());
    		break;
    	case S:
    		this.game.addEnnemy(384, 384);
    		break;
    	case E:
    		this.game.getPlayer().loosePv(1);
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

    @FXML
    void onKeyReleased(KeyEvent event) {
    	
    	entitiesView.get(0).resetImage();
    	
    	switch (event.getCode()) {
    	case UP:
    		break;
    	case DOWN:
    		break;
    	case LEFT:
    		break;
    	case RIGHT:
    		break;
		default:
			break;
    	}
    	
    }
    
    private void mapGeneration() {
    	
    	this.game.getMapChanged().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				
				tileContainer.getChildren().clear();
				
				for (int i = 0 ; i < game.getMap().getField().length ; i++) 
					for (int j = 0 ; j < game.getMap().getField().length ; j++) {
						tileContainer.getChildren().add(game.getMap().intToTiles(new ImageView(tileset), game.getMap().getNextTile(i, j)));
						tileContainer.getChildren().get(tileContainer.getChildren().size()-1).setTranslateX(j*32);
						tileContainer.getChildren().get(tileContainer.getChildren().size()-1).setTranslateY(i*32);
					}
				
			}
    		
    	});
    	
    }
    
    private void entityLoading() {
    	
    	entitiesView.add(new PlayerView(game.getPlayer()));
    	entityContainer.getChildren().add(entitiesView.get(0));
    	
    	game.getEntities().addListener(new ListChangeListener<Entity>() {

			@Override
			public void onChanged(Change<? extends Entity> c) {
				
				while (c.next()) {
					if (c.wasAdded()) {
						entitiesView.add(new EnemyView(game.getEntities().get(game.getEntities().size()-1)));
						entityContainer.getChildren().add(entitiesView.get(entitiesView.size()-1));
					} else if (c.wasRemoved()) {
						
					}
				}
				
			}
    		
    	});
    	
    }
    
    public void interfaceUpdate(int min, int max, Number oldValue, Number newValue) {
    	if (oldValue.intValue() == 0)
			interfaceContainer.getChildren().get(min).setVisible(true);
		else {
			// Loose
			if (oldValue.intValue() > newValue.intValue()) {
				for (int i = max; i > min; i--) {
					if (!interfaceContainer.getChildren().get(i).isVisible()&& interfaceContainer.getChildren().get(i-1).isVisible()) {
    					interfaceContainer.getChildren().get(i-1).setVisible(false);
    					break;
    				}
				}
			}
			// Gain
			else {
				for (int i = min; i < max; i++) {
    				if (interfaceContainer.getChildren().get(i).isVisible()&& !interfaceContainer.getChildren().get(i+1).isVisible()) {
    					interfaceContainer.getChildren().get(i+1).setVisible(true);
    					break;
    				}
    			}
			}
			
		}
    }

    // Gère toute l'interface
    public void interfaceGeneration() {
    	/*
    	* Indice 0-2 : Potions
    	* Indice 3-7 : Jetons
    	* Indice 8-13 : Coeurs
    	*/
    	
    	// Potions
    	for (int i = 0 ; i < 3 ; i++) {
    		interfaceContainer.getChildren().add(new ItemView(game.getPlayer(), new Image("file:src/img/soda.png"), (i+1)*17, 45, ""));
    		interfaceContainer.getChildren().get(interfaceContainer.getChildren().size() -1).setVisible(false);
    	}
    	
    	// Argent
    	for (int i = 0 ; i < 5 ; i++) {
    		interfaceContainer.getChildren().add(new ItemView(game.getPlayer(), new Image("file:src/img/money.png"), (i+1)*17, 72, ""));
    		interfaceContainer.getChildren().get(interfaceContainer.getChildren().size() -1).setVisible(false);
    	}
    	
    	// Coeurs
    	for (int i = 0 ; i < 6 ; i++) {
    		interfaceContainer.getChildren().add(new ItemView(game.getPlayer(), new Image("file:src/img/h.png"), (i+1)*17, 15, ""));
    		if (i > 2)
    			interfaceContainer.getChildren().get(interfaceContainer.getChildren().size() -1).setVisible(false);
    	}
    		
    	// MAJ Heart
    	game.getPlayer().getPv().addListener(new ChangeListener<Number>() {
    		
    		@Override
    		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
    			interfaceUpdate(8, 13, oldValue, newValue);
    		}
	    });
	   
    	// MAJ Potion
    	game.getPlayer().getPotion().addListener(new ChangeListener<Number>() {
    		
    		@Override
    		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
    			interfaceUpdate(0, 2, oldValue, newValue);
    		}
	    });
		
    	// MAJ Jetons
    	game.getPlayer().getMoney().addListener(new ChangeListener<Number>() {
    		
    		@Override
    		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
    			interfaceUpdate(3, 7, oldValue, newValue);
    		}
	    });
	    
    }
    
}
