package app.vue;

import app.modele.entity.Player;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ItemView extends ImageView {
	
	private Player player;
	private boolean isEmpty;
	
	public ItemView(Player p, Image img, int x, int y, boolean b) {
		
		this.setImage(img);
		this.setTranslateX(x);
		this.setTranslateY(y);
		
		this.player = p;
		this.isEmpty = b;
	}
	
	public void switchIsEmpty() {
		if (this.isEmpty)
			this.isEmpty = false;
		else
			this.isEmpty = true;
	}
	
	public boolean getIsEmpty() {
		return this.isEmpty;
	}

}
