package app.modele.entity;

import app.modele.weapon.Lamp;
import app.modele.weapon.Pistol;
import javafx.beans.property.SimpleIntegerProperty;

public class ItemEntity extends InanimatedEntity {

	public ItemEntity(String id, int x, int y) {
		super(id, x, y);
	}
	
	// Dumb af cette methode mais est construite dans l'id�e o� une arme spawnera une et une seule fois dans le jeu
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
		case "soda" :
			if (p.getPotion().getValue() < 3) {
				p.earnPotion();
				this.die();
			}
			break;
		default :
			break;
		}
	}

}
