package app.modele.entity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import app.modele.Game;
import javafx.beans.property.IntegerProperty;
import javafx.collections.ObservableList;

public abstract class Entity {
	
	static protected ArrayList<Integer> crossableTiles;
	
	protected IntegerProperty x;
	protected IntegerProperty y;
	protected int velocity;
	
	public Entity() {
		this.crossableTiles = readFileCrossableTiles();
	}
	
	private ArrayList<Integer> readFileCrossableTiles() {
		ArrayList<Integer> crossableTiles = new ArrayList<>();
		
		try {
        	
			File f = new File("src/map/crossableTilesTest.txt");	// nom du fichier à modifier
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			Scanner s = new Scanner(br).useDelimiter(",");
			
			try {
				
				while (s.hasNextInt())
					crossableTiles.add(s.nextInt());
				
				s.close();
				br.close();
				fr.close();
				
			} catch (IOException e) {
				System.out.println("CrossableTiles : Erreur lecture");
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("CrossableTiles : Fichier introuvable");
		}System.out.println(crossableTiles.size());
				
		return crossableTiles;
	}
	
	public abstract void update(ObservableList<Entity> entities);
	
	public IntegerProperty getX() {
		return x;
	}
	
	public IntegerProperty getY() {
		return y;
	}
	
	public int getIndiceX() {
		return x.get()/32;
	}

	public int getIndiceY() {
		return y.get()/32;
	}
	
	public void setX(int x) {
		this.x.set(x);
	}
	
	public void setY(int y) {
		this.y.set(y);
	}
	
	public void moveLeft(ObservableList<Entity> entities) {
		if (x.get() % 32 != 0) // ajouter canMove
			x.set(x.get() - velocity);
		else 
			if (x.getValue() > 31 && crossableTiles.contains(Game.getMap().getNextTile(this.getIndiceY(), this.getIndiceX()-1)) && canMove(entities, 32, 0)) // y correspond au i, x au j
				x.set(x.get() - velocity);
	}
	
	public void moveRight(ObservableList<Entity> entities) {
		if (x.get() % 32 != 0) // ajouter canMove
			x.set(x.get() + velocity);
		else
			if (x.getValue() < 768 && crossableTiles.contains(Game.getMap().getNextTile(this.getIndiceY(), this.getIndiceX()+1)) && canMove(entities, -32, 0))
				x.set(x.get() + velocity);
	}

	public void moveDown(ObservableList<Entity> entities) {
		if (y.get() % 32 != 0) // ajouter canMove
			y.set(y.get() + velocity);
		else
			if (y.getValue() < 768 && crossableTiles.contains(Game.getMap().getNextTile(this.getIndiceY()+1, this.getIndiceX())) && canMove(entities, 0, -32))
				y.set(y.get() + velocity);
	}
	
	public void moveUp(ObservableList<Entity> entities) {
		if (y.get() % 32 != 0) // ajouter canMove
			y.set(y.get() - velocity);
		else
			if (y.getValue() > 31 && crossableTiles.contains(Game.getMap().getNextTile(this.getIndiceY()-1, this.getIndiceX())) && canMove(entities, 0, 32))
				y.set(y.get() - velocity);
	}
	
	public boolean canMove(ObservableList<Entity> entities, int x, int y) {
    	for (Entity e : entities)
    		if ((int)this.getX().getValue() == (int)e.getX().getValue() + x && (int)this.getY().getValue() == (int)e.getY().getValue() + y)
    			return false;
    	
    	return true;
	}
	
	
}
