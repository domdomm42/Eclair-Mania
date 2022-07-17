package dungeonmania.Entities.StaticEntities.CollectableEntities.Potions;

import dungeonmania.Dungeon;
import dungeonmania.util.Position;

public class InvincibilityPotion extends Potion {

    public InvincibilityPotion(Position position, String id) {
        super(false, 1, position, id, "invincibility_potion", Dungeon.getConfigValue("invincibility_potion_duration"));
    }
}
