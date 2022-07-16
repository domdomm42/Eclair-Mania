package dungeonmania.Entities.MovingEntities.PlayerBelongings;

import java.util.Queue;

import dungeonmania.Entities.StaticEntities.CollectableEntities.Potions.Potion;

public class PotionBag {
    private Potion activePotion;
    private Queue<Potion> potionQueue;

    public PotionBag() {}

    public String getActivePotionType() {
        return activePotion.getType();
    }

    public Potion getActivePotion() {
        return activePotion;
    }

    public void usePotion(Potion potion) {
        potionQueue.add(potion);
    }

    public void tick() {
        if (activePotion.getCurrentTicks() == activePotion.getTotalTicks()) {
            if (potionQueue.isEmpty()) activePotion = null;
            else activePotion = potionQueue.poll();
            return;
        }
        activePotion.setCurrentTicks(activePotion.getCurrentTicks() + 1);
    }
}
