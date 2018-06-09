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
		switch (this.bullet.getOrientation().get()) {
		case 0 :
			this.setRotate(-90);
			break;
		case 1 :
			this.setRotate(0);
			break;
		case 2 :
			this.setRotate(90);
			break;
		case 3 :
			this.setRotate(180);
			break;
		default : break;
		}
	}
	
}