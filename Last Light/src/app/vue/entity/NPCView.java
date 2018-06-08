package app.vue.entity;

import app.modele.entity.AnimatedEntity;
import app.modele.entity.NPC;

public class NPCView extends AnimatedEntityView {

	private AnimatedEntity NPC;

	public NPCView(AnimatedEntity e) {
		super(e);
		this.NPC = e;
	}

}
