package app.vue.entity;

import app.modele.entity.AnimatedEntity;
import app.modele.entity.Entity;
import app.modele.entity.Player;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PlayerView extends AnimatedEntityView {
	
	private Player player;
	
	private ImageView attaque;
	
	private Image tileset = new Image("file:src/img/tilesetplayer.png");
	private Image tilesetLamp = new Image("file:src/img/tilesetplayerlamp.png");

	public PlayerView(Player e) {
		super(e);
		this.player = e;
		
		this.attaque = new ImageView();
		
		initializePlayer();
	}
	
	public void initializePlayer() {
		
		this.setImage(tileset);
		
		this.player.getActiveWeaponIndex().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number oldValue, Number newValue) {
				
				switch (player.getWeaponName()) {
				case "lamp" :
					setImage(tilesetLamp);
					break;
				case "pistol" :
					setImage(tileset);
					break;
				default :
					setImage(tileset);
					break;
				}
				
				attaque.setImage(new Image("file:src/img/" + player.getWeaponName() + "attack.png"));
			}
			
		});
		
	}
	
	public void animationAttack() {
		this.player.setOrientation(this.player.getOrientation().get() + 4);
	}
	
	public void resetAnimationAttack() {
		this.player.setOrientation(this.player.getOrientation().get() - 4);
	}
	
	public ImageView getAttackImage() {
		return this.attaque;
	}

}
