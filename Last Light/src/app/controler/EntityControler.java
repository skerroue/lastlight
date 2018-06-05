package app.controler;

import java.util.ArrayList;

import app.modele.Game;
import app.modele.entity.Entity;
import app.vue.entity.AnimatedEntityView;
import app.vue.entity.EnemyView;
import app.vue.entity.InanimatedEntityView;
import app.vue.entity.PlayerView;
import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class EntityControler {
	
	private static FadeTransition ft;
	
    public static void initializeEntities(Pane entityContainer, Game game, PlayerView playerView, ArrayList<AnimatedEntityView> entitiesView, ArrayList<InanimatedEntityView> inanimatedEntityView) {
    	
    	ft = new FadeTransition();
    	ft.setFromValue(1.0);
 		ft.setToValue(0.0);
 		ft.setDuration(Duration.seconds(0.05));
    	
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
    	
    	game.addKeyFrame(e -> {
    		
    		for (int i = 0 ; i < game.getEntities().size() ; i++) {
    			if (entitiesView.get(i).getIsDead()) {
     				ft.setNode(entitiesView.get(i));
     				break;
    			}
    		}
    		
    		ft.setOnFinished(event -> {
    			entitiesView.remove(ft.getNode());
    			ft.setNode(null);
    		});
    		
    		ft.play();
    		
    		/*
			for (int k = 0; k < entitiesView.size(); k++)
				if (entitiesView.get(k).getIsDead()) {
					entityContainer.getChildren().remove(entitiesView.get(k));
					entitiesView.remove(entitiesView.get(k));
				}
			*/	
				
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
	
}
