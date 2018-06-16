package app.modele.entity.animated;

import app.modele.GameData;
import javafx.collections.ObservableList;

public class Walker extends Enemy {

	public Walker(int x, int y, int pv, int att, int v, int nb, int fmax) {
		super(GameData.ENTITY_WALKER, x, y, pv, att, v, nb, fmax);
	}

}
