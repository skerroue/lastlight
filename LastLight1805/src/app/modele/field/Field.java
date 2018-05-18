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
        	
			File f = new File("src/map/Map" + fileIndex + ".json");
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
				System.out.println("Erreur lecture");
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("Fichier introuvable");
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
    	switch (fieldValue) {
    	case 1 :
    		img.setViewport(new Rectangle2D(0, 0, 32, 32));
    		break;
    	case 2 :
    		img.setViewport(new Rectangle2D(32, 0, 32, 32));
    		break;
    	case 3 :
    		img.setViewport(new Rectangle2D(64, 0, 32, 32));
    		break;
    	case 4 :
    		img.setViewport(new Rectangle2D(96, 0, 32, 32));
    		break;
    	case 5 :
    		img.setViewport(new Rectangle2D(128, 0, 32, 32));
    		break;
    	case 6 :
    		img.setViewport(new Rectangle2D(160, 0, 32, 32));
    		break;
    	case 7 :
    		img.setViewport(new Rectangle2D(192, 0, 32, 32));
    		break;
    	case 8 :
    		img.setViewport(new Rectangle2D(224, 0, 32, 32));
    		break;
    	case 9 :
    		img.setViewport(new Rectangle2D(0, 32, 32, 32));
    		break;
    	case 10 :
    		img.setViewport(new Rectangle2D(32, 32, 32, 32));
    		break;
    	case 11 :
    		img.setViewport(new Rectangle2D(64, 32, 32, 32));
    		break;
    	case 12 :
    		img.setViewport(new Rectangle2D(96, 32, 32, 32));
    		break;
    	case 13 :
    		img.setViewport(new Rectangle2D(128, 32, 32, 32));
    		break;
    	case 14 :
    		img.setViewport(new Rectangle2D(160, 32, 32, 32));
    		break;
    	case 15 :
    		img.setViewport(new Rectangle2D(192, 32, 32, 32));
    		break;
    	case 16 :
    		img.setViewport(new Rectangle2D(224, 32, 32, 32));
    		break;
    	case 17 :
    		img.setViewport(new Rectangle2D(0, 64, 32, 32));
    		break;
    	case 18 :
    		img.setViewport(new Rectangle2D(32, 64, 32, 32));
    		break;
    	case 19 :
    		img.setViewport(new Rectangle2D(64, 64, 32, 32));
    		break;
    	case 20 :
    		img.setViewport(new Rectangle2D(96, 64, 32, 32));
    		break;
    	}

    	return img;
    }
	
}
