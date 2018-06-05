package app.modele.entity;

import app.modele.Game;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
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
		return (int)x.get()/32;
	}

	public int getIndiceY() {
		return (int)y.get()/32;
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
