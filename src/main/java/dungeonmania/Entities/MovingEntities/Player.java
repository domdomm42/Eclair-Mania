package dungeonmania.Entities.MovingEntities;

import dungeonmania.Entities.MovingEntities.PlayerBelongings.Inventory;
import dungeonmania.Entities.MovingEntities.PlayerBelongings.PotionBag;

public class Player {
    Inventory inventory;
    PotionBag potionBag;

    public Player() {
        inventory = new Inventory();
        potionBag = new PotionBag();
    };
}
