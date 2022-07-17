package dungeonmania.Entities.MovingEntities.MovementStrategies;

import java.util.List;

import dungeonmania.Dungeon;
import dungeonmania.Entities.Entity;
import dungeonmania.Entities.MovingEntities.MovementStrategy;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class BoulderMovementStrategy extends MovementStrategy {
    @Override
    public void move(Direction direction) {
        Position requestedPosition = getEntity().getPositionInDirection(direction);
        if (isValidMove(requestedPosition)) getEntity().setPosition(requestedPosition);
    }

    @Override
    public boolean isValidMove(Position position) {
        List<Entity> entitiesOnPosition = Dungeon.getEntitiesAtPosition(position);
        if (entitiesOnPosition.stream().anyMatch(entity -> entity.getType() == "wall")) return false;
        if (entitiesOnPosition.stream().anyMatch(entity -> entity.getType() == "boulder")) return false;
        return true;
    }
}
