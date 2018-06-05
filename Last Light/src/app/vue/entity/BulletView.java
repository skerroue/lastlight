package app.vue.entity;

import java.util.ArrayList;

import app.modele.entity.Bullet;
import app.modele.entity.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class BulletView {
	
	private Player player;
	private ArrayList<Bullet> bullets;
	private ObservableList<Circle> bulletsNodes;
	
	public BulletView(Player p) {
		this.player = p;
		this.bullets = new ArrayList<>();
		this.bulletsNodes = FXCollections.observableArrayList();
	}
	
	public void update(ArrayList<EntityView> entities) {
		for (Bullet b : bullets)
			b.update(entities);
	}
	
	public void addBullet() {
		
		switch (player.getOrientation().get()) {
		case 0 :
			this.bullets.add(new Bullet((int)this.player.getX().get(), (int)this.player.getY().get()+14, this.player.getOrientation().get()));
			break;
		case 1 :
			this.bullets.add(new Bullet((int)this.player.getX().get()+14, (int)this.player.getY().get(), this.player.getOrientation().get()));
			break;
		case 2 :
			this.bullets.add(new Bullet((int)this.player.getX().get()+32, (int)this.player.getY().get()+14, this.player.getOrientation().get()));
			break;
		case 3 :
			this.bullets.add(new Bullet((int)this.player.getX().get()+14, (int)this.player.getY().get()+32, this.player.getOrientation().get()));
			break;
		default :
			break;
		}
		
		this.bulletsNodes.add(new Circle(5, Color.BLACK));
		this.bulletsNodes.get(this.bulletsNodes.size()-1).translateXProperty().bind(this.bullets.get(this.bullets.size()-1).getX());
		this.bulletsNodes.get(this.bulletsNodes.size()-1).translateYProperty().bind(this.bullets.get(this.bullets.size()-1).getY());
		
	}
	
	public ObservableList<Circle> getBulletsNodes() {
		return this.bulletsNodes;
	}
	
	public ArrayList<Bullet> getBullets() {
		return this.bullets;
	}
	
}
