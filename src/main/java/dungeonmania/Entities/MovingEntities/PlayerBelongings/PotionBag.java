package dungeonmania.Entities.MovingEntities.PlayerBelongings;

import java.util.LinkedList;
import java.util.Queue;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import dungeonmania.Entities.StaticEntities.CollectableEntities.Potions.Potion;

public class PotionBag {
    private Potion activePotion;
    private Queue<Potion> potionQueue;

    public PotionBag() {
        potionQueue = new LinkedList<Potion>();
    }

    public String getActivePotionType() {
        if (activePotion == null) return "";
        return activePotion.getType();
    }

    public Potion getActivePotion() {
        return activePotion;
    }

    public void usePotion(Potion potion) {
        if (activePotion != null) potionQueue.add(potion);
        else activePotion = potion;
        
    }

    public void tick() {
        if (activePotion == null) return;
        activePotion.setCurrentTicks(activePotion.getCurrentTicks() + 1);
        if (activePotion.getCurrentTicks() > activePotion.getTotalTicks()) {
            if (potionQueue.isEmpty()) activePotion = null;
            else activePotion = potionQueue.poll();
        }
    }

    public JsonArray toJsonArray() {
        JsonArray potionBag = new JsonArray();
        if (activePotion == null) return potionBag;
        JsonObject activePotionJson = activePotion.toJsonObject();
        activePotionJson.addProperty("current_ticks", activePotion.getCurrentTicks());
        potionBag.add(activePotionJson);
        potionQueue.forEach(potion -> potionBag.add(potion.toJsonObject()));
        return potionBag;
    }
}
