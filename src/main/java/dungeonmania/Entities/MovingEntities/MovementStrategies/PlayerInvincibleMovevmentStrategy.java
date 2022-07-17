package dungeonmania.Entities.MovingEntities.MovementStrategies;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.Dungeon;
import dungeonmania.Entities.MovingEntities.MovementStrategy;
import dungeonmania.Entities.MovingEntities.MovingEntity;
import dungeonmania.Entities.MovingEntities.Player;
import dungeonmania.Entities.StaticEntities.Door;
import dungeonmania.util.Position;

public class PlayerInvincibleMovevmentStrategy extends MovementStrategy {
    @Override
    public void move() {
        Player player = Dungeon.getPlayer();
        if (player == null) return;
        Position currentPosition =  getEntity().getPosition();
        // List<Position> adjacentPositions = currentPosition.getAdjacentPositions();
        List<Position> cardinallyAdjacentPositions = currentPosition.getCardinallyAdjacentPositions();

        // get empty adjacent positions
        List<Position> movableAdjacentPositions = getMovableAdjacentPositions(cardinallyAdjacentPositions);

        Position nextPosition = movableAdjacentPositions.stream().filter(position -> {
            PathFinder pathfinder = new PathFinder(position, Dungeon.getPlayer().getPosition(), (MovingEntity) getEntity());
            if (pathfinder.getMoveDirection() == null) return false;
            if (position.translateBy(pathfinder.getMoveDirection()).equals(currentPosition)) return true;
            return false;
        }).findFirst().map(position -> position).orElse(null);
        if (nextPosition == null) return;
        System.err.println(nextPosition.toString());
        getEntity().setPosition(nextPosition);

    }

    public List<Position> getMovableAdjacentPositions (List<Position> cardinallyAdjacentPositions) {
        List<Position> movableAdjacentPositions = new ArrayList<Position>();
        for (Position position: cardinallyAdjacentPositions) {
            if (isValidMove(position)) {
                movableAdjacentPositions.add(position);
            }
        }
        return movableAdjacentPositions;
    }

    @Override
    public boolean isValidMove(Position position) {
        if (Dungeon.getEntitiesAtPosition(position).isEmpty()) {
            return true;
        } else if (Dungeon.isEntityOnPosition(position, "switch") || Dungeon.isEntityOnPosition(position, "exit")) {
            return true;
        } else if (Dungeon.isEntityOnPosition(position, "door")) {
            Door door = (Door) Dungeon.getFirstEntityOfTypeOnPosition(position, "door");
            if (door.isUnlocked()) {
                return true;
            }
        } 
        return false;
    }
    
}
