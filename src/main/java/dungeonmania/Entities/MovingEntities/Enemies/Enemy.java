package dungeonmania.Entities.MovingEntities.Enemies;

import dungeonmania.Entities.MovingEntities.MovingEntity;
import dungeonmania.util.Position;

public class Enemy extends MovingEntity {

    public Enemy(String id, String type, Position position, int health, boolean isInteractable) {
        super(id, type, position, health, isInteractable);
    }
    
}
