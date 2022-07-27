package dungeonmania.Entities.StaticEntities;

import java.util.List;

import dungeonmania.Dungeon;
import dungeonmania.Entities.Entity;
import dungeonmania.Entities.StaticEntities.CollectableEntities.Bomb;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Portal extends StaticEntity {
    private String color;

    public Portal(Position position, String id, String color) {
        super(position, id, "portal", false);
        this.color = color;
    }

    
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    // need to recurse in the case of teleporting into another portal
    public Position getTeleportLocation(Direction direction) {

        // given a portal, get position of other corresponding portal.
        Position correspondingPortalLocation = Dungeon.getEntitiesOfType("portal").stream().filter(entity -> ((Portal) entity).getColor().equals(color)).filter(entity -> !entity.equals(this)).findFirst().map(entity -> {
            return entity.getPosition();
        }).orElse(null);

        Position preferredEndLocation = correspondingPortalLocation.translateBy(direction);
        List<Position> adjacentPositions = correspondingPortalLocation.getCardinallyAdjacentPositions();

        
        if (isPositionMovableForPlayer(preferredEndLocation)) {

            // if preferredEndLocation is a portal
            if (Dungeon.isEntityOnPosition(preferredEndLocation, "portal") == true) {
                return multipleTeleporter(preferredEndLocation, direction);
            }
            return preferredEndLocation;
        } else if (adjacentPositions.stream().anyMatch(location -> isPositionMovableForPlayer(location))) {
            return adjacentPositions.stream().filter(location -> isPositionMovableForPlayer(location)).findFirst().get();
        } else {
            return Dungeon.getPlayer().getPosition();
        }
    }

    public boolean isTeleporterValid() {
        Position correspondingPortalLocation = Dungeon.getEntitiesOfType("portal").stream().filter(entity -> ((Portal) entity).getColor().equals(color)).filter(entity -> !entity.equals(this)).findFirst().map(entity -> {
            return entity.getPosition();
        }).orElse(null);

        List<Position> adjacentPositions = correspondingPortalLocation.getCardinallyAdjacentPositions();

        return adjacentPositions.stream().anyMatch(location -> isPositionMovableForPlayer(location));
    }

    public boolean isPositionMovableForPlayer(Position newPos) {
        List<Entity> entitiesOnPosition = Dungeon.getEntitiesAtPosition(newPos);

        if (Dungeon.isEntityOnPosition(newPos, "wall")) {
            return false;
        } else if (Dungeon.isEntityOnPosition(newPos, "door")) {
            Door door = (Door) Dungeon.getFirstEntityOfTypeOnPosition(newPos, "door");
            if (!door.isUnlocked()) {
                return false;
            } 
        } else if (Dungeon.isEntityOnPosition(newPos, "bomb")) {
            Bomb bomb = (Bomb) entitiesOnPosition.stream().filter(entity -> entity instanceof Bomb).findFirst().get();
            if (bomb.isHasBeenPickedUp()) {
                return false;
            }
        } else if (Dungeon.isEntityOnPosition(newPos, "boulder")) {
            return false;
        } else if (Dungeon.isEntityOnPosition(newPos, "zombie_toast_spawner")) {
            return false;
        }   
        return true;
        
    }

    public Position multipleTeleporter(Position currpos, Direction direction) {

    Position requestedPosition = currpos;

    // while player is standing on portal after teleporting
    while (Dungeon.isEntityOnPosition(requestedPosition, "portal")) {
        Portal portal = (Portal) Dungeon.getFirstEntityOfTypeOnPosition(currpos, "portal");
        requestedPosition = portal.getTeleportLocation(direction);
    }
    return requestedPosition;
}
}
