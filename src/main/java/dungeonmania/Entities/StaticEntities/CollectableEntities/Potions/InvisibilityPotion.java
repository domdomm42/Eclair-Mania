package dungeonmania.Entities.StaticEntities.CollectableEntities.Potions;

import dungeonmania.Dungeon;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.Position;

public class InvisibilityPotion extends Potion {

    public InvisibilityPotion(Position position, String id) {
        super(false, 1, position, id, "invisibility_potion", Dungeon.getConfigValue("invisibility_potion_duration"));
    }

    @Override
    public void use() {
        this.setCurrentTicks(1);
    }

    @Override
    public void tick() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void tick(String itemId) throws InvalidActionException, IllegalArgumentException {
        // TODO Auto-generated method stub
    }
    
}
