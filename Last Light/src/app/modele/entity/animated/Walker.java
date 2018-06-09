package app.modele.entity.animated;

import javafx.collections.ObservableList;

public class Walker extends Enemy {

	public Walker(int x, int y, int pv, int att, int v, int nb, int fmax) {
		super("walker", x, y, pv, att, v, nb, fmax);
	}
	
	public void attack(ObservableList<AnimatedEntity> entities) {
		
		AnimatedEntity e = entities.get(0);
		
		switch (this.getOrientation().get()) {
		case LEFT :
    		if (this.getX().get() == e.getX().get() + 32 && 
    			this.getY().get() >= e.getY().get() - 31 && this.getY().get() <= e.getY().get() + 31)
	    		e.loseHP(this.attaque);
			break;
		case UP :
    		if (this.getY().get() == e.getY().get() + 32 && 
    			this.getX().get() >= e.getX().get() - 31 && this.getX().get() <= e.getX().get() + 31)
    			e.loseHP(this.attaque);
			break;
		case RIGHT :
    		if (this.getX().get() == e.getX().get() - 32 && 
    			this.getY().get() >= e.getY().get() - 31 && this.getY().get() <= e.getY().get() + 31)
	    		e.loseHP(this.attaque);
			break;
		case DOWN :
    		if (this.getY().get() == e.getY().get() - 32 && 
    			this.getX().get() >= e.getX().get() - 31 && this.getX().get() <= e.getX().get() + 31)
	    		e.loseHP(this.attaque);
			break;
		default :
			break;
		}
	}

}
