package app.modele.entity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import app.modele.Game;
import app.modele.weapon.Lamp;
import app.modele.weapon.Pistol;
import javafx.beans.property.SimpleIntegerProperty;

public class ItemEntity extends InanimatedEntity {

	public ItemEntity(String id, int x, int y) {
		super(id, x, y);
	}
	
	// Dumb af cette methode mais est construite dans l'id�e o� une arme spawnera une et une seule fois dans le jeu
	public void interact(Player p) {
		switch (this.id) {
		case "lamp" :
			p.getWeapons().add(new Lamp(1, 1));
			this.die();
			break;
		case "pistol" :
			p.getWeapons().add(new Pistol(1, 1));
			this.die();
			break;
		case "soda" :
			if (p.getPotion().getValue() < 3) {
				p.earnPotion();
				this.die();
			}
			break;
		default :
			break;
		}
		
		try {
			
			File f = new File("src/map/takenItems.txt");
			FileWriter fw = new FileWriter(f, true);
			BufferedWriter bw = new BufferedWriter(fw);
			
			bw.write(Game.getMapId() + "," + this.id.charAt(0) + "\n");
				
			bw.close();
			fw.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("takenItems : Fichier introuvable");
		} catch (IOException e) {
			System.out.println("takenItems : Erreur de lecture");
		}
	}

}
