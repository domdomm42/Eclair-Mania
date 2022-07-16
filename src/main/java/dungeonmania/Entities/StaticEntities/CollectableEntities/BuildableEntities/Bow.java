package dungeonmania.Entities.StaticEntities.CollectableEntities.BuildableEntities;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import dungeonmania.Dungeon;
import dungeonmania.Entities.MovingEntities.Player;
import dungeonmania.Entities.MovingEntities.PlayerBelongings.Inventory;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.Position;

public class Bow extends BuildableEntity {

    public Bow(String id) { // position is always the players position
        super(true, Dungeon.getConfigValue("bow_durability"), Dungeon.getPlayer().getPosition(), id, "bow");
    }

    @Override
    public void build(String buildable) throws InvalidActionException, IllegalArgumentException {
        // player's inventory must have 1 wood and 3 arrows
        Inventory inventory = Dungeon.getPlayer().getInventory();
        
        if (inventory.canBuildBow()) {
            inventory.addItem(new Bow(Integer.toString(Dungeon.getEntities().size())));
        }
    }
    
}
