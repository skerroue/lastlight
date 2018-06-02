package app.modele.weapon;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Weapon {

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
}
