package app.modele.weapon;

import app.modele.entity.AnimatedEntity;
import javafx.collections.ObservableList;

public class Pistol extends Weapon {

	public Pistol(int a, int d) {
		super(a, d);
		this.id = "pistol";
	}

	@Override
	public void attack(ObservableList<AnimatedEntity> entities, int orientation, int x, int y) {
		
		switch (orientation) {
		case LEFT :
			for (int i = 1 ; i < entities.size() ; i++) {
	    		if (x <= entities.get(i).getX().get() + 32 && x >= entities.get(i).getX().get() - 32 && 
	    			y >= entities.get(i).getY().get() - TILE_SIZE && y <= entities.get(i).getY().get() + TILE_SIZE) {
	    			entities.get(i).loseHP(this.att.get());
	    		}
			}
			break;
		case UP :
			for (int i = 1 ; i < entities.size() ; i++)
	    		if (y <= entities.get(i).getY().get() && y >= entities.get(i).getY().get() - 32  && 
	    			x >= entities.get(i).getX().get() - TILE_SIZE && x <= entities.get(i).getX().get() + TILE_SIZE)
	    			entities.get(i).loseHP(this.att.get());
			break;
		case RIGHT :
			for (int i = 1 ; i < entities.size() ; i++) {
				if (x >= entities.get(i).getX().get() - 32 && x <= entities.get(i).getX().get() + 32 && 
		    		y >= entities.get(i).getY().get() - TILE_SIZE && y <= entities.get(i).getY().get() + TILE_SIZE)
					entities.get(i).loseHP(this.att.get());
			}
			break;
		case DOWN :
			for (int i = 1 ; i < entities.size() ; i++)
	    		if (y >= entities.get(i).getY().get() && y <= entities.get(i).getY().get() + 32 && 
	    			x >= entities.get(i).getX().get() - TILE_SIZE && x <= entities.get(i).getX().get() + TILE_SIZE)
	    			entities.get(i).loseHP(this.att.get());
			break;
		default :
			break;
			
		}
		
	}

}
