package app.vue;

import app.modele.Game; 
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FieldView {
	
	private final static int NB_TILESET_COLUMN = 8;
	
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
	}
	
	public void refreshField() {
		int hauteur = 0, largeur = 0;
		
		for (int i = 0 ; i < this.fieldView.size() ; i++) {
			this.fieldView.set(i, this.intToTiles(this.fieldView.get(i), Game.getMap().getNextTile(hauteur, largeur).getId()));
			
			largeur++;
			
			if (largeur % 25 == 0) {
				hauteur++;
				largeur = 0;
			}
		}
	}
	
	public ImageView intToTiles(ImageView img, int fieldValue) {
    	
		int x = 32 * ((fieldValue-1) % NB_TILESET_COLUMN);
		int y = 32 * ((fieldValue-1) / NB_TILESET_COLUMN);
		img.setViewport(new Rectangle2D(x, y, 32, 32));

    	return img;
    }
	
	public ObservableList<ImageView> getFieldView() {
		return fieldView;
	}

}
