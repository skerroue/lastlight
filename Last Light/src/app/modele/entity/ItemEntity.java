package app.modele.entity;

import app.modele.weapon.Lampe;
import javafx.beans.property.SimpleIntegerProperty;

public class ItemEntity extends InanimatedEntity {

	public ItemEntity(String id, int x, int y) {
		super(id);
		this.x = new SimpleIntegerProperty(x);
		this.y = new SimpleIntegerProperty(y);
	}
	
	// Dumb af cette methode mais est construite dans l'id�e o� une arme spawnera une et une seule fois dans le jeu
	public void interact(Player p) {
		switch (this.id) {
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
