package dungeonmania.Entities.StaticEntities.CollectableEntities.BuildableEntities;


import dungeonmania.Dungeon;
import dungeonmania.Entities.StaticEntities.CollectableEntities.CollectableEntity;
import dungeonmania.util.Position;

public abstract class BuildableEntity extends CollectableEntity {

    public BuildableEntity(boolean isPickedUp, int durability, Position position, String id, String type) {
        super(isPickedUp, durability, position, id, type);
    }

    // the player's position is the buildable entity's position
    @Override
    public void tick() {
        this.setPosition(Dungeon.getPlayer().getPosition());
    }
}
