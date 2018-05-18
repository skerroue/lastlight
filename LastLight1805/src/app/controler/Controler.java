package app.controler;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import app.modele.Game;
import app.modele.entity.Entity;
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
    private Pane TileContainer;

    @FXML
    private Pane EntityContainer;
    
    private ImageView playerImage;
    private ArrayList<ImageView> entitiesImages;
    
    private Game game;
    private Image tileset;
    
    public Controler() {	
    	
    	game = new Game();
    	tileset = new Image("file:src/img/tileset.png");
    	playerImage = new ImageView();
    	
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {	
		
		// Generation de la map
		mapGeneration();
		
		// Generation des entites
		entityLoading();
		
		// Ecoute sur la position du personnage afin de lancer le changement de map
		listenerX();
		listenerY();

    	game.firstLoad();
		
	}

    @FXML
    void onKeyPressed(KeyEvent event) {
    	
    	switch (event.getCode()) {
    	case UP:
    		game.getPlayer().moveUp();
    		break;
    	case DOWN:
    		game.getPlayer().moveDown();
    		break;
    	case LEFT:
    		game.getPlayer().moveLeft();
    		break;
    	case RIGHT:
    		game.getPlayer().moveRight();
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
    
    void mapGeneration() {
    	
    	game.getMapChanged().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				
				for (int i = 0 ; i < game.getMap().getField().length ; i++) 
					for (int j = 0 ; j < game.getMap().getField().length ; j++) {
						TileContainer.getChildren().add(game.getMap().intToTiles(new ImageView(tileset), game.getMap().getField()[i][j]));
						TileContainer.getChildren().get(TileContainer.getChildren().size()-1).setTranslateX(j*32);
						TileContainer.getChildren().get(TileContainer.getChildren().size()-1).setTranslateY(i*32);
					}
				
			}
    		
    	});
    	
    }
    
    void entityLoading() {
    	
    	playerImage.translateXProperty().bind(game.getPlayer().getX());
    	playerImage.translateYProperty().bind(game.getPlayer().getY());
    	playerImage.setImage(new Image("file:src/img/pbas.png"));
    	EntityContainer.getChildren().add(playerImage);
    	
    	game.getEntities().addListener(new ListChangeListener<Entity>() {

			@Override
			public void onChanged(Change<? extends Entity> c) {
				
				entitiesImages.add(new ImageView(new Image("file:src/img/p.png")));
				entitiesImages.get(entitiesImages.size()-1).translateXProperty().bind(game.getEntities().get(game.getEntities().size()-1).getX());
				entitiesImages.get(entitiesImages.size()-1).translateYProperty().bind(game.getEntities().get(game.getEntities().size()-1).getY());
				EntityContainer.getChildren().add(entitiesImages.get(entitiesImages.size()-1));
				
			}
    		
    	});
    	
    }

    void listenerX()  {
    	
    	game.getPlayer().getX().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				
				switch (game.getPlayer().getX().getValue()) {
				case 0 :
					System.out.println("O");
					
					break;
				case 768 :
					if (game.loadField(3)) {
						System.out.println("ok");
					}
					System.out.println("768");
					break;
				default :
					break;
				}
				
				if (game.getPlayer().getX().getValue() == 0)
					if (game.loadField(1)) {
						
					}
				else if (game.getPlayer().getX().getValue() >= 767) {
					System.out.println("1er if");
					if (game.loadField(3)) {
						
					}
				}
			}
    		
    	});
    	
    }

    void listenerY() {
    	
    	game.getPlayer().getY().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				
				if (game.getPlayer().getY().getValue() == 0)
					if (game.loadField(2)) {
						
					}
				else if (game.getPlayer().getY().getValue() == 768)
					if (game.loadField(4)) {
							
					}
				
			}
    		
    	});
    	
    }
}
