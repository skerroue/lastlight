package app.vue.entity;

import app.modele.GameData;
import app.modele.entity.animated.AnimatedEntity;
import javafx.scene.image.Image;

public class RockView extends EntityView {

	public RockView(AnimatedEntity e) {
		super(e);
		this.setImage(new Image("file:src/img/" + GameData.ENTITY_ROCK + ".png"));
	}

}
