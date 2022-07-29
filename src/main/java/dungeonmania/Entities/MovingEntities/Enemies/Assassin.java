package dungeonmania.Entities.MovingEntities.Enemies;

import com.google.gson.JsonObject;

import dungeonmania.Dungeon;
import dungeonmania.Entities.MovingEntities.Player;
import dungeonmania.Entities.MovingEntities.MovementStrategies.MercenaryMovementStrategy;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Assassin extends Mercenary {
    private int assassin_bribe_amount = Dungeon.getConfigValue("assassin_bribe_amount");

    public Assassin(String id, Position position, double health, double attack) {
        //super(id, "assassin", position, Dungeon.getConfigValue("assassin_health"), true, new MercenaryMovementStrategy(), Dungeon.getConfigValue("assasin_attack"));
        super(id, position, "assassin", Dungeon.getConfigValue("assassin_health"), Dungeon.getConfigValue("assassin_attack"));

        
    }


}
