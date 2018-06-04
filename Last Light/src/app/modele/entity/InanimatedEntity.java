package app.modele.entity;

import javafx.collections.ObservableList;

public abstract class InanimatedEntity extends Entity {
	// TODO : Trouver un moyen plus "clair" d'identifier les entites inanimees
	
	protected String id;
	
	public InanimatedEntity(String id) {
		this.id = id;
	}

	public abstract void interact(Player p);
	
	public String getId() {
		return this.id;
	}

}
