package app.modele.weapon;

import app.modele.Game;
import app.modele.GameData;
import app.modele.entity.animated.AnimatedEntity;
import app.modele.entity.animated.Bullet;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Duration;

public class Taser extends Weapon {
	
	private ObservableList<Bullet> bullets;
	private int magSize;
	private AnimatedEntity hittedEntity;
	
	private PauseTransition bufferTime;
	private boolean canShoot;

	public Taser(int a, int d) {
		super(GameData.ENTITY_TASER, a, d);
		this.bullets = FXCollections.observableArrayList();
		this.magSize = 7;
		
		this.bufferTime = new PauseTransition(Duration.seconds(0.5));
		this.bufferTime.setOnFinished(e -> this.canShoot = true);
		this.canShoot = true;
	}

	@Override
	public void attack(int orientation, int x, int y) {
		
		if (this.bullets.size() < this.magSize && this.canShoot) {
			this.addBullet(x, y, orientation);
			this.canShoot = false;
			this.bufferTime.play();
		}
		
	}
	
	@Override
	public void update() {
		
		for (Bullet b : bullets) 
			if (!b.getIsDead().get()) {
				b.update();
				this.hittedEntity = b.isCollidingWith(Game.getAnimatedEntities());
				if (this.hittedEntity != null) {
					this.hittedEntity.loseHP(this.att.get());
					b.die();
				}
				
			}
		
	}
	
	public void reload() {
		if (this.bullets.size() >= this.magSize && Game.getPlayer().loseAmmunition()) {
			killAllBullets();	
			bullets.clear();
		}
	}
	
	public boolean allBulletsDead() {
		for (Bullet b : bullets)
			if (!b.getIsDead().get())
				return false;
		
		return true;
	}

	@Override
	public ObservableList<Bullet> getBullets() {
		return this.bullets;
	}
	
	public void killAllBullets() {
		for (Bullet b : bullets)
			b.die();
	}
	
	public void addBullet(int x, int y, int orientation) {
		
		switch (orientation) {
		case GameData.LEFT 	: x -= 32; break;
		case GameData.UP 	: y -= 32; break;
		case GameData.RIGHT : x += 32; break;
		case GameData.DOWN 	: y += 32; break;
		default 	: break;
		}
		
		this.bullets.add(new Bullet(x, y, orientation));
		
	}

}
