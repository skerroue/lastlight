package app.modele.entity.animated;

import app.modele.Game;
import app.modele.GameData;
import app.modele.BFS.BFS;
import app.modele.entity.inanimated.InanimatedEntity;
import app.modele.field.Tile;
import javafx.collections.ObservableList;

public class Enemy extends AnimatedEntity {

	public Enemy(String id, int x, int y, int pv, int att, int v, int nb, int fmax) {
		super(id, x, y, pv, att, v, nb, fmax);
	}
	
	public void attack(AnimatedEntity player) {
		
		AnimatedEntity p = player;
		
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
	
	public void update(ObservableList<AnimatedEntity> animatedEntities, ObservableList<InanimatedEntity> inanimatedEntities, BFS bfs) {
		this.move(animatedEntities, inanimatedEntities, bfs);
		this.attack(animatedEntities.get(0));
	}
	
    private void move(ObservableList<AnimatedEntity> animatedEntities, ObservableList<InanimatedEntity> inanimatedEntities, BFS bfs) {
    	if (Game.playerIsDetected() || this.playerDetection(GameData.ENEMY_RANGE, this, animatedEntities.get(0))) {
    		
    		Game.setPlayerDetected();
    		
	    	Tile nextTile = bfs.searchWay(this);
	    	Tile enemyAt = Game.getMap().getNextTile(this.getIndiceY(), this.getIndiceX());
	    	
	    	if (nextTile != null) {
		    	if (nextTile.getI() == enemyAt.getI() && nextTile.getJ() < enemyAt.getJ()) 
		    		this.moveLeft(animatedEntities, inanimatedEntities);
		    	if (nextTile.getI() < enemyAt.getI() && nextTile.getJ() == enemyAt.getJ()) 
		    		this.moveUp(animatedEntities, inanimatedEntities);
		    	if (nextTile.getI() == enemyAt.getI() && nextTile.getJ() > enemyAt.getJ()) 
		    		this.moveRight(animatedEntities, inanimatedEntities);
		    	if (nextTile.getI() > enemyAt.getI() && nextTile.getJ() == enemyAt.getJ()) 
		    		this.moveDown(animatedEntities, inanimatedEntities);
	    	}
    	}
    	
    }
    
    private boolean playerDetection(int range, AnimatedEntity e, AnimatedEntity player) {
    	if (player.getX().get() >= e.getX().get() - range && player.getX().get() <= e.getX().get() + range &&
    		player.getY().get() >= e.getY().get() - range && player.getY().get() <= e.getX().get() + range)
    		return true;
    	
    	return false;
    }
}
