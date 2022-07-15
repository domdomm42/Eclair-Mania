package dungeonmania.Entities.MovingEntities.Enemies;

import dungeonmania.Entities.MovingEntities.MovementStrategy;
import dungeonmania.util.Position;

public class Mercenary extends Enemy {
    private int bribePrice;
    
    public Mercenary(String id, String type, Position position, int health, boolean isInteractable, MovementStrategy movementStrategy, int attack, int bribePrice) {
        super(id, type, position, health, isInteractable, movementStrategy, attack);
        this.bribePrice = bribePrice;
    }
    
}
