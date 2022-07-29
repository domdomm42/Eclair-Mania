package dungeonmania.Entities.MovingEntities.Enemies;

import java.util.Random;

import com.google.gson.JsonObject;

import dungeonmania.Dungeon;
import dungeonmania.Entities.MovingEntities.Player;
import dungeonmania.Entities.MovingEntities.MovementStrategies.MercenaryMovementStrategy;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Assassin extends Mercenary {
    private int assassin_bribe_amount = Dungeon.getConfigValue("assassin_bribe_amount");

    public Assassin(String id, Position position) {
        //super(id, "assassin", position, Dungeon.getConfigValue("assassin_health"), true, new MercenaryMovementStrategy(), Dungeon.getConfigValue("assasin_attack"));
        super(id, position, "assassin", Dungeon.getConfigValue("assassin_health"), Dungeon.getConfigValue("assassin_attack"));
        
    }

    @Override
    public void interactionWithAPlayer(Player player) throws InvalidActionException, IllegalArgumentException {
        if (player == null) return;
        
        boolean isAlly = this.isAlly();
        boolean isMindControlled = this.isMindControlled();
        int bribeRadius = getBribeRadius();

        if (!isAlly) {

            // Sceptre mind control is prioritised over bribery
            if (player.getInventory("sceptre").size() >= 1) {
                isAlly = true;
                isMindControlled = true;
                player.mindControlMercenary();
                setInteractable(false);
            } else if ((Math.abs(getPosition().getX() - player.getPosition().getX()) <= bribeRadius 
                        && Math.abs(getPosition().getY() - player.getPosition().getY()) <= bribeRadius)) {
                            
                if ((player.getInventory("treasure").size() >= assassin_bribe_amount)) {
                    if (doesAssassinGetBribed()) {
                        isAlly = true;
                        player.bribeAssassin();
                        setInteractable(false);
                    } else {
                        player.bribeAssassin();
                    }   
                } else {
                    throw new InvalidActionException("Not enough treasure to bribe Assassin");
                }

            } else {
                throw new InvalidActionException("Assassin is not in range to be bribed or don't have sceptre to mind control");
            }
            
        } else {
            super.interact();
        }
    }

    public boolean doesAssassinGetBribed() {
        double bribe_fail_rate = Dungeon.getConfigValue("assassin_bribe_fail_rate");
        Random rand = new Random();
        
        if (bribe_fail_rate == 1) {
            return false;
        } else if (bribe_fail_rate == 0) {
            return true;
        }
        
        if (rand.nextInt(100) <= (bribe_fail_rate * 100)) {
            return false;
        } else {
            return true;
        }
        
    }


}
