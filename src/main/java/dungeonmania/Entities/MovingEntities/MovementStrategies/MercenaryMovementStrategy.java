package dungeonmania.Entities.MovingEntities.MovementStrategies;

import java.util.List;

import dungeonmania.Battle;
import dungeonmania.Dungeon;
import dungeonmania.Entities.Entity;
import dungeonmania.Entities.MovingEntities.MovementStrategy;
import dungeonmania.Entities.MovingEntities.MovingEntity;
import dungeonmania.Entities.MovingEntities.Enemies.Enemy;
import dungeonmania.Entities.MovingEntities.Enemies.Mercenary;
import dungeonmania.Entities.StaticEntities.Door;
import dungeonmania.Entities.StaticEntities.Portal;
import dungeonmania.Entities.StaticEntities.CollectableEntities.Bomb;
import dungeonmania.util.Position;

public class MercenaryMovementStrategy extends MovementStrategy {
    @Override
    public void move() {
        if (Dungeon.getPlayer().activePotionEffect().equals("invisibility_potion")) {
            moveAsZombie();
            return;
        }
        PathFinder pathfinder = new PathFinder(getEntity().getPosition(), Dungeon.getPlayer().getPosition(), (MovingEntity) getEntity());
        if (pathfinder.getMoveDirection() == null) {
            return;
        } else {
            Position requestedPosition = getEntity().getPosition().translateBy(pathfinder.getMoveDirection());
            getEntity().setPosition(requestedPosition);
            if (!((Mercenary) getEntity()).isAlly() && Dungeon.getFirstEntityOfTypeOnPosition(requestedPosition, "player") != null) {
                Dungeon.addBattle(new Battle(Dungeon.getPlayer(), (Enemy) getEntity()));
            }
        }
    }

    private void moveAsZombie() {
        Mercenary mercenary = (Mercenary) getEntity();
        mercenary.setMovementStrategy(new ZombieMovementStrategy());
        mercenary.getMovementStrategy().setEntity(mercenary);
        mercenary.getMovementStrategy().move();
        mercenary.setMovementStrategy(this);
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
