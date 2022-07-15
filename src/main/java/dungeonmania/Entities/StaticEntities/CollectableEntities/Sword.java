package dungeonmania.Entities.StaticEntities.CollectableEntities;

import dungeonmania.Dungeon;
import dungeonmania.util.Position;

public class Sword extends CollectableEntity {
    public Sword(Position position, String id) {
        super(false, Dungeon.getConfigValue("sword_durability"), position, id, "sword");
    }
}
