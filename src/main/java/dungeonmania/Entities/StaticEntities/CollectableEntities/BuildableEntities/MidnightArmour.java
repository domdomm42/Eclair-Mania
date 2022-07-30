package dungeonmania.Entities.StaticEntities.CollectableEntities.BuildableEntities;

import dungeonmania.Dungeon;

public class MidnightArmour extends BuildableEntity {

    public MidnightArmour(String id) {
        super(true, 1, Dungeon.getPlayer().getPosition(), id, "midnight_armour");
    }
    
}
