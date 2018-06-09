package app.controler;

import app.modele.Game; 
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class DialogControler {
	
	public static void initializeDialogContainer(Pane dialogPane, Game game, Label dialogContainer) {
		
		game.getCurrentTextProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0, String oldValue, String newValue) {
			
				dialogContainer.setText(game.getCurrentTextProperty().get());
				
			}
			
		});
		
	}	

}
