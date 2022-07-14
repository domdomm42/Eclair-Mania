package dungeonmania.Entities.StaticEntities.CollectableEntities;

import dungeonmania.Entities.StaticEntities.StaticEntity;
import dungeonmania.util.Position;

public abstract class CollectableEntity extends StaticEntity {
    private boolean isPickedUp;
    private int durability;

    public CollectableEntity(boolean isPickedUp, int durability, Position position, String id, String type) {
        super(position, id, type, false);
        this.isPickedUp = isPickedUp;
        this.durability = durability;
        
    }

    public boolean isPickedUp() {
        return isPickedUp;
    }

    public void setPickedUp(boolean isPickedUp) {
        this.isPickedUp = isPickedUp;
    }

    public int getDurability() {
        return durability;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }
    
}