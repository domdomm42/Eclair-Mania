package dungeonmania.Entities.MovingEntities.Enemies;

import com.google.gson.JsonObject;

import dungeonmania.Dungeon;
import dungeonmania.Entities.MovingEntities.MovementStrategies.SpiderMovementStrategy;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Spider extends Enemy {
    private int numberOfTicks;
    private int positionIterator;
    private boolean isClockwise = true;
    private Position spawnPosition;

    public Spider(String id, Position position) {
        super(id, "spider", position, Dungeon.getConfigValue("spider_health"), false, new SpiderMovementStrategy(), Dungeon.getConfigValue("spider_attack"));
        getMovementStrategy().setEntity(this);
        this.numberOfTicks = 0;
        this.spawnPosition = position;
    }

    @Override
    public void tick(Direction playerMovementDirection) {
        getMovementStrategy().move();
        numberOfTicks += 1;
    }

    @Override
    public void tick(String playerAction) {
        getMovementStrategy().move();
        numberOfTicks += 1;
    }

    public int getNumberOfTicks() {
        return numberOfTicks;
    }

    public void setNumberOfTicks(int numberOfTicks) {
        this.numberOfTicks = numberOfTicks;
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

    public void setClockwise(boolean isClockwise) {
        this.isClockwise = isClockwise;
    }

    public void setSpawnPosition(Position spawnPosition) {
        this.spawnPosition = spawnPosition;
    }

    @Override
    public JsonObject toJsonObject() {
        JsonObject spiderJson = super.toJsonObject();
        spiderJson.addProperty("numberOfTicks", numberOfTicks);
        spiderJson.addProperty("spawnPositionX", spawnPosition.getX());
        spiderJson.addProperty("spawnPositionY", spawnPosition.getY());
        spiderJson.addProperty("positionIterator", positionIterator);
        spiderJson.addProperty("isClockwise", isClockwise);
        return spiderJson;
    }
}
