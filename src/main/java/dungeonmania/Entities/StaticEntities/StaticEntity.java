package dungeonmania.Entities.StaticEntities;

import dungeonmania.Entities.Entity;
import dungeonmania.util.Position;

public abstract class StaticEntity extends Entity {
        
        public StaticEntity(Position position, String id, String type, boolean Isinteractable) {
            super(id, type, position, false);
        }

}
