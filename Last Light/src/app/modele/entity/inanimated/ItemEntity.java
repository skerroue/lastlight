package app.modele.entity.inanimated;

import java.io.BufferedWriter; 
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import app.modele.Game;
import app.modele.entity.animated.Player;
import app.modele.weapon.Lamp;
import app.modele.weapon.Taser;

public class ItemEntity extends InanimatedEntity {

	public ItemEntity(String id, int x, int y, String dialog) {
		super(id, x, y, dialog);
	}
	
	// Dumb af cette methode mais est construite dans l'id�e o� une arme spawnera une et une seule fois dans le jeu
	public boolean interact(Player p) {
		boolean hasInteracted = false;
		
		switch (this.id) {
		case "lamp" :
			p.getWeapons().add(new Lamp(1, 1));
			this.die();
			hasInteracted = true;
			break;
		case "taser" :
			p.getWeapons().add(new Taser(1, 1));
			this.die();
			hasInteracted = true;
			break;
		case "soda" :
			if (p.getPotion().getValue() < 3) {
				p.earnPotion();
				this.die();
			}
			hasInteracted = true;
			break;
		case "necklace" :
			p.setNecklace(true);
			this.die();
			hasInteracted = true;
			break;
		case "boots" :
			p.setBoots(true);
			this.die();
			hasInteracted = true;
			break;
 		default : break;
		}
		
		// Remplis le fichier pour savoir si un item a ete pris
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
		
		return hasInteracted;
	}

}
