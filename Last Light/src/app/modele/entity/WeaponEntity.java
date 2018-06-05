package app.modele.entity;

import app.modele.weapon.Lamp;
import app.modele.weapon.Pistol;
import app.modele.weapon.Weapon;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;

public class WeaponEntity extends InanimatedEntity {

	public WeaponEntity(String id, int x, int y) {
		super(id, x, y);
	}
	
	// Dumb af cette methode mais est construite dans l'idée où une arme spawnera une et une seule fois dans le jeu
	public void interact(Player p) {
		switch (this.id) {
		case "lamp" :
			p.getWeapons().add(new Lamp(1, 1));
			this.die();
			break;
		case "pistol" :
			p.getWeapons().add(new Pistol(1, 1));
			this.die();
			break;
  		default :
			break;
		}
	}

}
