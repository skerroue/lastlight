package app.vue;

import app.modele.entity.AnimatedEntity;
import app.modele.entity.Entity;
import app.modele.entity.Player;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PlayerView extends EntityView {
	
	private ImageView attaque;
	
	private Image tileset = new Image("file:src/img/tilesetplayer.png");
	private Image tilesetLamp = new Image("file:src/img/tilesetplayerlamp.png");

	public PlayerView(Player e) {
		super(e);
		this.attaque = new ImageView();
		
		initializePlayer();
	}
	
	public void initializePlayer() {
		
		this.player.getActiveWeaponIndex().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number oldValue, Number newValue) {
				switch (newValue.intValue()) {
				case 0 :
					setImage(tileset);
				case 1 :
					setImage(tilesetLamp);
					break;
				case 2 :
					break;
				case 3 :
					break;
				default :
					break;
				}
				
				attaque.setImage(new Image("file:src/img/" + player.getWeapons().get(player.getActiveWeaponIndex().get()-1).getId() + "attack.png"));
			}
			
		});
		
	}
	
	public ImageView getAttackImage() {
		return this.attaque;
	}

}
