package app.modele.BFS;

import java.util.ArrayList; 
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import app.modele.Game;
import app.modele.entity.animated.AnimatedEntity;
import app.modele.entity.animated.Player;
import app.modele.field.Field;
import app.modele.field.Tile;

public class BFS {
	
	private Player player;
	private Field field;
	private Queue<Tile> queue;
	private HashMap<Tile, Tile> parents;
	private ArrayList<Tile> adjTiles;
	
	public BFS(Player player, Field field) {
		this.player = player;
		this.field = field;
		this.queue = new LinkedList<>();
		this.parents = new HashMap<Tile, Tile>();
		this.adjTiles = new ArrayList<>();
	}
	
	public void lancerBFS() {
		int x = player.getIndiceX();
		int y = player.getIndiceY();
		
		this.parents.clear();
		this.queue.clear();
		this.adjTiles.clear();
	
		this.queue.add(field.getNextTile(y, x));
		this.parents.put(field.getNextTile(y, x), field.getNextTile(y, x));
		
		while (!queue.isEmpty()) {
			Tile temp = queue.remove();
			y = temp.getI();
			x = temp.getJ();
			
			if (x > 0) {
				this.adjTiles.add(field.getNextTile(y, x-1));
			}
			if (y > 0) {
				this.adjTiles.add(field.getNextTile(y-1, x));
			}
			if (x < Game.getMap().getFieldSize() - 1) {
				this.adjTiles.add(field.getNextTile(y, x+1));
			}
			if (y < Game.getMap().getFieldSize() - 1) {
				this.adjTiles.add(field.getNextTile(y+1, x));
			}
			
			/*
			for (Tile t : adjTiles) 
				if (t.isCrossable() && this.parents.putIfAbsent(t, temp) == null) 
					this.queue.add(t);
			*/
			
			for (Tile t : adjTiles) 
				if (this.parents.putIfAbsent(t, temp) == null) 
					this.queue.add(t);
			
			this.adjTiles.clear();
		}
		
	}
	
	public Tile searchWay(AnimatedEntity entity) {
		int x = entity.getIndiceX();
		int y = entity.getIndiceY();
		
		Tile t = this.field.getNextTile(y, x);
		
		return this.parents.get(t);
	}
	
	public HashMap<Tile, Tile> getParents() {
		return parents;
	}
 
}
