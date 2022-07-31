package dungeonmania.Entities.StaticEntities.CollectableEntities;

import com.google.gson.JsonObject;

import dungeonmania.util.Position;

public class Key extends CollectableEntity {

    public Key(Position position, String id) {
        super(false, 1, position, id, "key");
    }

    @Override
    public JsonObject toJsonObject() {
        JsonObject keyJson = super.toJsonObject();
        keyJson.addProperty("key", Integer.parseInt(getId().split("door-")[1]));
        return keyJson;
    }

    
}
