package app.modele.entity;

import javafx.beans.property.IntegerProperty;

public abstract class Entity {
	
	protected IntegerProperty x;
	protected IntegerProperty y;
	
	public abstract void update();
	
}
