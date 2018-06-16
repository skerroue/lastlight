package app.modele;

import java.io.BufferedReader;    
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import app.modele.BFS.BFS;
import app.modele.entity.animated.AnimatedEntity;
import app.modele.entity.animated.Flying;
import app.modele.entity.animated.NPC;
import app.modele.entity.animated.Player;
import app.modele.entity.animated.Rock;
import app.modele.entity.animated.Walker;
import app.modele.entity.inanimated.Button;
import app.modele.entity.inanimated.Dispenser;
import app.modele.entity.inanimated.InanimatedEntity;
import app.modele.entity.inanimated.ItemEntity;
import app.modele.field.Field;
import app.modele.weapon.Weapon;
import javafx.animation.KeyFrame;
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

	private GameData gameData;

	private Timeline gameloop;
	
	private BFS bfs;
	
	private static Field map;
	private BooleanProperty mapChanged;
	private Player player;
	private ObservableList<AnimatedEntity> animatedEntities;
	private ObservableList<InanimatedEntity> inanimatedEntities;
	
	// variable static permettant a tout les ennemis de la map de reperer le joueur une fois qu'un l'a apercu
	private static boolean playerIsDetected;
	
	private StringProperty currentText;
	
	public Game() {
		
		this.gameloop = new Timeline();
		this.gameloop.setCycleCount(Timeline.INDEFINITE);
		
		this.currentText = new SimpleStringProperty("");
		
		this.gameData = new GameData();
		
		map = new Field(GameData.STARTING_MAP_LINE, GameData.STARTING_MAP_COLUMN, GameData.mapsOfMap[GameData.STARTING_MAP_LINE][GameData.STARTING_MAP_COLUMN], 25, 25, GameData.crossableTiles);
		this.mapChanged = new SimpleBooleanProperty(true);
		
		this.animatedEntities = FXCollections.observableArrayList();
		this.inanimatedEntities = FXCollections.observableArrayList();
		this.player = new Player(416, 416, 3, 0, GameData.PLAYER_SPEED, 0, 6, 18);
		this.animatedEntities.add(player);
		
		this.bfs = new BFS(player, map);
		playerIsDetected = false;
		
		initializeGame();
	}
	
	private void initializeGame() {
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
			
			for (AnimatedEntity animated : animatedEntities)
				animated.update(animatedEntities, inanimatedEntities, bfs);
			
			if (player.getActiveWeaponIndex().get() > -1)
				for (Weapon w : this.player.getWeapons())
					w.update(animatedEntities);
			
			for (int k = 0; k < getAnimatedEntities().size(); k++)
				if (getAnimatedEntities().get(k).getIsDead().get()) 
					getAnimatedEntities().remove(getAnimatedEntities().get(k));
			
			for (int i = 0 ; i < getInanimatedEntities().size() ; i++)
				if (getInanimatedEntities().get(i).getIsDead().get())
					getInanimatedEntities().remove(getInanimatedEntities().get(i));
		});
		
		gameloop.getKeyFrames().add(updateEntities);
		
	}
	
	public static Field getMap() {
		return map;
	}
	
	public static int getMapId() {
		return GameData.mapsOfMap[map.getI()][map.getJ()];
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public ObservableList<AnimatedEntity> getAnimatedEntities() {
		return this.animatedEntities;
	}
	
	public ObservableList<InanimatedEntity> getInanimatedEntities() {
		return this.inanimatedEntities;
	}
	
	public BooleanProperty getMapChanged() {
		return this.mapChanged;
	}
	
	public StringProperty getCurrentTextProperty() {
		return this.currentText;
	}
	
	public BFS getBFS() {
		return this.bfs;
	}
	
	public GameData getGameData() {
		return this.gameData;
	}
	
	public void mapChanged() {
		mapChanged.set(!mapChanged.get());
	}
	
	private boolean loadField(int direction) {
		int i = map.getI();
		int j = map.getJ();
		
		boolean changing = false;
		
		switch (direction) {
		case GameData.LEFT :
			if (j > 0 && GameData.mapsOfMap[i][j - 1] != 0) {
				map = new Field(i, j - 1, GameData.mapsOfMap[i][j - 1], 25, 25, GameData.crossableTiles);
				changing = true;
			}
			break;
		case GameData.UP :
			if (i > 0 && GameData.mapsOfMap[i - 1][j] != 0) {
				map = new Field(i - 1, j, GameData.mapsOfMap[i - 1][j], 25, 25, GameData.crossableTiles);
				changing = true;
			}
			break;
		case GameData.RIGHT :
			if (j < GameData.FILE_MAP_WIDTH - 1 && GameData.mapsOfMap[i][j + 1] != 0) {
				map = new Field(i, j + 1, GameData.mapsOfMap[i][j + 1], 25, 25, GameData.crossableTiles);
				changing = true;
			}
			break;
		case GameData.DOWN :
			if (i < GameData.FILE_MAP_HEIGHT - 1 && GameData.mapsOfMap[i + 1][j] != 0) {
				map = new Field(i + 1, j, GameData.mapsOfMap[i + 1][j], 25, 25, GameData.crossableTiles);
				changing = true;
			}
			break;
		}
		
		if (changing) {
			setPlayerUndetected();
			
			for (int indice = 1 ; indice < animatedEntities.size() ; indice++) 
				this.animatedEntities.get(indice).die();
			
			for (int l = 0 ; l < this.inanimatedEntities.size() ; l++) 
				this.inanimatedEntities.get(l).die();
			
			spawnEntities();
		}
		
		return changing;
	}
	
	@SuppressWarnings("resource")
	private void spawnEntities() {
		int noMap = GameData.mapsOfMap[map.getI()][map.getJ()];
		String line;
		
		try {
        	
			File f = new File("src/map/entityLocations.txt");
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			
			try {
				
				line = br.readLine();
				String str = "" + line.charAt(0);
				int i = 1;
				while (line.charAt(i) != ',') {
					str = str + line.charAt(i);
					i++;
				}
				int currentInt = Integer.parseInt(str);
				
				while (br.ready() && currentInt != noMap) {
					line = br.readLine();
					str = "" + line.charAt(0);
					i = 1;
					while (line.charAt(i) != ',') {
						str = str + line.charAt(i);
						i++;
					}
					currentInt = Integer.parseInt(str);
				}
				
				if (currentInt == noMap) {
					Scanner s = new Scanner(line).useDelimiter(",");
					s.nextInt();
					
					while (s.hasNext()) {
						int nextInt = s.nextInt();
						switch (nextInt) {
						case 1 :
							this.addAnimated(GameData.ENTITY_WALKER, s.nextInt(), s.nextInt(), noMap);
							break;
						case 2 :
							this.addAnimated(GameData.ENTITY_FLYING, s.nextInt(), s.nextInt(), noMap);
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
							this.addInanimated(GameData.ENTITY_DOOR, s.nextInt(), s.nextInt(), noMap);
							break;
						case 7 :
							this.addInanimated(GameData.ENTITY_BUTTON, s.nextInt(), s.nextInt(), noMap);
							break;
						case 8 :
							this.addAnimated(GameData.ENTITY_ROCK, s.nextInt(), s.nextInt(), noMap);
							break;
						case 9 :
							this.addAnimated(GameData.ENTITY_NPC, s.nextInt(), s.nextInt(), noMap);
							break;
						case 10 :
							this.addInanimated(GameData.ENTITY_DISPENSER, s.nextInt(), s.nextInt(), noMap);
							break;
						case 11 :
							this.addInanimated(GameData.ENTITY_NECKLACE, s.nextInt(), s.nextInt(), noMap);
							break;
						case 12 :
							this.addInanimated(GameData.ENTITY_BOOTS, s.nextInt(), s.nextInt(), noMap);
							break;
						case 13 :
							this.addInanimated(GameData.ENTITY_HEART, s.nextInt(), s.nextInt(), noMap);
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
	
    private void addInanimated(String type, int x, int y, int noMap) {
    	switch (type) {
    	case GameData.ENTITY_DISPENSER :
    		inanimatedEntities.add(new Dispenser(x, y, "Voulez vous acheter une potion ?"));
    		inanimatedEntities.add(new ItemEntity(type + "Top", x, y-32, ""));
    		break;
    	case GameData.ENTITY_BUTTON :
    		inanimatedEntities.add(new Button(x, y, "", inanimatedEntities.get(inanimatedEntities.size() - 1)));
    		break;
    	default : 
    		if (!takenItem(type, noMap))
    			inanimatedEntities.add(new ItemEntity(type, x, y, ""));
    		break;
    	}
    	
    }
    
    private void addAnimated(String type, int x, int y, int noMap) {
    	switch (type) {
    	case GameData.ENTITY_WALKER :
    		animatedEntities.add(new Walker(x, y, 1, 1, GameData.ENEMY_SPEED, 6, 18));
    		break;
    	case GameData.ENTITY_FLYING :
    		animatedEntities.add(new Flying(x, y, 1, 1, GameData.ENEMY_SPEED, 4, 12));
    		break;
    	case GameData.ENTITY_ROCK :
    		animatedEntities.add(new Rock(x, y));
    		break;
    	case GameData.ENTITY_NPC :
    		animatedEntities.add(new NPC(x, y, 1, 0, GameData.PLAYER_SPEED, 6, 18, readNPCDialog(noMap)));
    		break;
     	default : break;
    	}
    	
    }
	
	@SuppressWarnings("resource")
	private boolean takenItem(String id, int noMap) {
		boolean takenItem = false;
		String line;
		
		try {
			
			File f = new File("src/map/takenItems.txt");
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			
			line = br.readLine();
			Scanner current;
			int currentInt = 0;
			
			if (line != null) {
				current = new Scanner(line).useDelimiter(",");
				currentInt = current.nextInt();
			}
			
			while (br.ready() && currentInt != noMap) {
				line = br.readLine();
				current = new Scanner(line).useDelimiter(",");
				currentInt = current.nextInt();
			}
			
			if (currentInt == noMap) {
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
	
	@SuppressWarnings("resource")
	private String readNPCDialog(int noMap) {
		String NPCDialog = "";
		String line;
		
		try {
			
			File f = new File("src/map/NPCDialog.txt");
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			
			line = br.readLine();
			Scanner current;
			int currentInt = 0;
			
			if (line != null) {
				current = new Scanner(line).useDelimiter(",");
				currentInt = current.nextInt();
			}
			
			while (br.ready() && currentInt != noMap) {
				line = br.readLine();
				current = new Scanner(line).useDelimiter(",");
				currentInt = current.nextInt();
			}
			
			if (currentInt == noMap) {
				Scanner s = new Scanner(line).useDelimiter(",");
				s.next();
				
				NPCDialog = s.next();
				
				s.close();
			}
				
			br.close();
			fr.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("NPCDialog : Fichier introuvable");
		} catch (IOException e) {
			System.out.println("NPCDialog : Erreur de lecture");
		}
		
		return NPCDialog;
	}
	
    public void addKeyFrame(EventHandler<ActionEvent> e, double duration) {
    	gameloop.pause();
    	KeyFrame k = new KeyFrame(Duration.seconds(duration), e);
    	gameloop.getKeyFrames().add(k);
    	gameloop.play();
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
					player.moveLeft(animatedEntities, inanimatedEntities);
				break;
			case UP :
				if (player.getY().get() == GameData.LEFT_TOP_LIMIT) {
					if (loadField(GameData.UP)) {
						player.setY(GameData.RIGHT_BOTTOM_LIMIT);
						this.mapChanged();
					}
				}
				else 
					player.moveUp(animatedEntities, inanimatedEntities);
				break;
			case RIGHT :
				if (player.getX().get() == GameData.RIGHT_BOTTOM_LIMIT) {
					if (loadField(GameData.RIGHT)) {
						player.setX(GameData.LEFT_TOP_LIMIT);
						this.mapChanged();
					}
				}
				else 
					player.moveRight(animatedEntities, inanimatedEntities);
				break;
			case DOWN :
				if (player.getY().get() == GameData.RIGHT_BOTTOM_LIMIT) {
					if (loadField(GameData.DOWN)) {
						player.setY(GameData.LEFT_TOP_LIMIT);
						this.mapChanged();
					}
				}
				else 
					player.moveDown(animatedEntities, inanimatedEntities);
				break;
				default:
					break;
			}
	
			bfs.lancerBFS();
		
    	}
	    
    }
    
    public boolean playerInteraction() {
    	boolean hasInteracted = false;
    	
    	switch (player.getOrientation().get()) {
		case GameData.LEFT :
			for (AnimatedEntity e : animatedEntities)
	    		if (player.getX().get() == e.getX().get() + 32 && 
	    			player.getY().get() >= e.getY().get() - 31 && player.getY().get() <= e.getY().get() + 31) {
	    			hasInteracted = e.interact();
	    			this.currentText.set(e.getDialog());
	    		}
			for (InanimatedEntity e : inanimatedEntities)
	    		if (player.getX().get() == e.getX().get() + 32 && 
	    			player.getY().get() >= e.getY().get() - 31 && player.getY().get() <= e.getY().get() + 31) {
	    			hasInteracted = e.interact(this.player);
	    			this.currentText.set(e.getDialog());
	    		}
			break;
		case GameData.UP :
			for (AnimatedEntity e : animatedEntities)
	    		if (player.getY().get() == e.getY().get() + 32 && 
	    			player.getX().get() >= e.getX().get() - 31 && player.getX().get() <= e.getX().get() + 31) {
	    			hasInteracted = e.interact();
	    			this.currentText.set(e.getDialog());
	    		}
			for (InanimatedEntity e : inanimatedEntities)
	    		if (player.getY().get() == e.getY().get() + 32 && 
	    			player.getX().get() >= e.getX().get() - 31 && player.getX().get() <= e.getX().get() + 31) {
	    			hasInteracted = e.interact(this.player);
	    			this.currentText.set(e.getDialog());
	    		}
			break;
		case GameData.RIGHT :
			for (AnimatedEntity e : animatedEntities)
				if (player.getX().get() == e.getX().get() - 32 && 
    				player.getY().get() >= e.getY().get() - 31 && player.getY().get() <= e.getY().get() + 31) {
					hasInteracted = e.interact();
					this.currentText.set(e.getDialog());
				}
			for (InanimatedEntity e : inanimatedEntities)
				if (player.getX().get() == e.getX().get() - 32 && 
    				player.getY().get() >= e.getY().get() - 31 && player.getY().get() <= e.getY().get() + 31) {
					hasInteracted = e.interact(this.player);
					this.currentText.set(e.getDialog());
				}
			break;
		case GameData.DOWN :
			for (AnimatedEntity e : animatedEntities)
				if (player.getY().get() == e.getY().get() - 32 && 
    				player.getX().get() >= e.getX().get() - 31 && player.getX().get() <= e.getX().get() + 31) {
					hasInteracted = e.interact();
					this.currentText.set(e.getDialog());
				}
			for (InanimatedEntity e : inanimatedEntities)
				if (player.getY().get() == e.getY().get() - 32 && 
    				player.getX().get() >= e.getX().get() - 31 && player.getX().get() <= e.getX().get() + 31) {
					hasInteracted = e.interact(this.player);
					this.currentText.set(e.getDialog());
				}
			break;
		default :
			break;
		}
    	
    	return hasInteracted;
    	
    }
    
	public static boolean playerIsDetected() {
		return playerIsDetected;
	}
	
	public static void setPlayerDetected() {
		playerIsDetected = true;
	}
	
	public static void setPlayerUndetected() {
		playerIsDetected = false;
	}
	
}
