package dungeonmania.Entities.MovingEntities.MovementStrategies;

import dungeonmania.Entities.MovingEntities.MovementStrategy;
import dungeonmania.Entities.MovingEntities.Player;
import dungeonmania.util.Direction;

public class PlayerMovementStrategy extends MovementStrategy {
    private Player player;
    
    public void move(Direction direction) {
        player.setPositionByDirection(direction);
    }
}
