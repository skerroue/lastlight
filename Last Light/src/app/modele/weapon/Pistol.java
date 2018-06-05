package app.modele.weapon;

import app.modele.entity.AnimatedEntity;
import javafx.collections.ObservableList;

public class Pistol extends Weapon {

	public Pistol(int a, int d) {
		super(a, d);
		this.id = "pistol";
	}

	@Override
	public void attack(ObservableList<AnimatedEntity> entities, int orientation, int x, int y) {
		
		
	}

}
