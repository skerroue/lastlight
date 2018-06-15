package app.modele.entity.inanimated;

import app.modele.Game; 
import app.modele.GameData;

public class Dispenser extends InanimatedEntity {

	public Dispenser(int x, int y, String dialog) {
		super(GameData.ENTITY_DISPENSER, x, y, dialog);
	}

	@Override
	public boolean interact() {
		if (Game.getPlayer().getPotion().getValue() < 3 && Game.getPlayer().buyPotion())
			this.dialog = "Vous avez acheté une potion";
		else 
			this.dialog = "Vous n'avez pas assez d'argent ou pas assez de place";
		
		return true;
	}

}
