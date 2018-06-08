package app.modele.entity;

public class NPC extends AnimatedEntity {
	
	private String dialog;

	public NPC(int x, int y, int hp, int att, int v, int nb, int fmax, String dialog) {
		super("sprite", x, y, hp, att, v, nb, fmax);
		this.dialog = dialog;
	}
	
	public boolean interact() {
		return true;
	}
	
	public String getDialog() {
		return this.dialog;
	}

}
