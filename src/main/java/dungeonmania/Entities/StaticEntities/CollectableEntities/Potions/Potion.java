package dungeonmania.Entities.StaticEntities.CollectableEntities.Potions;

import dungeonmania.Entities.StaticEntities.CollectableEntities.Usable;
import dungeonmania.util.Position;

public abstract class Potion extends Usable {

    public Potion(boolean isPickedUp, int durability, Position position, String id, String type) {
        super(isPickedUp, durability, position, id, type);
    }
    
}
