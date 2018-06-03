package app.vue.entity;

import app.modele.entity.InanimatedEntity;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class InanimatedEntityView extends EntityView {
	
	private InanimatedEntity inanimatedEntity;

	public InanimatedEntityView(InanimatedEntity i) {
		super(i);
		this.inanimatedEntity = i;
		
		this.setImage(new Image("file:src/img/i" + this.inanimatedEntity.getId() + ".png"));
	}

}
