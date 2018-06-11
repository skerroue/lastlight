package app.modele;

import java.io.BufferedReader;  
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import app.modele.BFS.BFS;
import app.modele.entity.animated.AnimatedEntity;
import app.modele.entity.animated.NPC;
import app.modele.entity.animated.Player;
import app.modele.entity.animated.Rock;
import app.modele.entity.animated.Walker;
import app.modele.entity.inanimated.Button;
import app.modele.entity.inanimated.Dispenser;
import app.modele.entity.inanimated.InanimatedEntity;
import app.modele.entity.inanimated.ItemEntity;
import app.modele.field.Field;
import app.modele.field.Tile;
import app.modele.weapon.Weapon;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

public class Game {
	
	static protected ArrayList<Integer> crossableTiles;

	private Timeline gameloop;
	
	private static int[][] fieldsMap;	// contient les indices des fichiers de chaque map
								// valeurs allant de 1 a ... (0 = pas de map)
	private static Field map;
	private static Player player;
	private ObservableList<AnimatedEntity> entities;
	private ObservableList<InanimatedEntity> inanimatedEntities;
	private static BooleanProperty mapChanged;
	
	private BFS bfs;
	
	private StringProperty currentText;
	
	private PauseTransition necklaceUse;
	private PauseTransition bootsUse;
	
	public Game() {
		
		fieldsMap = readFileMaps();
		crossableTiles = readFileCrossableTiles();
		map = new Field(3, 0, fieldsMap[3][0] , 25, 25, crossableTiles);
		mapChanged = new SimpleBooleanProperty(true);
		
		this.gameloop = new Timeline();
		this.gameloop.setCycleCount(Timeline.INDEFINITE);
		this.necklaceUse = new PauseTransition(Duration.seconds(10));
		this.necklaceUse.setOnFinished(e -> {
			this.player.setNecklaceInactive();
			map.makeATileUncrossable(GameData.NECKLACE_WALL);
			});
		this.bootsUse = new PauseTransition(Duration.seconds(0.07));
		this.bootsUse.setOnFinished(e -> {
			System.out.println(this.player.getX());
			this.player.setBootsInactive();
			});
		this.currentText = new SimpleStringProperty("");
		
		this.entities = FXCollections.observableArrayList();
		this.inanimatedEntities = FXCollections.observableArrayList();
		this.player = new Player(416, 416, 3, 0, 8, 0, 6, 18);
		this.entities.add(player);
		
		this.bfs = new BFS(player, map);
		
		initializeGame();
	}
	
	public void initializeGame() {
		try {
			
			File f = new File("src/map/takenItems.txt");
			FileWriter fw = new FileWriter(f);
			BufferedWriter bw = new BufferedWriter(fw);
			
			bw.write("");
			
			bw.close();
			fw.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("takenItems : Fichier introuvable");
		} catch (IOException e) {
			System.out.println("takenItems : Erreur de lecture");
		}

		spawnEntities();
		
		KeyFrame updateEntities = new KeyFrame(Duration.seconds(0.035), e -> {	
			updateEnemies();
			player.update();
			
			if (player.getActiveWeaponIndex().get() > -1)
				for (Weapon w : this.player.getWeapons())
					w.update(entities);
			
			for (int k = 0; k < getEntities().size(); k++)
				if (getEntities().get(k).getIsDead().get()) 
					getEntities().remove(getEntities().get(k));
			
			for (int i = 0 ; i < getInanimatedEntities().size() ; i++)
				if (getInanimatedEntities().get(i).getIsDead().get())
					getInanimatedEntities().remove(getInanimatedEntities().get(i));
		});
		
		gameloop.getKeyFrames().add(updateEntities);
		
	}
	
	private int[][] readFileMaps() { 
		int[][] fieldsMap = new int [GameData.FILE_MAP_HEIGHT][GameData.FILE_MAP_WIDTH];	// taille à modifier
		        
        try {
        	
			File f = new File("src/map/maps.txt");	// nom du fichier à modifier
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			@SuppressWarnings("resource")
			Scanner s = new Scanner(br).useDelimiter(",");
			
			try {
				
				for (int i = 0; i < GameData.FILE_MAP_HEIGHT; i++) {
					for (int j = 0; j < GameData.FILE_MAP_WIDTH; j++) {	// taille à modifier
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
			@SuppressWarnings("resource")
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
	
	public static int getMapId() {
		return fieldsMap[map.getI()][map.getJ()];
	}
	
	public static Player getPlayer() {
		return player;
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
	
	public StringProperty getCurrentTextProperty() {
		return this.currentText;
	}
	
	public void mapChanged() {
		mapChanged.set(!mapChanged.get());
	}
	
	public boolean loadField(int direction) {
		int i = map.getI();
		int j = map.getJ();
		
		boolean changing = false;
		
		switch (direction) {
		case GameData.LEFT :
			if (j > 0 && fieldsMap[i][j - 1] != 0) {
				map = new Field(i, j - 1, fieldsMap[i][j - 1], 25, 25, crossableTiles);
				changing = true;
			}
			break;
		case GameData.UP :
			if (i > 0 && fieldsMap[i - 1][j] != 0) {
				map = new Field(i - 1, j, fieldsMap[i - 1][j], 25, 25, crossableTiles);
				changing = true;
			}
			break;
		case GameData.RIGHT :
			if (j < GameData.FILE_MAP_WIDTH-1 && fieldsMap[i][j + 1] != 0) {
				map = new Field(i, j + 1, fieldsMap[i][j + 1], 25, 25, crossableTiles);
				changing = true;
			}
			break;
		case GameData.DOWN :
			if (i < GameData.FILE_MAP_HEIGHT-1 && fieldsMap[i + 1][j] != 0) {
				map = new Field(i + 1, j, fieldsMap[i + 1][j], 25, 25, crossableTiles);
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
		int noMap = fieldsMap[map.getI()][map.getJ()];
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
					@SuppressWarnings("resource")
					Scanner s = new Scanner(line).useDelimiter(",");
					s.nextInt();
					
					while (s.hasNext()) {
						int nextInt = s.nextInt();
						switch (nextInt) {
						case 1 :
							this.addAnimated(GameData.ENTITY_WALKER, s.nextInt(), s.nextInt());
							break;
						case 3 :
							this.addInanimated(GameData.ENTITY_LAMP, s.nextInt(), s.nextInt(), noMap);
							break;
						case 4 :
							this.addInanimated(GameData.ENTITY_TASER, s.nextInt(), s.nextInt(), noMap);
							break;
						case 5 :
							this.addInanimated(GameData.ENTITY_SODA, s.nextInt(), s.nextInt(), noMap);
							break;
						case 6 :
							this.addAnimated(GameData.ENTITY_ROCK, s.nextInt(), s.nextInt());
							break;
						case 7 :
							this.addInanimated(GameData.ENTITY_BUTTON, s.nextInt(), s.nextInt(), noMap);
							break;
						case 8 :
							this.addAnimated(GameData.ENTITY_NPC, s.nextInt(), s.nextInt());
							break;
						case 9 :
							this.addInanimated(GameData.ENTITY_DISPENSER, s.nextInt(), s.nextInt(), noMap);
							break;
						case 10 :
							this.addInanimated(GameData.ENTITY_NECKLACE, s.nextInt(), s.nextInt(), noMap);
							break;
						case 11 :
							this.addInanimated(GameData.ENTITY_BOOTS, s.nextInt(), s.nextInt(), noMap);
							break;
						default : break;
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
	
	private boolean takenItem(String id, int noMap) {
		boolean takenItem = false;
		String line;
		
		try {
			
			File f = new File("src/map/takenItems.txt");
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			
			line = br.readLine();
			
			while (br.ready() && line.charAt(0) != Integer.toString(noMap).charAt(0))
				line = br.readLine();
						
			if (line != null && line.charAt(0) == Integer.toString(noMap).charAt(0)) {
				@SuppressWarnings("resource")
				Scanner s = new Scanner(line).useDelimiter(",");
				s.next();
				
				if (s.next().charAt(0) == id.charAt(0)) {
					takenItem = true;
				}
				
				s.close();
			}
				
			br.close();
			fr.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("takenItems : Fichier introuvable");
		} catch (IOException e) {
			System.out.println("takenItems : Erreur de lecture");
		}
		
		return takenItem;
	}
	
    public void addKeyFrame(EventHandler<ActionEvent> e, double duration) {
    	gameloop.pause();
    	KeyFrame k = new KeyFrame(Duration.seconds(duration), e);
    	gameloop.getKeyFrames().add(k);
    	gameloop.play();
    }
    
    public void addInanimated(String type, int x, int y, int noMap) {
    	switch (type) {
    	case GameData.ENTITY_LAMP :
    		if (!takenItem(type, noMap))
    			inanimatedEntities.add(new ItemEntity(type, x, y, ""));
    		break;
    	case GameData.ENTITY_TASER :
    		if (!takenItem(type, noMap))
    			inanimatedEntities.add(new ItemEntity(type, x, y, ""));
    		break;
    	case GameData.ENTITY_SODA :
    		if (!takenItem(type, noMap))
    			inanimatedEntities.add(new ItemEntity(type, x, y, ""));
    		break;
    	case GameData.ENTITY_DISPENSER :
    		inanimatedEntities.add(new Dispenser(x, y, "Voulez vous acheter une potion ?"));
    		inanimatedEntities.add(new ItemEntity(type + "Top", x, y-32, ""));
    		break;
    	case GameData.ENTITY_BUTTON :
    		// Reflechir a comment connaitre le child du boutton
    		InanimatedEntity ent = new ItemEntity("door", 512, 512, "");
    		inanimatedEntities.add(ent);
    		inanimatedEntities.add(new Button(x, y, "", ent));
    		break;
    	case GameData.ENTITY_NECKLACE :
    		inanimatedEntities.add(new ItemEntity(type, x, y, ""));
    		break;
    	case GameData.ENTITY_BOOTS :
    		inanimatedEntities.add(new ItemEntity(type, x, y, ""));
    		break;
    	default : break;
    	}
    	
    }
    
    public void addAnimated(String type, int x, int y) {
    	switch (type) {
    	case GameData.ENTITY_WALKER :
    		entities.add(new Walker(x, y, 1, 1, 4, 6, 18));
    		break;
    	case GameData.ENTITY_ROCK :
    		entities.add(new Rock(x, y));
    		break;
    	case GameData.ENTITY_NPC :
    		entities.add(new NPC(x, y, 1, 0, 4, 6, 18, "Martin est un fdp"));
    		break;
     	default : break;
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
				if (player.getX().get() == GameData.LEFT_TOP_LIMIT) {
					if (loadField(GameData.LEFT)) {
						player.setX(GameData.RIGHT_BOTTOM_LIMIT);
						this.mapChanged();
					}
				}
				else 
					player.moveLeft(entities, inanimatedEntities);
				break;
			case UP :
				if (player.getY().get() == GameData.LEFT_TOP_LIMIT) {
					if (loadField(GameData.UP)) {
						player.setY(GameData.RIGHT_BOTTOM_LIMIT);
						this.mapChanged();
					}
				}
				else 
					player.moveUp(entities, inanimatedEntities);
				break;
			case RIGHT :
				if (player.getX().get() == GameData.RIGHT_BOTTOM_LIMIT) {
					if (loadField(GameData.RIGHT)) {
						player.setX(GameData.LEFT_TOP_LIMIT);
						this.mapChanged();
					}
				}
				else 
					player.moveRight(entities, inanimatedEntities);
				break;
			case DOWN :
				if (player.getY().get() == GameData.RIGHT_BOTTOM_LIMIT) {
					if (loadField(GameData.DOWN)) {
						player.setY(GameData.LEFT_TOP_LIMIT);
						this.mapChanged();
					}
				}
				else 
					player.moveDown(entities, inanimatedEntities);
				break;
				default:
					break;
			}
	
			this.bfs.lancerBFS();
		
    	}
	    
    }
    
    public boolean playerInteraction() {
    	boolean hasInteracted = false;
    	
    	switch (this.player.getOrientation().get()) {
		case GameData.LEFT :
			for (AnimatedEntity e : entities)
	    		if (this.player.getX().get() == e.getX().get() + 32 && 
	    			this.player.getY().get() >= e.getY().get() - 31 && this.player.getY().get() <= e.getY().get() + 31) {
	    			hasInteracted = e.interact();
	    			this.currentText.set(e.getDialog());
	    		}
			for (InanimatedEntity e : inanimatedEntities)
	    		if (this.player.getX().get() == e.getX().get() + 32 && 
	    			this.player.getY().get() >= e.getY().get() - 31 && this.player.getY().get() <= e.getY().get() + 31) {
	    			hasInteracted = e.interact(this.player);
	    			this.currentText.set(e.getDialog());
	    		}
			break;
		case GameData.UP :
			for (AnimatedEntity e : entities)
	    		if (this.player.getY().get() == e.getY().get() + 32 && 
	    			this.player.getX().get() >= e.getX().get() - 31 && this.player.getX().get() <= e.getX().get() + 31) {
	    			hasInteracted = e.interact();
	    			this.currentText.set(e.getDialog());
	    		}
			for (InanimatedEntity e : inanimatedEntities)
	    		if (this.player.getY().get() == e.getY().get() + 32 && 
	    			this.player.getX().get() >= e.getX().get() - 31 && this.player.getX().get() <= e.getX().get() + 31) {
	    			hasInteracted = e.interact(this.player);
	    			this.currentText.set(e.getDialog());
	    		}
			break;
		case GameData.RIGHT :
			for (AnimatedEntity e : entities)
				if (this.player.getX().get() == e.getX().get() - 32 && 
    				this.player.getY().get() >= e.getY().get() - 31 && this.player.getY().get() <= e.getY().get() + 31) {
					hasInteracted = e.interact();
					this.currentText.set(e.getDialog());
				}
			for (InanimatedEntity e : inanimatedEntities)
				if (this.player.getX().get() == e.getX().get() - 32 && 
    				this.player.getY().get() >= e.getY().get() - 31 && this.player.getY().get() <= e.getY().get() + 31) {
					hasInteracted = e.interact(this.player);
					this.currentText.set(e.getDialog());
				}
			break;
		case GameData.DOWN :
			for (AnimatedEntity e : entities)
				if (this.player.getY().get() == e.getY().get() - 32 && 
    				this.player.getX().get() >= e.getX().get() - 31 && this.player.getX().get() <= e.getX().get() + 31) {
					hasInteracted = e.interact();
					this.currentText.set(e.getDialog());
				}
			for (InanimatedEntity e : inanimatedEntities)
				if (this.player.getY().get() == e.getY().get() - 32 && 
    				this.player.getX().get() >= e.getX().get() - 31 && this.player.getX().get() <= e.getX().get() + 31) {
					hasInteracted = e.interact(this.player);
					this.currentText.set(e.getDialog());
				}
			break;
		default :
			break;
		}
    	
    	return hasInteracted;
    	
    }
    
    public void playerUseNecklace() {
    	if (!this.player.necklaceIsActive().get() && this.player.hasNecklace()) {
    		map.makeATileCrossable(GameData.NECKLACE_WALL);
    		this.player.setNecklaceActive();
    		this.necklaceUse.play();
    	}
    }
    
    public void playerUseBoots() {
    	if (!this.player.bootsIsActive() && this.player.hasBoots()) {
    		System.out.println(this.player.getX());
    		this.player.setBootsActive();
    		this.bootsUse.play();
    	}
    }
    
    public void updateEnemies() {
    	for (int i = 1 ; i < entities.size() ; i++) 
    		if (entities.get(i).getId() != GameData.ENTITY_ROCK && entities.get(i).getId() != GameData.ENTITY_NPC) {
    			moveEnemy(entities.get(i));
    			entities.get(i).attack(entities);
    		}
    }
    
    public void moveEnemy(AnimatedEntity e) {
    	Tile nextTile = this.bfs.searchWay(e);
    	Tile enemyAt = map.getNextTile(e.getIndiceY(), e.getIndiceX());
    	
    	if (nextTile.getI() == enemyAt.getI() && nextTile.getJ() < enemyAt.getJ()) {
    		e.moveLeft(entities, inanimatedEntities);
    	}
    	if (nextTile.getI() < enemyAt.getI() && nextTile.getJ() == enemyAt.getJ()) {
    		e.moveUp(entities, inanimatedEntities);
    	}
    	if (nextTile.getI() == enemyAt.getI() && nextTile.getJ() > enemyAt.getJ()) {
    		e.moveRight(entities, inanimatedEntities);
    	}
    	if (nextTile.getI() > enemyAt.getI() && nextTile.getJ() == enemyAt.getJ()) {
    		e.moveDown(entities, inanimatedEntities);
    	}
    	
    }
	
}
