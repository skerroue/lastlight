package app.vue;

import app.modele.entity.animated.Player; 
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class InterfaceView {
	
	private Player player;
	private ObservableList<ComponentView> hearts;
	private ObservableList<ComponentView> potions;
	private ObservableList<ComponentView> money;
	private ImageView ammunitionImageView;
	private Label ammunitionLabel;
	
	public InterfaceView(Player player) {		
		this.player = player;
		
		this.hearts = FXCollections.observableArrayList();
		this.potions = FXCollections.observableArrayList();
		this.money = FXCollections.observableArrayList();
		this.ammunitionImageView = new ImageView();
		this.ammunitionLabel = new Label();
		
		initializeHearts();
		initializePotions();
		initializeMoney();
		initializeAmmunition();
	}
	
	public void initializeHearts() {
		
		for (int i = 0 ; i < player.getPotentialHP().get() ; i++) {
			hearts.add(new ComponentView("h"));
			if (i < player.getMaxHP().get())
				hearts.get(i).setFull();
			else
				hearts.get(i).setLocked();
			
			hearts.get(i).setTranslateX(10+(i)*28);
			hearts.get(i).setTranslateY(10);
		}
		
	}

	public void initializePotions() {
		
		for (int i = 0 ; i < player.getMaxPotion().get() ; i++) {
			potions.add(new ComponentView("soda"));
			potions.get(i).setEmpty();
			
			potions.get(i).setTranslateX(205+((i+1)*28));
			potions.get(i).setTranslateY(10);
		}
		
	}
	
	public void initializeMoney() {
		
		for (int i = 0 ; i < player.getMaxMoney().get() ; i++) {
			money.add(new ComponentView("money"));
			money.get(i).setEmpty();
			
			money.get(i).setTranslateX(250+((i+4)*28));
			money.get(i).setTranslateY(10);
		}
		
	}
	
	public void initializeAmmunition() {
		this.ammunitionImageView.setImage(new Image("file:src/img/0.png"));
		this.ammunitionImageView.setTranslateX(448);
		this.ammunitionImageView.setTranslateY(480);
		
		this.ammunitionLabel.textProperty().bind(player.getAmmunitionProperty());
		this.ammunitionLabel.setTranslateX(480);
		this.ammunitionLabel.setTranslateY(480);
	}
	
	public ObservableList<ComponentView> getHearts() {
		return hearts;
	}
	
	public ObservableList<ComponentView> getPotions() {
		return potions;
	}
	
	public ObservableList<ComponentView> getMoney() {
		return money;
	}
	
	public ImageView getAmmunitionImageView() {
		return ammunitionImageView;
	}
	
	public Label getAmmunitionLabel() {
		return ammunitionLabel;
	}
	
	public Player getPlayer() {
		return player;
	}

}
