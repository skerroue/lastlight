package app.vue;

import app.modele.entity.AnimatedEntity;
import app.modele.entity.Entity;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

public class PlayerView extends EntityView {

	public PlayerView(AnimatedEntity e) {
		super(new Image("file:src/img/tilesetsprite.png"), e);
		this.actualiserImage();
		this.initializeListeners();
	}
	
	public void actualiserImage() {
		this.setViewport(new Rectangle2D((this.entity.getFrame()/3)*32, this.entity.getOrientation().getValue()*32, 32, 32));
	}
	
	public void initializeListeners() {
		
		this.entity.getOrientation().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				actualiserImage();
			}
			
		});
		
		this.entity.getX().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				entity.incrementeFrame();
				if (entity.getFrame() % 3 == 0)
					actualiserImage();
			}
			
		});
		
		this.entity.getY().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				entity.incrementeFrame();
				if (entity.getFrame() % 3 == 0)
					actualiserImage();
			}
			
		});
		
	}

}
