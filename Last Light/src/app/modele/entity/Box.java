package app.modele.entity;

import javafx.collections.ObservableList;

public class Box extends AnimatedEntity {

	public Box(int x, int y) {
		super("box", x, y, 1, 0, 4, 1, 1);
		System.out.println("box");
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