package app.modele;

import java.io.BufferedReader; 
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import app.modele.BFS.BFS;
import app.modele.entity.AnimatedEntity;
import app.modele.entity.Rock;
import app.modele.entity.Enemy;
import app.modele.entity.Entity;
import app.modele.entity.InanimatedEntity;
import app.modele.entity.ItemEntity;
import app.modele.entity.Player;
import app.modele.entity.Walker;
import app.modele.field.Field;
import app.modele.field.Tile;
import app.modele.weapon.Weapon;
import javafx.animation.FadeTransition;
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
	
	final static int FILE_MAP_WIDTH = 3;
	final static int FILE_MAP_HEIGHT = 4;
	
	static protected ArrayList<Integer> crossableTiles;

	private Timeline gameloop;
	private float compteur;
	private int[][] fieldsMap;	// contient les indices des fichiers de chaque map
								// valeurs allant de 1 a ... (0 = pas de map)
	private static Field map;
	private Player player;
	private ObservableList<AnimatedEntity> entities;
	private ObservableList<InanimatedEntity> inanimatedEntities;
	private static BooleanProperty mapChanged;
	
	private BFS bfs;
	
	public Game() {
		this.gameloop = new Timeline();
		this.gameloop.setCycleCount(Timeline.INDEFINITE);
		this.fieldsMap = readFileMaps();
		this.crossableTiles = readFileCrossableTiles();
		this.map = new Field(3, 0, this.fieldsMap[3][0] , 25, 25, crossableTiles);	// coordonnées à modifier
		this.player = new Player(416, 416, 3, 0, 4, 0, 6, 18);	// coordonnées à modifier
		this.entities = FXCollections.observableArrayList();
		this.inanimatedEntities = FXCollections.observableArrayList();
		this.mapChanged = new SimpleBooleanProperty(true);
		this.entities.add(player);
		
		
		this.bfs = new BFS(player, map);
		
		this.compteur = 0;
		
		initializeGame();
	}
	
	public void initializeGame() {
		spawnEntities();
	
		/*
		KeyFrame compt = new KeyFrame(Duration.seconds(0.017), e -> {
	           compteur += Duration.seconds(0.017).toMillis();

	           if ((int)this.compteur % ((int)Duration.seconds(0.017).toMillis()*10) == 0) {
	               this.compteur = 0;
	               if (player.getIsAttacking().get())
	                   player.resetIsAttacking();
	           }

		});
		*/
		
		KeyFrame updateEntities = new KeyFrame(Duration.seconds(0.035), e -> {
			moveAllEnemies();
			
			for (int k = 0; k < getEntities().size(); k++)
				if (getEntities().get(k).getIsDead().get()) 
					getEntities().remove(getEntities().get(k));
			
			for (int i = 0 ; i < getInanimatedEntities().size() ; i++)
				if (getInanimatedEntities().get(i).getIsDead().get())
					getInanimatedEntities().remove(getInanimatedEntities().get(i));
		});
		
		gameloop.getKeyFrames().add(updateEntities);
		//gameloop.getKeyFrames().add(compt);
	}
	
	private int[][] readFileMaps() { 
		int[][] fieldsMap = new int [FILE_MAP_HEIGHT][FILE_MAP_WIDTH];	// taille à modifier
		        
        try {
        	
			File f = new File("src/map/maps.txt");	// nom du fichier à modifier
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			Scanner s = new Scanner(br).useDelimiter(",");
			
			try {
				
				for (int i = 0; i < FILE_MAP_HEIGHT; i++) {
					for (int j = 0; j < FILE_MAP_WIDTH; j++) {	// taille à modifier
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
	
	public ObservableList<InanimatedEntity> getInanimatedEntities() {
		return this.inanimatedEntities;
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
			if (j < FILE_MAP_WIDTH-1 && this.fieldsMap[i][j + 1] != 0) {
				this.map = new Field(i, j + 1, this.fieldsMap[i][j + 1], 25, 25, crossableTiles);
				changing = true;
			}
			break;
		case DOWN :
			if (i < FILE_MAP_HEIGHT-1 && this.fieldsMap[i + 1][j] != 0) {
				this.map = new Field(i + 1, j, this.fieldsMap[i + 1][j], 25, 25, crossableTiles);
				changing = true;
			}
			break;
		}
		
		if (changing) {
			for (int k = 1 ; k < this.entities.size() ; k++) {
				this.entities.get(k).die();
			}
			for (int l = 0 ; l < this.inanimatedEntities.size() ; l++) {
				this.inanimatedEntities.get(l).die();
			}
			
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
					
					while (s.hasNext()) {
						int nextInt = s.nextInt();
						switch (nextInt) {
						case 1 :
							this.addEnnemy("walker", s.nextInt(), s.nextInt());
							break;
						case 3 :
							if (!hasWeapon("lamp"))
								this.addInanimated(new ItemEntity("lamp", s.nextInt(), s.nextInt()));
							break;
						case 4 :
							if (!hasWeapon("pistol"))
								this.addInanimated(new ItemEntity("pistol", s.nextInt(), s.nextInt()));
							break;
						case 5 :
							this.addInanimated(new ItemEntity("soda", s.nextInt(), s.nextInt()));
							break;
						case 6 :
							this.addAnimated("rock", s.nextInt(), s.nextInt());
							break;
						default :
							break;
						}
					}
					
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
	
	private boolean hasWeapon(String id) {
		if (this.player.getWeapons().size() == 0)
			return false;
		else {
			for (Weapon e : this.player.getWeapons())
				if (e.getId().equals(id))
					return true;
			return false;
		}
	}
	
    public void addKeyFrame(EventHandler<ActionEvent> e, double duration) {
    	gameloop.pause();
    	KeyFrame k = new KeyFrame(Duration.seconds(duration), e);
    	gameloop.getKeyFrames().add(k);
    	gameloop.play();
    }
    
    public void addEnnemy(String type, int x, int y) {
    	switch (type) {
    	case "walker" :
    		entities.add(new Walker(x, y, 1, 0, 4, 6, 18));
    		break;
    	default :
    		break;
    	}
    }
    
    public void addInanimated(InanimatedEntity i) {
    	inanimatedEntities.add(i);
    }
    
    public void addAnimated(String type, int x, int y) {
    	switch (type) {
    	case "walker" :
    		entities.add(new Walker(x, y, 1, 0, 4, 6, 18));
    		break;
    	case "rock" :
    		entities.add(new Rock(x, y));
    		break;
     	default :
    		break;
    	}
    }
    
    public void playGameLoop() {
    	gameloop.setCycleCount(Timeline.INDEFINITE);
    	gameloop.play();
    }
    
    public void pauseGameLoop() {
    	gameloop.pause();
    }
    
    public void movePlayer(KeyCode event) {
	    
    	if (!this.player.getIsAttacking().get()) {
		player.setOrientation(event);

		switch (event) {
		case LEFT :
			if (player.getX().get() == LEFT_TOP_LIMIT) {
				if (loadField(LEFT)) {
					player.setX(RIGHT_BOTTOM_LIMIT);
					this.mapChanged();
				}
			}
			else 
				player.moveLeft(entities);
			break;
		case UP :
			if (player.getY().get() == LEFT_TOP_LIMIT) {
				if (loadField(UP)) {
					player.setY(RIGHT_BOTTOM_LIMIT);
					this.mapChanged();
				}
			}
			else 
				player.moveUp(entities);
			break;
		case RIGHT :
			if (player.getX().get() == RIGHT_BOTTOM_LIMIT) {
				if (loadField(RIGHT)) {
					player.setX(LEFT_TOP_LIMIT);
					this.mapChanged();
				}
			}
			else 
				player.moveRight(entities);
			break;
		case DOWN :
			if (player.getY().get() == RIGHT_BOTTOM_LIMIT) {
				if (loadField(DOWN)) {
					player.setY(LEFT_TOP_LIMIT);
					this.mapChanged();
				}
			}
			else 
				player.moveDown(entities);
			break;
			default:
				break;
		}

		this.bfs.lancerBFS();
		
	}
	    
    }
    
    public void moveAllEnemies() {
    	for (int i = 1 ; i < entities.size() ; i++) 
    		if (entities.get(i).getId() != "rock")
    			moveEnemy(entities.get(i));
    }
    
    public void moveEnemy(AnimatedEntity e) {
    	Tile temp = this.bfs.searchWay(e);
    	Tile enemyAt = this.map.getNextTile(e.getIndiceY(), e.getIndiceX());
    	
    	if (temp.getI() == enemyAt.getI() && temp.getJ() < enemyAt.getJ()) {
    		e.moveLeft(entities);
    	}
    	if (temp.getI() < enemyAt.getI() && temp.getJ() == enemyAt.getJ()) {
    		e.moveUp(entities);
    	}
    	if (temp.getI() == enemyAt.getI() && temp.getJ() > enemyAt.getJ()) {
    		e.moveRight(entities);
    	}
    	if (temp.getI() > enemyAt.getI() && temp.getJ() == enemyAt.getJ()) {
    		e.moveDown(entities);
    	}
    }
	
}
