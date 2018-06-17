package app.modele.entity.inanimated;

import app.modele.entity.Entity;
import app.modele.entity.animated.Player;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;  

public abstract class InanimatedEntity extends Entity {
	
	protected String dialog;
	private BooleanProperty isInteracting;
		
	public InanimatedEntity(String id, int x, int y, String dialog) {
		super(id, x, y);
		this.isInteracting = new SimpleBooleanProperty(false);
		this.dialog = dialog;
	}

	public abstract boolean interact(Player p);
	
	public String getId() {
		return this.id;
	}
	
	public String getDialog() {
		return this.dialog;
	}
	
	public void setDialog(String s) {
		this.dialog = s;
	}
	
	public BooleanProperty isInteracting() {
		return this.isInteracting;
	}
	
	public void hasInteracted() {
		this.isInteracting.set(!this.isInteracting.get());
	}

}
