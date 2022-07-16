package dungeonmania.Entities.MovingEntities.Enemies;

import dungeonmania.Entities.MovingEntities.MovementStrategy;
import dungeonmania.util.Position;

public class ZombieToast extends Enemy {
    private int positionIterator = 0;
    
    public ZombieToast(String id, String type, Position position, int health, boolean isInteractable, MovementStrategy movementStrategy, int attack) {
        super(id, type, position, health, isInteractable, movementStrategy, health);
    }
    
    public int getPositionIterator() {
        return positionIterator;
    }
    
    public void setPositionIterator(int positionIterator) {
        this.positionIterator = positionIterator;
    }

    @Override
    public void tick() {
        getMovementStrategy().move();
    }
    
    
}
