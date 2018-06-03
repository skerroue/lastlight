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

}
