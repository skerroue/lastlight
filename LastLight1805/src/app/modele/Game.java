package app.modele;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import app.modele.entity.Entity;
import app.modele.entity.Player;
import app.modele.field.Field;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Game {

	private Timeline gameloop;
	private int[][] fieldsMap;	// contient les indices des fichiers de chaque map
								// valeurs allant de 1 à ... (0 = pas de map)
	private Field map;
	private Player player;
	private ArrayList<Entity> entities;
	private BooleanProperty mapChange;
	
	public Game() {
		this.gameloop = new Timeline();
		this.fieldsMap = readFileMaps();
		this.map = new Field(0, 0, this.fieldsMap[0][0] , 25, 25);	// coordonnées à modifier
		this.player = new Player(0, 0, 0, 0, 0);	// coordonnées à modifier
		this.entities = new ArrayList<>();
		this.mapChange = new SimpleBooleanProperty(true);
	}
	
	private int[][] readFileMaps() { 
		int[][] fieldsMap = new int[100][100];	// taille à modifier
		
		ArrayList<String> lines = new ArrayList<>();
        
        try {
        	
			File f = new File("src/map/Maps.txt");	// nom du fichier à modifier
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			Scanner s = new Scanner(br).useDelimiter(",");
			
			try {
												
				for (int i = 0; i < 100; i++) {
					for (int j = 0; j < 100; j++) {	// taille à modifier
						fieldsMap[i][j] = s.nextInt();
					}
				}
				
				s.close();
				br.close();
				fr.close();
				
			} catch (IOException e) {
				System.out.println("Erreur lecture");
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("Fichier introuvable");
		}
		
		return fieldsMap;
	}
	
	public Field getMap() {
		return this.map;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public ArrayList<Entity> getEntities() {
		return this.entities;
	}
	
	public BooleanProperty getMapChange() {
		return this.mapChange;
	}
	
	public boolean loadField(int direction) {
		int i = this.map.getI();
		int j = this.map.getJ();
		switch (direction) {
		case 1 : // Ouest
			if (j == 0 || this.fieldsMap[i][j - 1] == 0)
				return false;
			else {
				this.map = new Field(i, j - 1, this.fieldsMap[i][j - 1], 25, 25);
				return true;
			}
		case 2 : // Nord
			if (i == 0 || this.fieldsMap[i - 1][j] == 0)
				return false;
			else {
				this.map = new Field(i - 1, j, this.fieldsMap[i - 1][j], 25, 25);
				return true;
			}
		case 3 : // Est
			if (j == 100 || this.fieldsMap[i][j + 1] == 0)	// valeur max du tableau à modifier
				return false;
			else {
				this.map = new Field(i, j + 1, this.fieldsMap[i][j + 1], 25, 25);
				return true;
			}
		case 4 : // Sud
			if (i == 100 || this.fieldsMap[i + 1][j] == 0)	// valeur max du tableau à modifier
				return false;
			else {
				this.map = new Field(i + 1, j, this.fieldsMap[i + 1][j], 25, 25);
				return true;
			}
		default :
			return false;
		}
	}
	
}
