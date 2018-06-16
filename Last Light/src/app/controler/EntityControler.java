package app.controler;

import java.util.ArrayList;   

import app.modele.Game;
import app.modele.GameData;
import app.modele.entity.Entity;
import app.vue.entity.BulletView;
import app.vue.entity.ButtonView;
import app.vue.entity.EnemyView;
import app.vue.entity.EntityView;
import app.vue.entity.InanimatedEntityView;
import app.vue.entity.NPCView;
import app.vue.entity.PlayerView;
import app.vue.entity.RockView;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class EntityControler {
	
	private static FadeTransition entityDisappearance;
	private static FadeTransition attackAnimation;
	private static boolean attackAnimationActive = false;
	
	protected static void initializeEntities(Pane entityContainer, Game game, PlayerView playerView, ArrayList<EntityView> entitiesView) {
    	
    	entityDisappearance = new FadeTransition();
    	entityDisappearance.setFromValue(1.0);
 		entityDisappearance.setToValue(0.0);
 		entityDisappearance.setDuration(Duration.seconds(0.05));
 		entityDisappearance.setOnFinished(event -> {
			entitiesView.remove(entityDisappearance.getNode());
			entityDisappearance.setNode(null);
		});
 		
 		attackAnimation = new FadeTransition();
 		attackAnimation.setFromValue(1.0);
 		attackAnimation.setToValue(0.0);
 		attackAnimation.setDuration(Duration.seconds(0.2));
 		attackAnimation.setNode(playerView.getAttackImage());
		attackAnimation.setOnFinished(event2 -> {
			playerView.resetAnimationAttack();
			entityContainer.getChildren().remove(attackAnimation.getNode());
			game.getPlayer().resetIsAttacking();
			attackAnimationActive = false;
		});
    	
    	entitiesView.add(playerView);
    	entityContainer.getChildren().add(playerView);
    	
    	game.getAnimatedEntities().addListener(new ListChangeListener<Entity>() {

			@Override
			public void onChanged(Change<? extends Entity> c) {
				
				while (c.next()) {
					if (c.wasAdded()) {
						switch (game.getAnimatedEntities().get(game.getAnimatedEntities().size() - 1).getId()) {
						case GameData.ENTITY_WALKER :
							entitiesView.add(new EnemyView(game.getAnimatedEntities().get(game.getAnimatedEntities().size() - 1)));
							break;
						case GameData.ENTITY_FLYING :
							entitiesView.add(new EnemyView(game.getAnimatedEntities().get(game.getAnimatedEntities().size() - 1)));
							break;
						case GameData.ENTITY_ROCK :
							entitiesView.add(new RockView(game.getAnimatedEntities().get(game.getAnimatedEntities().size() - 1)));
							break;
						case GameData.ENTITY_NPC :
							entitiesView.add(new NPCView(game.getAnimatedEntities().get(game.getAnimatedEntities().size() - 1)));
						default :
							break;
						}
						
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
						switch (game.getInanimatedEntities().get(game.getInanimatedEntities().size() - 1).getId()) {
						case GameData.ENTITY_BUTTON :
							entitiesView.add(new ButtonView(game.getInanimatedEntities().get(game.getInanimatedEntities().size()-1)));
							break;
						default :
							entitiesView.add(new InanimatedEntityView(game.getInanimatedEntities().get(game.getInanimatedEntities().size()-1)));
							break;
						}
						
						entityContainer.getChildren().add(entitiesView.get(entitiesView.size()-1));
					}
				}
				
			}
    		
    	});
    	
    	playerView.getBullets().addListener(new ListChangeListener<BulletView>() {

			@Override
			public void onChanged(Change<? extends BulletView> c) {
				while (c.next())
					if (c.wasAdded()) {
						entitiesView.add(playerView.getBullets().get(playerView.getBullets().size()-1));
						entityContainer.getChildren().add(entitiesView.get(entitiesView.size()-1));
					}
				
			}
    		
    	});
    	
    	game.getPlayer().getIsAttacking().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldValue, Boolean newValue) {

                if (newValue.booleanValue()) {
                	
                	rotatePlayerAttack(playerView.getAttackImage(), game.getPlayer());
                	
                	switch (game.getPlayer().getOrientation().get()) {
                	case GameData.LEFT :
                		translatePlayerAttack(playerView.getAttackImage(), game.getPlayer(), -32, 5);
                		break;
                	case GameData.UP :
                		translatePlayerAttack(playerView.getAttackImage(), game.getPlayer(), 0, -32);
                		break;
                	case GameData.RIGHT :
                		translatePlayerAttack(playerView.getAttackImage(), game.getPlayer(), 32, 5);
                		break;
                	case GameData.DOWN :
                		translatePlayerAttack(playerView.getAttackImage(), game.getPlayer(), 0, 32);
                		break;
                	default : break;
                	}
                    
                }

            }

        });
        
    	
    	game.addKeyFrame(e -> {
		
    		if (game.getPlayer().getIsDead().get())
    			Platform.exit();
    		
    		for (int i = 0 ; i < entitiesView.size() ; i++)
    			entitiesView.get(i).update();
    		
    		for (int i = 0 ; i < entitiesView.size() ; i++) 
    			if (entitiesView.get(i).getIsDead()) {
     				entityDisappearance.setNode(entitiesView.get(i));
     				break;
    			}
    		
    		entityDisappearance.play();
    		
    		if (game.getPlayer().getIsAttacking().get()) { 
    			playerView.resetImage();
    			if (!attackAnimationActive) {
    				playerView.animationAttack();
    				attackAnimationActive = true; 
    				entityContainer.getChildren().add(attackAnimation.getNode());
    				attackAnimation.play();
    			}
    		}
    		
 		}, 0.017);
    	
    }
    
	private static void rotatePlayerAttack(Node n, Entity e) {
		n.setRotate((e.getOrientation().get()-1)*90);
	}
	
	private static void translatePlayerAttack(Node n, Entity e, int x, int y) {
		n.setTranslateX(e.getX().get() + x);
        n.setTranslateY(e.getY().get() + y);
	}
	
}
