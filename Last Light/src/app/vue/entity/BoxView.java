package app.vue.entity;

import app.modele.entity.AnimatedEntity;
import javafx.scene.image.Image;

public class BoxView extends EntityView {

	public BoxView(AnimatedEntity e) {
		super(e);
		this.setImage(new Image("file:src/img/0.png"));
	}

}
