package app.modele.entity;

import java.util.ArrayList;
import java.util.Collections;

import app.modele.field.Field;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public abstract class AnimatedEntity extends Entity {
	
	private IntegerProperty pv;
	private int attaque;
	private int velocity;
	private Field map;
	
	public AnimatedEntity(int x, int y, int pv, int att, int v, Field map) {
		this.x = new SimpleIntegerProperty(x);
		this.y = new SimpleIntegerProperty(y);
		this.pv = new SimpleIntegerProperty(pv);
		this.attaque = att;
		this.velocity = v;
		this.map = map;
		
		CROSSABLE_TILES_LIST = new ArrayList<Integer>();
		Collections.addAll(CROSSABLE_TILES_LIST, CROSSABLE_TILES);
	}
	
	@Override
	public void update() {
		
	}
	
	public void moveLeft() {
		if (x.get() % 32 != 0)
			x.set(x.get() - velocity);
		else {
			if (x.get() > 31 && CROSSABLE_TILES_LIST.contains(map.getField()[this.getIndiceY()][this.getIndiceX()-1]))
				x.set(x.get() - velocity);
		}
	}
	
	public void moveRight() {
		if (x.get() % 32 != 0)
			x.set(x.get() + velocity);
		else
			if (x.get() < 768 && CROSSABLE_TILES_LIST.contains(map.getField()[this.getIndiceY()][this.getIndiceX()+1]))
				x.set(x.get() + velocity);
	}

	public void moveDown() {
		if (y.get() % 32 != 0)
			y.set(y.get() + velocity);
		else
			if (y.get() < 768 && CROSSABLE_TILES_LIST.contains(map.getField()[this.getIndiceY()+1][this.getIndiceX()]))
				y.set(y.get() + velocity);
	}
	
	public void moveUp() {
		if (y.get() % 32 != 0)
			y.set(y.get() - velocity);
		else
			if (y.get() > 31 && CROSSABLE_TILES_LIST.contains(map.getField()[this.getIndiceY()-1][this.getIndiceX()]))
				y.set(y.get() - velocity);
	}
	
	public void setMap(Field f) {
		this.map = f;
	}

}
