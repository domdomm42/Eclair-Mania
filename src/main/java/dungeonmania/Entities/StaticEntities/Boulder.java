package dungeonmania.Entities.StaticEntities;

import dungeonmania.util.Position;
import dungeonmania.Entities.MovingEntities.MovementStrategies.BoulderMovementStrategy;
import dungeonmania.Entities.MovingEntities.MovingEntity;



public class Boulder extends StaticEntity {

    // implement moving strategy (check from players)
    public Boulder(Position position, String id, String type, boolean Isinteractable) {
        super(position, id, "boulder", false);

    }

    // public void moveBouder() {
    //     MovingEntity.getMovementStrategy().setEntity(this);

    // }


    
}
