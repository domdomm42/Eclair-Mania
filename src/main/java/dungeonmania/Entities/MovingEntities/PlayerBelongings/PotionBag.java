package dungeonmania.Entities.MovingEntities.PlayerBelongings;

import java.util.Queue;

import dungeonmania.Entities.StaticEntities.CollectableEntities.Potions.Potion;

public class PotionBag {
    Potion activePotion;
    Queue<Potion> potionQueue;

    public PotionBag() {}
}
