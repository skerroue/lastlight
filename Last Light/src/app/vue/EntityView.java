package app.vue;

import app.modele.entity.Entity;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class EntityView extends ImageView {
	
	protected Entity entity;
	protected boolean isDead;
	
	public EntityView(Image img, Entity e) {
		this.setImage(img);
		this.entity = e;
		
		this.translateXProperty().bind(e.getX());
		this.translateYProperty().bind(e.getY());
		
		e.getIsDead().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				isDead = true;
			}
			
		});
		
	}
	
	public boolean getIsDead() {
		return this.isDead;
	}
	
	public void resetImage() {
		this.entity.resetFrame();
		this.setViewport(new Rectangle2D((this.entity.getFrame()/3)*32, this.entity.getOrientation().getValue()*32, 32, 32));
	}

}
