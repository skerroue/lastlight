package app.modele.weapon;

import java.util.ArrayList;

import app.modele.entity.AnimatedEntity;
import app.modele.entity.Bullet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Pistol extends Weapon {
	
	private ObservableList<Bullet> bullets;
	private int magSize;
	private AnimatedEntity hittedEntity;

	public Pistol(int a, int d) {
		super(a, d);
		this.id = "pistol";
		this.bullets = FXCollections.observableArrayList();
		this.magSize = 7;
	}

	@Override
	public void attack(ObservableList<AnimatedEntity> entities, int orientation, int x, int y) {
		
		if (this.bullets.size() < this.magSize)
			this.addBullet(x, y, orientation);
		
	}
	
	@Override
	public void update(ObservableList<AnimatedEntity> entities) {
		
		for (Bullet b : bullets) 
			if (!b.getIsDead().get()) {
				b.update();
				this.hittedEntity = b.isCollidingWith(entities);
				if (this.hittedEntity != null) {
					this.hittedEntity.loseHP(this.att.get());
					b.die();
				}
				
			}
		
	}
	
	public void reload() {
		if (bullets.size() > 6 && this.allBulletsDead())
			bullets.clear();
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
	
	public boolean canShoot() {
		if (this.bullets.size() > 0)
			return this.bullets.get(this.bullets.size()-1).getIsDead().get()?true:false;
		else return true;
	}
	
	public void addBullet(int x, int y, int orientation) {
		
		switch (orientation) {
		case LEFT 	: x -= 32; break;
		case UP 	: y -= 32; break;
		case RIGHT 	: x += 32; break;
		case DOWN 	: y += 32; break;
		default 	: break;
		}
		
		this.bullets.add(new Bullet(x, y, orientation));
		
	}

}
