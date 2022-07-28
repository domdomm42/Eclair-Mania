package dungeonmania.Entities.StaticEntities;

import java.util.List;

import com.google.gson.JsonObject;

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

    public Position getTeleportLocation(Direction direction) {
        Position correspondingPortalLocation = Dungeon.getEntitiesOfType("portal").stream().filter(entity -> ((Portal) entity).getColor().equals(color)).filter(entity -> !entity.equals(this)).findFirst().map(entity -> {
            return entity.getPosition();
        }).orElse(null);

        Position preferredEndLocation = correspondingPortalLocation.translateBy(direction);
        List<Position> adjacentPositions = correspondingPortalLocation.getCardinallyAdjacentPositions();

        
        if (isPositionMovableForPlayer(preferredEndLocation)) {
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

    @Override
    public JsonObject toJsonObject() {
        JsonObject portalJson = super.toJsonObject();
        portalJson.addProperty("colour", color);
        return portalJson;
    }
}
