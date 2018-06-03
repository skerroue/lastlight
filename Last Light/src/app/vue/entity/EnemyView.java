package app.vue.entity;

import app.modele.entity.AnimatedEntity;
import app.modele.entity.Entity;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;

public class EnemyView extends AnimatedEntityView {

	public EnemyView(AnimatedEntity e) {
		super(e);
	}
}
