package app.modele.weapon;

import app.modele.Game;
import app.modele.GameData;
import app.modele.entity.animated.AnimatedEntity;
import app.modele.entity.animated.Bullet;
import javafx.collections.ObservableList;

public class Lamp extends Weapon {
	
	public Lamp(int a, int d){
		super(GameData.ENTITY_LAMP , a, d);
		this.id = "lamp";
	}

	@Override
	public void attack(int orientation, int x, int y) {
		
		switch (orientation) {
		case LEFT :
			for (AnimatedEntity e : Game.getAnimatedEntities())
	    		if (x <= e.getX().get() + 2*(this.distance.get()*TILE_SIZE) && x >= e.getX().get() + this.distance.get()*TILE_SIZE && 
	    			y >= e.getY().get() - TILE_SIZE && y <= e.getY().get() + TILE_SIZE)
	    			e.loseHP(this.att.get());
			break;
		case UP :
			for (AnimatedEntity e : Game.getAnimatedEntities())
	    		if (y <= e.getY().get() + 2*(this.distance.get()*TILE_SIZE) && y >= e.getY().get() + this.distance.get()*TILE_SIZE && 
	    			x >= e.getX().get() - TILE_SIZE && x <= e.getX().get() + TILE_SIZE)
	    			e.loseHP(this.att.get());
			break;
		case RIGHT :
			for (AnimatedEntity e : Game.getAnimatedEntities()) {
				if (x >= e.getX().get() - 2*(this.distance.get()*TILE_SIZE) && x <= e.getX().get() - this.distance.get()*TILE_SIZE && 
		    		y >= e.getY().get() - TILE_SIZE && y <= e.getY().get() + TILE_SIZE)
					e.loseHP(this.att.get());
			}
			break;
		case DOWN :
			for (AnimatedEntity e : Game.getAnimatedEntities())
	    		if (y >= e.getY().get() - 2*(this.distance.get()*TILE_SIZE) && y <= e.getY().get() - this.distance.get()*TILE_SIZE && 
	    			x >= e.getX().get() - TILE_SIZE && x <= e.getX().get() + TILE_SIZE)
	    			e.loseHP(this.att.get());
			break;
		default :
			break;
			
		}
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void killAllBullets() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ObservableList<Bullet> getBullets() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void reload() {
		// TODO Auto-generated method stub
		
	}
	
}
