package dungeonmania.Entities.StaticEntities;

import com.google.gson.JsonObject;

import dungeonmania.Dungeon;
import dungeonmania.Entities.StaticEntities.CollectableEntities.Key;
import dungeonmania.util.Position;

public class Door extends StaticEntity {
    private boolean isUnlocked;


    public Door(Position position, String id) {
        super(position, id, "door", false);
        this.isUnlocked = false;
    }


    public boolean isUnlocked() {
        return isUnlocked;
    }


    public void setUnlocked(boolean isUnlocked) {
        this.isUnlocked = isUnlocked;
    }


    public Key getKeyThatUnlock() {
        String keyNum = getId().replace("door-", "");
        return (Key) Dungeon.getPlayer().getInventory().getItemFromId("key-".concat(keyNum).concat(getId()));
    }

    @Override
    public JsonObject toJsonObject() {
        JsonObject doorJson = super.toJsonObject();
        doorJson.addProperty("key", Integer.parseInt(getId().split("-")[1]));
        return doorJson;
    }
}
