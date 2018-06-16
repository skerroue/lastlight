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
	}
	
	public boolean getIsDead() {
		return this.isDead;
	}
	
	
	public void update() {
		if (this.entity.getIsDead().get())
			this.isDead = true;
	}
	
}
