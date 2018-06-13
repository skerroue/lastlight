package app.modele.entity.animated;

import app.modele.Game;
import app.modele.entity.Entity;
import app.modele.entity.inanimated.InanimatedEntity;
import javafx.animation.Animation;
import javafx.animation.PauseTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

public abstract class AnimatedEntity extends Entity {
	
	protected int attaque;
	protected IntegerProperty hp;
	protected BooleanProperty isAttacking;
	
	protected int nbFrame;
	protected int frameMax;
	
	private Animation invicibilityFrame;
	protected boolean isInvicible;
	
	public AnimatedEntity(String id, int x, int y, int hp, int att, int v, int nb, int fmax) {
		super(id, x, y);
		this.hp = new SimpleIntegerProperty(hp);
		this.attaque = att;
		this.velocity = v;
		this.nbFrame = nb;
		this.frameMax = fmax;
		
		this.isAttacking = new SimpleBooleanProperty(false);
		this.isInvicible = false;
		
		this.invicibilityFrame = new PauseTransition(Duration.seconds(3));
		this.invicibilityFrame.setOnFinished(e -> this.setInvicible(false) );
	}
	
	public int moveLeft(ObservableList<AnimatedEntity> entities, ObservableList<InanimatedEntity> inanimatedEntities, int velocity) {
		this.setOrientation(KeyCode.LEFT);
		int space = canMove(entities, inanimatedEntities, velocity);
		if (space == velocity)
			x.set(x.get() - velocity);
		return space;
	}
	
	public int moveRight(ObservableList<AnimatedEntity> entities, ObservableList<InanimatedEntity> inanimatedEntities, int velocity) {
		this.setOrientation(KeyCode.RIGHT);
		int space = canMove(entities, inanimatedEntities, velocity);
		if (space == velocity)
			x.set(x.get() + velocity);
		return space;
	}

	public int moveDown(ObservableList<AnimatedEntity> entities, ObservableList<InanimatedEntity> inanimatedEntities, int velocity) {
		this.setOrientation(KeyCode.DOWN);
		int space = canMove(entities, inanimatedEntities, velocity);
		if (space == velocity)
			y.set(y.get() + velocity);
		return space;
	}
	
	public int moveUp(ObservableList<AnimatedEntity> entities, ObservableList<InanimatedEntity> inanimatedEntities, int velocity) {
		this.setOrientation(KeyCode.UP);
		int space = canMove(entities, inanimatedEntities, velocity);
		if (space == velocity)
			y.set(y.get() - velocity);
		return space;
	}
	
	public int canMove(ObservableList<AnimatedEntity> entities, ObservableList<InanimatedEntity> inanimatedEntities, int velocity) {
		int canMove = 0;
		int emptyTile = velocity;
		
		switch (this.orientation.getValue()) {
		case LEFT :
			emptyTile = tileIsEmpty(entities, inanimatedEntities, LEFT, velocity);
			if (x.get() % 32 < velocity) {
				if (y.get() % 32 == 0) {
					if (Game.getMap().getNextTile(getIndiceY(), getIndiceX() - 1).isCrossable() && emptyTile == velocity)
						canMove = velocity;
					else canMove = (int) x.get() % 32;
				} else {
					if (Game.getMap().getNextTile(getIndiceY() + 1, getIndiceX() - 1).isCrossable() && 
							Game.getMap().getNextTile(getIndiceY(), getIndiceX() - 1).isCrossable() && emptyTile == velocity)
						canMove = velocity;
					else canMove = (int) x.get() % 32;
				}
			} else
				canMove = velocity;
			break;
			
		case UP :
			emptyTile = tileIsEmpty(entities, inanimatedEntities, UP, velocity);
			if (y.get() % 32 < velocity) {
				if (x.get() % 32 == 0) {
					if (Game.getMap().getNextTile(getIndiceY() - 1, getIndiceX()).isCrossable() && emptyTile == velocity)
						canMove = velocity;
					else canMove = (int) y.get() % 32;
				} else {
					if (Game.getMap().getNextTile(getIndiceY() - 1, getIndiceX() + 1).isCrossable() && 
							Game.getMap().getNextTile(getIndiceY() - 1, getIndiceX()).isCrossable() && emptyTile == velocity)
						canMove = velocity;
					else canMove = (int) y.get() % 32;
				}
			} else
				canMove = velocity;
			break;
			
		case DOWN :
			emptyTile = tileIsEmpty(entities, inanimatedEntities, DOWN, velocity);
			if (y.get() % 32 < velocity) {
				if (velocity >= 32 && y.get() % 32 != 0) {
					if (x.get() % 32 == 0) {
						if (Game.getMap().getNextTile(getIndiceY() + 2, getIndiceX()).isCrossable() && emptyTile == velocity)
							canMove = velocity;
						else canMove = (32 - ((int) y.get() % 32)) % 32;
					} else {
						if (Game.getMap().getNextTile(getIndiceY() + 2, getIndiceX() + 1).isCrossable() && 
								Game.getMap().getNextTile(getIndiceY() + 2, getIndiceX()).isCrossable() && emptyTile == velocity)
							canMove = velocity;
						else canMove = (32 - ((int) y.get() % 32)) % 32;
					}
				}
				else if (x.get() % 32 == 0) {
					if (Game.getMap().getNextTile(getIndiceY() + 1, getIndiceX()).isCrossable() && emptyTile == velocity)
						canMove = velocity;
					else canMove = (32 - ((int) y.get() % 32)) % 32;
				} else {
					if (Game.getMap().getNextTile(getIndiceY() + 1, getIndiceX() + 1).isCrossable() && 
							Game.getMap().getNextTile(getIndiceY() + 1, getIndiceX()).isCrossable() && emptyTile == velocity)
						canMove = velocity;
					else canMove = (32 - ((int) y.get() % 32)) % 32;
				}
			} else
				canMove = velocity;
			break;
			
		case RIGHT :
			emptyTile = tileIsEmpty(entities, inanimatedEntities, RIGHT, velocity);
			if (x.get() % 32 < velocity) {
				if (velocity >= 32 && x.get() % 32 != 0) {
					if (y.get() % 32 == 0) {
						if (Game.getMap().getNextTile(getIndiceY(), getIndiceX() + 2).isCrossable() && emptyTile == velocity)
							canMove = velocity;
						else canMove = (32 - ((int) x.get() % 32)) % 32;
					} else {
						if (Game.getMap().getNextTile(getIndiceY() + 1, getIndiceX() + 2).isCrossable() && 
								Game.getMap().getNextTile(getIndiceY(), getIndiceX() + 2).isCrossable() && emptyTile == velocity)
							canMove = velocity;
						else canMove = (32 - ((int) x.get() % 32)) % 32;
					}
				}
				else if (y.get() % 32 == 0) {
					if (Game.getMap().getNextTile(getIndiceY(), getIndiceX() + 1).isCrossable() && emptyTile == velocity)
						canMove = velocity;
					else canMove = (32 - ((int) x.get() % 32)) % 32;
				} else {
					if (Game.getMap().getNextTile(getIndiceY() + 1, getIndiceX() + 1).isCrossable() && 
							Game.getMap().getNextTile(getIndiceY(), getIndiceX() + 1).isCrossable() && emptyTile == velocity)
						canMove = velocity;
					else canMove = (32 - ((int) x.get() % 32)) % 32;
				}
			} else
				canMove = velocity;
			break;
			
		default :
			break;
		}
		
		return canMove;
	}
	
	public void setOrientation(KeyCode k) {
		switch (k) {
		case LEFT :
			this.orientation.set(LEFT);
			break;
		case UP :
			this.orientation.set(UP);
			break;
		case RIGHT :
			this.orientation.set(RIGHT);
			break;
		case DOWN :
			this.orientation.set(DOWN);
			break;
		default :
			break;
		}
 	}
	
	public void setOrientation(int n) {
		this.orientation.set(n);
	}
	
	public int tileIsEmpty(ObservableList<AnimatedEntity> entities, ObservableList<InanimatedEntity> inanimatedEntities, int direction, int velocity) {
		
		int emptyTile = velocity;
    	
		switch (direction) {
		case LEFT :
			for (AnimatedEntity e : entities)
	    		if (this.getX().get() - 32 == e.getX().get() &&
	    			this.getY().get() >= e.getY().get() - 31 && this.getY().get() <= e.getY().get() + 31) {
//	    			emptyTile = e.push(LEFT, entities, inanimatedEntities, velocity);
//	    			if (emptyTile != velocity)
//	    				return emptyTile;
	    			return e.push(LEFT, entities, inanimatedEntities, velocity);
	    		}
			for (InanimatedEntity e : inanimatedEntities)
	    		if (this.getX().get() - 32 == e.getX().get() && 
	    			this.getY().get() >= e.getY().get() - 31 && this.getY().get() <= e.getY().get() + 31) {
	    			emptyTile = (int) (this.getX().get() - e.getX().get() + 31);
	    			if (emptyTile != velocity)
	    				return emptyTile;
	    		}
			break;
		case UP :
			for (AnimatedEntity e : entities)
	    		if (this.getY().get() - 32 == e.getY().get() && 
	    			this.getX().get() >= e.getX().get() - 31 && this.getX().get() <= e.getX().get() + 31) {
//	    			emptyTile = e.push(UP, entities, inanimatedEntities, velocity);
//	    			if (emptyTile != velocity)
//	    				return emptyTile;
	    			return e.push(UP, entities, inanimatedEntities, velocity);
	    		}
			for (InanimatedEntity e : inanimatedEntities)
	    		if (this.getY().get() - 32 == e.getY().get() && 
	    			this.getX().get() >= e.getX().get() - 31 && this.getX().get() <= e.getX().get() + 31) {
	    			emptyTile = (int) (this.getX().get() - e.getX().get() + 31);
	    			if (emptyTile != velocity)
	    				return emptyTile;
	    		}
			break;
		case RIGHT :
			for (AnimatedEntity e : entities) {
	    		if (this.getX().get() + 32 == e.getX().get() && 
	    			this.getY().get() >= e.getY().get() - 31 && this.getY().get() <= e.getY().get() + 31) {
//	    			emptyTile = e.push(RIGHT, entities, inanimatedEntities, velocity);
//	    			if (emptyTile != velocity)
//	    				return emptyTile;
	    			return e.push(RIGHT, entities, inanimatedEntities, velocity);
	    		}
			}
			for (InanimatedEntity e : inanimatedEntities)
	    		if (this.getX().get() + 32 == e.getX().get() && 
	    			this.getY().get() >= e.getY().get() - 31 && this.getY().get() <= e.getY().get() + 31) {
	    			emptyTile = (int) (e.getY().get() - this.getY().get() + 31);
	    			if (emptyTile != velocity)
	    				return emptyTile;
	    		}
			break;
		case DOWN :
			for (AnimatedEntity e : entities)
	    		if (this.getY().get() + 32 == e.getY().get() && 
	    			this.getX().get() >= e.getX().get() - 31 && this.getX().get() <= e.getX().get() + 31) {
//	    			emptyTile = e.push(DOWN, entities, inanimatedEntities, velocity);
//	    			if (emptyTile != velocity)
//	    				return emptyTile;
	    			return e.push(DOWN, entities, inanimatedEntities, velocity);
	    		}
			for (InanimatedEntity e : inanimatedEntities)
	    		if (this.getY().get() + 32 == e.getY().get() && 
	    			this.getX().get() >= e.getX().get() - 31 && this.getX().get() <= e.getX().get() + 31) {
	    			emptyTile = (int) (e.getX().get() - this.getX().get() + 31);
	    			if (emptyTile != velocity)
	    				return emptyTile;
	    		}
			break;
		default :
			break;
		}
		
    	return emptyTile;
	}
	
	public void attack(ObservableList<AnimatedEntity> entities) {
		return;
	}
	
	public int push(int DIRECTION, ObservableList<AnimatedEntity> entities, ObservableList<InanimatedEntity> inanimatedEntities, int velocity) {
		return 0;
	}
	
	public boolean interact() {
		return false;
	}
	
	public String getDialog() {
		return null;
	}
	
	public ObservableList<Bullet> getBullets() {
		return null;
	}
	
	public void resetIsAttacking() {
		this.isAttacking.set(false);
	}
	
	public BooleanProperty getIsAttacking() {
		return this.isAttacking;
	}
	
	public void setInvicible(boolean b) {
		this.isInvicible = b;
	}
	
	public boolean isInvicible() {
		return this.isInvicible;
	}
	
	public String getId() {
		return this.id;
	}
	
	public int getFrameMax() {
		return this.frameMax;
	}
	
	public int getNbFrame() {
		return this.nbFrame;
	}
	
	public int getFrame() {
		return frame;
	}
	
	public void incrementeFrame() {
		if (this.frame > this.frameMax-1)
			this.frame = 0;
		else 
			this.frame++;
	}
	
	public void resetFrame() {
		this.frame = 0;
	}
	
	public IntegerProperty getHP() {
		return this.hp;
	}
	
	public void loseHP(int a) {
		if (!this.isInvicible) {
			this.hp.set(this.hp.get() - a);
			if (this.hp.get() == 0)
				this.die();
			this.isInvicible = true;
			this.invicibilityFrame.play();
		}
	}

}
