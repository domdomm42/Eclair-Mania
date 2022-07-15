package dungeonmania.Entities.MovingEntities.MovementStrategies;

import java.util.List;

import dungeonmania.Battle;
import dungeonmania.Dungeon;
import dungeonmania.Entities.Entity;
import dungeonmania.Entities.MovingEntities.MovementStrategy;
import dungeonmania.Entities.MovingEntities.Player;
import dungeonmania.Entities.MovingEntities.Enemies.Enemy;
import dungeonmania.Entities.StaticEntities.Boulder;
import dungeonmania.Entities.StaticEntities.Portal;
import dungeonmania.Entities.StaticEntities.CollectableEntities.CollectableEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class PlayerMovementStrategy extends MovementStrategy {
    @Override
    public void move(Direction direction) {
        Player player = (Player) getEntity();
        Position requestedPosition = player.getPositionInDirection(direction);
        List<Entity> entitiesOnPosition = Dungeon.getEntitiesAtPosition(requestedPosition);
        if (entitiesOnPosition.stream().anyMatch(entity -> entity.getType() == "portal")) 
            requestedPosition = ((Portal) entitiesOnPosition.stream().filter(entity -> entity.getType() == "portal").findFirst().get()).getEndLocation();
        entitiesOnPosition.stream().filter(entity -> entity instanceof CollectableEntity).forEach(entity -> player.pickup((CollectableEntity) entity));
        if (entitiesOnPosition.stream().anyMatch(entity -> entity.getType() == "wall")) return;
        if (entitiesOnPosition.stream().anyMatch(entity -> entity.getType() == "boulder")) {
            ((Boulder) entitiesOnPosition.stream().filter(entity -> entity.getType() == "boulder").findFirst().get()).getMovementStrategy().move(direction);
            return;
        } 
        if (entitiesOnPosition.stream().anyMatch(entity -> entity instanceof Enemy)) {
            Dungeon.addBattle(new Battle(player, (Enemy) entitiesOnPosition.stream().filter(entity -> entity instanceof Enemy).findFirst().get()));
            return;
        }
        player.setPosition(requestedPosition);
    }
}
