package dungeonmania.Entities.StaticEntities;

import dungeonmania.Entities.Entity;
import dungeonmania.util.Position;

public abstract class StaticEntity extends Entity {
        // static entities are uninteractable, if it is then just add boolean Isinteractable into the parameters
        public StaticEntity(Position position, String id, String type) {
            super(id, type, position, false);
        }
    
}
