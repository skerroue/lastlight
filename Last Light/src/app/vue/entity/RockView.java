package app.vue.entity;

import app.modele.entity.animated.AnimatedEntity;
import javafx.scene.image.Image;

public class RockView extends EntityView {

	public RockView(AnimatedEntity e) {
		super(e);
		this.setImage(new Image("file:src/img/rock.png"));
	}

}
