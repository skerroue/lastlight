package app.modele.field;

public class Tile {
	
	private int id;
	private boolean isCrossable;
	
	public Tile(int id, boolean cross) {
		this.id = id;
		this.isCrossable = cross;
	}
	
	public boolean isCrossable() {
		return this.isCrossable;
	}
	
	public int getId() {
		return this.id;
	}
	 
}
