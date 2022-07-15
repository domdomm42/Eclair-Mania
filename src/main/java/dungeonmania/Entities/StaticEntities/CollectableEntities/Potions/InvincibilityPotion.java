package dungeonmania.Entities.StaticEntities.CollectableEntities.Potions;

import dungeonmania.util.Position;

public class InvincibilityPotion extends Potion {

    public InvincibilityPotion(Position position, String id) {
        super(true, 1, position, id, "invincibility_potion");
    }

    @Override
    public void use() {
        // TODO Auto-generated method stub
        
    }
    
}
