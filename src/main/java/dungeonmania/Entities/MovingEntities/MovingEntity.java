package dungeonmania.Entities.MovingEntities;

import dungeonmania.Entities.Entity;
import dungeonmania.util.Position;


public class MovingEntity extends Entity {
    private int health;
    private MovementStrategy movementStrategy;
    
    public MovingEntity(String id, String type, Position position, int health, boolean isInteractable, MovementStrategy movementStrategy) {
        super(id, type, position, isInteractable);
        this.health = health;
        this.movementStrategy = movementStrategy;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public MovementStrategy getMovementStrategy() {
        return movementStrategy;
    }

    public void setMovementStrategy(MovementStrategy movementStrategy) {
        this.movementStrategy = movementStrategy;
    }

}
