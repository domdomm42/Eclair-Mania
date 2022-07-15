package dungeonmania.Entities.StaticEntities;

import dungeonmania.util.Position;

public class FloorSwitch extends StaticEntity {

    private boolean isTriggered;

    public FloorSwitch(Position position, String id, String type, boolean Isinteractable, boolean isTriggered) {
        super(position, id, type, Isinteractable);
        this.isTriggered = isTriggered;
    }

    public boolean isTriggered() {
        return isTriggered;
    }

    public void setTriggered(boolean isTriggered) {
        this.isTriggered = isTriggered;
    }
    
}
