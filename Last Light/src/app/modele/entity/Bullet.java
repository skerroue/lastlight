package app.modele.entity;

import java.util.ArrayList;

import app.vue.entity.EntityView;
import javafx.beans.property.SimpleIntegerProperty;

public class Bullet extends Entity {

	public Bullet(int x, int y, int orientation) {
		super("bullet", x, y);
		this.orientation = new SimpleIntegerProperty(orientation);
		this.velocity = 4;
	}
	
	public void update(ArrayList<EntityView> entities) {
		
		if (this.getX().get() <= 100 || this.getY().get() <= 100 || this.getX().get() >= 795 || this.getY().get() >= 795 || isCollidingWith(entities) > -1) 
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
	
	public int isCollidingWith(ArrayList<EntityView> entities) {
		
		switch (this.orientation.get()) {
		case LEFT :
			for (int i = 1 ; i < entities.size() ; i++)
				if (entities.get(i).getTranslateX() == this.getX().get() && entities.get(i).getTranslateY() >= this.getY().get()-32 && entities.get(i).getTranslateY() <= this.getY().get())
					return i;
			break;
		case UP :
			for (int i = 1 ; i < entities.size() ; i++)
				if (entities.get(i).getTranslateY() == this.getY().get() && entities.get(i).getTranslateX() >= this.getX().get()-32 && entities.get(i).getTranslateX() <= this.getX().get())
					return i;
			break;
		case RIGHT :
			for (int i = 1 ; i < entities.size() ; i++)
				if (entities.get(i).getTranslateX() == this.getX().get() && entities.get(i).getTranslateY() >= this.getY().get()-32 && entities.get(i).getTranslateY() <= this.getY().get())
					return i;
			break;
		case DOWN :
			for (int i = 1 ; i < entities.size() ; i++)
				if (entities.get(i).getTranslateY() == this.getY().get() && entities.get(i).getTranslateX() >= this.getX().get()-32 && entities.get(i).getTranslateX() <= this.getX().get())
					return i;
			break;
		default :
			break;
		}
		
		return -1;
	}
	
}
