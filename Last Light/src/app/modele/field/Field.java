package app.modele.field;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;

public class Field {
	
	private int[][] field;
	private Tile[][] tileField;
	private int i, j; // coordonnées dans la grande map
	
	public Field(int i, int j, int fileIndex, int height, int width, ArrayList<Integer> crossableTiles) {
		this.field = readFile(fileIndex, height, width);
		this.i = i;
		this.j = j;
		
		this.tileField = new Tile[height][width];
		for (int line = 0 ; line < height ; line++) 
			for (int column = 0 ; column < width ; column++) 
				this.tileField[line][column] = new Tile(field[line][column], crossableTiles.contains(field[line][column]), line, column);
	}
	
	private int[][] readFile(int fileIndex, int height, int width) {
		int[][] field = new int[height][width];
		
		ArrayList<String> lines = new ArrayList<>();
        
        try {
        	
			File f = new File("src/map/map" + fileIndex + ".json");
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			
			try {
				
				for (int i = 0; i < 5; i++)
					lines.add(br.readLine());
				
				br.close();
				fr.close();
				
				lines.set(4, lines.get(4).substring(17, lines.get(4).length() - 2));
				@SuppressWarnings("resource")
				Scanner s = new Scanner(lines.get(4)).useDelimiter(", ");
												
				for (int i = 0; i < height; i++) {
					for (int j = 0; j < width; j++) {
						field[i][j] = s.nextInt();
					}
				}
				
				s.close();
				
			} catch (IOException e) {
				System.out.println("map" + fileIndex + " : Erreur lecture");
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("map" + fileIndex + "Fichier introuvable");
		}
		
		return field;
	}
	
	public int[][] getField() {
		return this.field;
	}
	
	public Tile getNextTile(int i, int j) {
		return this.tileField[i][j];
	}
	
	public int getFieldSize() {
		return field.length;
	}
	
	public int getI() {
		return this.i;
	}
	
	public int getJ() {
		return this.j;
	}
	
	public void makeATileCrossable(int id) {
		for (int i = 0 ; i < this.tileField.length ; i++)
			for (int j = 0 ; j < this.tileField[i].length ; j++)
				if (this.tileField[i][j].getId() == id)
					this.tileField[i][j].setCrossable();
	}
	
	public void makeATileUncrossable(int id) {
		for (int i = 0 ; i < this.tileField.length ; i++)
			for (int j = 0 ; j < this.tileField[i].length ; j++)
				if (this.tileField[i][j].getId() == id)
					this.tileField[i][j].setUncrossable();
	}
}
