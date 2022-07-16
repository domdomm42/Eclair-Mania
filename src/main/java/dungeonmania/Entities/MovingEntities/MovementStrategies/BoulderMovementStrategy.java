package dungeonmania.Entities.MovingEntities.MovementStrategies;

import java.util.List;

// import dungeonmania.Battle;
import dungeonmania.Dungeon;
import dungeonmania.Entities.Entity;
import dungeonmania.Entities.MovingEntities.MovementStrategy;
import dungeonmania.Entities.StaticEntities.Boulder;
import dungeonmania.Entities.StaticEntities.FloorSwitch;
// import dungeonmania.Entities.MovingEntities.Enemies.Enemy;
// import dungeonmania.Entities.StaticEntities.CollectableEntities.CollectableEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class BoulderMovementStrategy extends MovementStrategy {
    @Override
    public void move(Direction direction) {
        Boulder boulder = (Boulder) getEntity();
        Position requestedPosition = boulder.getPositionInDirection(direction);
        List<Entity> entitiesOnPosition = Dungeon.getEntitiesAtPosition(requestedPosition);
<<<<<<< HEAD
        if (entitiesOnPosition.stream().anyMatch(entity -> entity.getType() == "wall")) return;
        if (entitiesOnPosition.stream().anyMatch(entity -> entity.getType() == "boulder")) return;
=======
        
        // check from list if entity is of type wall
        if (entitiesOnPosition.stream().anyMatch(entity -> entity.getType() == "wall")) return;
        // check from list if entity is of type boulder
        if (entitiesOnPosition.stream().anyMatch(entity -> entity.getType() == "boulder")) return;

        // else move boulder to location
>>>>>>> origin/staticEntitiesFunctions
        getEntity().setPosition(requestedPosition);
    }
}
