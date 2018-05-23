package app.modele.weapon;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Weapon {

	private IntegerProperty att;
	private IntegerProperty distance;
	
	public Weapon(int a, int d) {
		this.att = new SimpleIntegerProperty(a);
		this.distance = new SimpleIntegerProperty(d);
	}
}
