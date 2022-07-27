package dungeonmania.Entities.StaticEntities.CollectableEntities.BuildableEntities;

import dungeonmania.Dungeon;

public class Sceptre extends BuildableEntity {

    public Sceptre(String id) {
        super(true, 1, Dungeon.getPlayer().getPosition(), id, "sceptre");
    }
    
}
