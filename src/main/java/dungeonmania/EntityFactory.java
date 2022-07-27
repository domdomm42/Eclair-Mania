package dungeonmania;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import dungeonmania.Entities.Entity;
import dungeonmania.Entities.MovingEntities.Player;
import dungeonmania.Entities.MovingEntities.Enemies.Mercenary;
import dungeonmania.Entities.MovingEntities.Enemies.Spider;
import dungeonmania.Entities.MovingEntities.Enemies.ZombieToast;
import dungeonmania.Entities.StaticEntities.Boulder;
import dungeonmania.Entities.StaticEntities.Door;
import dungeonmania.Entities.StaticEntities.Exit;
import dungeonmania.Entities.StaticEntities.FloorSwitch;
import dungeonmania.Entities.StaticEntities.Portal;
import dungeonmania.Entities.StaticEntities.Wall;
import dungeonmania.Entities.StaticEntities.ZombieToastSpawner;
import dungeonmania.Entities.StaticEntities.CollectableEntities.Arrow;
import dungeonmania.Entities.StaticEntities.CollectableEntities.Bomb;
import dungeonmania.Entities.StaticEntities.CollectableEntities.Key;
import dungeonmania.Entities.StaticEntities.CollectableEntities.SunStone;
import dungeonmania.Entities.StaticEntities.CollectableEntities.Sword;
import dungeonmania.Entities.StaticEntities.CollectableEntities.Treasure;
import dungeonmania.Entities.StaticEntities.CollectableEntities.Wood;
import dungeonmania.Entities.StaticEntities.CollectableEntities.BuildableEntities.Bow;
import dungeonmania.Entities.StaticEntities.CollectableEntities.BuildableEntities.Sceptre;
import dungeonmania.Entities.StaticEntities.CollectableEntities.BuildableEntities.Shield;
import dungeonmania.Entities.StaticEntities.CollectableEntities.Potions.InvincibilityPotion;
import dungeonmania.Entities.StaticEntities.CollectableEntities.Potions.InvisibilityPotion;
import dungeonmania.util.Position;

public class EntityFactory {
    private static int totalEntitiesCreated;

    public static void resetTotalEntitiesCreated() {
        totalEntitiesCreated = 0;
    }

    static public Entity createEntity(JsonObject entityDetails) throws IllegalArgumentException {
        String type = entityDetails.get("type").getAsString();
        String id = Integer.toString(totalEntitiesCreated);
        JsonElement x = entityDetails.get("x");
        JsonElement y = entityDetails.get("y");
        JsonElement keyId = entityDetails.get("key");
        JsonElement color = entityDetails.get("colour");
        totalEntitiesCreated += 1;

        switch (type) {
            case "player":
                return new Player(id, new Position(x.getAsInt(), y.getAsInt()));
            case "spider":
                return new Spider(id, new Position(x.getAsInt(), y.getAsInt()));
            case "zombie_toast":
                return new ZombieToast(id, new Position(x.getAsInt(), y.getAsInt()));
            case "mercenary":
                return new Mercenary(id, new Position(x.getAsInt(), y.getAsInt()));
            case "wall":
                return new Wall(new Position(x.getAsInt(), y.getAsInt()), id);
            case "exit":
                return new Exit(new Position(x.getAsInt(), y.getAsInt()), id);
            case "boulder":
                return new Boulder(new Position(x.getAsInt(), y.getAsInt()), id);
            case "switch": 
                return new FloorSwitch(new Position(x.getAsInt(), y.getAsInt()), id);
            case "door":
                return new Door(new Position(x.getAsInt(), y.getAsInt()), "door-".concat(keyId.getAsString()));
            case "key":
                return new Key(new Position(x.getAsInt(), y.getAsInt()), "key-".concat(keyId.getAsString()).concat("door-").concat(keyId.getAsString()));
            case "portal":
                return new Portal(new Position(x.getAsInt(), y.getAsInt()), id, color.getAsString());
            case "zombie_toast_spawner":
                return new ZombieToastSpawner(new Position(x.getAsInt(), y.getAsInt()), id);
            case "treasure":
                return new Treasure(new Position(x.getAsInt(), y.getAsInt()), id);
            case "invincibility_potion":
                return new InvincibilityPotion(new Position(x.getAsInt(), y.getAsInt()), id);
            case "invisibility_potion":
                return new InvisibilityPotion(new Position(x.getAsInt(), y.getAsInt()), id);
            case "wood":
                return new Wood(new Position(x.getAsInt(), y.getAsInt()), id);
            case "arrow":
                return new Arrow(id, new Position(x.getAsInt(), y.getAsInt()));
            case "bomb":
                return new Bomb(new Position(x.getAsInt(), y.getAsInt()), id);
            case "sword":
                return new Sword(new Position(x.getAsInt(), y.getAsInt()), id);
            case "bow":
                return new Bow(id);
            case "shield":
                return new Shield(id);
            case "sceptre":
                return new Sceptre(id);
            case "sun_stone":
                return new SunStone(new Position(x.getAsInt(), y.getAsInt()), id);
            default:
                totalEntitiesCreated -= 1;
                throw new IllegalArgumentException("Entity type does not exist");
        }
    }
}
