package dungeonmania.Entities.MovingEntities.MovementStrategies;

import java.util.List;

import dungeonmania.Battle;
import dungeonmania.Dungeon;
import dungeonmania.Entities.Entity;
import dungeonmania.Entities.MovingEntities.MovementStrategy;
import dungeonmania.Entities.MovingEntities.Player;
import dungeonmania.Entities.MovingEntities.Enemies.Enemy;
import dungeonmania.Entities.MovingEntities.Enemies.Mercenary;
import dungeonmania.Entities.StaticEntities.Boulder;
import dungeonmania.Entities.StaticEntities.Door;
import dungeonmania.Entities.StaticEntities.Portal;
import dungeonmania.Entities.StaticEntities.CollectableEntities.Bomb;
import dungeonmania.Entities.StaticEntities.CollectableEntities.CollectableEntity;
import dungeonmania.Entities.StaticEntities.LogicalEntities.SwitchDoor;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class PlayerMovementStrategy extends MovementStrategy {
    @Override
    public void move(Direction direction) {
        Player player = (Player) getEntity();
        if (player == null) return;
        Position requestedPosition = player.getPositionInDirection(direction);
        List<Entity> entitiesOnPosition = Dungeon.getEntitiesAtPosition(requestedPosition);

        // if enter portal
        if (Dungeon.isEntityOnPosition(requestedPosition, "portal")) {
            Portal portal = (Portal) Dungeon.getFirstEntityOfTypeOnPosition(requestedPosition, "portal");
            requestedPosition = portal.getTeleportLocation(direction);
        }
        if (Dungeon.isEntityOnPosition(requestedPosition, "door")) {
            Door door = (Door) Dungeon.getFirstEntityOfTypeOnPosition(requestedPosition, "door");
            if (!door.isUnlocked()) {
                if(player.hasCollectable("sun_stone")) door.setUnlocked(true); // ADDED FOR SUNSTONE 
                else if (player.getInventory("key").stream().filter(entity -> door.getKeyThatUnlock() != null && door.getKeyThatUnlock().equals(entity)).findFirst().isEmpty()) return;
                else {
                    player.useKey(door.getKeyThatUnlock());
                    door.setUnlocked(true);
                }
            }
        }

        // NEW SwitchDoor
        if (Dungeon.isEntityOnPosition(requestedPosition, "switch_door")) {
            SwitchDoor SwitchDoor = (SwitchDoor) Dungeon.getFirstEntityOfTypeOnPosition(requestedPosition, "switch_door");

            // if door is not open
            if (!SwitchDoor.isIsOpen()) return;
        }

        if (Dungeon.isEntityOnPosition(requestedPosition, "wall")) return;
        if (Dungeon.isEntityOnPosition(requestedPosition, "boulder")) {
            ((Boulder) Dungeon.getFirstEntityOfTypeOnPosition(requestedPosition, "boulder")).getMovementStrategy().move(direction);
            if (Dungeon.getFirstEntityOfTypeOnPosition(requestedPosition, "boulder") != null) return;
        } 
        player.setLastPosition(player.getPosition());
        player.setPosition(requestedPosition);
        if (entitiesOnPosition.stream().anyMatch(entity -> entity instanceof Enemy) && !player.activePotionEffect().equals("invisibility_potion")) {
            if (Dungeon.getFirstEntityOfTypeOnPosition(requestedPosition, "mercenary") != null && ((Mercenary) Dungeon.getFirstEntityOfTypeOnPosition(requestedPosition, "mercenary")).isAlly()) return;
            Dungeon.addBattle(new Battle(player, (Enemy) entitiesOnPosition.stream().filter(entity -> entity instanceof Enemy).findFirst().map(entity -> {
                return entity;
            }).orElse(null)));
            return;
        }
        entitiesOnPosition.stream().filter(entity -> entity instanceof CollectableEntity).forEach(entity -> {
            CollectableEntity collectableEntity = (CollectableEntity) entity;
            if (collectableEntity instanceof Bomb) {
                if (((Bomb) collectableEntity).isHasBeenPickedUp()) {
                    return;
                }
            }
            player.pickup((CollectableEntity) entity);
            collectableEntity.setPickedUp(true);
        });
    }

    @Override
    public boolean isValidMove(Position requestedPosition) {
        List<Entity> entitiesOnPosition = Dungeon.getEntitiesAtPosition(requestedPosition);
        
        if (Dungeon.isEntityOnPosition(requestedPosition, "wall")) {
            return false;
        } else if (Dungeon.isEntityOnPosition(requestedPosition, "door")) {
            Door door = (Door) Dungeon.getFirstEntityOfTypeOnPosition(requestedPosition, "door");
            if (!door.isUnlocked()) {
                return false;
            } 
        } else if (Dungeon.isEntityOnPosition(requestedPosition, "portal")) {
            Portal portal = (Portal) Dungeon.getFirstEntityOfTypeOnPosition(requestedPosition, "portal");
            if (portal.isTeleporterValid()) {
                return true;
            } else {
                return false;
            }
            
        } else if (Dungeon.isEntityOnPosition(requestedPosition, "bomb")) {
            Bomb bomb = (Bomb) entitiesOnPosition.stream().filter(entity -> entity instanceof Bomb).findFirst().get();
            if (bomb.isHasBeenPickedUp()) {
                return false;
            }
        } 
        return true;
    }
}
