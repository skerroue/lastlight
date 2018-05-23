package app.modele.entity;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;

public class Player extends AnimatedEntity {

	private IntegerProperty potion;
	private IntegerProperty money;
	private BooleanProperty boots;
	private BooleanProperty necklace;
	private boolean hpAdded;
	
	public Player(int x, int y, int pv, int att, int v, int m) {
		super(x, y, pv, att, v);
		this.potion = new SimpleIntegerProperty(0);
		this.money = new SimpleIntegerProperty(m);
		this.boots = new SimpleBooleanProperty(false);
		this.necklace = new SimpleBooleanProperty(false);
	}
	
	public void update(ObservableList<Entity> entities) {
		
	}
	
	public void loosePv(int a) {
		pv.set(pv.getValue()-a);
	}
	
	public IntegerProperty getPotion() {
		return this.potion;
	}
	
	public IntegerProperty getMoney() {
		return this.money;
	}
	
	// Gagne une potion 1 à 1
	public void earnPotion() {
		this.potion.set(this.potion.getValue() + 1);
	}
	
	// TODO
	public void usePotion() {
		
	}
	
	public void earnMoney(int a) {
		this.money.set(this.money.getValue() + a);
	}
	
	// TODO Vérifier que l'on ne passe pas en négatif
	public void spendMoney(int a) {
		this.money.set(this.money.getValue() - a);
	}
	
}
