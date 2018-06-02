package app.vue;

import app.modele.entity.InanimatedEntity;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class InanimatedEntityView extends ImageView {
	
	private InanimatedEntity inanimatedEntity;
	private boolean isDead;

	public InanimatedEntityView(InanimatedEntity i) {
		this.inanimatedEntity = i;
		this.setImage(new Image("file:src/img/i" + this.inanimatedEntity.getId() + ".png"));
		
		this.translateXProperty().bind(inanimatedEntity.getX());
		this.translateYProperty().bind(inanimatedEntity.getY());
		
		initializeListeners();
	}
	
	public void initializeListeners() {
		this.inanimatedEntity.getIsDead().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				isDead = true;
			}
			
		});
	}
	
	public boolean getIsDead() {
		return this.isDead;
	}

}
