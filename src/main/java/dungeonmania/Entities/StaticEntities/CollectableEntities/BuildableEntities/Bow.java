package dungeonmania.Entities.StaticEntities.CollectableEntities.BuildableEntities;

import dungeonmania.Dungeon;
import dungeonmania.util.Position;

public class Bow extends BuildableEntity {

    public Bow(String id) { // position is always the players position
        super(true, Dungeon.getConfigValue("bow_durability"), position, id, "bow");
    }
    
}
