package app.modele.field;

public class Tile {
	
	private int id;
	private boolean isCrossable;
	private int i;
	private int j;
	
	public Tile(int id, boolean cross, int i, int j) {
		this.id = id;
		this.isCrossable = cross;
		this.i = i;
		this.j = j;
	}
	
	public boolean isCrossable() {
		return this.isCrossable;
	}
	
	public int getId() {
		return this.id;
	}
	
	public int getI() {
		return this.i;
	}
	
	public int getJ() {
		return this.j;
	}
	
	public void setCrossable() {
		this.isCrossable = true;
	}
	
	public void setUncrossable() {
		this.isCrossable = false;
	}
	 
}
