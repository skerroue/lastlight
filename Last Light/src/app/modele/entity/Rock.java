package app.modele.entity;

import javafx.collections.ObservableList;

public class Rock extends AnimatedEntity {

	public Rock(int x, int y) {
		super("rock", x, y, 1, 0, 4, 1, 1);
	}
	
	public boolean push(int DIRECTION, ObservableList<AnimatedEntity> entities) {
		
		switch (DIRECTION) {
		case LEFT :
			return this.moveLeft(entities);
		case UP :
			return this.moveUp(entities);
		case RIGHT :
			return this.moveRight(entities);
		case DOWN :
			return this.moveDown(entities);
		default :
			return false;
		}
		
	}

}