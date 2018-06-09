package app.modele.entity;

import java.util.ArrayList;

import app.vue.entity.EntityView;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;

public class Bullet extends Entity {

	public Bullet(int x, int y, int orientation) {
		super("bullet", x, y);
		this.orientation = new SimpleIntegerProperty(orientation);
		this.velocity = 4;
	}
	
	public void update() {
		
		if (this.getX().get() <= 0 || this.getY().get() <= 0 || this.getX().get() >= 768 || this.getY().get() >= 768) 
			this.die();
		
		switch (this.orientation.get()) {
		case LEFT :
			this.x.set(this.x.get() - velocity);
			break;
		case UP :
			this.y.set(this.y.get() - velocity);
			break;
		case RIGHT :
			this.x.set(this.x.get() + velocity);
			break;
		case DOWN :
			this.y.set(this.y.get() + velocity);
			break;
		default :
			break;
		}
		
	}
	
	public AnimatedEntity isCollidingWith(ObservableList<AnimatedEntity> entities) {
		
		switch (this.orientation.get()) {
		case LEFT :
			for (int i = 1 ; i < entities.size() ; i++)
				if (entities.get(i).getX().get() == this.getX().get() - 32 && entities.get(i).getY().get() >= this.getY().get() - 32 && entities.get(i).getY().get() <= this.getY().get() + 32)
					return entities.get(i);
			break;
		case UP :
			for (int i = 1 ; i < entities.size() ; i++)
				if (entities.get(i).getY().get() == this.getY().get() - 32 && entities.get(i).getX().get() >= this.getX().get() - 32 && entities.get(i).getX().get() <= this.getX().get() + 32)
					return entities.get(i);
			break;
		case RIGHT :
			for (int i = 1 ; i < entities.size() ; i++)
				if (entities.get(i).getX().get() == this.getX().get() + 32 && entities.get(i).getY().get() >= this.getY().get() - 32 && entities.get(i).getY().get() <= this.getY().get() + 32)
					return entities.get(i);
			break;
		case DOWN :
			for (int i = 1 ; i < entities.size() ; i++)
				if (entities.get(i).getY().get() == this.getY().get() + 32 && entities.get(i).getX().get() >= this.getX().get() - 32 && entities.get(i).getX().get() <= this.getX().get() + 32)
					return entities.get(i);
			break;
		default :
			break;
		}
		
		return null;
	}
	
	public AnimatedEntity isCollidingWithPlayer(ObservableList<AnimatedEntity> entities) {
		
		switch (this.orientation.get()) {
		case LEFT :
			if (entities.get(0).getX().get() == this.getX().get() - 32 && entities.get(0).getY().get() >= this.getY().get() - 32 && entities.get(0).getY().get() <= this.getY().get() + 32)
				return entities.get(0);
			break;
		case UP :
			if (entities.get(0).getY().get() == this.getY().get() - 32 && entities.get(0).getX().get() >= this.getX().get() - 32 && entities.get(0).getX().get() <= this.getX().get() + 32)
				return entities.get(0);
			break;
		case RIGHT :
			if (entities.get(0).getX().get() == this.getX().get() + 32 && entities.get(0).getY().get() >= this.getY().get() - 32 && entities.get(0).getY().get() <= this.getY().get() + 32)
				return entities.get(0);
			break;
		case DOWN :
			if (entities.get(0).getY().get() == this.getY().get() + 32 && entities.get(0).getX().get() >= this.getX().get() - 32 && entities.get(0).getX().get() <= this.getX().get() + 32)
				return entities.get(0);
			break;
		default :
			break;
		}
		
		return null;
	}
	
}
