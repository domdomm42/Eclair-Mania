package dungeonmania.Entities.MovingEntities.MovementStrategies;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dungeonmania.Battle;
import dungeonmania.Dungeon;
import dungeonmania.Entities.Entity;
import dungeonmania.Entities.MovingEntities.MovementStrategy;
import dungeonmania.Entities.MovingEntities.Player;
import dungeonmania.Entities.MovingEntities.Enemies.Enemy;
import dungeonmania.Entities.StaticEntities.Door;
import dungeonmania.util.Position;

public class ZombieMovementStrategy extends MovementStrategy {
    @Override
    public void move() {
        Position currentPosition =  getEntity().getPosition();
        // List<Position> adjacentPositions = currentPosition.getAdjacentPositions();
        List<Position> cardinallyAdjacentPositions = currentPosition.getCardinallyAdjacentPositions();

        // get empty adjacent positions
        List<Position> movableAdjacentPositions = getMovableAdjacentPositions(cardinallyAdjacentPositions);

        Random rand = new Random();
        if (movableAdjacentPositions.size() == 0) return; 
        int randomNumber = rand.nextInt(movableAdjacentPositions.size()); // random number, max is number of empty cardianlly adjacent positions
        Position nextPosition = movableAdjacentPositions.get(randomNumber);

        // if player is also on next position --> battle
        List<Entity> entitiesOnPosition =  Dungeon.getEntitiesAtPosition(nextPosition);
        if (entitiesOnPosition.stream().anyMatch(entity -> entity instanceof Player)) {
            Dungeon.addBattle(new Battle((Player) entitiesOnPosition.stream().filter(entity -> entity instanceof Player).findFirst().get(), (Enemy) this.getEntity()));
            return;
        }
        
        // move zombie to random adjacent position
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
