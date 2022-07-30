package dungeonmania.Entities.StaticEntities;

import dungeonmania.Dungeon;
import dungeonmania.Entities.StaticEntities.LogicalEntities.Wire;
import dungeonmania.util.Position;

public class FloorSwitch extends StaticEntity {

    private boolean isTriggered;

    public FloorSwitch(Position position, String id) {
        super(position, id, "switch", false);
        this.isTriggered = false;
    }

    public boolean isTriggered() {
        return isTriggered;
    }

    public void setTriggered(boolean isTriggered) {
        this.isTriggered = isTriggered;
    }
    
    @Override
    public void tick() {
        super.tick();
        if (Dungeon.getFirstEntityOfTypeOnPosition(getPosition(), "boulder") == null) setTriggered(false);
        else {
            setTriggered(true);

            // //activate surrounding wires
            // if (Dungeon.getFirstEntityOfTypeOnPosition(getPosition(), "wire") != null) {
            //     Wire wire = (Wire) Dungeon.getFirstEntityOfTypeOnPosition(getPosition(), "wire");
            //     wire.ActivateWire();
            // } 
        }
    }
}
