package dungeonmania.Entities.MovingEntities.MovementStrategies;

import dungeonmania.Dungeon;
import dungeonmania.Entities.Entity;
import dungeonmania.Entities.MovingEntities.MovementStrategy;
import dungeonmania.Entities.MovingEntities.Enemies.Spider;
import dungeonmania.util.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;



public class SpiderMovementStrategy extends MovementStrategy {
    @Override
    public void move() {
        Position currentPosition = getEntity().getPosition();

        
        Spider spider = (Spider) getEntity();

        int numberOfTicks = spider.getNumberOfTicks();
        List<Position> adjacentPositions = currentPosition.getAdjacentPositions();
        Position nextPosition = getNextPosition(currentPosition, spider, adjacentPositions);
        
        if (numberOfTicks == 0 && isNextPositionBoulder(nextPosition, spider)) { //if boulder is above spawn --> spider stays stationary
            return;
        } else if (numberOfTicks == 0 && !isNextPositionBoulder(nextPosition, spider)) { // spider spawns, next move is up
            spider.setPositionIterator(1);
            getEntity().setPosition(nextPosition);
            return;
        } else if ((spider.getIsClockwise() && isNextPositionBoulder(nextPosition, spider)) || (!spider.getIsClockwise() && isNextPositionBoulder(nextPosition, spider))) { // if the next move is boulder, switch direction of spider
            spider.setIsClockwise(!spider.getIsClockwise());
        } 

        // get nextPosition 
        // case 1: if already was moving clockwise or anti clockwise and not boulder next position stays the same
        // case 2: if there is a boulder --> direction switches and next position is in the opposite direction
        if (spider.getIsClockwise()) {
            spider.setPositionIterator((spider.getPositionIterator() + 1) % 9);
            nextPosition = getNextPosition(currentPosition, spider, adjacentPositions);
            
        } else {
            spider.setPositionIterator((spider.getPositionIterator() - 1) % 9);
            nextPosition = getNextPosition(currentPosition, spider, adjacentPositions);
        }

        getEntity().setPosition(nextPosition);
        spider.addOneToNumberOfTicks(); // increment number of ticks of spider
    }

    public boolean isNextPositionBoulder(Position nextPosition, Spider spider) {

            List<Entity> entitiesAtPosition = Dungeon.getEntitiesAtPosition(nextPosition).stream().filter(entity -> entity.getType().equals("boulder")).collect(Collectors.toList());
            if (entitiesAtPosition.isEmpty() == false) {
                // spider.setIsClockwise(!spider.getIsClockwise());
                return true;
            }

            return false;
            
           
    }

    public Position getNextPosition(Position currentPosition, Spider spider, List<Position> adjacentPositions) {
        Position nextPosition = new Position (1,1,1);
        
        if (spider.getNumberOfTicks() == 0) { // move up
            nextPosition = adjacentPositions.get(1);
        } else if (spider.getIsClockwise() /* && isNextPositionBoulder(nextPosition) == false */) { // move clockwise
            nextPosition = adjacentPositions.get((spider.getPositionIterator() + 1) % 9);
        } else if (spider.getIsClockwise() == false ) {
            nextPosition = adjacentPositions.get((spider.getPositionIterator() - 1) % 9);
        }
        
        return nextPosition;
    }
}


