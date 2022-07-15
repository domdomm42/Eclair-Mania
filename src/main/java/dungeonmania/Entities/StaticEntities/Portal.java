package dungeonmania.Entities.StaticEntities;

import dungeonmania.util.Position;

public class Portal extends StaticEntity {
    private Position endLocation;

    public Portal(Position position, String id, String type, boolean Isinteractable, Position endLocation) {
        super(position, id, type, Isinteractable);
        this.endLocation = endLocation;
    }

    public Position getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(Position endLocation) {
        this.endLocation = endLocation;
    }


}
