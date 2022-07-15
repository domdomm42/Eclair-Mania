package dungeonmania.Entities.MovingEntities.MovementStrategies;

import dungeonmania.Entities.MovingEntities.MovementStrategy;
import dungeonmania.Entities.MovingEntities.Player;
import dungeonmania.util.Direction;

public class PlayerMovementStrategy extends MovementStrategy {
    public PlayerMovementStrategy(Player player) {
        super(player);
    }
    
    @Override
    public void move(Direction direction) {
        getEntity().setPositionByDirection(direction);
    }
}
