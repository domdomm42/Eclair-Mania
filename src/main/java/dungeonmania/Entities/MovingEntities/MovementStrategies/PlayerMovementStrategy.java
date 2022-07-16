package dungeonmania.Entities.MovingEntities.MovementStrategies;

import java.util.List;

import dungeonmania.Battle;
import dungeonmania.Dungeon;
import dungeonmania.Entities.Entity;
import dungeonmania.Entities.MovingEntities.MovementStrategy;
import dungeonmania.Entities.MovingEntities.Player;
import dungeonmania.Entities.MovingEntities.Enemies.Enemy;
import dungeonmania.Entities.StaticEntities.Boulder;
import dungeonmania.Entities.StaticEntities.Door;
import dungeonmania.Entities.StaticEntities.Portal;
import dungeonmania.Entities.StaticEntities.CollectableEntities.Bomb;
import dungeonmania.Entities.StaticEntities.CollectableEntities.CollectableEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class PlayerMovementStrategy extends MovementStrategy {
    @Override
    public void move(Direction direction) {
        Player player = (Player) getEntity();
        Position requestedPosition = player.getPositionInDirection(direction);
        List<Entity> entitiesOnPosition = Dungeon.getEntitiesAtPosition(requestedPosition);
        if (Dungeon.isEntityOnPosition(requestedPosition, "portal")) 
            requestedPosition = ((Portal) Dungeon.getFirstEntityOfTypeOnPosition(requestedPosition, "portal") ).getEndLocation();
        if (Dungeon.isEntityOnPosition(requestedPosition, "door")) {
            Door door = (Door) Dungeon.getFirstEntityOfTypeOnPosition(requestedPosition, "door");
            if (!door.isUnlocked()) {
                if (player.getInventory("key").stream().filter(entity -> door.getKeyThatUnlock().equals(entity)).findFirst().isEmpty()) return;
                else {
                    player.useKey(door.getKeyThatUnlock());
                    door.setUnlocked(true);
                }
            }
        }
        if (Dungeon.isEntityOnPosition(requestedPosition, "wall")) return;
        if (Dungeon.isEntityOnPosition(requestedPosition, "boulder")) {
            ((Boulder) Dungeon.getFirstEntityOfTypeOnPosition(requestedPosition, "boulder")).getMovementStrategy().move(direction);
            return;
        } 
        player.setPosition(requestedPosition);
        if (entitiesOnPosition.stream().anyMatch(entity -> entity instanceof Enemy)) {
            Dungeon.addBattle(new Battle(player, (Enemy) entitiesOnPosition.stream().filter(entity -> entity instanceof Enemy).findFirst().get()));
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
            // NEED TO ADD IN PORTAL REQUIREMENTS
            return true;
        } else if (Dungeon.isEntityOnPosition(requestedPosition, "bomb")) {
            Bomb bomb = (Bomb) entitiesOnPosition.stream().filter(entity -> entity instanceof Bomb).findFirst().get();
            if (bomb.isHasBeenPickedUp()) {
                return false;
            }
        } 
        return true;
    }
}
