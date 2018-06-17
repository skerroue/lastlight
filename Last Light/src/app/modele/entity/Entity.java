package app.modele.entity;

import app.modele.GameData;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public abstract class Entity {
	
	protected String id;
	protected DoubleProperty x;
	protected DoubleProperty y;
	protected IntegerProperty orientation;
	protected int frame;
	protected int velocity;
	protected BooleanProperty isDead;
	
	public Entity(String id, int x, int y) {
		this.id = id;
		this.x = new SimpleDoubleProperty(x);
		this.y = new SimpleDoubleProperty(y);
		this.orientation = new SimpleIntegerProperty(0);
		this.frame = 0;
		this.isDead = new SimpleBooleanProperty(false);
	}
	
	public DoubleProperty getX() {
		return x;
	}
	
	public DoubleProperty getY() {
		return y;
	}
	
	public int getIndiceX() {
		return (int)x.get()/GameData.TILE_SIZE;
	}

	public int getIndiceY() {
		return (int)y.get()/GameData.TILE_SIZE;
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
	
	public IntegerProperty getOrientation() {
		return this.orientation;
	}

}
