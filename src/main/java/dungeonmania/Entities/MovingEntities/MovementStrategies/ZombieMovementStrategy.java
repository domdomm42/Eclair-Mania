package dungeonmania.Entities.MovingEntities.MovementStrategies;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dungeonmania.Dungeon;
import dungeonmania.Entities.MovingEntities.MovementStrategy;
import dungeonmania.Entities.MovingEntities.Enemies.ZombieToast;
import dungeonmania.util.Position;

public class ZombieMovementStrategy extends MovementStrategy {
    @Override
    public void move() {
        ZombieToast zombieToast = (ZombieToast) getEntity();
        Position currentPosition =  zombieToast.getPosition();
        List<Position> adjacentPositions = currentPosition.getAdjacentPositions();

        // get empty adjacent positions
        List<Position> emptyAdjacentPositions = getEmptyAdjacentPositions(adjacentPositions);

        Random rand = new Random();
        int randomNumber = rand.nextInt(emptyAdjacentPositions.size()); // random number, max is number of empty adjacent positions

        // move zombie to random adjacent position
        zombieToast.setPosition(emptyAdjacentPositions.get(randomNumber));

    }

    public List<Position> getEmptyAdjacentPositions (List<Position> adjacentPositions) {
        List<Position> emptyAdjacentPositions = new ArrayList<Position>();
        for (Position position: adjacentPositions) {
            if (Dungeon.getEntityAtPosition(position).isEmpty()) {
                emptyAdjacentPositions.add(position);
            }
        }
        return emptyAdjacentPositions;
    }
}
