package dungeonmania.Entities.StaticEntities.CollectableEntities.BuildableEntities;

import dungeonmania.Dungeon;
import dungeonmania.util.Position;

public class Shield extends BuildableEntity {

    public Shield(Position position, String id) {
        super(true, Dungeon.getConfigValue("shield_durability"), position, id, "shield");
    }
    
}
