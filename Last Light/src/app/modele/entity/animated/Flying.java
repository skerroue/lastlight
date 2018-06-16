package app.modele.entity.animated;

import app.modele.Game;
import app.modele.GameData;
import javafx.scene.input.KeyCode;

public class Flying extends Enemy {

	public Flying(int x, int y, int pv, int att, int v, int nb, int fmax) {
		super("flying", x, y, pv, att, v, nb, fmax);
	}
	
	public boolean moveLeft() {
		this.setOrientation(KeyCode.LEFT);
		if (x.get() > GameData.LEFT_TOP_LIMIT && tileIsEmptyAnimated(Game.getAnimatedEntities())) {
			x.set(x.get() - velocity);
			return true;
		}
		return false;
	}
	
	public boolean moveRight() {
		this.setOrientation(KeyCode.RIGHT);
		if (x.get() < GameData.RIGHT_BOTTOM_LIMIT && tileIsEmptyAnimated(Game.getAnimatedEntities())) {
			x.set(x.get() + velocity);
			return true;
		}
		return false;
	}

	public boolean moveDown() {
		this.setOrientation(KeyCode.DOWN);
		if (y.get() < GameData.RIGHT_BOTTOM_LIMIT && tileIsEmptyAnimated(Game.getAnimatedEntities())) {
			y.set(y.get() + velocity);
			return true;
		}
		return false;
	}
	
	public boolean moveUp() {
		this.setOrientation(KeyCode.UP);
		if (y.get() > GameData.LEFT_TOP_LIMIT && tileIsEmptyAnimated(Game.getAnimatedEntities())) {
			y.set(y.get() - velocity);
			return true;
		}
		return false;
	}

}
