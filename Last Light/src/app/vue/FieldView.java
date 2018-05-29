package app.vue;

import app.modele.Game;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FieldView {
	
	private ObservableList<ImageView> fieldView;
	private Image tileset;
	
	public FieldView() {
		this.tileset = new Image("file:src/img/tilesetLastLight.png");
		this.fieldView = FXCollections.observableArrayList();
		
		initializeField();
	}
	
	public void initializeField() {
		
		for (int i = 0 ; i < Game.getMap().getFieldSize() ; i++)
			for (int j = 0 ; j < Game.getMap().getFieldSize() ; j++) {
				this.fieldView.add(new ImageView(tileset));
				this.fieldView.get(this.fieldView.size()-1).setTranslateX(j*32);
				this.fieldView.get(this.fieldView.size()-1).setTranslateY(i*32);
			}
		
		refreshField();
		
		Game.getMapChanged().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
				refreshField();
			}
			
		});
	}
	
	public void refreshField() {
		int hauteur = 0, largeur = 0;
		
		for (int i = 0 ; i < this.fieldView.size() ; i++) {
			this.fieldView.set(i, Game.getMap().intToTiles(this.fieldView.get(i), Game.getMap().getNextTile(hauteur, largeur).getId()));
			
			largeur++;
			
			if (largeur % 25 == 0) {
				hauteur++;
				largeur = 0;
			}
		}
	}
	
	public ObservableList<ImageView> getFieldView() {
		return fieldView;
	}

}
