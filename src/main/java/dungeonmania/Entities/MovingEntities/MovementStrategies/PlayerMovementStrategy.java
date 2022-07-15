package dungeonmania.Entities.MovingEntities.MovementStrategies;

import dungeonmania.Entities.MovingEntities.MovementStrategy;
import dungeonmania.util.Direction;

public class PlayerMovementStrategy extends MovementStrategy {
    @Override
    public void move(Direction direction) {
        getEntity().setPositionByDirection(direction);
    }
}
