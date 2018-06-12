package app.modele;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class GameData {
	
	public final static int TILE_SIZE = 32; 
	
	public final static int LEFT = 0;
	public final static int UP = 1;
	public final static int RIGHT = 2;
	public final static int DOWN = 3;
	
	public final static int LEFT_TOP_LIMIT = 0;
	public final static int RIGHT_BOTTOM_LIMIT = 768;
	
	final static int FILE_MAP_WIDTH = 6;
	final static int FILE_MAP_HEIGHT = 5;
	
	public final static double NECKLACE_TIME = 10;
	public final static int NECKLACE_WALL = 28;
	
	public final static String ENTITY_WALKER = "walker";
	final static String ENTITY_FLYING = "flying";
	public final static String ENTITY_LAMP = "lamp";
	public final static String ENTITY_TASER = "taser";
	public final static String ENTITY_SODA = "soda";
	public final static String ENTITY_ROCK = "rock";
	public final static String ENTITY_DOOR = "door";
	public final static String ENTITY_BUTTON = "button";
	public final static String ENTITY_NPC = "npc";
	public final static String ENTITY_DISPENSER = "dispenser";
	public final static String ENTITY_NECKLACE = "necklace";
	public final static String ENTITY_BOOTS = "boots";
	public final static String ENTITY_BULLET = "bullet";
	public final static String ENTITY_PLAYER = "player";
	
	public static ArrayList<String> ENEMIES_ID;
	private final String[] ENEMIES_ID_ARRAY = {ENTITY_WALKER, ENTITY_FLYING};
	
	public static ArrayList<Integer> crossableTiles;
	
	public static int[][] mapsOfMap;
	public final static int STARTING_MAP_LINE = 4;
	public final static int STARTING_MAP_COLUMN = 3;
	
	public GameData() {
		this.ENEMIES_ID = new ArrayList<String>();
		for (int i = 0 ; i < this.ENEMIES_ID_ARRAY.length ; i++)
			this.ENEMIES_ID.add(ENEMIES_ID_ARRAY[i]);
		
		this.crossableTiles = readFileCrossableTiles();
		this.mapsOfMap = readMapOfMapsFile();
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
	
	private int[][] readMapOfMapsFile() { 
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

}
