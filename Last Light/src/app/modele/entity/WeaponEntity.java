package app.modele.entity;

import app.modele.weapon.Lampe;
import app.modele.weapon.Weapon;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;

public class WeaponEntity extends InanimatedEntity {

	public WeaponEntity(int id, int x, int y) {
		super(id);
		this.x = new SimpleIntegerProperty(x);
		this.y = new SimpleIntegerProperty(y);
	}
	
	// Dumb af cette methode mais est construite dans l'idée où une arme spawnera une et une seule fois dans le jeu
	public void interact(Player p) {
		switch (this.id) {
		case 3 :
			if (p.getWeapons().size() < 1) {
				p.getWeapons().add(new Lampe(1, 0));
				this.die();
			}
			break;
		default :
			break;
		}
	}

}
