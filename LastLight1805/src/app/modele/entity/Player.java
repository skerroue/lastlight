package app.modele.entity;

import app.modele.weapon.Weapon;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Player extends AnimatedEntity {

	// LIST OF TODOs
	// update - useBoots - useNecklace - interact
	
	// Attributes
	private BooleanProperty boots;
	private BooleanProperty necklace;
	private IntegerProperty money; // Always >= 0
	private IntegerProperty potion; // Always >= 0
	private IntegerProperty equippedWeapon;
	private ObservableList<Weapon> listWeapons;
	
	// Constructor
	public Player(int x, int y, int pv, int att, int v) {
		super(x, y, pv, att, v);
		this.boots = new SimpleBooleanProperty(false);
		this.necklace = new SimpleBooleanProperty(false);
		this.money = new SimpleIntegerProperty(0);
		this.potion = new SimpleIntegerProperty(0);
		this.equippedWeapon = null;
		this.listWeapons = FXCollections.observableArrayList();
	}
	
	// TODO
	public void update() {
		
	}

	// Increment by 1 the number of equippedWeapon.
	// Set it t 0 when equippedWeapon = listWeapons.size() -1
	public void changeWeapon() {
		if (this.equippedWeapon.getValue() < this.listWeapons.size() -1)
			this.equippedWeapon.set(this.equippedWeapon.getValue()+1);
		else
			this.equippedWeapon.set(0);
	}
	
	// Gain 1 PV if the player is not full pv (max 3)
	// Use 1 potion
	public void usePotion() {
		if (this.pv.getValue() < 3 && this.potion.getValue() > 0) {
			this.pv.set(pv.getValue() +1);
			this.potion.set(potion.getValue() - 1);
		}
		
	}
	
	// Can only recieve potion 1 by 1
	public void recievePotion(){
		this.potion.set(this.potion.getValue() + 1);
	}
	
	// TODO
	public void useBoots() {
		if (this.boots.getValue()) {
			
		}
	}
	
	// TODO
	public void useNecklace() {
		if (this.necklace.getValue()) {
			
		}
	}
	
	// Recieve a weapon and add it to listWeapons
	public void recieveWeapon(Weapon w) {
		this.listWeapons.add(w);
	}
	
	// TODO
	public void interact() {
		
	}
	
	// Needs to recieve posivite value
	public void recieveMoney(int v) {
		this.money.set(this.money.getValue() + v);
	}
	
	// Needs to recieve positive value
	public void spendMoney(int v) {
		this.money.set(this.money.getValue() - v);
	}
	
}
