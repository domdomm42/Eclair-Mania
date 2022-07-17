package dungeonmania.Entities.MovingEntities.Enemies;

import dungeonmania.Dungeon;
import dungeonmania.Entities.MovingEntities.Player;
import dungeonmania.Entities.MovingEntities.MovementStrategies.MercenaryMovementStrategy;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.Position;

public class Mercenary extends Enemy {
    private int bribePrice = Dungeon.getConfigValue("bribe_amount");
    private int bribeRadius = Dungeon.getConfigValue("bribe_radius");
    private boolean isAlly = false;

    
    
    
    public Mercenary(String id, Position position) {
        super(id, "mercenary", position, Dungeon.getConfigValue("mercenary_health"), true, new MercenaryMovementStrategy(), Dungeon.getConfigValue("mercenary_attack"));
        getMovementStrategy().setEntity(this);
    }

    @Override
    public void tick() {
        getMovementStrategy().move();
    }

    // when mercenary turns into ally switch isinteractable to false
    @Override
    public void interact() throws InvalidActionException, IllegalArgumentException {
        Player player = Dungeon.getPlayer();
        if (getIsInteractable()) {
            if (Math.abs(getPosition().getX() - player.getPosition().getX()) <= bribeRadius 
            && Math.abs(getPosition().getY() - player.getPosition().getY()) <= bribeRadius) {
                if (player.getInventory("treasure").size() >= bribePrice) {
                    isAlly = true;
                    setInteractable(false);
                }
            } else throw new InvalidActionException("Mercenary is not in range to be interacted with");
        } else throw new InvalidActionException("Mercenary is already an ally, cannot bribe");
    }

    public boolean isAlly() {
        return isAlly;
    }
}
