package dungeonmania.Entities.StaticEntities.CollectableEntities.BuildableEntities;

import java.util.HashMap;
import java.util.Map;

import dungeonmania.Dungeon;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.Position;

public class Shield extends BuildableEntity {

    public Shield(Position position, String id) {
        super(true, Dungeon.getConfigValue("shield_durability"), position, id, "shield");
    }

    @Override
    public void tick() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void tick(String itemId) throws InvalidActionException, IllegalArgumentException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void build(String buildable) throws InvalidActionException, IllegalArgumentException {
        // get players inventory
        // if player has 2 wood and (1 treasure OR 1 key)
        
    }

    @Override
    public void interact(String entityId) throws InvalidActionException, IllegalArgumentException {
        // TODO Auto-generated method stub
        
    }


    
}
