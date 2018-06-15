package app.vue.entity;

import app.modele.entity.inanimated.InanimatedEntity;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;

public class InanimatedEntityView extends EntityView {
	
	protected InanimatedEntity inanimatedEntity;

	public InanimatedEntityView(InanimatedEntity i) {
		super(i);
		this.inanimatedEntity = i;
		
		this.setImage(new Image("file:src/img/" + this.inanimatedEntity.getId() + ".png"));
	}
	
}
