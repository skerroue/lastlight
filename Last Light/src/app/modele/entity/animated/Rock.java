package app.modele.entity.animated;

import app.modele.GameData; 

public class Rock extends AnimatedEntity {

	public Rock(int x, int y) {
		super(GameData.ENTITY_ROCK, x, y, 1, 0, 4, 1, 1);
		this.isInvicible = true;
	}
	
	public boolean push(int DIRECTION) {
		
		switch (DIRECTION) {
		case GameData.LEFT :
			return this.moveLeft();
		case GameData.UP :
			return this.moveUp();
		case GameData.RIGHT :
			return this.moveRight();
		case GameData.DOWN :
			return this.moveDown();
		default :
			return false;
		}
		
	}

}