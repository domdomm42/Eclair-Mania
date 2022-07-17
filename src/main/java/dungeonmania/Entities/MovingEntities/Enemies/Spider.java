package dungeonmania.Entities.MovingEntities.Enemies;

import dungeonmania.Dungeon;
import dungeonmania.Entities.MovingEntities.MovementStrategies.SpiderMovementStrategy;
import dungeonmania.util.Position;
import java.util.Random;

public class Spider extends Enemy {
    private int numberOfTicks;
    private int positionIterator;
    private int spider_spawn_rate;
    private boolean isClockwise = true;
    private Position spawnPosition;

    

    public Spider(String id, Position position) {
        super(id, "spider", position, Dungeon.getConfigValue("spider_health"), false, new SpiderMovementStrategy(), Dungeon.getConfigValue("spider_attack"));
        getMovementStrategy().setEntity(this);
        this.numberOfTicks = 0;
        this.spawnPosition = position;
        this.spider_spawn_rate = Dungeon.getConfigValue("spider_spawn_rate");
    }

    @Override
    public void tick() {
        getMovementStrategy().move();
        numberOfTicks += 1;
    }

    public int getNumberOfTicks() {
        return numberOfTicks;
    }

    public int getPositionIterator() {
        return positionIterator;
    }

    public void setPositionIterator(int positionIterator) {
        this.positionIterator = positionIterator;
    }

    public boolean getIsClockwise() {
        return isClockwise;
    }

    public void setIsClockwise(boolean isClockwise) {
        this.isClockwise = isClockwise;
    }

    


    public Position getSpawnPosition() {
        return spawnPosition;
    }

    

    
    
}
