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
import dungeonmania.Entities.StaticEntities.TimeTravellingPortal;
import dungeonmania.Entities.StaticEntities.Wall;
import dungeonmania.Entities.StaticEntities.ZombieToastSpawner;
import dungeonmania.Entities.StaticEntities.CollectableEntities.Arrow;
import dungeonmania.Entities.StaticEntities.CollectableEntities.Bomb;
import dungeonmania.Entities.StaticEntities.CollectableEntities.Key;
import dungeonmania.Entities.StaticEntities.CollectableEntities.SunStone;
import dungeonmania.Entities.StaticEntities.CollectableEntities.Sword;
import dungeonmania.Entities.StaticEntities.CollectableEntities.TimeTurner;
import dungeonmania.Entities.StaticEntities.CollectableEntities.Treasure;
import dungeonmania.Entities.StaticEntities.CollectableEntities.Wood;
import dungeonmania.Entities.StaticEntities.CollectableEntities.BuildableEntities.Bow;
import dungeonmania.Entities.StaticEntities.CollectableEntities.BuildableEntities.MidnightArmour;
import dungeonmania.Entities.StaticEntities.CollectableEntities.BuildableEntities.Sceptre;
import dungeonmania.Entities.StaticEntities.CollectableEntities.BuildableEntities.Shield;
import dungeonmania.Entities.StaticEntities.CollectableEntities.Potions.InvincibilityPotion;
import dungeonmania.Entities.StaticEntities.CollectableEntities.Potions.InvisibilityPotion;
import dungeonmania.Entities.StaticEntities.LogicalEntities.LightBulbOff;
import dungeonmania.Entities.StaticEntities.LogicalEntities.SwitchDoor;
import dungeonmania.Entities.StaticEntities.LogicalEntities.Wire;
import dungeonmania.util.Position;

public class EntityFactory {
    private static int totalEntitiesCreated;

    public static void resetTotalEntitiesCreated() {
        totalEntitiesCreated = 0;
    }

    static public Entity createEntity(JsonObject entityDetails) throws IllegalArgumentException {
        String type = entityDetails.get("type").getAsString();
        String id = Integer.toString(totalEntitiesCreated);
        JsonElement loadedId = entityDetails.get("id");
        JsonElement x = entityDetails.get("x");
        JsonElement y = entityDetails.get("y");
        JsonElement keyId = entityDetails.get("key");
        JsonElement LogicType = entityDetails.get("logic");
        totalEntitiesCreated += 1;
        Entity entity;

        switch (type) {
            case "player":
                JsonElement playerIsEvil = entityDetails.get("isEvil");
                JsonElement playerLastPositionX = entityDetails.get("lastPositionX");
                JsonElement playerLastPositionY = entityDetails.get("lastPositionY");
                JsonElement playerActions = entityDetails.get("actions");
                Player player = new Player(id, new Position(x.getAsInt(), y.getAsInt()));
                if (playerIsEvil != null) {
                    player.setEvil(playerIsEvil.getAsBoolean());
                }
                if (playerLastPositionX != null && playerLastPositionY != null) {
                    player.setLastPosition(new Position(playerLastPositionX.getAsInt(), playerLastPositionY.getAsInt()));
                }
                if (playerActions != null) {
                    playerActions.getAsJsonArray().forEach(action -> player.addAction(action.getAsString()));
                }
                entity = player;
                break;
            case "spider":
                JsonElement spiderNumberOfTicks = entityDetails.get("numberOfTicks");
                JsonElement spiderSpawnPositionX = entityDetails.get("spawnPositionX");
                JsonElement spiderSpawnPositionY = entityDetails.get("spawnPositionY");
                JsonElement spiderIsClockwise = entityDetails.get("isClockwise");
                JsonElement spiderPositionIterator = entityDetails.get("positionIterator");
                Spider spider = new Spider(id, new Position(x.getAsInt(), y.getAsInt()));
                if (spiderNumberOfTicks != null) {
                    spider.setNumberOfTicks(spiderNumberOfTicks.getAsInt());
                }
                if (spiderSpawnPositionX != null && spiderSpawnPositionY != null) {
                    spider.setSpawnPosition(new Position(spiderSpawnPositionX.getAsInt(), spiderSpawnPositionY.getAsInt()));
                }
                if (spiderIsClockwise != null) {
                    spider.setClockwise(spiderIsClockwise.getAsBoolean());
                }
                if (spiderPositionIterator != null) {
                    spider.setPositionIterator(spiderPositionIterator.getAsInt());
                }
                entity = spider;
                break;
            case "zombie_toast":
                entity = new ZombieToast(id, new Position(x.getAsInt(), y.getAsInt()));
                break;
            case "mercenary":
                JsonElement mercenaryIsAlly = entityDetails.get("isAlly");
                JsonElement mercenaryHasReachedPlayer = entityDetails.get("hasReachedPlayer");
                Mercenary mercenary = new Mercenary(id, new Position(x.getAsInt(), y.getAsInt()));
                if (mercenaryIsAlly != null) {
                    mercenary.setAlly(mercenaryIsAlly.getAsBoolean());
                }
                if (mercenaryHasReachedPlayer != null) {
                    mercenary.setHasReachedPlayer(mercenaryHasReachedPlayer.getAsBoolean());
                }
                entity = mercenary;
                break;
            case "wall":
                entity = new Wall(new Position(x.getAsInt(), y.getAsInt()), id);
                break;
            case "exit":
                entity = new Exit(new Position(x.getAsInt(), y.getAsInt()), id);
                break;
            case "boulder":
                entity = new Boulder(new Position(x.getAsInt(), y.getAsInt()), id);
                break;
            case "switch": 
                FloorSwitch floorswitch = new FloorSwitch(new Position(x.getAsInt(), y.getAsInt()), id, null);

                if (!LogicType.getAsString().equals(null)) {
                    floorswitch = new FloorSwitch(new Position(x.getAsInt(), y.getAsInt()), id, LogicType.getAsString());
                }

                entity = floorswitch;
                break;
            case "door":
                entity = new Door(new Position(x.getAsInt(), y.getAsInt()), "door-".concat(keyId.getAsString()));
                break;
            case "key":
                entity = new Key(new Position(x.getAsInt(), y.getAsInt()), "key-".concat(keyId.getAsString()).concat("door-").concat(keyId.getAsString()));
                break;
            case "portal":
                JsonElement color = entityDetails.get("colour");
                entity = new Portal(new Position(x.getAsInt(), y.getAsInt()), id, color.getAsString());
                break;
            case "zombie_toast_spawner":
                entity = new ZombieToastSpawner(new Position(x.getAsInt(), y.getAsInt()), id);
                break;
            case "treasure":
                entity = new Treasure(new Position(x.getAsInt(), y.getAsInt()), id);
                break;
            case "invincibility_potion":
                entity = new InvincibilityPotion(new Position(x.getAsInt(), y.getAsInt()), id);
                break;
            case "invisibility_potion":
                entity = new InvisibilityPotion(new Position(x.getAsInt(), y.getAsInt()), id);
                break;
            case "wood":
                entity = new Wood(new Position(x.getAsInt(), y.getAsInt()), id);
                break;
            case "arrow":
                entity = new Arrow(id, new Position(x.getAsInt(), y.getAsInt()));
                break;
            case "bomb":
                JsonElement bombHasBeenPickedUp = entityDetails.get("hasBeenPickedUp");
                Bomb bomb = new Bomb(new Position(x.getAsInt(), y.getAsInt()), id, null);

                if (!LogicType.getAsString().equals(null)) {
                    bomb = new Bomb(new Position(x.getAsInt(), y.getAsInt()), id, LogicType.getAsString());
                }
                
                if (bombHasBeenPickedUp != null) {
                    bomb.setHasBeenPickedUp(bombHasBeenPickedUp.getAsBoolean());
                }
                entity = bomb;
                break;
            case "sword":
                entity = new Sword(new Position(x.getAsInt(), y.getAsInt()), id);
                break;
            case "bow":
                entity = new Bow(id);
                break;
            case "shield":
                entity = new Shield(id);
                break;
            case "time_turner":
                entity = new TimeTurner(new Position(x.getAsInt(), y.getAsInt()), id);
                break;
            case "sun_stone":
                entity = new SunStone(new Position(x.getAsInt(), y.getAsInt()), id);
                break;
            case "sceptre":
                entity = new Sceptre(id);
                break;
            case "light_bulb_off":
                entity = new LightBulbOff(new Position(x.getAsInt(), y.getAsInt()), id, LogicType.getAsString());
                break;
            case "switch_door":
                entity = new SwitchDoor(new Position(x.getAsInt(), y.getAsInt()), id, LogicType.getAsString());
                break;
            case "wire":
                entity = new Wire(new Position(x.getAsInt(), y.getAsInt()), id);
                break;
            case "midnight_armour":
                entity = new MidnightArmour(id);
                break;
            case "time_travelling_portal":
                entity = new TimeTravellingPortal(new Position(x.getAsInt(), y.getAsInt()), id);
                break;
            default:
                totalEntitiesCreated -= 1;
                throw new IllegalArgumentException("Entity type does not exist");
            }
        if (loadedId != null) {
            entity.setId(loadedId.toString());
        }
        return entity;
    }
}
