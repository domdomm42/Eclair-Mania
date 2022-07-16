package dungeonmania.Entities.StaticEntities.CollectableEntities.Potions;

import dungeonmania.Entities.StaticEntities.CollectableEntities.CollectableEntity;
import dungeonmania.util.Position;

public abstract class Potion extends CollectableEntity {

    private int currentTicks;
    private int totalTicks;

    public Potion(boolean isPickedUp, int durability, Position position, String id, String type, int totalTicks) {
        super(isPickedUp, durability, position, id, type);
        this.currentTicks = 0;
        this.totalTicks = totalTicks;
    }

    public int getCurrentTicks() {
        return currentTicks;
    }

    public void setCurrentTicks(int currentTicks) {
        this.currentTicks = currentTicks;
    }

    public int getTotalTicks() {
        return totalTicks;
    }

    public void setTotalTicks(int totalTicks) {
        this.totalTicks = totalTicks;
    }
}
