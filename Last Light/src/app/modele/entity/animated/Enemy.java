package app.modele.entity.animated;

import app.modele.Game;
import app.modele.GameData;

public class Enemy extends AnimatedEntity {

	public Enemy(String id, int x, int y, int pv, int att, int v, int nb, int fmax) {
		super(id, x, y, pv, att, v, nb, fmax);
	}
	
	public void attack() {
		
		Player p = Game.getPlayer();
		
		switch (this.getOrientation().get()) {
		case GameData.LEFT :
    		if (this.getX().get() == p.getX().get() + 32 && 
    			this.getY().get() >= p.getY().get() - 31 && this.getY().get() <= p.getY().get() + 31)
	    		p.loseHP(this.attaque);
			break;
		case GameData.UP :
    		if (this.getY().get() == p.getY().get() + 32 && 
    			this.getX().get() >= p.getX().get() - 31 && this.getX().get() <= p.getX().get() + 31)
    			p.loseHP(this.attaque);
			break;
		case GameData.RIGHT :
    		if (this.getX().get() == p.getX().get() - 32 && 
    			this.getY().get() >= p.getY().get() - 31 && this.getY().get() <= p.getY().get() + 31)
	    		p.loseHP(this.attaque);
			break;
		case GameData.DOWN :
    		if (this.getY().get() == p.getY().get() - 32 && 
    			this.getX().get() >= p.getX().get() - 31 && this.getX().get() <= p.getX().get() + 31)
	    		p.loseHP(this.attaque);
			break;
		default :
			break;
		}
	}

}
