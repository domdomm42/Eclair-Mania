package dungeonmania.Entities.MovingEntities.Enemies;

import dungeonmania.Entities.MovingEntities.MovementStrategy;
import dungeonmania.util.Position;

public class Spider extends Enemy {
    private int numberOfTicks = 0;
    private int positionIterator;
    private boolean isClockwise = true;

    public Spider(String id, String type, Position position, double health, boolean isInteractable, MovementStrategy movementStrategy, double attack) {
        super(id, type, position, health, isInteractable, movementStrategy, attack);
        getMovementStrategy().setEntity(this);
    }

    @Override
    public void tick() {
        getMovementStrategy().move();
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
