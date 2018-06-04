package app.modele.entity;

public class Walker extends Enemy {

	public Walker(int x, int y, int pv, int att, int v, int nb, int fmax) {
		super(x, y, pv, att, v, nb, fmax);
		this.id = "walker";
	}

}
