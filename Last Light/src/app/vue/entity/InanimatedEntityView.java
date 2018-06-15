package app.vue.entity;

import app.modele.entity.inanimated.InanimatedEntity;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;

public class InanimatedEntityView extends EntityView {
	
	private InanimatedEntity inanimatedEntity;

	public InanimatedEntityView(InanimatedEntity i) {
		super(i);
		this.inanimatedEntity = i;
		
		this.setImage(new Image("file:src/img/" + this.inanimatedEntity.getId() + ".png"));
		
		if (this.inanimatedEntity.getId().equals("button"))
			this.inanimatedEntity.isInteracting().addListener(new ChangeListener<Boolean>() {

				@Override
				public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
					setGif();
				}
				
			});
	}
	
	public void setGif() {
		this.setImage(new Image("file:src/img/" + this.inanimatedEntity.getId() + "-gif.gif"));
	}
	
}
