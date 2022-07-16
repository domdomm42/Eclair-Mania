package dungeonmania.Entities.StaticEntities;

import dungeonmania.util.Position;

public class Portal extends StaticEntity {
    private Position endLocation;

    public Portal(Position position, String id, Position endLocation) {
        super(position, id, "portal", false);
        this.endLocation = endLocation;
    }

    public Position getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(Position endLocation) {
        this.endLocation = endLocation;
    }


}
