package app.vue;

import app.modele.entity.Entity;
import javafx.scene.image.Image;

public class EnemyView extends EntityView {

	public EnemyView(Entity e) {
		super(new Image("file:src/img/p.png"), e);
	}

}
