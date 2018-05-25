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
import javafx.beans.property.IntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

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
    	this.tileset = new Image("file:src/img/tileset.png");
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
		
		this.game.playGameLoop();
		
	}

    @FXML
    void onKeyPressed(KeyEvent event) {
    	
    	int rightLimit = 0, leftLimit = 768;
    	
    	switch (event.getCode()) {
    	case UP:
    		if (this.game.getPlayer().getY().getValue() == rightLimit) {
    			if (this.game.loadField(2))
    				this.game.getPlayer().setY(leftLimit);
    		}
    		else 
    			this.game.getPlayer().moveUp(this.game.getEntities());
    		break;
    	case DOWN:
    		if (this.game.getPlayer().getY().getValue() == leftLimit) {
    			if (this.game.loadField(4))
    				this.game.getPlayer().setY(rightLimit);
    		}
    		else
    			this.game.getPlayer().moveDown(this.game.getEntities());
    		break;
    	case LEFT:
    		if (this.game.getPlayer().getX().getValue() == rightLimit) {
    			if (this.game.loadField(1))
    				this.game.getPlayer().setX(leftLimit);
    		}
    		else
    			this.game.getPlayer().moveLeft(this.game.getEntities());
    		break;
    	case RIGHT:
    		if (this.game.getPlayer().getX().getValue() == leftLimit) {
    			if (this.game.loadField(3))
    				this.game.getPlayer().setX(rightLimit);
    		}
    		else
    			this.game.getPlayer().moveRight(this.game.getEntities());
    		break;
    	case S:
    		this.game.addEnnemi(new Enemy(384, 384, 0, 0, 32));
    		break;
    	case E:
    		this.game.getPlayer().loosePv(1);
    		this.game.getPlayer().earnPotion();
    		this.game.getPlayer().earnMoney(50);
    		break;
		default:
			break;
    	}
    	
    }

    @FXML
    void onKeyReleased(KeyEvent event) {
    	
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

	    // TODO
    // Gère toute l'interface
    public void interfaceGeneration() {
    	/*
    	* Indice 0 : Label Potions
    	* Indice 1 : Image Potions
    	* Indice 2 : Label Argent
    	* Indice 3 : Image Argent
    	*/
    	
    	// Potions
    	ItemView potions = new ItemView(game.getPlayer(), new Image("file:src/img/soda.png"), 20, 50, "0");
    	interfaceContainer.getChildren().add(potions);
    	interfaceContainer.getChildren().add(potions.getLabel());
    	
    	// Argent
    	ItemView money = new ItemView(game.getPlayer(), new Image("file:src/img/money.png"), 80, 50, "0");
    	interfaceContainer.getChildren().add(money);
    	interfaceContainer.getChildren().add(money.getLabel());
    	
    	// Coeurs
    	for (int i = 0 ; i < game.getPlayer().getPv().getValue() ; i++)
    		interfaceContainer.getChildren().add(new ItemView(game.getPlayer(), new Image("file:src/img/h.png"), (i+1)*17, 15, ""));
    	
    	game.getPlayer().getPv().addListener(new ChangeListener<Number>() {
    		
    		@Override
    		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
    			if (oldValue.intValue() > newValue.intValue())
    				interfaceContainer.getChildren().remove(interfaceContainer.getChildren().size()-1);
    			else 
    				interfaceContainer.getChildren().add(new ItemView(game.getPlayer(), new Image("file:src/img/h.png"), newValue.intValue()*17, 15, ""));
    		}
	 	
	    });
	    
		game.getPlayer().getPotion().addListener(new ChangeListener<Object>() {
			    	
	    	@Override
	    	public void changed(ObservableValue<? extends Object> observableValue, Object oldValue, Object newValue) {
	    			potions.getLabel().setText(game.getPlayer().getPotion().getValue().toString());
	    	}
	    	
		});
		
		game.getPlayer().getMoney().addListener(new ChangeListener<Object>() {
	    	
	    	@Override
	    	public void changed(ObservableValue<? extends Object> observableValue, Object oldValue, Object newValue) {
	    			money.getLabel().setText(game.getPlayer().getMoney().getValue().toString());
	    	}
	    	
		});
	    
    }
    
}
