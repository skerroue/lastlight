package app.vue.entity;

import app.modele.GameData;
import app.modele.entity.animated.Bullet;
import javafx.scene.image.Image;

public class BulletView extends EntityView {

	private Bullet bullet;
	private static Image bulletImage = new Image("file:src/img/bullet.png");

	public BulletView(Bullet e) {
		super(e);
		this.bullet = e;
		this.setImage(bulletImage);
		this.rotate();
	}
	
	public void rotate() {
		switch (this.bullet.getOrientation().get()) {
		case GameData.LEFT :
			this.setRotate(-90);
			break;
		case GameData.UP :
			this.setRotate(0);
			break;
		case GameData.RIGHT :
			this.setRotate(90);
			break;
		case GameData.DOWN :
			this.setRotate(180);
			break;
		default : break;
		}
	}
	
}