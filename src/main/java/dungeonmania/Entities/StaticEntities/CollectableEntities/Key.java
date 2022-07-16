package dungeonmania.Entities.StaticEntities.CollectableEntities;

import dungeonmania.util.Position;

public class Key extends CollectableEntity {

    public Key(Position position, String id) {
        super(false, 1, position, id, "key");
    }
}
