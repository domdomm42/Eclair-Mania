package dungeonmania.Entities.StaticEntities;

import dungeonmania.util.Position;
import dungeonmania.Entities.MovingEntities.MovementStrategies.BoulderMovementStrategy;
import dungeonmania.Entities.MovingEntities.MovementStrategy;



public class Boulder extends StaticEntity {
    private MovementStrategy boulderMovementStrategy;

    public Boulder(Position position, String id) {
        super(position, id, "boulder", false);
        this.boulderMovementStrategy = new BoulderMovementStrategy();
        boulderMovementStrategy.setEntity(this);
    }

    public MovementStrategy getMovementStrategy() {
        return boulderMovementStrategy;
    }
}
