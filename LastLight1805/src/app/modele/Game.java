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
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

public class Game {

	private Timeline gameloop;
	private int[][] fieldsMap;	// contient les indices des fichiers de chaque map
								// valeurs allant de 1 à ... (0 = pas de map)
	private static Field map;
	private Player player;
	private ObservableList<Entity> entities;
	private BooleanProperty mapChanged;
	
	public Game() {
		this.gameloop = new Timeline();
		this.fieldsMap = readFileMaps();
		this.map = new Field(0, 0, this.fieldsMap[0][0] , 25, 25);	// coordonnées à modifier
		this.player = new Player(32, 512, 0, 0, 32);	// coordonnées à modifier
		this.entities = FXCollections.observableArrayList();
		this.mapChanged = new SimpleBooleanProperty(true);
	}
	
	private int[][] readFileMaps() { 
		int[][] fieldsMap = new int [1][2];	// taille à modifier
		
		ArrayList<String> lines = new ArrayList<>();
        
        try {
        	
			File f = new File("src/map/Maps.txt");	// nom du fichier à modifier
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			Scanner s = new Scanner(br).useDelimiter(",");
			
			try {
												
				for (int i = 0; i < 1; i++) {
					for (int j = 0; j < 2; j++) {	// taille à modifier
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
	
	public static Field getMap() {
		return map;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public ObservableList<Entity> getEntities() {
		return this.entities;
	}
	
	public BooleanProperty getMapChanged() {
		return this.mapChanged;
	}
	
	public void mapOnChange() {
		this.mapChanged.set(!this.mapChanged.get());
	}
	
	public boolean loadField(int direction) {
		int i = this.map.getI();
		int j = this.map.getJ();
		
		boolean result = false;
		
		switch (direction) {
		case 1 : // Ouest
			if (j > 0 && this.fieldsMap[i][j - 1] != 0) {
				this.map = new Field(i, j - 1, this.fieldsMap[i][j - 1], 25, 25);
				this.mapOnChange();
				result = true;
			}
			break;
		case 2 : // Nord
			if (i > 0 && this.fieldsMap[i - 1][j] != 0) {
				this.map = new Field(i - 1, j, this.fieldsMap[i - 1][j], 25, 25);
				this.mapOnChange();
				result = true;
			}
			break;
		case 3 : // Est
			if (j < 1 && this.fieldsMap[i][j + 1] != 0) {
				this.map = new Field(i, j + 1, this.fieldsMap[i][j + 1], 25, 25);
				this.mapOnChange();
				result = true;
			}
			break;
		case 4 : // Sud
			if (i < 0 && this.fieldsMap[i + 1][j] != 0) {
				this.map = new Field(i + 1, j, this.fieldsMap[i + 1][j], 25, 25);
				this.mapOnChange();
				result = true;
			}
			break;
		}
		
		if (result) {
		}
		
		return result;
	}
	
    public void addKeyFrame(EventHandler<ActionEvent> e) {
    	gameloop.pause();
    	KeyFrame k = new KeyFrame(Duration.seconds(0.5), e);
    	gameloop.getKeyFrames().add(k);
    	gameloop.play();
    }
    
    public void addEnnemi(Entity e) {
    	entities.add(e);
    	addKeyFrame(event -> {
    		e.update();
    	});
    }
    
    public void playGameLoop() {
    	gameloop.setCycleCount(Timeline.INDEFINITE);
    	gameloop.play();
    }
	
}
