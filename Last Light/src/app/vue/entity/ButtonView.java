package app.vue.entity;

import app.modele.entity.inanimated.InanimatedEntity;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;

public class ButtonView extends InanimatedEntityView {

	public ButtonView(InanimatedEntity i) {
		super(i);
		
		this.inanimatedEntity.isInteracting().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldValue, Boolean newValue) {
				setGif();
			}
			
		});
	}
	
	private void setGif() {
		this.setImage(new Image("file:src/img/" + this.inanimatedEntity.getId() + "-gif.gif"));
	}

}
