package app.modele.entity.animated;

import app.modele.GameData;
import app.modele.entity.inanimated.InanimatedEntity;
import javafx.collections.ObservableList; 

public class Rock extends AnimatedEntity {

	public Rock(int x, int y) {
		super(GameData.ENTITY_ROCK, x, y, 1, 0, GameData.PLAYER_SPEED, 1, 1);
		this.isInvicible = true;
	}
	
	public boolean push(ObservableList<AnimatedEntity> animatedEntities, ObservableList<InanimatedEntity> inanimatedEntities, int direction) {
		
		switch (direction) {
		case GameData.LEFT :
			return this.moveLeft(animatedEntities, inanimatedEntities);
		case GameData.UP :
			return this.moveUp(animatedEntities, inanimatedEntities);
		case GameData.RIGHT :
			return this.moveRight(animatedEntities, inanimatedEntities);
		case GameData.DOWN :
			return this.moveDown(animatedEntities, inanimatedEntities);
		default :
			return false;
		}
		
	}

}