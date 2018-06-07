package app.modele.entity;

import javafx.collections.ObservableList;

public class Rock extends AnimatedEntity {

	public Rock(int x, int y) {
		super("rock", x, y, 1, 0, 4, 1, 1);
		System.out.println("rock");
	}
	
	public boolean interact(int DIRECTION, ObservableList<AnimatedEntity> entities) {
		
		switch (DIRECTION) {
		case LEFT :
			this.moveLeft(entities);
			return true;
		case UP :
			this.moveUp(entities);
			return true;
		case RIGHT :
			this.moveRight(entities);
			return true;
		case DOWN :
			this.moveDown(entities);
			return true;
		default :
			return false;
		}
		
	}

}