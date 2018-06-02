package app.vue;

import app.modele.entity.AnimatedEntity;
import app.modele.entity.Entity;
import app.modele.entity.Player;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class EntityView extends ImageView {
	
	protected AnimatedEntity entity;
	protected Player player;
	protected boolean isDead;
	
	public EntityView(AnimatedEntity e) {
		this.setImage(new Image("file:src/img/tileset" + e.getId() + ".png"));
		this.entity = e;
		
		this.translateXProperty().bind(e.getX());
		this.translateYProperty().bind(e.getY());
		
		this.actualiserImage();
		this.initializeListeners();
	}
	
	public EntityView(Player e) {
		this.setImage(new Image("file:src/img/tileset" + e.getId() + ".png"));
		this.entity = e;
		this.player = e;
		
		this.translateXProperty().bind(e.getX());
		this.translateYProperty().bind(e.getY());
		
		this.actualiserImage();
		this.initializeListeners();
	}
	
	public boolean getIsDead() {
		return this.isDead;
	}
	
	public void actualiserImage() {
		this.setViewport(new Rectangle2D((this.entity.getFrame()/(entity.getFrameMax()/entity.getNbFrame()))*32, this.entity.getOrientation().getValue()*32, 32, 32));
	}
	
	public void resetImage() {
		this.entity.resetFrame();
		this.setViewport(new Rectangle2D((this.entity.getFrame()/(entity.getFrameMax()/entity.getNbFrame()))*32, this.entity.getOrientation().getValue()*32, 32, 32));
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
				if (entity.getFrame() % (entity.getFrameMax()/entity.getNbFrame()) == 0)
					actualiserImage();
			}
			
		});
		
		this.entity.getY().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				entity.incrementeFrame();
				if (entity.getFrame() % (entity.getFrameMax()/entity.getNbFrame()) == 0)
					actualiserImage();
			}
			
		});
		
		this.entity.getIsDead().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				isDead = true;
			}
			
		});
		
	}
	
}
