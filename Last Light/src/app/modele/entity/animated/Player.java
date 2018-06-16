 package app.modele.entity.animated;

import app.modele.Game;
import app.modele.GameData;
import app.modele.entity.inanimated.InanimatedEntity;
import app.modele.weapon.Weapon;
import javafx.animation.PauseTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Duration;

public class Player extends AnimatedEntity {
	
	private IntegerProperty maxHP;
	private IntegerProperty potentialHP;
	
	private IntegerProperty maxPotion;
	private IntegerProperty potion;
	
	private IntegerProperty maxMoney;
	private IntegerProperty money;
	
	private BooleanProperty boots;
	private boolean bootsIsActive;
	private int bootsCount;
	private final int maxDash = 2;
	private final int velocityDash = 32;
	
	private boolean necklace;
	private BooleanProperty necklaceIsActive;
	private boolean canUseNecklace;
	private PauseTransition necklaceUse;
	private PauseTransition necklaceDownTime;
	
	private ObservableList<Weapon> weapons;
	private IntegerProperty activeWeaponIndex;
	
	private IntegerProperty ammunition;
	private StringProperty ammunitionString;
	
	public Player(int x, int y, int pv, int att, int v, int m, int nb, int fmax) {
		super(GameData.ENTITY_PLAYER, x, y, pv, att, v, nb, fmax);
		
		this.maxPotion = new SimpleIntegerProperty(3);
		this.potion = new SimpleIntegerProperty(0);
		this.maxMoney = new SimpleIntegerProperty(5);
		this.money = new SimpleIntegerProperty(m);
		this.maxHP = new SimpleIntegerProperty(3);
		this.potentialHP = new SimpleIntegerProperty(6);
		
		this.boots = new SimpleBooleanProperty(false);
		this.bootsIsActive = false;
		this.bootsCount = 0;
		
		this.necklace = false;
		this.necklaceIsActive = new SimpleBooleanProperty(false);
		this.canUseNecklace = true;
		this.necklaceUse = new PauseTransition(Duration.seconds(GameData.NECKLACE_TIME));
		this.necklaceUse.setOnFinished(e -> {
			this.setNecklaceInactive();
			Game.getMap().makeATileUncrossable(GameData.NECKLACE_WALL);
			this.canUseNecklace = false;
			this.necklaceDownTime.play();
		});
		this.necklaceDownTime = new PauseTransition(Duration.seconds(GameData.NECKLACE_DOWNTIME));
		this.necklaceDownTime.setOnFinished(e -> this.canUseNecklace = true);
		
		
		this.weapons = FXCollections.observableArrayList();
		this.activeWeaponIndex = new SimpleIntegerProperty(-1);
		this.ammunition = new SimpleIntegerProperty(0);
		this.ammunitionString = new SimpleStringProperty("x " + 0);
	}
	
	public IntegerProperty getMaxPotion() {
		return this.maxPotion;
	}
	
	public IntegerProperty getPotion() {
		return this.potion;
	}
	
	public IntegerProperty getMaxMoney() {
		return this.maxMoney;
	}
	
	public IntegerProperty getMoney() {
		return this.money;
	}
	
	public IntegerProperty getMaxHP() {
		return maxHP;
	}
	
	public IntegerProperty getPotentialHP() {
		return potentialHP;
	}
	
	public void unlockHeart() {
		if (this.maxHP.get() + 1 < this.potentialHP.get()) {
			this.maxHP.set(this.maxHP.get() + 1);
			this.hp.set(this.hp.getValue() + 1);
		}
	}
	
	public void setNecklace(boolean b) {
		this.necklace = b;
	}
	
	public boolean hasNecklace() {
		return this.necklace;
	}
	
	public BooleanProperty necklaceIsActive() {
		return this.necklaceIsActive;
	}
	
	public void setNecklaceActive() {
		this.necklaceIsActive.set(true);
	}
	
	public void setNecklaceInactive() {
		this.necklaceIsActive.set(false);
	}
	
    public void useNecklace() {
    	if (!this.necklaceIsActive().get() && this.hasNecklace() && this.canUseNecklace) {
    		Game.getMap().makeATileCrossable(GameData.NECKLACE_WALL);
    		this.setNecklaceActive();
    		this.necklaceUse.play();
    	}
    }
	
	public void setBoots(boolean b) {
		this.boots.set(b);
	}
	
	public boolean hasBoots() {
		return this.boots.get();
	}
	
	public boolean bootsIsActive() {
		return this.bootsIsActive;
	}
	
	public void setBootsActive() {
		this.bootsIsActive = true;
	}
	
	public void setBootsInactive() {
		this.bootsIsActive = false;
	}
	
    public void useBoots() {
    	if (!this.bootsIsActive() && this.hasBoots())
    		this.setBootsActive();
    }
	
	public ObservableList<Weapon> getWeapons() {
		return this.weapons;
	}
	
	public void reload() {
		if (this.activeWeaponIndex.get() != -1)
			this.weapons.get(this.activeWeaponIndex.get()).reload();
	}
	
	public ObservableList<Bullet> getBullets() {
		for (Weapon w : this.weapons)
			if (w.getId().equals(GameData.ENTITY_TASER))
				return w.getBullets();
		
		return null;
	}
	
	public IntegerProperty getActiveWeaponIndex() {
		return this.activeWeaponIndex;
	}
	
	public String getWeaponName() {
		if (this.weapons.size() > 0  && this.activeWeaponIndex.get() > -1)
			return this.weapons.get(this.activeWeaponIndex.get()).getId();
		
		return "default";
	}
	
	public void nextWeapon() {
		if (this.weapons.size() > 0) {
			if (this.activeWeaponIndex.get() + 1 < this.weapons.size()) 
				this.activeWeaponIndex.set(this.activeWeaponIndex.get() + 1);
			else 
				this.activeWeaponIndex.set(0);
		}
	}
	
	public IntegerProperty getAmmunition() {
		return this.ammunition;
	}
	
	public StringProperty getAmmunitionProperty() {
		return this.ammunitionString;
	}
	
	public void earnAmmunition() {
		this.ammunition.set(this.ammunition.get() + 1);
		this.ammunitionString.set("x " + this.ammunition.get());
	}
	
	public boolean loseAmmunition() {
		if (this.ammunition.get() - 1 < 0)
			return false;
		
		this.ammunition.set(this.ammunition.get() - 1);
		this.ammunitionString.set("x " + this.ammunition.get());
		return true;
	}
	
	// Gagne une potion 1 à 1
	public void earnPotion() {
		if (this.potion.get() < 3)
			this.potion.set(this.potion.getValue() + 1);
	}
	
	public boolean buyPotion() {
		if (this.money.get() > 1 && this.potion.get() < 3) {
			this.earnPotion();
			this.spendMoney(2);
			return true;
		}
		else 
			return false;
	}
	
	// TODO
	public void usePotion() {
		if (this.potion.getValue() > 0 && this.hp.getValue() < this.maxHP.getValue() && this.hp.get() > 0) {
			this.potion.set(this.potion.getValue() - 1);
			this.hp.set(this.maxHP.get());
		}
	}
	
	public void earnMoney(int a) {
		this.money.set(this.money.getValue() + a);
	}
	
	// TODO Vérifier que l'on ne passe pas en négatif
	public void spendMoney(int a) {
		if (this.money.get() - a < 0)
			throw new Error("Player money negative");
		
		this.money.set(this.money.getValue() - a);
	}
	
	public void attack() {
		
		if (this.weapons.size() > 0  && this.activeWeaponIndex.get() > -1) {
			this.isAttacking.set(true);
			this.weapons.get(this.activeWeaponIndex.get()).attack(this.orientation.get(), (int)this.getX().get(), (int)this.getY().get());
		}
		
	}
	
	public void update() {
		if (this.bootsIsActive) {
			int space = canMoveDash(Game.getAnimatedEntities(), Game.getInanimatedEntities());
			switch (this.orientation.get()) {
			case LEFT 	: 
				x.set(x.get() - space);
				break;
			case UP 	: 
				y.set(y.get() - space);
				break;
			case RIGHT 	: 
				x.set(x.get() + space);
				break;
			case DOWN 	: 
				y.set(y.get() + space);
				break;
			default : break;
			}
			
			this.bootsCount++;
			
			if (this.bootsCount >= this.maxDash) {
				this.setBootsInactive();
				this.bootsCount = 0;
			}
		}
		
	}
	
	public int canMoveDash(ObservableList<AnimatedEntity> entities, ObservableList<InanimatedEntity> inanimatedEntities) {
		int canMove = 0;
		int emptyTile = velocityDash;
		
		switch (this.orientation.getValue()) {
		case LEFT :
			if (x.get() % 32 == 0 && bootsCount == 0 && x.get() > LEFT_TOP_LIMIT && Game.getMap().getNextTile(getIndiceY(), getIndiceX() - 1).getId() == GameData.BOOTS_HOLE)
				canMove = emptyTile;
			else {
				emptyTile = tileIsEmptyDash(entities, inanimatedEntities, LEFT);
				if (x.get() % 32 < velocityDash) {
					if (y.get() % 32 == 0) {
						if (x.get() > LEFT_TOP_LIMIT && Game.getMap().getNextTile(getIndiceY(), getIndiceX() - 1).isCrossable())
							canMove = emptyTile;
						else canMove = (int) x.get() % 32;
					} else {
						if (x.get() > LEFT_TOP_LIMIT && Game.getMap().getNextTile(getIndiceY() + 1, getIndiceX() - 1).isCrossable() && 
								Game.getMap().getNextTile(getIndiceY(), getIndiceX() - 1).isCrossable())
							canMove = emptyTile;
						else canMove = (int) x.get() % 32;
					}
					
				} else
					canMove = velocityDash;
			}
			break;
		case UP :
			if (y.get() % 32 == 0 && bootsCount == 0 && y.get() > LEFT_TOP_LIMIT && Game.getMap().getNextTile(getIndiceY() - 1, getIndiceX()).getId() == GameData.BOOTS_HOLE) {
				System.out.println("270");
				canMove = emptyTile;
			}
			else {
				emptyTile = tileIsEmptyDash(entities, inanimatedEntities, UP);
				if (y.get() % 32 < velocityDash) {
					if (x.get() % 32 == 0) {
						if (y.get() > LEFT_TOP_LIMIT && Game.getMap().getNextTile(getIndiceY() - 1, getIndiceX()).isCrossable() && emptyTile == velocityDash)
							canMove = velocityDash;
						else canMove = (int) y.get() % 32;
					} else {
						if (y.get() > LEFT_TOP_LIMIT && Game.getMap().getNextTile(getIndiceY() - 1, getIndiceX() + 1).isCrossable() && 
								Game.getMap().getNextTile(getIndiceY() - 1, getIndiceX()).isCrossable() && emptyTile == velocityDash)
							canMove = velocityDash;
						else canMove = (int) y.get() % 32;
					}
				} else
					canMove = velocityDash;
			}
			break;
		case RIGHT :
			if (x.get() % 32 == 0 && bootsCount == 0 && x.get() < RIGHT_BOTTOM_LIMIT && Game.getMap().getNextTile(getIndiceY(), getIndiceX() + 1).getId() == GameData.BOOTS_HOLE)
				canMove = emptyTile;
			else {
				emptyTile = tileIsEmptyDash(entities, inanimatedEntities, RIGHT);
				if (x.get() % 32 < velocityDash) {
					if (x.get() % 32 != 0) {
						if (y.get() % 32 == 0) {
							if (x.get() < RIGHT_BOTTOM_LIMIT - 32 && Game.getMap().getNextTile(getIndiceY(), getIndiceX() + 2).isCrossable() && emptyTile == velocityDash)
								canMove = velocityDash;
							else canMove = (32 - ((int) x.get() % 32)) % 32;
						} else {
							if (x.get() < RIGHT_BOTTOM_LIMIT - 32 && Game.getMap().getNextTile(getIndiceY() + 1, getIndiceX() + 2).isCrossable() && 
									Game.getMap().getNextTile(getIndiceY(), getIndiceX() + 2).isCrossable() && emptyTile == velocityDash)
								canMove = velocityDash;
							else canMove = (32 - ((int) x.get() % 32)) % 32;
						}
					}
					else if (y.get() % 32 == 0) {
						if (x.get() < RIGHT_BOTTOM_LIMIT && Game.getMap().getNextTile(getIndiceY(), getIndiceX() + 1).isCrossable() && emptyTile == velocityDash)
							canMove = velocityDash;
						else canMove = (32 - ((int) x.get() % 32)) % 32;
					} else {
						if (x.get() < RIGHT_BOTTOM_LIMIT && Game.getMap().getNextTile(getIndiceY() + 1, getIndiceX() + 1).isCrossable() && 
								Game.getMap().getNextTile(getIndiceY(), getIndiceX() + 1).isCrossable() && emptyTile == velocityDash)
							canMove = velocityDash;
						else canMove = (32 - ((int) x.get() % 32)) % 32;
					}
				} else
					canMove = velocityDash;
			}
			break;
		case DOWN :
			if (y.get() % 32 == 0 && bootsCount == 0 && y.get() < RIGHT_BOTTOM_LIMIT && Game.getMap().getNextTile(getIndiceY() + 1, getIndiceX()).getId() == GameData.BOOTS_HOLE)
				canMove = emptyTile;
			else {
				emptyTile = tileIsEmptyDash(entities, inanimatedEntities, DOWN);
				if (y.get() % 32 < velocityDash) {
					if (y.get() % 32 != 0) {
						if (x.get() % 32 == 0) {
							if (y.get() < RIGHT_BOTTOM_LIMIT - 32 && Game.getMap().getNextTile(getIndiceY() + 2, getIndiceX()).isCrossable() && emptyTile == velocityDash)
								canMove = velocityDash;
							else canMove = (32 - ((int) y.get() % 32)) % 32;
						} else {
							if (y.get() < RIGHT_BOTTOM_LIMIT - 32 && Game.getMap().getNextTile(getIndiceY() + 2, getIndiceX() + 1).isCrossable() && 
									Game.getMap().getNextTile(getIndiceY() + 2, getIndiceX()).isCrossable() && emptyTile == velocityDash)
								canMove = velocityDash;
							else canMove = (32 - ((int) y.get() % 32)) % 32;
						}
					}
					else if (x.get() % 32 == 0) {
						if (y.get() < RIGHT_BOTTOM_LIMIT && Game.getMap().getNextTile(getIndiceY() + 1, getIndiceX()).isCrossable() && emptyTile == velocityDash)
							canMove = velocityDash;
						else canMove = (32 - ((int) y.get() % 32)) % 32;
					} else {
						if (y.get() < RIGHT_BOTTOM_LIMIT && Game.getMap().getNextTile(getIndiceY() + 1, getIndiceX() + 1).isCrossable() && 
								Game.getMap().getNextTile(getIndiceY() + 1, getIndiceX()).isCrossable() && emptyTile == velocityDash)
							canMove = velocityDash;
						else canMove = (32 - ((int) y.get() % 32)) % 32;
					}
				} else
					canMove = velocityDash;
			}
			break;
		default :
			break;
		}
		
		return canMove;
	}
	
	private int tileIsEmptyDash(ObservableList<AnimatedEntity> entities, ObservableList<InanimatedEntity> inanimatedEntities, int direction) {
		
		int emptyTile = velocityDash;
    	
		switch (direction) {
		case LEFT :
			for (AnimatedEntity e : entities)
	    		if (this.getX().get() - velocityDash == e.getX().get() &&
	    			this.getY().get() >= e.getY().get() - velocityDash - 1 && this.getY().get() <= e.getY().get() + velocityDash - 1) {
	    			emptyTile = 0;
	    			if (emptyTile != velocityDash)
	    				return emptyTile;
	    		}
			for (InanimatedEntity e : inanimatedEntities)
	    		if (this.getX().get() - velocityDash == e.getX().get() && 
	    			this.getY().get() >= e.getY().get() - velocityDash - 1 && this.getY().get() <= e.getY().get() + velocityDash - 1) {
	    			emptyTile = (int) (this.getX().get() - e.getX().get() - 32);
	    			if (emptyTile != velocityDash)
	    				return emptyTile;
	    		}
			break;
		case UP :
			for (AnimatedEntity e : entities)
	    		if (this.getY().get() - velocityDash == e.getY().get() && 
	    			this.getX().get() >= e.getX().get() - velocityDash - 1 && this.getX().get() <= e.getX().get() + velocityDash - 1) {
	    			emptyTile = 0;
	    			if (emptyTile != velocityDash)
	    				return emptyTile;
	    		}
			for (InanimatedEntity e : inanimatedEntities)
	    		if (this.getY().get() - velocityDash == e.getY().get() && 
	    			this.getX().get() >= e.getX().get() - velocityDash - 1 && this.getX().get() <= e.getX().get() + velocityDash - 1) {
	    			emptyTile = (int) (this.getY().get() - e.getY().get() - 32);
	    			if (emptyTile != velocityDash)
	    				return emptyTile;
	    		}
			break;
		case RIGHT :
			for (AnimatedEntity e : entities) {
	    		if (this.getX().get() + velocityDash == e.getX().get() && 
	    			this.getY().get() >= e.getY().get() - velocityDash - 1 && this.getY().get() <= e.getY().get() + velocityDash - 1) {
	    			emptyTile = 0;
	    			if (emptyTile != velocityDash)
	    				return emptyTile;
	    		}
			}
			for (InanimatedEntity e : inanimatedEntities)
	    		if (this.getX().get() + velocityDash == e.getX().get() && 
	    			this.getY().get() >= e.getY().get() - velocityDash - 1 && this.getY().get() <= e.getY().get() + velocityDash - 1) {
	    			emptyTile = (int) (e.getX().get() - this.getX().get() + 31);
	    			if (emptyTile != velocityDash)
	    				return emptyTile;
	    		}
			break;
		case DOWN :
			for (AnimatedEntity e : entities)
	    		if (this.getY().get() + velocityDash == e.getY().get() && 
	    			this.getX().get() >= e.getX().get() - velocityDash - 1 && this.getX().get() <= e.getX().get() + velocityDash - 1) {
	    			emptyTile = 0;
	    			if (emptyTile != velocityDash)
	    				return emptyTile;
	    		}
			for (InanimatedEntity e : inanimatedEntities)
	    		if (this.getY().get() + velocityDash == e.getY().get() && 
	    			this.getX().get() >= e.getX().get() - velocityDash - 1 && this.getX().get() <= e.getX().get() + velocityDash - 1) {
	    			emptyTile = (int) (e.getY().get() - this.getY().get() + 31);
	    			if (emptyTile != velocityDash)
	    				return emptyTile;
	    		}
			break;
		default :
			break;
		}
		
    	return emptyTile;
	}
	
}
