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
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.scene.input.KeyCode;

public abstract class Entity {
	
	final static int LEFT = 0;
	final static int UP = 1;
	final static int RIGHT = 2;
	final static int DOWN = 3;
	
	static protected ArrayList<Integer> crossableTiles;
	
	protected IntegerProperty x;
	protected IntegerProperty y;
	private int leftTopLimit;
	private int rightBottomLimit;
	private IntegerProperty orientation;
	private int frame;
	protected int velocity;
	
	public Entity() {
		this.crossableTiles = readFileCrossableTiles();
		this.leftTopLimit = 31;
		this.rightBottomLimit = 768;
		this.orientation = new SimpleIntegerProperty(0);
		this.frame = 0;
	}
	
	private ArrayList<Integer> readFileCrossableTiles() {
		ArrayList<Integer> crossableTiles = new ArrayList<>();
		
		try {
        	
			File f = new File("src/map/crossableTiles.txt");	// nom du fichier à modifier
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
		}
				
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
	
	public IntegerProperty getOrientation() {
		return orientation;
	}
	
	public int getFrame() {
		return frame;
	}
	
	public void incrementeFrame() {
		if (frame > 18)
			frame = 0;
		else 
			frame++;
	}
	
	public void moveLeft(ObservableList<Entity> entities) {
		if (canMove(entities))
			x.set(x.get() - velocity);
	}
	
	public void moveRight(ObservableList<Entity> entities) {
		if (canMove(entities))
			x.set(x.get() + velocity);
	}

	public void moveDown(ObservableList<Entity> entities) {
		if (canMove(entities))
			y.set(y.get() + velocity);
	}
	
	public void moveUp(ObservableList<Entity> entities) {
		if (canMove(entities))
			y.set(y.get() - velocity);
	}
	
	public boolean canMove(ObservableList<Entity> entities) {
		boolean canMove = false;
		boolean emptyTile = true;
		
		switch (this.orientation.getValue()) {
		case LEFT :
			emptyTile = tileIsEmpty(entities, 32, 0);
			if (x.get() % 32 != 0)
				canMove = true;
			else if (x.get() % 32 == 0 && y.get() % 32 != 0) {
				if (y.get() < rightBottomLimit) {
					if (crossableTiles.contains(Game.getMap().getNextTile(this.getIndiceY(), this.getIndiceX()-1)) &&
						crossableTiles.contains(Game.getMap().getNextTile(this.getIndiceY()+1, this.getIndiceX()-1)) && emptyTile)
						canMove = true;
				}
				else {
					if (crossableTiles.contains(Game.getMap().getNextTile(this.getIndiceY(), this.getIndiceX()-1)) &&
						crossableTiles.contains(Game.getMap().getNextTile(this.getIndiceY()-1, this.getIndiceX()-1)) && emptyTile)
						canMove = true;
				}
			}
			else
				if (x.getValue() > leftTopLimit && crossableTiles.contains(Game.getMap().getNextTile(this.getIndiceY(), this.getIndiceX()-1)) && emptyTile)
					canMove = true;
			break;
		case UP :
			emptyTile = tileIsEmpty(entities, 0, 32);
			if (y.get() % 32 != 0)
				canMove = true;
			else if (y.get() % 32 == 0 && x.get() % 32 != 0) {
				if (x.get() < rightBottomLimit) {
					if (crossableTiles.contains(Game.getMap().getNextTile(this.getIndiceY()-1, this.getIndiceX())) && 
						crossableTiles.contains(Game.getMap().getNextTile(this.getIndiceY()-1, this.getIndiceX()+1)) && emptyTile)
						canMove = true;
				}
				else {
					if (crossableTiles.contains(Game.getMap().getNextTile(this.getIndiceY()-1, this.getIndiceX())) && 
						crossableTiles.contains(Game.getMap().getNextTile(this.getIndiceY()-1, this.getIndiceX()-1)) && emptyTile)
						canMove = true;
				}
			}
			else 
				if (y.get() > leftTopLimit && crossableTiles.contains(Game.getMap().getNextTile(this.getIndiceY()-1, this.getIndiceX())) && emptyTile)
					canMove = true;
			break;
		case DOWN :
			emptyTile = tileIsEmpty(entities, 0, -32);
			if (y.get() % 32 != 0)
				canMove = true;
			else if (y.get() % 32 == 0 && x.get() % 32 != 0) {
				if (x.get() < rightBottomLimit) {
					if (crossableTiles.contains(Game.getMap().getNextTile(this.getIndiceY()+1, this.getIndiceX())) && 
						crossableTiles.contains(Game.getMap().getNextTile(this.getIndiceY()+1, this.getIndiceX()+1)) && emptyTile)
						canMove = true;
				}
				else {
					if (crossableTiles.contains(Game.getMap().getNextTile(this.getIndiceY()+1, this.getIndiceX())) && 
						crossableTiles.contains(Game.getMap().getNextTile(this.getIndiceY()+1, this.getIndiceX()-1)) && emptyTile)
						canMove = true;
				}
			}
			else 
				if (y.get() < rightBottomLimit && crossableTiles.contains(Game.getMap().getNextTile(this.getIndiceY()+1, this.getIndiceX())) && emptyTile)
					canMove = true;
			break;
		case RIGHT :
			emptyTile = tileIsEmpty(entities, -32, 0);
			if (x.get() % 32 != 0)
				canMove = true;
			else if (x.get() % 32 == 0 && y.get() % 32 != 0) {
				if (y.get() < rightBottomLimit) {
					if (crossableTiles.contains(Game.getMap().getNextTile(this.getIndiceY(), this.getIndiceX()+1)) &&
						crossableTiles.contains(Game.getMap().getNextTile(this.getIndiceY()+1, this.getIndiceX()+1)) && emptyTile)
						canMove = true;
				}
				else {
					if (crossableTiles.contains(Game.getMap().getNextTile(this.getIndiceY(), this.getIndiceX()+1)) &&
						crossableTiles.contains(Game.getMap().getNextTile(this.getIndiceY()-1, this.getIndiceX()+1)) && emptyTile)
						canMove = true;
				}
			}
			else
				if (x.getValue() < rightBottomLimit && crossableTiles.contains(Game.getMap().getNextTile(this.getIndiceY(), this.getIndiceX()+1)) && emptyTile)
					canMove = true;
			break;
		default :
			break;
		}
		
		return canMove;
	}
	
	public void setOrientation(KeyCode k) {
		switch (k) {
		case LEFT :
			this.orientation.set(this.LEFT);
			break;
		case UP :
			this.orientation.set(this.UP);
			break;
		case RIGHT :
			this.orientation.set(this.RIGHT);
			break;
		case DOWN :
			this.orientation.set(this.DOWN);
			break;
		default :
			break;
		}
 	}
	
	public boolean tileIsEmpty(ObservableList<Entity> entities, int x, int y) {
    	for (Entity e : entities)
    		if ((int)this.getX().getValue() == (int)e.getX().getValue() + x && (int)this.getY().getValue() == (int)e.getY().getValue() + y)
    			return false;
    	
    	return true;
	}
}
