package app.vue;

import app.modele.entity.Player;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ItemView extends ImageView {
	
	private Label label;
	private Player player;
	
	public ItemView(Player p, Image img, int x, int y, String l) {
		
		this.setImage(img);
		this.setTranslateX(x);
		this.setTranslateY(y);
		
		this.player = p;
		
		this.label = new Label(l);
		this.label.setTextFill(Color.web("#ffffff"));
		this.label.setFont(Font.font ("Roboto", 18));
		this.label.setTranslateX(x+30);
		this.label.setTranslateY(y+5);
	}
	
	public Label getLabel() {
		return label;
	}

}
