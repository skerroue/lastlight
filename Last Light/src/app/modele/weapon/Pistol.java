package app.modele.weapon;

import java.util.ArrayList;

import app.modele.entity.AnimatedEntity;
import app.modele.entity.Bullet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Pistol extends Weapon {
	
	private ObservableList<Bullet> bullets;
	private int magSize;

	public Pistol(int a, int d) {
		super(a, d);
		this.id = "pistol";
		this.bullets = FXCollections.observableArrayList();
		this.magSize = 7;
	}

	@Override
	public void attack(ObservableList<AnimatedEntity> entities, int orientation, int x, int y) {
		
		if (this.bullets.size() < this.magSize)
			this.bullets.add(new Bullet(x, y, orientation));
		
		/*
		switch (orientation) {
		case LEFT :
			for (int i = 1 ; i < entities.size() ; i++) {
	    		if (x <= entities.get(i).getX().get() + 32 && x >= entities.get(i).getX().get() - 32 && 
	    			y >= entities.get(i).getY().get() - TILE_SIZE && y <= entities.get(i).getY().get() + TILE_SIZE) {
	    			entities.get(i).loseHP(this.att.get());
	    		}
			}
			break;
		case UP :
			for (int i = 1 ; i < entities.size() ; i++)
	    		if (y <= entities.get(i).getY().get() && y >= entities.get(i).getY().get() - 32  && 
	    			x >= entities.get(i).getX().get() - TILE_SIZE && x <= entities.get(i).getX().get() + TILE_SIZE)
	    			entities.get(i).loseHP(this.att.get());
			break;
		case RIGHT :
			for (int i = 1 ; i < entities.size() ; i++) {
				if (x >= entities.get(i).getX().get() - 32 && x <= entities.get(i).getX().get() + 32 && 
		    		y >= entities.get(i).getY().get() - TILE_SIZE && y <= entities.get(i).getY().get() + TILE_SIZE)
					entities.get(i).loseHP(this.att.get());
			}
			break;
		case DOWN :
			for (int i = 1 ; i < entities.size() ; i++)
	    		if (y >= entities.get(i).getY().get() && y <= entities.get(i).getY().get() + 32 && 
	    			x >= entities.get(i).getX().get() - TILE_SIZE && x <= entities.get(i).getX().get() + TILE_SIZE)
	    			entities.get(i).loseHP(this.att.get());
			break;
		default :
			break;
			
		}
		*/
		
	}
	
	public void resetBullets() {
		if (bullets.size() > 6)
			bullets.clear();
	}

	@Override
	public ObservableList<Bullet> getBullets() {
		return this.bullets;
	}

}
