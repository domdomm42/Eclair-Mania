package dungeonmania.Entities.MovingEntities;

import dungeonmania.Entities.Entity;
import dungeonmania.util.Position;


public class MovingEntity extends Entity {
    private int health;
    private int attack;
    private MovementStrategy movementStrategy;
    
    public MovingEntity(String id, String type, Position position, int health, boolean isInteractable, MovementStrategy movementStrategy, int attack) {
        super(id, type, position, isInteractable);
        this.health = health;
        this.movementStrategy = movementStrategy;
        this.attack = attack;
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

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

}
