package dungeonmania.Entities.MovingEntities.Enemies;

import dungeonmania.Dungeon;
import dungeonmania.Entities.MovingEntities.Player;
import dungeonmania.Entities.MovingEntities.MovementStrategies.MercenaryMovementStrategy;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Mercenary extends Enemy {
    private int bribePrice = Dungeon.getConfigValue("bribe_amount");
    private int bribeRadius = Dungeon.getConfigValue("bribe_radius");
    private boolean isAlly;
    private boolean hasReachedPlayer;
    
    public boolean isHasReachedPlayer() {
        return hasReachedPlayer;
    }

    public void setHasReachedPlayer(boolean hasReachedPlayer) {
        this.hasReachedPlayer = hasReachedPlayer;
    }

    public Mercenary(String id, Position position) {
        super(id, "mercenary", position, Dungeon.getConfigValue("mercenary_health"), true, new MercenaryMovementStrategy(), Dungeon.getConfigValue("mercenary_attack"));
        getMovementStrategy().setEntity(this);
        isAlly = false;
        hasReachedPlayer = (Dungeon.getPlayer() != null && Position.isAdjacent(getPosition(), Dungeon.getPlayer().getPosition()));
    }

    @Override
    public void tick(Direction playerMovementDirection) {
        getMovementStrategy().move();
    }

    @Override
    public void tick(String playerAction) {
        getMovementStrategy().move();
    }

    // when mercenary turns into ally switch isinteractable to false
    @Override
    public void interact() throws InvalidActionException, IllegalArgumentException {
        Player player = Dungeon.getPlayer();
        if (player == null) return;
        if (!isAlly) {
            if (Math.abs(getPosition().getX() - player.getPosition().getX()) <= bribeRadius 
            && Math.abs(getPosition().getY() - player.getPosition().getY()) <= bribeRadius) {
                if (player.getInventory("treasure").size() >= bribePrice) {
                    isAlly = true;
                    player.bribeMercenary();
                    setInteractable(false);
                } else {
                    throw new InvalidActionException("Not enough treasure to bribe Mercenary with");
                }
            } else {
                throw new InvalidActionException("Mercenary is not in range to be interacted with");
            }
        } else {
            super.interact();
        }
    }

    public boolean isAlly() {
        return isAlly;
    }
}
