package dungeonmania.Entities.MovingEntities.MovementStrategies;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dungeonmania.Dungeon;
import dungeonmania.Entities.MovingEntities.MovementStrategy;
import dungeonmania.Entities.StaticEntities.Door;
import dungeonmania.util.Position;

public class ZombieMovementStrategy extends MovementStrategy {
    @Override
    public void move() {
        Position currentPosition =  getEntity().getPosition();
        // List<Position> adjacentPositions = currentPosition.getAdjacentPositions();
        List<Position> cardinallyAdjacentPositions = currentPosition.getCardinallyAdjacentPositions();

        // get empty adjacent positions
        List<Position> emptyAdjacentPositions = getEmptyAdjacentPositions(cardinallyAdjacentPositions);

        Random rand = new Random();
        int randomNumber = rand.nextInt(emptyAdjacentPositions.size()); // random number, max is number of empty cardianlly adjacent positions

        // move zombie to random adjacent position
        getEntity().setPosition(emptyAdjacentPositions.get(randomNumber));

    }

    public List<Position> getEmptyAdjacentPositions (List<Position> cardinallyAdjacentPositions) {
        List<Position> emptyAdjacentPositions = new ArrayList<Position>();
        for (Position position: cardinallyAdjacentPositions) {
            if (Dungeon.getEntitiesAtPosition(position).isEmpty()) {
                emptyAdjacentPositions.add(position);
            } else if (Dungeon.isEntityOnPosition(position, "switch") || Dungeon.isEntityOnPosition(position, "exit")) {
                emptyAdjacentPositions.add(position);
            } else if (Dungeon.isEntityOnPosition(position, "door")) {
                Door door = (Door) Dungeon.getFirstEntityOfTypeOnPosition(position, "door");
                if (door.isUnlocked()) {
                    emptyAdjacentPositions.add(position);
                }
            }
        }
        return emptyAdjacentPositions;
    }
}
