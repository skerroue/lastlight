package app.modele.weapon;

import app.modele.entity.animated.AnimatedEntity;
import app.modele.entity.animated.Bullet;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;

public abstract class Weapon {
	
	final static int LEFT = 0;
	final static int UP = 1;
	final static int RIGHT = 2;
	final static int DOWN = 3;
	
	final static int TILE_SIZE = 32;

	protected IntegerProperty att;
	protected IntegerProperty distance;
	protected String id;
	
	public Weapon(int a, int d) {
		this.att = new SimpleIntegerProperty(a);
		this.distance = new SimpleIntegerProperty(d);
	}
	
	public int getAttack() {
		return this.att.get();
	}
	
	public String getId() {
		return this.id;
	}
	
	public abstract void attack(ObservableList<AnimatedEntity> entities, int orientation, int x, int y);
	
	public abstract void update(ObservableList<AnimatedEntity> entities);
	
	public void killAllBullets() {
		
	}
	
	public ObservableList<Bullet> getBullets() {
		return null;
	}
	
	public void reload() {
		
	}
	
}
