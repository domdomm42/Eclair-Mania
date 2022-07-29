package dungeonmania.Entities.MovingEntities.Enemies;

import com.google.gson.JsonObject;

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
    private boolean isMindControlled;
    private int mindControlTicks;
    
    public boolean isHasReachedPlayer() {
        return hasReachedPlayer;
    }

    public void setHasReachedPlayer(boolean hasReachedPlayer) {
        this.hasReachedPlayer = hasReachedPlayer;
    }

    public Mercenary(String id, Position position, String type, double health, double attack) {
        super(id, type, position, health, true, new MercenaryMovementStrategy(), attack);
        getMovementStrategy().setEntity(this);
        isAlly = false;
        hasReachedPlayer = (Dungeon.getPlayer() != null && Position.isAdjacent(getPosition(), Dungeon.getPlayer().getPosition()));
        isMindControlled = false;
        mindControlTicks = 0;
    }

    @Override
    public void tick(Direction playerMovementDirection) {
        getMovementStrategy().move();
        mindControl();
    }

    @Override
    public void tick(String playerAction) {
        getMovementStrategy().move();
        mindControl();
    }

    // when mercenary turns into ally switch isinteractable to false
    public void interactionWithAPlayer(Player player) throws InvalidActionException, IllegalArgumentException {
        if (player == null) return;
        
        if (!isAlly) {

            // Sceptre mind control is prioritised over bribery
            if (player.getInventory("sceptre").size() >= 1) {
                isAlly = true;
                isMindControlled = true;
                player.mindControlMercenary();
                setInteractable(false);
            } else if ((Math.abs(getPosition().getX() - player.getPosition().getX()) <= bribeRadius 
                        && Math.abs(getPosition().getY() - player.getPosition().getY()) <= bribeRadius)) {

                if (player.getInventory("treasure").size() >= bribePrice) {
                    isAlly = true;
                    player.bribeMercenary();
                    setInteractable(false);
                } else {
                    throw new InvalidActionException("Mercenary is not in range to be interacted with");
                }

            } else {
                throw new InvalidActionException("Mercenary is not in range to be bribed or don't have sceptre to mind control");
            }
            

        } else {
            super.interact();
        }
    }

    @Override
    public void interact(boolean isEvil) throws InvalidActionException, IllegalArgumentException {
        if (!isEvil) interact();
        else {
            Player player = (Player) Dungeon.getEntitiesOfType("player").stream().filter(evilPlayer -> ((Player) evilPlayer).isEvil()).findFirst().get();
            interactionWithAPlayer(player);
        }
        
    }

    @Override
    public void interact() throws InvalidActionException, IllegalArgumentException {
        Player player = (Player) Dungeon.getPlayer();
        interactionWithAPlayer(player);
    }


    public boolean isAlly() {
        return isAlly;
    }

    public void setAlly(boolean isAlly) {
        this.isAlly = isAlly;
    }

    public void mindControl() {
        if (isMindControlled) {
            mindControlTicks++;
            if (isMindControlled && mindControlTicks >= Dungeon.getConfigValue("mind_control_duration")) {
                isMindControlled = false;
                isAlly = false;
                setInteractable(true);
                mindControlTicks = 0;
            }
        }
    }

    @Override
    public JsonObject toJsonObject() {
        JsonObject mercJson = super.toJsonObject();
        mercJson.addProperty("isAlly", isAlly);
        mercJson.addProperty("hasReachedPlayer", hasReachedPlayer);
        return super.toJsonObject();
    }

    public boolean isMindControlled() {
        return isMindControlled;
    }

    public void setMindControlled(boolean isMindControlled) {
        this.isMindControlled = isMindControlled;
    }

    public int getMindControlTicks() {
        return mindControlTicks;
    }

    public void setMindControlTicks(int mindControlTicks) {
        this.mindControlTicks = mindControlTicks;
    }

    
}
