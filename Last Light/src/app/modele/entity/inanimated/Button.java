package app.modele.entity.inanimated;

import app.modele.entity.animated.Player;

public class Button extends InanimatedEntity {
	
	private InanimatedEntity child;

	public Button(String id, int x, int y, String dialog, InanimatedEntity child) {
		super(id, x, y, dialog);
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
