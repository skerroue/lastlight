package app.modele.entity.animated;

import app.modele.entity.inanimated.InanimatedEntity;
import javafx.collections.ObservableList;

public class Rock extends AnimatedEntity {

	public Rock(int x, int y) {
		super("rock", x, y, 1, 0, 4, 1, 1);
		this.isInvicible = true;
	}
	
	public boolean push(int DIRECTION, ObservableList<AnimatedEntity> entities, ObservableList<InanimatedEntity> inanimatedEntities) {
		
		switch (DIRECTION) {
		case LEFT :
			return this.moveLeft(entities, inanimatedEntities);
		case UP :
			return this.moveUp(entities, inanimatedEntities);
		case RIGHT :
			return this.moveRight(entities, inanimatedEntities);
		case DOWN :
			return this.moveDown(entities, inanimatedEntities);
		default :
			return false;
		}
		
	}

}