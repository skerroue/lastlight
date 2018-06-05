package app.modele.entity;

import javafx.collections.ObservableList;

public abstract class InanimatedEntity extends Entity {
	// TODO : Trouver un moyen plus "clair" d'identifier les entites inanimees
		
	public InanimatedEntity(String id, int x, int y) {
		super(id, x, y);
	}

	public abstract void interact(Player p);
	
	public String getId() {
		return this.id;
	}

}
