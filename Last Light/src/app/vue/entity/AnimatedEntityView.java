package app.vue.entity;

import app.modele.entity.AnimatedEntity;
import app.modele.entity.Player;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

public class AnimatedEntityView extends EntityView {
	
	private AnimatedEntity animatedEntity;
	
	public AnimatedEntityView(AnimatedEntity e) {
		super(e);
		this.animatedEntity = e;
		
		this.setImage(new Image("file:src/img/tileset" + e.getId() + ".png"));
		
		this.actualiserImage();
		this.initializeAnimatedEntity();
	}
	
	public void actualiserImage() {
		this.setViewport(new Rectangle2D((this.animatedEntity.getFrame()/(this.animatedEntity.getFrameMax()/this.animatedEntity.getNbFrame()))*32, this.animatedEntity.getOrientation().getValue()*32, 32, 32));
	}
	
	public void resetImage() {
		this.animatedEntity.resetFrame();
		this.setViewport(new Rectangle2D((this.animatedEntity.getFrame()/(this.animatedEntity.getFrameMax()/this.animatedEntity.getNbFrame()))*32, this.animatedEntity.getOrientation().getValue()*32, 32, 32));
	}
	
	public void initializeAnimatedEntity() {
		
		this.animatedEntity.getOrientation().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				actualiserImage();
			}
			
		});
		
		this.animatedEntity.getX().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				animatedEntity.incrementeFrame();
				if (animatedEntity.getFrame() % (animatedEntity.getFrameMax()/animatedEntity.getNbFrame()) == 0)
					actualiserImage();
			}
			
		});
		
		this.animatedEntity.getY().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				animatedEntity.incrementeFrame();
				if (animatedEntity.getFrame() % (animatedEntity.getFrameMax()/animatedEntity.getNbFrame()) == 0)
					actualiserImage();
			}
			
		});
		
	}

}
