package app.modele.weapon;

import app.modele.entity.AnimatedEntity;
import app.modele.entity.Bullet;
import javafx.collections.ObservableList;

public class Lamp extends Weapon{
	
	public Lamp(int a, int d){
		super(a, d);
		this.id = "lamp";
	}

	@Override
	public void attack(ObservableList<AnimatedEntity> entities, int orientation, int x, int y) {
		
		switch (orientation) {
		case LEFT :
			for (AnimatedEntity e : entities)
	    		if (x <= e.getX().get() + 2*(this.distance.get()*TILE_SIZE) && x >= e.getX().get() + this.distance.get()*TILE_SIZE && 
	    			y >= e.getY().get() - TILE_SIZE && y <= e.getY().get() + TILE_SIZE)
	    			e.loseHP(this.att.get());
			break;
		case UP :
			for (AnimatedEntity e : entities)
	    		if (y <= e.getY().get() + 2*(this.distance.get()*TILE_SIZE) && y >= e.getY().get() + this.distance.get()*TILE_SIZE && 
	    			x >= e.getX().get() - TILE_SIZE && x <= e.getX().get() + TILE_SIZE)
	    			e.loseHP(this.att.get());
			break;
		case RIGHT :
			for (AnimatedEntity e : entities) {
				if (x >= e.getX().get() - 2*(this.distance.get()*TILE_SIZE) && x <= e.getX().get() - this.distance.get()*TILE_SIZE && 
		    		y >= e.getY().get() - TILE_SIZE && y <= e.getY().get() + TILE_SIZE)
					e.loseHP(this.att.get());
			}
			break;
		case DOWN :
			for (AnimatedEntity e : entities)
	    		if (y >= e.getY().get() - 2*(this.distance.get()*TILE_SIZE) && y <= e.getY().get() - this.distance.get()*TILE_SIZE && 
	    			x >= e.getX().get() - TILE_SIZE && x <= e.getX().get() + TILE_SIZE)
	    			e.loseHP(this.att.get());
			break;
		default :
			break;
			
		}
		
	}

	@Override
	public void update(ObservableList<AnimatedEntity> entities) {
		// TODO Auto-generated method stub
		
	}
	
}
