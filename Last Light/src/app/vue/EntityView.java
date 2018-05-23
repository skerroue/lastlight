package app.vue;

import app.modele.entity.Entity;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class EntityView extends ImageView {
	
	protected Entity entity;
	
	public EntityView(Image img, Entity e) {
		this.setImage(img);
		this.entity = e;
		
		this.translateXProperty().bind(e.getX());
		this.translateYProperty().bind(e.getY());
	}

}
