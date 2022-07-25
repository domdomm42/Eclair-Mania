package dungeonmania;

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
import dungeonmania.Entities.StaticEntities.CollectableEntities.Sword;
import dungeonmania.Entities.StaticEntities.CollectableEntities.Treasure;
import dungeonmania.Entities.StaticEntities.CollectableEntities.Wood;
import dungeonmania.Entities.StaticEntities.CollectableEntities.BuildableEntities.Bow;
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
        Position position = new Position(entityDetails.get("x").getAsInt(), entityDetails.get("y").getAsInt());
        String id = Integer.toString(totalEntitiesCreated);
        String keyId = entityDetails.get("key").getAsString();
        String color = entityDetails.get("color").getAsString();
        totalEntitiesCreated += 1;

        switch (type) {
            case "player":
                return new Player(id, position);
            case "spider":
                return new Spider(id, position);
            case "zombie_toast":
                return new ZombieToast(id, position);
            case "mercenary":
                return new Mercenary(id, position);
            case "wall":
                return new Wall(position, id);
            case "exit":
                return new Exit(position, id);
            case "boulder":
                return new Boulder(position, id);
            case "switch": 
                return new FloorSwitch(position, id);
            case "door":
                return new Door(position, "door-".concat(keyId));
            case "key":
                return new Key(position, "key-".concat(keyId).concat("door-").concat(keyId));
            case "portal":
                return new Portal(position, id, color);
            case "zombie_toast_spawner":
                return new ZombieToastSpawner(position, id);
            case "treasure":
                return new Treasure(position, id);
            case "invincibility_potion":
                return new InvincibilityPotion(position, id);
            case "invisibility_potion":
                return new InvisibilityPotion(position, id);
            case "wood":
                return new Wood(position, id);
            case "arrow":
                return new Arrow(id, position);
            case "bomb":
                return new Bomb(position, id);
            case "sword":
                return new Sword(position, id);
            case "bow":
                return new Bow(id);
            case "shield":
                return new Shield(id);
            default:
                totalEntitiesCreated -= 1;
                throw new IllegalArgumentException("Entity type does not exist");
        }
    }
}
