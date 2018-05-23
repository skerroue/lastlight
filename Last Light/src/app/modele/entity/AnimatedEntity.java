package app.modele.entity;

import java.util.ArrayList;
import java.util.Collections;

import app.modele.Game;
import app.modele.field.Field;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public abstract class AnimatedEntity extends Entity {
	
	protected IntegerProperty pv;
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
	
	public IntegerProperty getPv() {
		return this.pv;
	}

}
