package dungeonmania.Entities.StaticEntities;

import dungeonmania.util.Position;
import dungeonmania.Entities.MovingEntities.MovementStrategies.BoulderMovementStrategy;
import dungeonmania.Entities.MovingEntities.MovementStrategy;



public class Boulder extends StaticEntity {
    private MovementStrategy boulderMovementStrategy;

    // implement moving strategy (check from players)
    public Boulder(Position position, String id, String type, boolean Isinteractable) {
        super(position, id, "boulder", false);
        this.boulderMovementStrategy = new BoulderMovementStrategy();
        boulderMovementStrategy.setEntity(this);
    }

    public MovementStrategy getMovementStrategy() {
        return boulderMovementStrategy;
    }
}
