package dungeonmania.Entities.StaticEntities.CollectableEntities.BuildableEntities;

import dungeonmania.Entities.StaticEntities.CollectableEntities.CollectableEntity;
import dungeonmania.util.Position;

public abstract class BuildableEntity extends CollectableEntity {

    public BuildableEntity(boolean isPickedUp, int durability, Position position, String id, String type) {
        super(isPickedUp, durability, position, id, type);
    }

    public abstract void build();
}
