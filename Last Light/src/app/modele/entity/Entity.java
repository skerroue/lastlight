package app.modele.entity;

import app.modele.Game;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.scene.input.KeyCode;

public abstract class Entity {
	
	final static int LEFT = 0;
	final static int UP = 1;
	final static int RIGHT = 2;
	final static int DOWN = 3;
	
	final static int LEFT_TOP_LIMIT = 31;
	final static int RIGHT_BOTTOM_LIMIT = 768;
	
	protected IntegerProperty x;
	protected IntegerProperty y;
	protected IntegerProperty orientation;
	protected int frame;
	protected int velocity;
	protected BooleanProperty isDead;
	
	public Entity() {
		this.orientation = new SimpleIntegerProperty(0);
		this.frame = 0;
		this.isDead = new SimpleBooleanProperty(false);
	}
	
	public abstract void update(ObservableList<AnimatedEntity> entities);
	
	public IntegerProperty getX() {
		return x;
	}
	
	public IntegerProperty getY() {
		return y;
	}
	
	public int getIndiceX() {
		return x.get()/32;
	}

	public int getIndiceY() {
		return y.get()/32;
	}
	
	public void setX(int x) {
		this.x.set(x);
	}
	
	public void setY(int y) {
		this.y.set(y);
	}
	
	public void die() {
		this.isDead.set(true);
	}
	
	public BooleanProperty getIsDead() {
		return this.isDead;
	}
	
	public void moveLeft(ObservableList<AnimatedEntity> entities) {
		this.setOrientation(KeyCode.LEFT);
		if (canMove(entities))
			x.set(x.get() - velocity);
	}
	
	public void moveRight(ObservableList<AnimatedEntity> entities) {
		this.setOrientation(KeyCode.RIGHT);
		if (canMove(entities))
			x.set(x.get() + velocity);
	}

	public void moveDown(ObservableList<AnimatedEntity> entities) {
		this.setOrientation(KeyCode.DOWN);
		if (canMove(entities))
			y.set(y.get() + velocity);
	}
	
	public void moveUp(ObservableList<AnimatedEntity> entities) {
		this.setOrientation(KeyCode.UP);
		if (canMove(entities))
			y.set(y.get() - velocity);
	}
	
	public boolean canMove(ObservableList<AnimatedEntity> entities) {
		boolean canMove = false;
		boolean emptyTile = true;
		
		switch (this.orientation.getValue()) {
		case LEFT :
			emptyTile = tileIsEmpty(entities, LEFT);
			if (x.get() % 32 != 0 && emptyTile)
				canMove = true;
			else if (x.get() % 32 == 0 && y.get() % 32 != 0) {
				if (y.get() > LEFT_TOP_LIMIT) {
					if (Game.getMap().getNextTile(this.getIndiceY(), this.getIndiceX()-1).isCrossable() &&
						Game.getMap().getNextTile(this.getIndiceY()+1, this.getIndiceX()-1).isCrossable() && emptyTile)
						canMove = true;
				}
				else {
					if (Game.getMap().getNextTile(this.getIndiceY(), this.getIndiceX()-1).isCrossable() &&
						Game.getMap().getNextTile(this.getIndiceY()-1, this.getIndiceX()-1).isCrossable() && emptyTile)
						canMove = true;
				}
			}
			else
				if (x.get() > LEFT_TOP_LIMIT && Game.getMap().getNextTile(this.getIndiceY(), this.getIndiceX()-1).isCrossable() && emptyTile)
					canMove = true;
			break;
		case UP :
			emptyTile = tileIsEmpty(entities, UP);
			if (y.get() % 32 != 0 && emptyTile)
				canMove = true;
			else if (y.get() % 32 == 0 && x.get() % 32 != 0 && y.get() > 0) {
				if (x.get() < RIGHT_BOTTOM_LIMIT) {
					if (Game.getMap().getNextTile(this.getIndiceY()-1, this.getIndiceX()).isCrossable() && 
						Game.getMap().getNextTile(this.getIndiceY()-1, this.getIndiceX()+1).isCrossable() && emptyTile)
						canMove = true;
				}
				else {
					if (Game.getMap().getNextTile(this.getIndiceY()-1, this.getIndiceX()).isCrossable() && 
					   	Game.getMap().getNextTile(this.getIndiceY()-1, this.getIndiceX()-1).isCrossable() && emptyTile)
						canMove = true;
				}
			}
			else 
				if (y.get() > LEFT_TOP_LIMIT && Game.getMap().getNextTile(this.getIndiceY()-1, this.getIndiceX()).isCrossable() && emptyTile)
					canMove = true;
			break;
		case DOWN :
			emptyTile = tileIsEmpty(entities, DOWN);
			if (y.get() % 32 != 0 && emptyTile) 
				canMove = true;
			else if (y.get() % 32 == 0 && x.get() % 32 != 0) {
				if (x.get() < RIGHT_BOTTOM_LIMIT) {
					if (Game.getMap().getNextTile(this.getIndiceY()+1, this.getIndiceX()).isCrossable() && 
						Game.getMap().getNextTile(this.getIndiceY()+1, this.getIndiceX()+1).isCrossable() && emptyTile)
						canMove = true;
				}
				else {
					if (Game.getMap().getNextTile(this.getIndiceY()+1, this.getIndiceX()).isCrossable() && 
						Game.getMap().getNextTile(this.getIndiceY()+1, this.getIndiceX()-1).isCrossable() && emptyTile)
						canMove = true;
				}
			}
			else 
				if (y.get() < RIGHT_BOTTOM_LIMIT && Game.getMap().getNextTile(this.getIndiceY()+1, this.getIndiceX()).isCrossable() && emptyTile)
					canMove = true;
			break;
		case RIGHT :
			emptyTile = tileIsEmpty(entities, RIGHT);
			if (x.get() % 32 != 0 && emptyTile)
				canMove = true;
			else if (x.get() % 32 == 0 && y.get() % 32 != 0) {
				if (y.get() > LEFT_TOP_LIMIT) {
					if (Game.getMap().getNextTile(this.getIndiceY(), this.getIndiceX()+1).isCrossable() &&
						Game.getMap().getNextTile(this.getIndiceY()+1, this.getIndiceX()+1).isCrossable() && emptyTile)
						canMove = true;
				}
				else {
					if (Game.getMap().getNextTile(this.getIndiceY(), this.getIndiceX()+1).isCrossable() &&
						Game.getMap().getNextTile(this.getIndiceY()-1, this.getIndiceX()+1).isCrossable() && emptyTile)
						canMove = true;
				}
			}
			else
				if (x.get() < RIGHT_BOTTOM_LIMIT && Game.getMap().getNextTile(this.getIndiceY(), this.getIndiceX()+1).isCrossable() && emptyTile)
					canMove = true;
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
	
	public boolean tileIsEmpty(ObservableList<AnimatedEntity> entities, int DIRECTION) {
    	
		switch (DIRECTION) {
		case LEFT :
			for (Entity e : entities)
	    		if (this.getX().get() == e.getX().get() + 32 && 
	    			this.getY().get() >= e.getY().get() - 31 && this.getY().get() <= e.getY().get() + 31)
	    			return false;
			break;
		case UP :
			for (Entity e : entities)
	    		if (this.getY().get() == e.getY().get() + 32 && 
	    			this.getX().get() >= e.getX().get() - 31 && this.getX().get() <= e.getX().get() + 31)
	    			return false;
			break;
		case RIGHT :
			for (Entity e : entities)
	    		if (this.getX().get() == e.getX().get() - 32 && 
	    			this.getY().get() >= e.getY().get() - 31 && this.getY().get() <= e.getY().get() + 31)
	    			return false;
			break;
		case DOWN :
			for (Entity e : entities)
	    		if (this.getY().get() == e.getY().get() - 32 && 
	    			this.getX().get() >= e.getX().get() - 31 && this.getX().get() <= e.getX().get() + 31)
	    			return false;
			break;
		default :
			break;
		}
		
    	return true;
	}

}
