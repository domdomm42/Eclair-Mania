package dungeonmania.Entities.MovingEntities.Enemies;

import dungeonmania.util.Position;

public class Mercenary extends Enemy {
    private boolean isAlly = false;
    private int bribePrice;
    
    public Mercenary(String id, String type, Position position, int health, boolean isInteractable, int bribePrice) {
        super(id, type, position, health, isInteractable);
        this.bribePrice = bribePrice;
    }
    
}
