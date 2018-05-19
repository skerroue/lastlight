package app.modele.entity;

import app.modele.field.Field;

public class Ennemy extends AnimatedEntity {

	public Ennemy(int x, int y, int pv, int att, int v) {
		super(x, y, pv, att, v);
	}
	
	public void update() {
		if (Math.random() < 0.25)
			this.moveDown();
		else if (Math.random() < 0.5)
			this.moveLeft();
		else if (Math.random() < 0.75)
			this.moveRight();
		else
			this.moveUp();
	}

}
