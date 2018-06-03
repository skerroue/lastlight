package app.modele.entity;

import javafx.collections.ObservableList;

public abstract class InanimatedEntity extends Entity {
	// TODO : Trouver un moyen plus "clair" d'identifier les entites inanimees
	
	protected int id;
	
	public InanimatedEntity(int id) {
		this.id = id;
	}

	public abstract void interact(Player p);
	
	public int getId() {
		return this.id;
	}

}
