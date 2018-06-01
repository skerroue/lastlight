package app.vue;

import app.modele.entity.Player;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class InterfaceView {
	
	private Player player;
	private ObservableList<ComponentView> hearts;
	private ObservableList<ComponentView> potions;
	private ObservableList<ComponentView> money;
	
	public InterfaceView(Player player) {		
		this.player = player;
		
		this.hearts = FXCollections.observableArrayList();
		this.potions = FXCollections.observableArrayList();
		this.money = FXCollections.observableArrayList();
		
		initializeHearts();
		initializePotions();
		initializeMoney();
	}
	
	public void initializeHearts() {
		
		for (int i = 0 ; i < player.getPotentialHP().get() ; i++) {
			hearts.add(new ComponentView("h"));
			if (i < player.getMaxHP().get())
				hearts.get(i).setFull();
			else
				hearts.get(i).setLocked();
			
			hearts.get(i).setTranslateX(10+(i)*28);
			hearts.get(i).setTranslateY(10);
		}
		
	}

	public void initializePotions() {
		
		for (int i = 0 ; i < player.getMaxPotion().get() ; i++) {
			potions.add(new ComponentView("soda"));
			potions.get(i).setEmpty();
			
			potions.get(i).setTranslateX(205+((i+1)*28));
			potions.get(i).setTranslateY(10);
		}
		
	}
	
	public void initializeMoney() {
		
		for (int i = 0 ; i < player.getMaxMoney().get() ; i++) {
			money.add(new ComponentView("money"));
			money.get(i).setEmpty();
			
			money.get(i).setTranslateX(250+((i+4)*28));
			money.get(i).setTranslateY(10);
		}
		
	}
	
	public ObservableList<ComponentView> getHearts() {
		return hearts;
	}
	
	public ObservableList<ComponentView> getPotions() {
		return potions;
	}
	
	public ObservableList<ComponentView> getMoney() {
		return money;
	}
	
	public Player getPlayer() {
		return player;
	}

}
