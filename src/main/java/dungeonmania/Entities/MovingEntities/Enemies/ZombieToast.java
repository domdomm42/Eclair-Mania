package dungeonmania.Entities.MovingEntities.Enemies;

import dungeonmania.Dungeon;
import dungeonmania.Entities.MovingEntities.MovementStrategies.ZombieMovementStrategy;
import dungeonmania.util.Position;

public class ZombieToast extends Enemy {
    /* 
    public ZombieToast(String id, String type, Position position, double health, boolean isInteractable, MovementStrategy movementStrategy, double attack) {
        super(id, type, position, health, isInteractable, movementStrategy, health);
    }
    */

    public ZombieToast(String id, Position position) {
        super(id, "zombie_toast", position, Dungeon.getConfigValue("zombie_health"), false, new ZombieMovementStrategy(), Dungeon.getConfigValue("zombie_attack"));
        getMovementStrategy().setEntity(this);
    }
    

    @Override
    public void tick() {
        getMovementStrategy().move();
    }

    
}
