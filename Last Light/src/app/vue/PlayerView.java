package app.vue;

import app.modele.entity.AnimatedEntity;
import app.modele.entity.Entity;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PlayerView extends EntityView {
	
	private ImageView attaque;

	public PlayerView(AnimatedEntity e) {
		super(e);
		
		this.attaque = new ImageView("file:src/img/0.png");
	}

}
