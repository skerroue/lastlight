package app.modele.entity;

import app.modele.field.Field;
import javafx.collections.ObservableList;

public class Player extends AnimatedEntity {

	public Player(int x, int y, int pv, int att, int v) {
		super(x, y, pv, att, v);
	}
	
	public void update(ObservableList<Entity> entities) {
		
	}
	
}
