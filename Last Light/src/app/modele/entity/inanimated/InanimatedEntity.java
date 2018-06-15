package app.modele.entity.inanimated;

import app.modele.entity.Entity;  

public abstract class InanimatedEntity extends Entity {
	
	protected String dialog;
		
	public InanimatedEntity(String id, int x, int y, String dialog) {
		super(id, x, y);
	}

	public abstract boolean interact();
	
	public String getId() {
		return this.id;
	}
	
	public String getDialog() {
		return this.dialog;
	}
	
	public void setDialog(String s) {
		this.dialog = s;
	}

}
