package dungeonmania.Entities.MovingEntities.Enemies;

import dungeonmania.Entities.MovingEntities.MovementStrategy;
import dungeonmania.util.Position;

public class ZombieToast extends Enemy {
    
    public ZombieToast(String id, String type, Position position, int health, boolean isInteractable, MovementStrategy movementStrategy, int attack) {
        super(id, type, position, health, isInteractable, movementStrategy, health);
    }
    
    

    @Override
    public void tick() {
        getMovementStrategy().move();
    }

    
}
