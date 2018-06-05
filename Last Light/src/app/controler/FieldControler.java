package app.controler;

import java.util.ArrayList;

import app.modele.Game;
import app.vue.FieldView;
import app.vue.entity.AnimatedEntityView;
import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class FieldControler {
	
    private static FadeTransition ft;
    private static Rectangle rec;
    
	 public static void AnimationTransitionMap(Double i) {
	    	ft.setDuration(Duration.seconds(i));
			ft.play();
	    }
	 
	private static void creatingAnimation(Pane interfaceContainer) {
		interfaceContainer.getChildren().add(rec);
		rec.setFill(Color.BLACK);
        ft.setFromValue(1.0);
		ft.setToValue(0.0);
		ft.setNode(rec);
	}
    
    public static void initializeField(Pane tileContainer, FieldView field, Pane interfaceContainer) {
      
    	
    	// Map transition
        ft = new FadeTransition();
        rec = new Rectangle(600,600);
        creatingAnimation(interfaceContainer);
        
    	tileContainer.getChildren().addAll(field.getFieldView());
    	
    	Game.getMapChanged().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
				field.refreshField();
			}
			
		});
    	
    }
    
    public static void initializeScrollField(ArrayList<AnimatedEntityView> entitiesView, FieldView field, Game game, int SCROLL_WIDTH, int SCROLL_HEIGHT, int PANE_HEIGHT, int PANE_WIDTH, Pane tileContainer, Pane entityContainer) {
    	
    	setScrollX((int) entitiesView.get(0).getTranslateX() - SCROLL_WIDTH / 2, tileContainer, entityContainer);
		setScrollY((int) entitiesView.get(0).getTranslateY() - SCROLL_HEIGHT / 2, tileContainer, entityContainer);
    	
    	game.getMapChanged().addListener(new ChangeListener<Boolean>() {
    		
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				AnimationTransitionMap(0.2);
				field.refreshField();
				
				switch (game.getPlayer().getOrientation().get()) {
				case 0:
					if (entitiesView.get(0).getTranslateY() - SCROLL_HEIGHT / 2 < 0)
						setScrollY(0, tileContainer, entityContainer);
					else if (entitiesView.get(0).getTranslateY() + SCROLL_HEIGHT / 2 > PANE_HEIGHT)
						setScrollY(800 - SCROLL_HEIGHT, tileContainer, entityContainer);
					else
						setScrollY((int) entitiesView.get(0).getTranslateY() - SCROLL_HEIGHT / 2, tileContainer, entityContainer);
					setScrollX((int) entitiesView.get(0).getTranslateX() - SCROLL_WIDTH + 32, tileContainer, entityContainer);
					break;
				case 1:
					if (entitiesView.get(0).getTranslateX() - SCROLL_WIDTH / 2 < 0)
						setScrollX(0, tileContainer, entityContainer);
					else if (entitiesView.get(0).getTranslateX() + SCROLL_WIDTH / 2 > PANE_WIDTH)
						setScrollX(800 - SCROLL_WIDTH, tileContainer, entityContainer);
					else
						setScrollX((int) entitiesView.get(0).getTranslateX() - (SCROLL_WIDTH / 2), tileContainer, entityContainer);
					setScrollY((int) entitiesView.get(0).getTranslateY() - SCROLL_HEIGHT + 32, tileContainer, entityContainer);
					break;
				case 2:
					if (entitiesView.get(0).getTranslateY() - SCROLL_HEIGHT / 2 < 0)
						setScrollY(0, tileContainer, entityContainer);
					else if (entitiesView.get(0).getTranslateY() + SCROLL_HEIGHT / 2 > PANE_HEIGHT)
						setScrollY(800 - SCROLL_HEIGHT, tileContainer, entityContainer);
					else
						setScrollY((int) entitiesView.get(0).getTranslateY() - SCROLL_HEIGHT / 2, tileContainer, entityContainer);
					setScrollX((int) entitiesView.get(0).getTranslateX(), tileContainer, entityContainer);
					break;
				case 3:
					if (entitiesView.get(0).getTranslateX() - SCROLL_WIDTH / 2 < 0)
						setScrollX(0, tileContainer, entityContainer);
					else if (entitiesView.get(0).getTranslateX() + SCROLL_WIDTH / 2 > PANE_WIDTH)
						setScrollX(800 - SCROLL_WIDTH, tileContainer, entityContainer);
					else
						setScrollX((int) entitiesView.get(0).getTranslateX() - SCROLL_WIDTH / 2, tileContainer, entityContainer);
					setScrollY((int) entitiesView.get(0).getTranslateY(), tileContainer, entityContainer);
					break;
				}
				
			}
    		
    	});
    }
    
    private static void setScrollX(int a, Pane tileContainer, Pane entityContainer) {
    	tileContainer.setTranslateX(-a);
		entityContainer.setTranslateX(-a);
    }
    
    private static void setScrollY(int a, Pane tileContainer, Pane entityContainer) {
    	tileContainer.setTranslateY(-a);
		entityContainer.setTranslateY(-a);
    }
	

}
