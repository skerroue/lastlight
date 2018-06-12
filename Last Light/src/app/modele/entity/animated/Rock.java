package app.modele.entity.animated;

import app.modele.Game;
import app.modele.GameData;
import app.modele.entity.inanimated.InanimatedEntity;
import javafx.collections.ObservableList;

public class Rock extends AnimatedEntity {

	public Rock(int x, int y) {
		super(GameData.ENTITY_ROCK, x, y, 1, 0, 4, 1, 1);
		this.isInvicible = true;
	}
	
	public int push(int DIRECTION, ObservableList<AnimatedEntity> entities, ObservableList<InanimatedEntity> inanimatedEntities, int velocity) {
		
		switch (DIRECTION) {
		case LEFT :
			if (velocity < 32)
				return this.moveLeft(entities, inanimatedEntities, velocity);
			return (int) (Game.getPlayer().getX().get() - this.getX().get() + 31);
		case UP :
			if (velocity < 32)
				return this.moveUp(entities, inanimatedEntities, velocity);
			return (int) (Game.getPlayer().getY().get() - this.getY().get() + 31);
		case RIGHT :
			if (velocity < 32)
				return this.moveRight(entities, inanimatedEntities, velocity);
			return (int) (this.getX().get() - Game.getPlayer().getX().get() + 32);
		case DOWN :
			if (velocity < 32)
				return this.moveDown(entities, inanimatedEntities, velocity);
			return (int) (this.getY().get() - Game.getPlayer().getY().get() + 32);
		default :
			return 0;
		}
		
	}

}