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

    public Spider(String id, Position position) {
        super(id, "spider", position, Dungeon.getConfigValue("spider_health"), false, new SpiderMovementStrategy(), Dungeon.getConfigValue("spider_attack"));
        getMovementStrategy().setEntity(this);
        this.numberOfTicks = 0;
        this.spider_spawn_rate = Dungeon.getConfigValue("spider_spawn_rate");
    }

    @Override
    public void tick() {
        getMovementStrategy().move();
        if (numberOfTicks % spider_spawn_rate == 0) {
            spawnSpider();
        }
        numberOfTicks++;
    }

    private void spawnSpider() {
        int xMin = -20;
        int xMax = 20;
        int yMin = -20;
        int yMax = 20;

        Random rand = new Random();
        int xRandom = xMin+ rand.nextInt(xMax - xMin + 1);
        int yRandom = yMin+ rand.nextInt(yMax - yMin + 1);

        this.setPosition(new Position(xRandom, yRandom));

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

    public void addOneToNumberOfTicks() {
        this.numberOfTicks += 1;
    }

    
    
}
