package app.vue.entity;

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
		this.setRotate((this.bullet.getOrientation().get()-1)*90);
	}
	
}