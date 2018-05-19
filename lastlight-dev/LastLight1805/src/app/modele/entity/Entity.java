package app.modele.entity;

import java.util.ArrayList;

import javafx.beans.property.IntegerProperty;

public abstract class Entity {
	
	final static protected Integer[] CROSSABLE_TILES = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 19};
	protected ArrayList<Integer> CROSSABLE_TILES_LIST;
	
	protected IntegerProperty x;
	protected IntegerProperty y;
	
	public abstract void update();
	
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
	
}
