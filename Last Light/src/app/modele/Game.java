package app.modele;

import java.io.BufferedReader; 
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import app.modele.entity.AnimatedEntity;
import app.modele.entity.Enemy;
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
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

public class Game {
	
	final static int LEFT = 0;
	final static int UP = 1;
	final static int RIGHT = 2;
	final static int DOWN = 3;
	
	final static int LEFT_TOP_LIMIT = 0;
	final static int RIGHT_BOTTOM_LIMIT = 768;
	
	static protected ArrayList<Integer> crossableTiles;

	private Timeline gameloop;
	private int[][] fieldsMap;	// contient les indices des fichiers de chaque map
								// valeurs allant de 1 à ... (0 = pas de map)
	private static Field map;
	private Player player;
	private ObservableList<AnimatedEntity> entities;
	private static BooleanProperty mapChanged;
	
	public Game() {
		this.gameloop = new Timeline();
		this.fieldsMap = readFileMaps();
		this.crossableTiles = readFileCrossableTiles();
		this.map = new Field(0, 0, this.fieldsMap[0][0] , 25, 25, crossableTiles);	// coordonnées à modifier
		this.player = new Player(416, 416, 3, 0, 4, 0);	// coordonnées à modifier
		this.entities = FXCollections.observableArrayList();
		this.mapChanged = new SimpleBooleanProperty(true);
		this.entities.add(player);
		spawnEntities();
	}
	
	private int[][] readFileMaps() { 
		int[][] fieldsMap = new int [1][2];	// taille à modifier
		        
        try {
        	
			File f = new File("src/map/maps.txt");	// nom du fichier à modifier
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
				System.out.println("maps : Erreur lecture");
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("maps : Fichier introuvable");
		}
		
		return fieldsMap;
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
	
	public static Field getMap() {
		return map;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public ObservableList<AnimatedEntity> getEntities() {
		return this.entities;
	}
	
	public static BooleanProperty getMapChanged() {
		return mapChanged;
	}
	
	public void mapChanged() {
		mapChanged.set(!mapChanged.get());
	}
	
	public boolean loadField(int direction) {
		int i = this.map.getI();
		int j = this.map.getJ();
		
		boolean changing = false;
		
		switch (direction) {
		case LEFT :
			if (j > 0 && this.fieldsMap[i][j - 1] != 0) {
				this.map = new Field(i, j - 1, this.fieldsMap[i][j - 1], 25, 25, crossableTiles);
				changing = true;
			}
			break;
		case UP :
			if (i > 0 && this.fieldsMap[i - 1][j] != 0) {
				this.map = new Field(i - 1, j, this.fieldsMap[i - 1][j], 25, 25, crossableTiles);
				changing = true;
			}
			break;
		case RIGHT :
			if (j < 1 && this.fieldsMap[i][j + 1] != 0) {
				this.map = new Field(i, j + 1, this.fieldsMap[i][j + 1], 25, 25, crossableTiles);
				changing = true;
			}
			break;
		case DOWN :
			if (i < 0 && this.fieldsMap[i + 1][j] != 0) {
				this.map = new Field(i + 1, j, this.fieldsMap[i + 1][j], 25, 25, crossableTiles);
				changing = true;
			}
			break;
		}
		
		if (changing) {
			this.mapChanged();
			for (int k = 1 ; k < this.entities.size() ; k++)
				this.entities.get(k).die();
			spawnEntities();
		}
		
		return changing;
	}
	
	private void spawnEntities() {
		int noMap = this.fieldsMap[this.map.getI()][this.map.getJ()];
		String line;
		
		try {
        	
			File f = new File("src/map/entityLocations.txt");
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			
			try {
				
				line = br.readLine();
				
				while (br.ready() && Integer.parseInt("" + line.charAt(0)) != noMap)
					line = br.readLine();
				
				if (Integer.parseInt("" + line.charAt(0)) == noMap) {
					Scanner s = new Scanner(line).useDelimiter(",");
					s.nextInt();
					
					while (s.hasNext())
						this.addEnnemy(s.nextInt(), s.nextInt());
					
					s.close();
				}
				
				br.close();
				fr.close();
				
			} catch (IOException e) {
				System.out.println("entityLocations : Erreur lecture");
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("entityLocations : Fichier introuvable");
		}
	}
	
    public void addKeyFrame(EventHandler<ActionEvent> e, double duration) {
    	gameloop.pause();
    	KeyFrame k = new KeyFrame(Duration.seconds(duration), e);
    	gameloop.getKeyFrames().add(k);
    	gameloop.play();
    }
    
    public void addEnnemy(int x, int y) {
    	AnimatedEntity e = new Enemy(x, y, 1, 0, 4);
    	entities.add(e);
    	addKeyFrame(event -> {
    		e.update(entities);
    	}, 0.5);
    }
    
    public void playGameLoop() {
    	gameloop.setCycleCount(Timeline.INDEFINITE);
    	gameloop.play();
    }
    
    public void pauseGameLoop() {
    	gameloop.pause();
    }
    
    public void movePlayer(KeyCode event) {
    	
    	player.setOrientation(event);
    	
    	switch (event) {
    	case LEFT :
    		if (player.getX().get() == LEFT_TOP_LIMIT) {
    			if (loadField(LEFT))
    				player.setX(RIGHT_BOTTOM_LIMIT);
    		}
    		else 
    			player.moveLeft(entities);
    		break;
    	case UP :
    		if (player.getY().get() == LEFT_TOP_LIMIT) {
    			if (loadField(UP))
    				player.setY(RIGHT_BOTTOM_LIMIT);
    		}
    		else 
    			player.moveUp(entities);
    		break;
    	case RIGHT :
    		if (player.getX().get() == RIGHT_BOTTOM_LIMIT) {
    			if (loadField(RIGHT))
    				player.setX(LEFT_TOP_LIMIT);
    		}
    		else 
    			player.moveRight(entities);
    		break;
    	case DOWN :
    		if (player.getY().get() == RIGHT_BOTTOM_LIMIT) {
    			if (loadField(DOWN))
    				player.setY(LEFT_TOP_LIMIT);
    		}
    		else 
    			player.moveDown(entities);
    		break;
		default:
			break;
    	}
    	
    }
	
}
