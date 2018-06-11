package app.modele.entity.inanimated;

import app.modele.GameData;
import app.modele.entity.animated.Player;

public class Button extends InanimatedEntity {
	
	private InanimatedEntity child;

	public Button(int x, int y, String dialog, InanimatedEntity child) {
		super(GameData.ENTITY_BUTTON, x, y, dialog);
		this.child = child;
	}

	@Override
	public boolean interact(Player p) {
		if (this.child != null) {
			this.child.die();
			return true;
		}
		
		return false;
	}

}
