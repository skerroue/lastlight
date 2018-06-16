package app.modele.entity.animated;

import app.modele.Game;
import app.modele.GameData;
import app.modele.field.Tile;

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
	
	public void update() {
		this.move();
		this.attack();
	}
	
    private void move() {
    	if (Game.playerIsDetected() || this.playerDetection(GameData.ENEMY_RANGE, this)) {
    		
    		Game.setPlayerDetected();
    		
	    	Tile nextTile = Game.getBFS().searchWay(this);
	    	Tile enemyAt = Game.getMap().getNextTile(this.getIndiceY(), this.getIndiceX());
	    	
	    	if (nextTile != null) {
		    	if (nextTile.getI() == enemyAt.getI() && nextTile.getJ() < enemyAt.getJ()) 
		    		this.moveLeft();
		    	if (nextTile.getI() < enemyAt.getI() && nextTile.getJ() == enemyAt.getJ()) 
		    		this.moveUp();
		    	if (nextTile.getI() == enemyAt.getI() && nextTile.getJ() > enemyAt.getJ()) 
		    		this.moveRight();
		    	if (nextTile.getI() > enemyAt.getI() && nextTile.getJ() == enemyAt.getJ()) 
		    		this.moveDown();
	    	}
    	}
    	
    }
    
    private boolean playerDetection(int range, AnimatedEntity e) {
    	if (Game.getPlayer().getX().get() >= e.getX().get() - range && Game.getPlayer().getX().get() <= e.getX().get() + range &&
    		Game.getPlayer().getY().get() >= e.getY().get() - range && Game.getPlayer().getY().get() <= e.getX().get() + range)
    		return true;
    	
    	return false;
    }
}
