package app.modele.entity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.beans.property.IntegerProperty;

public abstract class Entity {
	
	static protected ArrayList<Integer> crossableTiles;
	
	protected IntegerProperty x;
	protected IntegerProperty y;
	
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
				System.out.println("crossableTiles : Erreur lecture");
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("crossableTiles : Fichier introuvable");
		}
				
		return crossableTiles;
	}
	
	public abstract void update();
	
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
	
}
