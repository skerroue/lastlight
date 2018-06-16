package app.modele.entity.animated;

import app.modele.GameData;
import app.modele.entity.inanimated.InanimatedEntity;
import javafx.collections.ObservableList;
import javafx.scene.input.KeyCode;

public class Flying extends Enemy {

	public Flying(int x, int y, int pv, int att, int v, int nb, int fmax) {
		super("flying", x, y, pv, att, v, nb, fmax);
	}
	
	public boolean moveLeft(ObservableList<AnimatedEntity> animatedEntities, ObservableList<InanimatedEntity> inanimatedEntities) {
		this.setOrientation(KeyCode.LEFT);
		if (x.get() > GameData.LEFT_TOP_LIMIT && tileIsEmptyAnimated(animatedEntities, inanimatedEntities)) {
			x.set(x.get() - velocity);
			return true;
		}
		return false;
	}
	
	public boolean moveRight(ObservableList<AnimatedEntity> animatedEntities, ObservableList<InanimatedEntity> inanimatedEntities) {
		this.setOrientation(KeyCode.RIGHT);
		if (x.get() < GameData.RIGHT_BOTTOM_LIMIT && tileIsEmptyAnimated(animatedEntities, inanimatedEntities)) {
			x.set(x.get() + velocity);
			return true;
		}
		return false;
	}

	public boolean moveDown(ObservableList<AnimatedEntity> animatedEntities, ObservableList<InanimatedEntity> inanimatedEntities) {
		this.setOrientation(KeyCode.DOWN);
		if (y.get() < GameData.RIGHT_BOTTOM_LIMIT && tileIsEmptyAnimated(animatedEntities, inanimatedEntities)) {
			y.set(y.get() + velocity);
			return true;
		}
		return false;
	}
	
	public boolean moveUp(ObservableList<AnimatedEntity> animatedEntities, ObservableList<InanimatedEntity> inanimatedEntities) {
		this.setOrientation(KeyCode.UP);
		if (y.get() > GameData.LEFT_TOP_LIMIT && tileIsEmptyAnimated(animatedEntities, inanimatedEntities)) {
			y.set(y.get() - velocity);
			return true;
		}
		return false;
	}

}
