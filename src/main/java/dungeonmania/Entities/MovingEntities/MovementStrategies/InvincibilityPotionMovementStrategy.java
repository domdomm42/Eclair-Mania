package dungeonmania.Entities.MovingEntities.MovementStrategies;

import dungeonmania.Entities.MovingEntities.MovementStrategy;

import dungeonmania.Entities.MovingEntities.MovingEntity;

public class InvincibilityPotionMovementStrategy extends MovementStrategy {
    
    @Override
    public void move() {
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
        if (entitiesOnPosition.stream().anyMatch(entity -> entity instanceof Enemy)) {
            Dungeon.addBattle(new Battle(player, (Enemy) entitiesOnPosition.stream().filter(entity -> entity instanceof Enemy).findFirst().get()));
            return;
        }
        player.setPosition(requestedPosition);
        entitiesOnPosition.stream().filter(entity -> entity instanceof CollectableEntity).forEach(entity -> {
            CollectableEntity collectableEntity = (CollectableEntity) entity;
            player.pickup((CollectableEntity) entity);
            collectableEntity.setPickedUp(true);
        });        
    }
}
