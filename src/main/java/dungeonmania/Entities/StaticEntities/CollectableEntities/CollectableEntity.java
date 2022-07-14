package dungeonmania.Entities.StaticEntities.CollectableEntities;

import dungeonmania.Entities.Entity;
import dungeonmania.util.Position;

public class CollectableEntity extends Entity {
    public CollectableEntity(String id, String type, Position position, boolean isInteractable) {
        super(id, type, position, isInteractable);
    }
}
