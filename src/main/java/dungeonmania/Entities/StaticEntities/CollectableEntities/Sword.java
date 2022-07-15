package dungeonmania.Entities.StaticEntities.CollectableEntities;

import dungeonmania.util.Position;

public class Sword extends CollectableEntity {
    public Sword(Position position, String id) {
        super(false, 10, position, id, "sword");
    } // fix durability to get from config file
}
