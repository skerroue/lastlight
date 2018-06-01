package app.vue;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ComponentView extends ImageView {
	
	private Image componentFull;
	private Image componentEmpty;
	private Image componentLocked;
	private String name;
	private boolean isEmpty;
	private boolean isLocked;
	private boolean isFull;

	public ComponentView(String name) {
		this.setImage(componentFull);
		this.isEmpty = false;
		this.isLocked = false;
		this.isFull = false;
		this.name = name;
		
		this.componentFull = new Image("file:src/img/" + this.name + "-full.png");
		this.componentEmpty = new Image("file:src/img/" + this.name + "-empty.png");
		this.componentLocked = new Image("file:src/img/" + this.name + "-locked.png");
	}
	
	public void setEmpty() {
		this.setImage(componentEmpty);
		this.isEmpty = true;
		this.isLocked = false;
		this.isFull = false;
	}
	
	public void setLocked() {
		this.setImage(componentLocked);
		this.isEmpty = false;
		this.isLocked = true;
		this.isFull = false;
	}
	
	public void setFull() {
		this.setImage(componentFull);
		this.isEmpty = false;
		this.isLocked = false;
		this.isFull = true;
	}
	
	public boolean getEmpty() {
		return isEmpty;
	}
	
	public boolean getLocked() {
		return isLocked;
	}
	
	public boolean getFull() {
		return isFull;
	}
	
}
