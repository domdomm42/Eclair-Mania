package dungeonmania.Entities.StaticEntities.CollectableEntities.BuildableEntities;



import dungeonmania.Dungeon;

public class Shield extends BuildableEntity {
    public Shield(String id) {
        super(true, Dungeon.getConfigValue("shield_durability"), Dungeon.getPlayer().getPosition(), id, "shield");
    }
}
