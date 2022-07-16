package dungeonmania.Entities.StaticEntities.CollectableEntities.Potions;

import dungeonmania.Dungeon;
import dungeonmania.util.Position;

public class InvisibilityPotion extends Potion {

    public InvisibilityPotion(Position position, String id) {
        super(false, 1, position, id, "invisibility_potion", Dungeon.getConfigValue("invisibility_potion_duration"));
    }

    @Override
    public void use() {
        Dungeon.getPlayer().usePotion(this);
    }
}
