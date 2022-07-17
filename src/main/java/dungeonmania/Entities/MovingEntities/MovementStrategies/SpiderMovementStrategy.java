package dungeonmania.Entities.MovingEntities.MovementStrategies;

import dungeonmania.Battle;
import dungeonmania.Dungeon;
import dungeonmania.Entities.Entity;
import dungeonmania.Entities.MovingEntities.MovementStrategy;
import dungeonmania.Entities.MovingEntities.Player;
import dungeonmania.Entities.MovingEntities.Enemies.Enemy;
import dungeonmania.Entities.MovingEntities.Enemies.Spider;
import dungeonmania.util.Position;
import java.util.List;



public class SpiderMovementStrategy extends MovementStrategy {
    @Override
    public void move() {
        Position currentPosition = getEntity().getPosition();
        Spider spider = (Spider) getEntity();
        int numberOfTicks = spider.getNumberOfTicks();
        Position spawnPosition = spider.getSpawnPosition();

        List<Position> adjacentPositions = spawnPosition.getAdjacentPositions();

        if (numberOfTicks == 0) {
            spider.setPosition(adjacentPositions.get(1));
            spider.setPositionIterator(1);
            // spider.setPositionIterator((spider.getPositionIterator() + 1) % 8);
        } 
        if (spider.getIsClockwise()) {
            if (isValidMove(adjacentPositions.get(spider.getPositionIterator()))) {
                spider.setPosition(adjacentPositions.get((spider.getPositionIterator())));
                spider.setPositionIterator((spider.getPositionIterator() + 1) % 8);
            } 
            /* 
            if (spider.getPositionIterator() == 0) {
                spider.setPosition(adjacentPositions.get(0));
            } else { 
                spider.setPosition(adjacentPositions.get((spider.getPositionIterator())));
            }*/
        } 


        // spider.setPositionIterator((spider.getPositionIterator() + 1) % 8);
    
    }
        
        
        /* 
        Position nextPosition = getNextPosition(currentPosition, spider, adjacentPositions);
        List<Entity> entitiesOnPosition =  Dungeon.getEntitiesAtPosition(nextPosition);
        
        // check for next position being player, if player battle
        if (entitiesOnPosition.stream().anyMatch(entity -> entity instanceof Player)) {
            Dungeon.addBattle(new Battle((Player) entitiesOnPosition.stream().filter(entity -> entity instanceof Player).findFirst().get(), (Enemy) spider));
            return;
        }
        

        // set spawn location
        if (numberOfTicks == 0) {
            spider.setSpawnLocation(currentPosition);
        }
        
        List<Position> adjacentPositions = spider.getSpawnLocation().getAdjacentPositions();
        Position nextPosition = getNextPosition(spider, adjacentPositions);

        if (numberOfTicks == 0 && isValidMove(nextPosition) == false) {
            return;
        } else if (numberOfTicks == 0 && isValidMove(nextPosition)) {
            spider.setPositionIterator(1);
            spider.setSpawnLocation(currentPosition);
            
        } else {
            if (spider.getIsClockwise()) {
                spider.setPositionIterator((spider.getPositionIterator() + 1) % 8);
                nextPosition = getNextPosition(spider, adjacentPositions);
                
            } else {
                spider.setPositionIterator((spider.getPositionIterator() - 1) % 8);
                nextPosition = getNextPosition(spider, adjacentPositions);
            }
        }


        /* 
        if (numberOfTicks == 0 && isValidMove(nextPosition) == false) { //if boulder is above spawn --> spider stays stationary
            return;

        } else if (numberOfTicks == 0 && isValidMove(nextPosition)) { // spider spawns, next move is up
            spider.setPositionIterator(1);
            spider.setSpawnLocation(currentPosition);
            getEntity().setPosition(nextPosition);
            spider.addOneToNumberOfTicks();
            return;
        } else if (isValidMove(nextPosition) == false) { // if the next move is boulder, switch direction of spider
            spider.setIsClockwise(!spider.getIsClockwise());
        } 

        // case 1: if already was moving clockwise or anti clockwise and not boulder next position stays the same
        // case 2: if there is a boulder --> direction switches and next position is in the opposite direction
        // Move spider in specified direction
        
        if (spider.getIsClockwise()) {
            spider.setPositionIterator((spider.getPositionIterator() + 1) % 7);
            nextPosition = getNextPosition(spider, adjacentPositions);
            
        } else {
            spider.setPositionIterator((spider.getPositionIterator() - 1) % 7);
            nextPosition = getNextPosition(spider, adjacentPositions);
        }
        
        //spider.setPositionIterator((spider.getPositionIterator() + 1) % 8);
        // nextPosition = getNextPosition(spider, adjacentPositions);
        getEntity().setPosition(nextPosition);
        spider.addOneToNumberOfTicks(); // increment number of ticks of spider
    }


    public Position getNextPosition(Spider spider, List<Position> adjacentPositions) {
        Position nextPosition = new Position (1,1,1);
        
        if (spider.getNumberOfTicks() == 0) { // move up
            nextPosition = adjacentPositions.get(1);
        } else if (spider.getIsClockwise()) { // move clockwise
            nextPosition = adjacentPositions.get((spider.getPositionIterator() + 1) % 7);
        } else if (spider.getIsClockwise() == false ) {
            nextPosition = adjacentPositions.get((spider.getPositionIterator() - 1) % 7);
        }
        
        return nextPosition;
    }

    */
    @Override
    public boolean isValidMove(Position position) {
        if(Dungeon.isEntityOnPosition(position, "boulder")) {
            return false;
        } else {
            return true;
        }
        
    }
}


