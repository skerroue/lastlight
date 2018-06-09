package app.vue.entity;

import app.modele.entity.Entity; 
import javafx.scene.image.ImageView;
 
public class EntityView extends ImageView {
	
	protected Entity entity;
	protected boolean isDead;
	
	public EntityView(Entity e) {
		this.entity = e;
		
		this.translateXProperty().bind(e.getX());
		this.translateYProperty().bind(e.getY());
		
		//initializeEntity();
	}
	
	public boolean getIsDead() {
		return this.isDead;
	}
	
	
	public void update() {
		if (this.entity.getIsDead().get())
			this.isDead = true;
	}
	
	
	/*
	public void initializeEntity() {
		
		this.entity.getIsDead().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				isDead = true;
			}
			
		});
		
	}
	*/
	
}
