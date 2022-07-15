package dungeonmania.Entities.StaticEntities.CollectableEntities;

import dungeonmania.util.Position;

public abstract class Usable extends CollectableEntity {

    public Usable(boolean isPickedUp, int durability, Position position, String id, String type) {
        super(isPickedUp, durability, position, id, type);
    }

    public abstract void use();

}
