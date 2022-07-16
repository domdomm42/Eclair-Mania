package dungeonmania.Entities.MovingEntities.Enemies;

import dungeonmania.Entities.MovingEntities.MovementStrategy;
import dungeonmania.Entities.MovingEntities.MovingEntity;
import dungeonmania.util.Position;

public class Enemy extends MovingEntity {

    public Enemy(String id, String type, Position position, double health, boolean isInteractable, MovementStrategy movementStrategy, double attack) {
        super(id, type, position, health, isInteractable, movementStrategy, attack);
    }
    
}
