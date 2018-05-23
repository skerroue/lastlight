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
	private int i, j; // coordonnées dans la grande map
	
	public Field(int i, int j, int fileIndex, int height, int width) {
		this.field = readFile(fileIndex, height, width);
		this.i = i;
		this.j = j;
	}
	
	private int[][] readFile(int fileIndex, int height, int width) {
		int[][] field = new int[height][width];
		
		ArrayList<String> lines = new ArrayList<>();
        
        try {
        	
			File f = new File("src/map/map" + fileIndex + "test.json");
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			
			try {
				
				for (int i = 0; i < 5; i++)
					lines.add(br.readLine());
				
				br.close();
				fr.close();
				
				lines.set(4, lines.get(4).substring(17, lines.get(4).length() - 2));
				Scanner s = new Scanner(lines.get(4)).useDelimiter(", ");
												
				for (int i = 0; i < 25; i++) {
					for (int j = 0; j < 25; j++) {
						field[i][j] = s.nextInt();
					}
				}
				
				s.close();
				
			} catch (IOException e) {
				System.out.println("map" + fileIndex + " : Erreur lecture");
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("map" + fileIndex + " : Fichier introuvable");
		}
		
		return field;
	}
	
	public int[][] getField() {
		return this.field;
	}
	
	public int getI() {
		return this.i;
	}
	
	public int getJ() {
		return this.j;
	}
	
	public ImageView intToTiles(ImageView img, int fieldValue) {
    	
		int x = 32 * ((fieldValue-1)%8);
		int y = 32 * ((fieldValue-1)/8);
		img.setViewport(new Rectangle2D(x, y, 32, 32));

    	return img;
    }
	
}
