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
			
			hearts.get(i).setTranslateX((i+1)*28);
			hearts.get(i).setTranslateY(10);
		}
		
		player.getHP().addListener(new ChangeListener<Number>() {
    		
    		@Override
    		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
    			
    			if (oldValue.intValue() > newValue.intValue()) {
    				for (int i = hearts.size()-1 ; i >= 0 ; i--) 
    					if (hearts.get(i).getFull()) {
    						hearts.get(i).setEmpty();
    						break;
    					}
    			}
    			
    			else {
    				for (int i = 0 ; i < hearts.size() ; i++)
    					if (hearts.get(i).getEmpty() && !hearts.get(i).getLocked()) {
    						hearts.get(i).setFull();
    						break;
    					}
    			}
    				
    		}
    		
	    });
		
		player.getMaxHP().addListener(new ChangeListener<Number>() {
    		
    		@Override
    		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
    			
    			if (newValue.intValue() > oldValue.intValue()) {
    				for (int i = 0 ; i < hearts.size() ; i++)
    					if (hearts.get(i).getLocked()) {
    						hearts.get(i).setEmpty();
    						break;
    					}
    				
    			}
    			
    		}
    		
	    });
		
	}

	public void initializePotions() {
		
		for (int i = 0 ; i < player.getMaxPotion().get() ; i++) {
			potions.add(new ComponentView("soda"));
			potions.get(i).setEmpty();
			
			potions.get(i).setTranslateX(330+((i+1)*28));
			potions.get(i).setTranslateY(10);
		}
		
		player.getPotion().addListener(new ChangeListener<Number>() {
    		
    		@Override
    		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
    			
    			if (oldValue.intValue() > newValue.intValue()) {
    				for (int i = potions.size()-1 ; i >= 0 ; i--) 
    					if (potions.get(i).getFull()) {
    						potions.get(i).setEmpty();
    						break;
    					}
    			}
    			
    			else {
    				for (int i = 0 ; i < potions.size() ; i++)
    					if (potions.get(i).getEmpty()) {
    						potions.get(i).setFull();
    						break;
    					}
    			}
    				
    		}
    		
	    });
		
	}
	
	public void initializeMoney() {
		
		for (int i = 0 ; i < player.getMaxMoney().get() ; i++) {
			money.add(new ComponentView("money"));
			money.get(i).setEmpty();
			
			money.get(i).setTranslateX(530+((i+4)*28));
			money.get(i).setTranslateY(10);
		}
		
		player.getMoney().addListener(new ChangeListener<Number>() {
    		
    		@Override
    		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
    			
    			if (oldValue.intValue() > newValue.intValue()) {
    				for (int i = money.size()-1 ; i >= 0 ; i--) 
    					if (money.get(i).getFull()) {
    						money.get(i).setEmpty();
    						break;
    					}
    			}
    			
    			else {
    				for (int i = 0 ; i < money.size() ; i++)
    					if (money.get(i).getEmpty()) {
    						money.get(i).setFull();
    						break;
    					}
    			}
    				
    		}
    		
	    });
		
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

}
