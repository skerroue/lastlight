package app.modele.entity;

import java.util.ArrayList;

import app.modele.weapon.Lamp;
import app.modele.weapon.Weapon;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;

public class Player extends AnimatedEntity {
	
	private IntegerProperty maxHP;
	private IntegerProperty potentialHP;
	
	private IntegerProperty maxPotion;
	private IntegerProperty potion;
	
	private IntegerProperty maxMoney;
	private IntegerProperty money;
	
	private BooleanProperty boots;
	private BooleanProperty necklace;
	
	private ArrayList<Weapon> weapons;
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
		this.necklace = new SimpleBooleanProperty(false);
		this.weapons = new ArrayList<>();
		this.activeWeaponIndex = new SimpleIntegerProperty(0);
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
	
	public ArrayList<Weapon> getWeapons() {
		return this.weapons;
	}
	
	public IntegerProperty getActiveWeaponIndex() {
		return this.activeWeaponIndex;
	}
	 
	public void switchWeapon(int n) {
		this.activeWeaponIndex.set(n);
	}
	
	// Gagne une potion 1 à 1
	public void earnPotion() {
		this.potion.set(this.potion.getValue() + 1);
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
	
	public void attack(ObservableList<AnimatedEntity> entities, int x, int y) {
		
		this.isAttacking.set(true);
		
		switch (this.activeWeaponIndex.get()) {
		case 1 :
			System.out.println("ok");
			this.weapons.get(this.activeWeaponIndex.get()-1).attack(entities, this.orientation.get(), (int)this.getX().get(), (int)this.getY().get());
			break;
		case 2 :
			this.weapons.get(this.activeWeaponIndex.get()-1).attack(entities, this.orientation.get(), x, y);
			break;
		default :
			break;
		}
		
	}
	
	public void interact(ObservableList<InanimatedEntity> entities) {
		switch (this.getOrientation().get()) {
		case LEFT :
			for (InanimatedEntity e : entities)
	    		if (this.getX().get() <= e.getX().get() + 64 && this.getX().get() >= e.getX().get() + 32 && 
	    			this.getY().get() >= e.getY().get() - 31 && this.getY().get() <= e.getY().get() + 31)
	    			e.interact(this);
			break;
		case UP :
			for (InanimatedEntity e : entities)
	    		if (this.getY().get() <= e.getY().get() + 64 && this.getY().get() >= e.getY().get() + 32 && 
	    			this.getX().get() >= e.getX().get() - 31 && this.getX().get() <= e.getX().get() + 31)
	    			e.interact(this);
			break;
		case RIGHT :
			for (InanimatedEntity e : entities) {
				if (this.getX().get() >= e.getX().get() - 64 && this.getX().get() <= e.getX().get() - 32 && 
		    		this.getY().get() >= e.getY().get() - 31 && this.getY().get() <= e.getY().get() + 31)
					e.interact(this);
			}
			break;
		case DOWN :
			for (InanimatedEntity e : entities)
	    		if (this.getY().get() >= e.getY().get() - 64 && this.getY().get() <= e.getY().get() - 32 && 
	    			this.getX().get() >= e.getX().get() - 31 && this.getX().get() <= e.getX().get() + 31)
	    			e.interact(this);
			break;
		default :
			break;
			
		}
	}
	
}
