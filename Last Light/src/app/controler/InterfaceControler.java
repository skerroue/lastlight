package app.controler;

import app.vue.InterfaceView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.Pane;

public class InterfaceControler {
	
	private static int difference;

    public static void initializeInterface(Pane interfaceContainer, InterfaceView hud) {
    	
    	interfaceContainer.getChildren().addAll(hud.getHearts());
    	interfaceContainer.getChildren().addAll(hud.getPotions());
    	interfaceContainer.getChildren().addAll(hud.getMoney());
    	interfaceContainer.getChildren().add(hud.getAmmunitionImageView());
    	interfaceContainer.getChildren().add(hud.getAmmunitionLabel());
    	
    	hud.getPlayer().getHP().addListener(new ChangeListener<Number>() {
    		
    		@Override
    		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
    			
    			if (oldValue.intValue() > newValue.intValue()) {
    				
    				difference = oldValue.intValue() - newValue.intValue();
    				while (difference > 0) {
	    				for (int i = hud.getHearts().size()-1 ; i >= 0 ; i--) 
	    					if (hud.getHearts().get(i).getFull()) {
	    						hud.getHearts().get(i).setEmpty();
	    						break;
	    					}
	    				difference--;
    				}
    			}
    			
    			else {
    				difference = newValue.intValue() - oldValue.intValue();
    				while (difference > 0) {
	    				for (int i = 0 ; i < hud.getHearts().size() ; i++)
	    					if (hud.getHearts().get(i).getEmpty() && !hud.getHearts().get(i).getLocked()) {
	    						hud.getHearts().get(i).setFull();
	    						break;
	    					}
	    				difference--;
    				}
    			}
    				
    		}
    		
	    });
		
    	hud.getPlayer().getMaxHP().addListener(new ChangeListener<Number>() {
    		
    		@Override
    		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
    			
    			if (newValue.intValue() > oldValue.intValue()) {
    				for (int i = 0 ; i < hud.getHearts().size() ; i++)
    					if (hud.getHearts().get(i).getLocked()) {
    						hud.getHearts().get(i).setEmpty();
    						break;
    					}
    				
    			}
    			
    		}
    		
	    });
    	
		hud.getPlayer().getPotion().addListener(new ChangeListener<Number>() {
    		
    		@Override
    		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
    			
    			if (oldValue.intValue() > newValue.intValue()) {
    				
    				difference = oldValue.intValue() - newValue.intValue();
    				while (difference > 0) {
	    				for (int i = hud.getPotions().size()-1 ; i >= 0 ; i--) 
	    					if (hud.getPotions().get(i).getFull()) {
	    						hud.getPotions().get(i).setEmpty();
	    						break;
	    					}
	    				difference--;
	    				
    				}
    			}
    			
    			else {
    				difference = newValue.intValue() - oldValue.intValue();
    				while (difference > 0) {
	    				for (int i = 0 ; i < hud.getPotions().size() ; i++)
	    					if (hud.getPotions().get(i).getEmpty()) {
	    						hud.getPotions().get(i).setFull();
	    						break;
	    					}
	    				difference--;
    				}
    			}
    				
    		}
    		
	    });
		
		hud.getPlayer().getMoney().addListener(new ChangeListener<Number>() {
    		
    		@Override
    		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
    			
    			if (oldValue.intValue() > newValue.intValue()) {
    				
    				difference = oldValue.intValue() - newValue.intValue();
    				while (difference > 0) {
	    				for (int i = hud.getMoney().size()-1 ; i >= 0 ; i--) 
	    					if (hud.getMoney().get(i).getFull()) {
	    						hud.getMoney().get(i).setEmpty();
	    						break;
	    					}
	    				difference--;
    				}
    				
    			}
    			
    			else {
    				
    				difference = newValue.intValue() - oldValue.intValue();
    				while (difference > 0) {
	    				for (int i = 0 ; i < hud.getMoney().size() ; i++)
	    					if (hud.getMoney().get(i).getEmpty()) {
	    						hud.getMoney().get(i).setFull();
	    						break;
	    					}
    					difference--;
    				}
    			}
    				
    		}
    		
	    });
	
    }
	
}
