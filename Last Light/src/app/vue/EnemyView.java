package app.vue;

import app.modele.entity.AnimatedEntity;
import app.modele.entity.Entity;
import javafx.scene.image.Image;

public class EnemyView extends EntityView {

	public EnemyView(AnimatedEntity e) {
		super(new Image("file:src/img/p.png"), e);
	}

}
