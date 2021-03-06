package app.vue.entity;
 
import app.modele.GameData;
import app.modele.entity.animated.Bullet; 
import app.modele.entity.animated.Player;
import app.modele.weapon.Weapon;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PlayerView extends AnimatedEntityView {
	
	private Player player;
	
	private ImageView attaque;
	
	private ObservableList<BulletView> bullets;
	
	private Image tileset = new Image("file:src/img/tileset" + GameData.ENTITY_PLAYER + ".png");
	private Image tilesetLamp = new Image("file:src/img/tilesetplayer" + GameData.ENTITY_LAMP + ".png");
	private Image tilesetTaser = new Image("file:src/img/tilesetplayer" + GameData.ENTITY_TASER + ".png");

	public PlayerView(Player e) {
		super(e);
		this.player = e;
		
		this.attaque = new ImageView();
		
		this.bullets = FXCollections.observableArrayList();
		
		initializePlayer();
	}
	
	public void initializePlayer() {
		
		this.setImage(tileset);
		
		this.player.getActiveWeaponIndex().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number oldValue, Number newValue) {
				
				switch (player.getWeaponName()) {
				case GameData.ENTITY_LAMP :
					setImage(tilesetLamp);
					break;
				case GameData.ENTITY_TASER :
					setImage(tilesetTaser);
					break;
				default :
					setImage(tileset);
					break;
				}
				
				attaque.setImage(new Image("file:src/img/" + player.getWeaponName() + "attack.png"));
			}
			
		});
		
		this.player.getWeapons().addListener(new ListChangeListener<Weapon>() {

			@Override
			public void onChanged(Change<? extends Weapon> c) {
				while (c.next())
					if (c.wasAdded())
						if (player.getWeapons().get(player.getWeapons().size()-1).getId().equals(GameData.ENTITY_TASER))
							initializeBullets();
			}
			
		});
		
		this.player.necklaceIsActive().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue)
					setOpacity(0.5);
				else
					setOpacity(1);
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
	
	private void initializeBullets() {
		
		player.getBullets().addListener(new ListChangeListener<Bullet>() {

			@Override
			public void onChanged(Change<? extends Bullet> c) {
				while (c.next()) 
					if (c.wasAdded()) 
						bullets.add(new BulletView(player.getBullets().get(player.getBullets().size()-1)));
			}
			
		});
		
	}
	
	public ObservableList<BulletView> getBullets() {
		return this.bullets;
	}

}
