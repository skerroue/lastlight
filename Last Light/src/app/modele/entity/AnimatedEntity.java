package app.modele.entity;

import java.util.ArrayList;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;

public abstract class AnimatedEntity extends Entity {
	
	protected int attaque;
	protected IntegerProperty hp;
	
	public AnimatedEntity(int x, int y, int hp, int att, int v) {
		this.x = new SimpleIntegerProperty(x);
		this.y = new SimpleIntegerProperty(y);
		this.hp = new SimpleIntegerProperty(hp);
		this.attaque = att;
		this.velocity = v;
	}
	
	@Override
	public void update(ObservableList<AnimatedEntity> entities) {
		// TODO Auto-generated method stub
		
	}
	
	public void attack(ObservableList<AnimatedEntity> entities) {
		
		switch (this.getOrientation().get()) {
		case LEFT :
			for (AnimatedEntity e : entities)
	    		if (this.getX().get() <= e.getX().get() + 64 && this.getX().get() >= e.getX().get() + 32 && 
	    			this.getY().get() >= e.getY().get() - 31 && this.getY().get() <= e.getY().get() + 31)
	    			e.loseHP(1);
			break;
		case UP :
			for (AnimatedEntity e : entities)
	    		if (this.getY().get() <= e.getY().get() + 64 && this.getY().get() >= e.getY().get() + 32 && 
	    			this.getX().get() >= e.getX().get() - 31 && this.getX().get() <= e.getX().get() + 31)
	    			e.loseHP(1);
			break;
		case RIGHT :
			for (AnimatedEntity e : entities) {
				if (this.getX().get() >= e.getX().get() - 64 && this.getX().get() <= e.getX().get() - 32 && 
		    		this.getY().get() >= e.getY().get() - 31 && this.getY().get() <= e.getY().get() + 31)
					e.loseHP(1);
			}
			break;
		case DOWN :
			for (AnimatedEntity e : entities)
	    		if (this.getY().get() >= e.getY().get() - 64 && this.getY().get() <= e.getY().get() - 32 && 
	    			this.getX().get() >= e.getX().get() - 31 && this.getX().get() <= e.getX().get() + 31)
	    			e.loseHP(1);
			break;
		default :
			break;
			
		}
		
	}
	
	public IntegerProperty getOrientation() {
		return this.orientation;
	}
	
	public int getFrame() {
		return frame;
	}
	
	public void incrementeFrame() {
		if (this.frame > 18)
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
		this.hp.set(this.hp.get() - a);
	}

}
