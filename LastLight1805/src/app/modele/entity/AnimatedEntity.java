package app.modele.entity;

import java.util.ArrayList;
import java.util.Collections;

import app.modele.Game;
import app.modele.field.Field;
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
		if (x.get() % 32 != 0)
			x.set(x.get() - velocity);
		else {
			if (x.getValue() > 31 && crossableTiles.contains(Game.getMap().getField()[this.getIndiceY()][this.getIndiceX()-1])) // y correspond au i, x au j
				x.set(x.get() - velocity);
		}
	}
	
	public void moveRight() {
		if (x.get() % 32 != 0)
			x.set(x.get() + velocity);
		else
			if (x.getValue() < 768 && crossableTiles.contains(Game.getMap().getField()[this.getIndiceY()][this.getIndiceX()+1]))
				x.set(x.get() + velocity);
	}

	public void moveDown() {
		if (y.get() % 32 != 0)
			y.set(y.get() + velocity);
		else
			if (y.getValue() < 768 && crossableTiles.contains(Game.getMap().getField()[this.getIndiceY()+1][this.getIndiceX()]))
				y.set(y.get() + velocity);
	}
	
	public void moveUp() {
		if (y.get() % 32 != 0)
			y.set(y.get() - velocity);
		else
			if (y.getValue() > 31 && crossableTiles.contains(Game.getMap().getField()[this.getIndiceY()-1][this.getIndiceX()]))
				y.set(y.get() - velocity);
	}

}
