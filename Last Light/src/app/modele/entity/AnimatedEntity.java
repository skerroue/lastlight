package app.modele.entity;

import java.util.ArrayList;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;

public abstract class AnimatedEntity extends Entity {
	
	protected int attaque;
	
	public AnimatedEntity(int x, int y, int pv, int att, int v) {
		this.x = new SimpleIntegerProperty(x);
		this.y = new SimpleIntegerProperty(y);
		this.pv = new SimpleIntegerProperty(pv);
		this.attaque = att;
		this.velocity = v;
	}

	public void update(ArrayList<Entity> entities) {
		// TODO Auto-generated method stub
		
	}
	
	public void attack(ObservableList<Entity> entities) {
		
		switch (this.getOrientation().get()) {
		case LEFT :
			for (Entity e : entities)
	    		if (this.getX().get() <= e.getX().get() + 64 && this.getX().get() >= e.getX().get() + 32 && 
	    			this.getY().get() >= e.getY().get() - 31 && this.getY().get() <= e.getY().get() + 31)
	    			e.loosePv(1);
			break;
		case UP :
			for (Entity e : entities)
	    		if (this.getY().get() <= e.getY().get() + 64 && this.getY().get() >= e.getY().get() + 32 && 
	    			this.getX().get() >= e.getX().get() - 31 && this.getX().get() <= e.getX().get() + 31)
	    			e.loosePv(1);
			break;
		case RIGHT :
			for (Entity e : entities) {
				if (this.getX().get() >= e.getX().get() - 64 && this.getX().get() <= e.getX().get() - 32 && 
		    		this.getY().get() >= e.getY().get() - 31 && this.getY().get() <= e.getY().get() + 31)
					e.loosePv(1);
			}
			break;
		case DOWN :
			for (Entity e : entities)
	    		if (this.getY().get() >= e.getY().get() - 64 && this.getY().get() <= e.getY().get() - 32 && 
	    			this.getX().get() >= e.getX().get() - 31 && this.getX().get() <= e.getX().get() + 31)
	    			e.loosePv(1);
			break;
		default :
			break;
			
		}
		
	}

}
