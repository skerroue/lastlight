package app.modele.entity.animated;

import app.modele.weapon.Weapon;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
	
	private boolean necklace;
	private BooleanProperty necklaceIsActive;
	
	private ObservableList<Weapon> weapons;
	private IntegerProperty activeWeaponIndex;
	
	public Player(int x, int y, int pv, int att, int v, int m, int nb, int fmax) {
		super("player", x, y, pv, att, v, nb, fmax);
		
		this.maxPotion = new SimpleIntegerProperty(3);
		this.potion = new SimpleIntegerProperty(0);
		this.maxMoney = new SimpleIntegerProperty(5);
		this.money = new SimpleIntegerProperty(m);
		this.maxHP = new SimpleIntegerProperty(3);
		this.potentialHP = new SimpleIntegerProperty(6);
		
		this.boots = new SimpleBooleanProperty(false);
		this.bootsIsActive = false;
		bootsCount = 0;
		this.necklace = false;
		this.necklaceIsActive = new SimpleBooleanProperty(false);
		this.weapons = FXCollections.observableArrayList();
		this.activeWeaponIndex = new SimpleIntegerProperty(-1);
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
	
	public ObservableList<Weapon> getWeapons() {
		return this.weapons;
	}
	
	public void reload() {
		if (this.activeWeaponIndex.get() != -1)
			this.weapons.get(this.activeWeaponIndex.get()).reload();
	}
	
	public ObservableList<Bullet> getBullets() {
		for (Weapon w : this.weapons)
			if (w.getId().equals("taser"))
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
	
	// Gagne une potion 1 à 1
	public void earnPotion() {
		if (this.potion.get() < 3)
			this.potion.set(this.potion.getValue() + 1);
	}
	
	public boolean buyPotion() {
		if (this.money.get() > 1 && this.potion.get() < 3) {
			this.earnPotion();
			this.earnMoney(-2);
			return true;
		}
		else 
			return false;
	}
	
	// TODO
	public void usePotion() {
		if (this.potion.getValue() > 0 && this.hp.getValue() < this.maxHP.getValue()) {
			this.potion.set(this.potion.getValue() - 1);
			this.hp.set(this.hp.getValue() + 1);
		}
	}
	
	public void earnMoney(int a) {
		this.money.set(this.money.getValue() + a);
	}
	
	// TODO Vérifier que l'on ne passe pas en négatif
	public void spendMoney(int a) {
		this.money.set(this.money.getValue() - a);
	}
	
	public void attack(ObservableList<AnimatedEntity> entities) {
		
		if (this.weapons.size() > 0  && this.activeWeaponIndex.get() > -1) {
			this.isAttacking.set(true);
			this.weapons.get(this.activeWeaponIndex.get()).attack(entities, this.orientation.get(), (int)this.getX().get(), (int)this.getY().get());
		}
		
	}
	
	public void update() {
		if (this.bootsIsActive) {
			switch (this.orientation.get()) {
			case LEFT : x.set(x.get() - 32);
				break;
			case UP : y.set(y.get() - 32);
				break;
			case RIGHT : x.set(x.get() + 32);
				break;
			case DOWN : y.set(y.get() - 32);
				break;
			default : break;
			}
			
			bootsCount++;
			
			if (bootsCount >= 2) {
				setBootsInactive();
				bootsCount = 0;
			}
		}
		
	}
	
}
