package app.modele.entity;

import javafx.collections.ObservableList;

public class Enemy extends AnimatedEntity {

	public Enemy(int x, int y, int pv, int att, int v) {
		super(x, y, pv, att, v);
	}

	public void update(ObservableList<AnimatedEntity> entities) {
		if (Math.random() < 0.25)
			this.moveDown(entities);
		else if (Math.random() < 0.5) 
			this.moveLeft(entities);
		else if (Math.random() < 0.75)
			this.moveRight(entities);
		else
			this.moveUp(entities);
	}

}
