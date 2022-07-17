package dungeonmania.Entities.StaticEntities.CollectableEntities;

import dungeonmania.util.Position;

public class Treasure extends CollectableEntity {
    public Treasure(Position position, String id) {
        super(false, 1, position, id, "treasure");
    }
}
