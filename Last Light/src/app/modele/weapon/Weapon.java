package app.modele.weapon;

import app.modele.entity.animated.AnimatedEntity;
import app.modele.entity.animated.Bullet;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;

public abstract class Weapon {

	protected IntegerProperty att;
	protected IntegerProperty distance;
	protected String id;
	
	public Weapon(String id, int a, int d) {
		this.id = id;
		this.att = new SimpleIntegerProperty(a);
		this.distance = new SimpleIntegerProperty(d);
	}
	
	public int getAttack() {
		return this.att.get();
	}
	
	public String getId() {
		return this.id;
	}
	
	public abstract boolean attack(int orientation, int x, int y, ObservableList<AnimatedEntity> animatedEntities);
	
	public abstract boolean update(ObservableList<AnimatedEntity> animatedEntities);
	
	public abstract void killAllBullets();
	
	public abstract ObservableList<Bullet> getBullets();
	
	public abstract void reload();
	
}
