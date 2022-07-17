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
        Spider spider = (Spider) getEntity();
        int numberOfTicks = spider.getNumberOfTicks();
        Position spawnPosition = spider.getSpawnPosition();

        List<Position> adjacentPositions = spawnPosition.getAdjacentPositions();

        if (numberOfTicks == 0) {
            spider.setPosition(adjacentPositions.get(1));
            spider.setPositionIterator(1);
        } 
        if (spider.getIsClockwise()) {
            if (isValidMove(adjacentPositions.get(spider.getPositionIterator()))) {
                spider.setPosition(adjacentPositions.get((spider.getPositionIterator())));
                spider.setPositionIterator((spider.getPositionIterator() + 1) % 8);
            } else {
                spider.setIsClockwise(false);
                spider.setPosition(adjacentPositions.get(((spider.getPositionIterator() - 2) + 8) % 8));
                spider.setPositionIterator(((spider.getPositionIterator() - 2) + 8 -1) % 8);
            }
        } else if (spider.getIsClockwise() == false) {
            if (isValidMove(adjacentPositions.get(spider.getPositionIterator()))) {
                spider.setPosition(adjacentPositions.get((spider.getPositionIterator())));
                spider.setPositionIterator(((spider.getPositionIterator() - 1) + 8) % 8);
            } else {
                spider.setIsClockwise(true);
                spider.setPosition(adjacentPositions.get(((spider.getPositionIterator() + 2))% 8));
                spider.setPositionIterator((spider.getPositionIterator() + 3) % 8);
            }
        }

        // Battle if spider lands on player
        Position newPosition = spider.getPosition();
        List<Entity> entitiesOnPosition = Dungeon.getEntitiesAtPosition(newPosition);
        if (entitiesOnPosition.stream().anyMatch(entity -> entity instanceof Player)) {
            Dungeon.addBattle(new Battle((Player) entitiesOnPosition.stream().filter(entity -> entity instanceof Player).findFirst().get(), (Enemy) spider));
            return;
        }
       
    
    }
        
        
        

    
    @Override
    public boolean isValidMove(Position position) {
        if(Dungeon.isEntityOnPosition(position, "boulder")) {
            return false;
        } else {
            return true;
        }
        
    }
}


