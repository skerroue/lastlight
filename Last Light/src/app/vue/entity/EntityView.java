package app.vue.entity;

import app.modele.entity.AnimatedEntity;
import app.modele.entity.Entity;
import app.modele.entity.Player;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class EntityView extends ImageView {
	
	protected Entity entity;
	protected boolean isDead;
	
	public EntityView(Entity e) {
		this.entity = e;
		
		this.translateXProperty().bind(e.getX());
		this.translateYProperty().bind(e.getY());
		
		initializeEntity();
	}
	
	public boolean getIsDead() {
		return this.isDead;
	}
	
	public void initializeEntity() {
		
		this.entity.getIsDead().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				isDead = true;
			}
			
		});
		
	}
	
}
