package app.modele.entity;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public abstract class AnimatedEntity extends Entity {
	
	private IntegerProperty pv;
	private int attaque;
	private int velocity;
	
	public AnimatedEntity(int x, int y, int pv, int att, int v) {
		this.x = new SimpleIntegerProperty(x);
		this.y = new SimpleIntegerProperty(y);
		this.pv = new SimpleIntegerProperty(pv);
		this.attaque = att;
		this.velocity = v;
	}
	
	@Override
	public void update() {
		
	}
	
	public void moveLeft() {
		
	}
	
	public void moveRight() {
		
	}

	public void moveDown() {
		
	}
	
	public void moveUp() {
		
	}

}
