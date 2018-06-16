package app.modele.entity.animated;

import static org.junit.Assert.*;

import org.junit.Test;

public class PlayerTest {
	
	private Player p;

	@Test
	public void healthPointstest() {
		p = new Player(416, 416, 3, 0, 4, 0, 6, 18);
		
		assertEquals("cas bonne initialisation", 3, p.getHP().get());
		
		p.loseHP(3);
		assertEquals("cas bonne perte d'HP", 0, p.getHP().get());
		assertTrue("cas joueur est bien considéré mort", p.getIsDead().get());
		
		p = new Player(416, 416, 3, 0, 4, 0, 6, 18);
		p.loseHP(4);
		assertTrue("cas bien mort si en dessous de 0", p.getIsDead().get());
		
		p.earnPotion();
		p.usePotion();
		assertEquals(-1, p.getHP().get());
	}
	
	@Test
	public void moneyTest() {
		p = new Player(416, 416, 3, 0, 4, 0, 6, 18);
		
		assertEquals("cas bonne initialisation", 0, p.getMoney().get());
		
		assertFalse("cas impossible d'acheter une potion sans avoir assez d'argent", p.buyPotion());
		
		p.earnMoney(2);
		assertTrue("cas possible d'acheter une potion quand on a assez d'argent", p.buyPotion());
		
		p.earnPotion();
		p.earnPotion();
		assertFalse("cas pas possible d'acheter quand on a trop de l'objet", p.buyPotion());
	}
	
	@Test
	public void potionTest() {
		p = new Player(416, 416, 3, 0, 4, 0, 6, 18);
		
		assertEquals("cas bonne initialisation", 0, p.getPotion().get());
		
		p.earnPotion();
		p.usePotion();
		assertEquals("cas utilisation potion quand HP plein (nb hp)", p.getMaxHP().get(), p.getHP().get());
		assertEquals("cas utilisation potion quand HP plein (nb potion)", 1, p.getPotion().get());
		
		p.loseHP(2);
		p.usePotion();
		assertEquals("cas utilisation potion quand HP non plein (nb hp)", p.getMaxHP().get(), p.getHP().get());
		assertEquals("cas utilisation potion quand HP non plein (nb potion)", 0, p.getPotion().get());
		
		p.earnPotion();	p.earnPotion();	p.earnPotion();
		p.earnPotion();
		assertEquals("cas recuperer une potion alors qu'inventaire de potion plein", p.getMaxPotion().get(), p.getPotion().get());
	}
	
	public void utilisationObjets() {
		p = new Player(416, 416, 3, 0, 4, 0, 6, 18);
		
		assertFalse("cas bonne initialisation", p.hasNecklace());
		assertFalse("cas bonne initialisation", p.hasBoots());
		
		p.useBoots();
		assertFalse("cas utilisation des bottes alors que le joueur ne les possede pas", p.bootsIsActive());
		
		p.useNecklace();
		assertFalse("cas utilisation du collier alors que le joueur ne les possede pas", p.necklaceIsActive().get());
		
		p.useBoots();
		assertTrue("cas utilisation des bottes alors que le joueur les possede", p.bootsIsActive());
		
		p.useNecklace();
		assertTrue("cas utilisation du collier alors que le joueur les possede", p.necklaceIsActive().get());
	}

}
