package app.controler;

import java.net.URL;  
import java.util.ArrayList;
import java.util.ResourceBundle;

import app.modele.Game;
import app.modele.entity.Enemy;
import app.modele.entity.Entity;
import app.vue.FieldView;
import app.vue.InterfaceView;
import app.vue.entity.AnimatedEntityView;
import app.vue.entity.EnemyView;
import app.vue.entity.EntityView;
import app.vue.entity.InanimatedEntityView;
import app.vue.entity.PlayerView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
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
	private Button resumeButton;
	private Button quitButton;
	
    @FXML
    private Pane tileContainer;
    private FieldView field;

    @FXML
    private Pane entityContainer;
	
    @FXML
    private Pane interfaceContainer;
   
    private ArrayList<AnimatedEntityView> entitiesView;
    private ArrayList<InanimatedEntityView> inanimatedEntityView;
    private PlayerView playerView;
    private InterfaceView hud;
    
    private Game game;
    
    public Controler() {	
    	
    	this.game = new Game();
    	this.entitiesView = new ArrayList<>();
    	this.inanimatedEntityView = new ArrayList<>();
    	this.hud = new InterfaceView(game.getPlayer());
    	this.field = new FieldView();
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Generation des entites (personnage, ennemis, objets, interface, map)
		initializeMap();
		initializeInterface();
		initializeEntities();
		this.game.playGameLoop();
		initializeScrollField();
	}

    @FXML
    void onKeyPressed(KeyEvent event) {
    	
    	switch (event.getCode()) {
    	case UP:
    		game.movePlayer(event.getCode());
    		if (entitiesView.get(0).getTranslateY() - SCROLL_HEIGHT / 2 > 0 && entitiesView.get(0).getTranslateY() + SCROLL_HEIGHT / 2 < PANE_HEIGHT) {
    			setScrollY((int) entitiesView.get(0).getTranslateY() - SCROLL_HEIGHT / 2);
    		}
    		break;
    	case DOWN:
    		game.movePlayer(event.getCode());
    		if (entitiesView.get(0).getTranslateY() - SCROLL_HEIGHT / 2 > 0 && entitiesView.get(0).getTranslateY() + SCROLL_HEIGHT / 2 < PANE_HEIGHT) {
    			setScrollY((int) entitiesView.get(0).getTranslateY() - SCROLL_HEIGHT / 2);
    		}
    		break;
    	case LEFT:
    		game.movePlayer(event.getCode());
    		if (entitiesView.get(0).getTranslateX() - SCROLL_WIDTH / 2 > 0 && entitiesView.get(0).getTranslateX() + SCROLL_WIDTH / 2 < PANE_WIDTH) {
    			setScrollX((int) entitiesView.get(0).getTranslateX() - SCROLL_WIDTH / 2);
    		}
    		break;
    	case RIGHT:
    		game.movePlayer(event.getCode());
    		if (entitiesView.get(0).getTranslateX() - SCROLL_WIDTH / 2 > 0 && entitiesView.get(0).getTranslateX() + SCROLL_WIDTH / 2 < PANE_WIDTH) {
    			setScrollX((int) entitiesView.get(0).getTranslateX() - SCROLL_WIDTH / 2);
    		}
    		break;
    	case S:
    		this.game.addEnnemy(384, 384);
    		break;
    	case F :
    		this.game.getPlayer().interact(game.getInanimatedEntities());
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
    	case ESCAPE:
    		showPauseMenu();
    		break;
    	case DIGIT1 :
    		this.game.getPlayer().switchWeapon(1);
    		break;
		default:
			break;
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
    
    public void initializeScrollField() {
    	
    	setScrollX((int) entitiesView.get(0).getTranslateX() - SCROLL_WIDTH / 2);
		setScrollY((int) entitiesView.get(0).getTranslateY() - SCROLL_HEIGHT / 2);
    	
    	this.game.getMapChanged().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				
				field.refreshField();
				
				switch (game.getPlayer().getOrientation().get()) {
				case 0:
					if (entitiesView.get(0).getTranslateY() - SCROLL_HEIGHT / 2 < 0)
						setScrollY(0);
					else if (entitiesView.get(0).getTranslateY() + SCROLL_HEIGHT / 2 > PANE_HEIGHT)
						setScrollY(800 - SCROLL_HEIGHT);
					else
						setScrollY((int) entitiesView.get(0).getTranslateY() - SCROLL_HEIGHT / 2);
					setScrollX((int) entitiesView.get(0).getTranslateX() - SCROLL_WIDTH + 32);
					break;
				case 1:
					if (entitiesView.get(0).getTranslateX() - SCROLL_WIDTH / 2 < 0)
						setScrollX(0);
					else if (entitiesView.get(0).getTranslateX() + SCROLL_WIDTH / 2 > PANE_WIDTH)
						setScrollX(800 - SCROLL_WIDTH);
					else
						setScrollX((int) entitiesView.get(0).getTranslateX() - (SCROLL_WIDTH / 2));
					setScrollY((int) entitiesView.get(0).getTranslateY() - SCROLL_HEIGHT + 32);
					break;
				case 2:
					if (entitiesView.get(0).getTranslateY() - SCROLL_HEIGHT / 2 < 0)
						setScrollY(0);
					else if (entitiesView.get(0).getTranslateY() + SCROLL_HEIGHT / 2 > PANE_HEIGHT)
						setScrollY(800 - SCROLL_HEIGHT);
					else
						setScrollY((int) entitiesView.get(0).getTranslateY() - SCROLL_HEIGHT / 2);
					setScrollX((int) entitiesView.get(0).getTranslateX());
					break;
				case 3:
					if (entitiesView.get(0).getTranslateX() - SCROLL_WIDTH / 2 < 0)
						setScrollX(0);
					else if (entitiesView.get(0).getTranslateX() + SCROLL_WIDTH / 2 > PANE_WIDTH)
						setScrollX(800 - SCROLL_WIDTH);
					else
						setScrollX((int) entitiesView.get(0).getTranslateX() - SCROLL_WIDTH / 2);
					setScrollY((int) entitiesView.get(0).getTranslateY());
					break;
				}
				
			}
    		
    	});
    }
    
    private void setScrollX(int a) {
    	tileContainer.setTranslateX(-a);
		entityContainer.setTranslateX(-a);
    }
    
    private void setScrollY(int a) {
    	tileContainer.setTranslateY(-a);
		entityContainer.setTranslateY(-a);
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
    	
    	playerView = new PlayerView(game.getPlayer());
    	entitiesView.add(playerView);
    	entityContainer.getChildren().add(playerView);
    	
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
    	
    	game.getInanimatedEntities().addListener(new ListChangeListener<Entity>() {

			@Override
			public void onChanged(Change<? extends Entity> c) {
				
				while (c.next()) {
					if (c.wasAdded()) {
						inanimatedEntityView.add(new InanimatedEntityView(game.getInanimatedEntities().get(game.getInanimatedEntities().size()-1)));
						entityContainer.getChildren().add(inanimatedEntityView.get(inanimatedEntityView.size()-1));
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
                        playerView.getAttackImage().setTranslateX(entitiesView.get(0).getTranslateX() - 32);
                        playerView.getAttackImage().setTranslateY(entitiesView.get(0).getTranslateY());
                        playerView.getAttackImage().setRotate(-90);
                        break;
                    case 1 :
                    	playerView.getAttackImage().setTranslateX(entitiesView.get(0).getTranslateX());
                    	playerView.getAttackImage().setTranslateY(entitiesView.get(0).getTranslateY() - 32);
                    	playerView.getAttackImage().setRotate(0);
                        break;
                    case 2 : 
                    	playerView.getAttackImage().setTranslateX(entitiesView.get(0).getTranslateX() + 32);
                    	playerView.getAttackImage().setTranslateY(entitiesView.get(0).getTranslateY());
                    	playerView.getAttackImage().setRotate(90);
                        break;
                    case 3 :
                    	playerView.getAttackImage().setTranslateX(entitiesView.get(0).getTranslateX());
                    	playerView.getAttackImage().setTranslateY(entitiesView.get(0).getTranslateY() + 32);
                    	playerView.getAttackImage().setRotate(180);
                        break;
                    default :
                        break;
                    }

                    entityContainer.getChildren().add(playerView.getAttackImage());
                }
                else
                    entityContainer.getChildren().remove(playerView.getAttackImage());

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
			for (int i = 0 ; i < inanimatedEntityView.size() ; i++)
				if (inanimatedEntityView.get(i).getIsDead()) {
					entityContainer.getChildren().remove(inanimatedEntityView.get(i));
					inanimatedEntityView.remove(inanimatedEntityView.get(i));
				}
			for (int i = 0 ; i < game.getInanimatedEntities().size() ; i++)
				if (game.getInanimatedEntities().get(i).getIsDead().get())
					game.getInanimatedEntities().remove(game.getInanimatedEntities().get(i));
 		}, 0.017);
    	
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
}
