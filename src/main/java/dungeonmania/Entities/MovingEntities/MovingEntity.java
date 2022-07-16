package dungeonmania.Entities.MovingEntities;

import dungeonmania.Entities.Entity;
import dungeonmania.util.Position;


public class MovingEntity extends Entity {
    private double health;
    private final double attack;
    private MovementStrategy movementStrategy;
    
    public MovingEntity(String id, String type, Position position, int health, boolean isInteractable, MovementStrategy movementStrategy, int attack) {
        super(id, type, position, isInteractable);
        this.health = health;
        this.movementStrategy = movementStrategy;
        this.attack = attack;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public MovementStrategy getMovementStrategy() {
        return movementStrategy;
    }

    public void setMovementStrategy(MovementStrategy movementStrategy) {
        this.movementStrategy = movementStrategy;
    }

    public double getAttack() {
        return attack;
    }

}
