package app.modele.entity;

import javafx.collections.ObservableList;

public abstract class InanimatedEntity extends Entity {
	
	protected int id;
	
	public InanimatedEntity(int id) {
		this.id = id;
	}

	public abstract void update(ObservableList<AnimatedEntity> entities);
	public abstract void interact(Player p);
	
	public int getId() {
		return this.id;
	}

}
