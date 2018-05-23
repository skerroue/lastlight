package app.controler;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import app.modele.Game;
import app.modele.entity.Ennemy;
import app.modele.entity.Entity;
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

public class Controler implements Initializable {

    @FXML
    private Pane tileContainer;

    @FXML
    private Pane entityContainer;
	
    @FXML
    private Pane interfaceContainer;
    
    private ArrayList<ImageView> entitiesImages;
    
    private Game game;
    private Image tileset;
    private Image heart;
    
    public Controler() {	
    	
    	this.game = new Game();
    	this.heart = new Image("file:src/img/h.png");
    	this.tileset = new Image("file:src/img/tileset.png");
    	this.entitiesImages = new ArrayList<>();
    	
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
    		this.game.addEnnemi(new Ennemy(384, 384, 0, 0, 32));
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
						tileContainer.getChildren().add(game.getMap().intToTiles(new ImageView(tileset), game.getMap().getField()[i][j]));
						tileContainer.getChildren().get(tileContainer.getChildren().size()-1).setTranslateX(j*32);
						tileContainer.getChildren().get(tileContainer.getChildren().size()-1).setTranslateY(i*32);
					}
				
			}
    		
    	});
    	
    }
    
    private void entityLoading() {
    	
    	imageBinding("file:src/img/pbas.png");	// Image du perso
    	
    	game.getEntities().addListener(new ListChangeListener<Entity>() {

			@Override
			public void onChanged(Change<? extends Entity> c) {
				
				while (c.next()) {
					if (c.wasAdded()) {
						imageBinding("file:src/img/p.png");
					} else if (c.wasRemoved()) {
						
					}
				}
				
			}
    		
    	});
    	
    }
    
    private void imageBinding(String filePath) {
    	this.entitiesImages.add(new ImageView(new Image(filePath)));
		this.entitiesImages.get(this.entitiesImages.size()-1).translateXProperty().bind(this.game.getEntities().get(this.game.getEntities().size()-1).getX());
		this.entitiesImages.get(this.entitiesImages.size()-1).translateYProperty().bind(this.game.getEntities().get(this.game.getEntities().size()-1).getY());
		this.entityContainer.getChildren().add(this.entitiesImages.get(this.entitiesImages.size()-1));
    }

	    // TODO
    // Gère toute l'interface
    public void interfaceGeneration() {
    	/*
    	 * Indice 0 : Potions
    	 * Indice 1 : Argent
    	 */
    	
    	// Premier démarrage
    	// Potions
    	interfaceContainer.getChildren().add(new Label("Potions : " + game.getPlayer().getPotion().getValue().toString()));
    	interfaceContainer.getChildren().get(interfaceContainer.getChildren().size()-1).setTranslateX(20);
    	interfaceContainer.getChildren().get(interfaceContainer.getChildren().size()-1).setTranslateY(730);
    	((Labeled) interfaceContainer.getChildren().get(interfaceContainer.getChildren().size()-1)).setTextFill(Color.web("#ffffff"));
    	// Argent
    	interfaceContainer.getChildren().add(new Label("Argent : " + game.getPlayer().getMoney().getValue().toString()));
    	interfaceContainer.getChildren().get(interfaceContainer.getChildren().size()-1).setTranslateX(20);
    	interfaceContainer.getChildren().get(interfaceContainer.getChildren().size()-1).setTranslateY(750);
    	((Labeled) interfaceContainer.getChildren().get(interfaceContainer.getChildren().size()-1)).setTextFill(Color.web("#ffffff"));
    	// Coeurs
    	for (int i = 1; i < game.getPlayer().getPv().getValue() +1; i++) {
    		interfaceContainer.getChildren().add(new ImageView(heart));
    		interfaceContainer.getChildren().get(interfaceContainer.getChildren().size()-1).setTranslateX(i*30);
    		interfaceContainer.getChildren().get(interfaceContainer.getChildren().size()-1).setTranslateY(15);
		}
    	
    	
	 game.getPlayer().getPv().addListener(new ChangeListener<Object>() {
	    	
	 	@Override
	    	public void changed(ObservableValue<? extends Object> observableValue, Object oldValue, Object newValue) {
	    		for (int i = interfaceContainer.getChildren().size()-1; i > game.getPlayer().getPv().getValue()+1; i--)
	    			interfaceContainer.getChildren().remove(interfaceContainer.getChildren().size()-1);
	    		
	    		for (int i =1; i < game.getPlayer().getPv().getValue()+1; i++) {
	        		interfaceContainer.getChildren().add(new ImageView(heart));
	        		interfaceContainer.getChildren().get(interfaceContainer.getChildren().size()-1).setTranslateX(i*30);
	        		interfaceContainer.getChildren().get(interfaceContainer.getChildren().size()-1).setTranslateY(15);
	    		}
	    		
	    	}
	    });
	    
		game.getPlayer().getPotion().addListener(new ChangeListener<Object>() {
			    	
			    	@Override
			    	public void changed(ObservableValue<? extends Object> observableValue, Object oldValue, Object newValue) {
			    			((Labeled) interfaceContainer.getChildren().get(0)).setText("Potions : " + game.getPlayer().getPotion().getValue());
			    	}
		});
		
		game.getPlayer().getMoney().addListener(new ChangeListener<Object>() {
	    	
	    	@Override
	    	public void changed(ObservableValue<? extends Object> observableValue, Object oldValue, Object newValue) {
	    			((Labeled) interfaceContainer.getChildren().get(1)).setText("Argent : " + game.getPlayer().getMoney().getValue());
	    	}
	});
	    
    }
}
