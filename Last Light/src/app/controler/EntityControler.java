package app.controler;

import java.util.ArrayList;

import app.modele.Game;
import app.modele.entity.Entity;
import app.vue.entity.AnimatedEntityView;
import app.vue.entity.BoxView;
import app.vue.entity.BulletView;
import app.vue.entity.EnemyView;
import app.vue.entity.EntityView;
import app.vue.entity.InanimatedEntityView;
import app.vue.entity.PlayerView;
import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class EntityControler {
	
	private static FadeTransition enemyDisappearance;
	private static FadeTransition attackAnimation;
	private static boolean attackAnimationActive = false;
	
    public static void initializeEntities(Pane entityContainer, Game game, PlayerView playerView, ArrayList<EntityView> entitiesView, BulletView bullets) {
    	
    	enemyDisappearance = new FadeTransition();
    	enemyDisappearance.setFromValue(1.0);
 		enemyDisappearance.setToValue(0.0);
 		enemyDisappearance.setDuration(Duration.seconds(0.05));
 		enemyDisappearance.setOnFinished(event -> {
			entitiesView.remove(enemyDisappearance.getNode());
			enemyDisappearance.setNode(null);
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
    	
    	game.getEntities().addListener(new ListChangeListener<Entity>() {

			@Override
			public void onChanged(Change<? extends Entity> c) {
				
				while (c.next()) {
					if (c.wasAdded()) {
						switch (game.getEntities().get(game.getEntities().size() - 1).getId()) {
						case "walker" :
							entitiesView.add(new EnemyView(game.getEntities().get(game.getEntities().size() - 1)));
							break;
						case "box" :
							entitiesView.add(new BoxView(game.getEntities().get(game.getEntities().size() - 1)));
							break;
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
						entitiesView.add(new InanimatedEntityView(game.getInanimatedEntities().get(game.getInanimatedEntities().size()-1)));
						entityContainer.getChildren().add(entitiesView.get(entitiesView.size()-1));
					}
				}
				
			}
    		
    	});
    	
    	bullets.getBulletsNodes().addListener(new ListChangeListener<Circle>() {

			@Override
			public void onChanged(Change c) {
				
				while (c.next()) 
					if (c.wasAdded()) 
						entityContainer.getChildren().add(bullets.getBulletsNodes().get(bullets.getBulletsNodes().size()-1));
					
			}
    		
    	});
    	
    	game.getPlayer().getIsAttacking().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldValue, Boolean newValue) {

                if (newValue.booleanValue()) {
                    switch (game.getPlayer().getOrientation().get()) {
                    case 0 :
                        playerView.getAttackImage().setTranslateX(entitiesView.get(0).getTranslateX() - 32);
                        playerView.getAttackImage().setTranslateY(entitiesView.get(0).getTranslateY() + 5);
                        playerView.getAttackImage().setRotate(-90);
                        break;
                    case 1 :
                    	playerView.getAttackImage().setTranslateX(entitiesView.get(0).getTranslateX());
                    	playerView.getAttackImage().setTranslateY(entitiesView.get(0).getTranslateY() - 32);
                    	playerView.getAttackImage().setRotate(0);
                        break;
                    case 2 : 
                    	playerView.getAttackImage().setTranslateX(entitiesView.get(0).getTranslateX() + 32);
                    	playerView.getAttackImage().setTranslateY(entitiesView.get(0).getTranslateY() + 5);
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
                    
                }

            }

        });
    	
    	game.addKeyFrame(e -> {
    		
    		for (int i = 0 ; i < entitiesView.size() ; i++)
    			entitiesView.get(i).update();
    		
    		for (int i = 0 ; i < bullets.getBullets().size() ; i++)
    			if (bullets.getBullets().get(i).getIsDead().get()) {
    				game.getPlayer().attack(game.getEntities(), (int)bullets.getBullets().get(i).getX().get(), (int)bullets.getBullets().get(i).getY().get());
    				entityContainer.getChildren().remove(bullets.getBulletsNodes().get(i));
    				bullets.getBulletsNodes().remove(i);
    				bullets.getBullets().remove(i);
    			}
    		
    		for (int i = 0 ; i < bullets.getBulletsNodes().size() ; i++)
    			bullets.update(entitiesView);
    		
    		for (int i = 0 ; i < entitiesView.size() ; i++) 
    			if (entitiesView.get(i).getIsDead()) {
     				enemyDisappearance.setNode(entitiesView.get(i));
     				break;
    			}
    		
    		enemyDisappearance.play();
    		
    		if (game.getPlayer().getIsAttacking().get()) 
    			if (!attackAnimationActive) {
    				playerView.animationAttack();
    				attackAnimationActive = true; 
    				entityContainer.getChildren().add(attackAnimation.getNode());
    				attackAnimation.play();
    			}
    		
 		}, 0.017);
    	
    }
	
}
