package dungeonmania;

import java.util.Map;

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

    static public Entity createEntity(String type, Map<String, String> args) throws IllegalArgumentException {
        Position position;
        if (!type.equals("bow") && !type.equals("shield")) position = new Position(Integer.parseInt(args.get("x")), Integer.parseInt(args.get("y")));
        else position = Dungeon.getPlayer().getPosition();
        String id = Integer.toString(totalEntitiesCreated);
        String keyId = args.get("key");
        String color = args.get("color");
        Entity entity;

        switch (type) {
            case "player":
                entity = new Player(id, position);
                break;
            case "spider":
                entity = new Spider(id, position);
                break;
            case "zombie_toast":
                entity = new ZombieToast(id, position);
                break;
            case "mercenary":
                entity = new Mercenary(id, position);
                break;
            case "wall":
                entity = new Wall(position, id);
                break;
            case "exit":
                entity = new Exit(position, id);
                break;
            case "boulder":
                entity = new Boulder(position, id);
                break;
            case "switch": 
                entity = new FloorSwitch(position, id);
                break;
            case "door":
                entity = new Door(position, "door-".concat(keyId));
                break;
            case "key":
                entity = new Key(position, "key-".concat(keyId).concat("door-").concat(keyId));
                break;
            case "portal":
                entity = new Portal(position, id, color);
                break;
            case "zombie_toast_spawner":
                entity = new ZombieToastSpawner(position, id);
                break;
            case "treasure":
                entity = new Treasure(position, id);
                break;
            case "invincibility_potion":
                entity = new InvincibilityPotion(position, id);
                break;
            case "invisibility_potion":
                entity = new InvisibilityPotion(position, id);
                break;
            case "wood":
                entity = new Wood(position, id);
                break;
            case "arrow":
                entity = new Arrow(id, position);
                break;
            case "bomb":
                entity = new Bomb(position, id);
                break;
            case "sword":
                entity = new Sword(position, id);
                break;
            case "bow":
                entity = new Bow(id);
                break;
            case "shield":
                entity = new Shield(id);
                break;
            default :
                throw new IllegalArgumentException("Entity type does not exist");
        }
        totalEntitiesCreated += 1;
        return entity;
    }
}
