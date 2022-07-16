package dungeonmania;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import dungeonmania.Entities.Entity;
import dungeonmania.Entities.MovingEntities.Player;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Dungeon {
    private static String id;
    private static String dungeonName;
    private static JsonObject config;
    private static ArrayList<Entity> entities;
    private static ArrayList<Battle> battles;
    private static Goal goals;
    private static Set<String> completedGoals;
    private static int enemiesKilled;
    private static ArrayList<Entity> entitiesToAddAfterTick;
    private static ArrayList<Entity> entitiesToRemoveAfterTick;
    private static int numberOfTicks;

    public static int incrementKilledEntities() {
        return enemiesKilled + 1;
    }

    public static void addBattle(Battle battle) {
        battles.add(battle);
    }

    public static void instantiateDungeonEntitiesAndGoals(String dungeonName) throws FileNotFoundException {
        id = "dungeon";
        Dungeon.dungeonName = dungeonName;
        entities = new ArrayList<Entity>();
        entitiesToAddAfterTick = new ArrayList<Entity>();
        entitiesToRemoveAfterTick = new ArrayList<Entity>();
        battles = new ArrayList<Battle>();
        completedGoals = new HashSet<String>();
        enemiesKilled = 0;
<<<<<<< HEAD
=======
        numberOfTicks = 0;
>>>>>>> feature/player
        File dungeonFile = new File("src/main/resources/dungeons/".concat(dungeonName).concat(".json"));
        FileReader reader = new FileReader(dungeonFile);
        JsonObject obj = (JsonObject) JsonParser.parseReader(reader);
        JsonArray entities = obj.getAsJsonArray("entities");
        for (int i = 0; i < entities.size(); i++) {;
            String type = entities.get(i).getAsJsonObject().get("type").getAsString();
            Map<String, String> creationArguments = new HashMap<String, String>();
            creationArguments.put("x", entities.get(i).getAsJsonObject().get("x").getAsString());
            creationArguments.put("y", entities.get(i).getAsJsonObject().get("y").getAsString());
            if (type.equals("portal")) System.out.println(entities.get(i).getAsJsonObject().get("colour").getAsString());
            if (type.equals("door") || type.equals("key")) creationArguments.put("key", entities.get(i).getAsJsonObject().get("key").getAsString());
            if (type.equals("portal")) creationArguments.put("color", entities.get(i).getAsJsonObject().get("colour").getAsString());
            creationArguments.put("id", Integer.toString(Dungeon.entities.size()));

            Entity requestedEntity = EntityFactory.createEntity(type, creationArguments);
            if (requestedEntity != null ) Dungeon.entities.add(requestedEntity);
        }
        JsonObject goals = obj.getAsJsonObject("goal-condition");
        Dungeon.goals = new Goal(goals);
        enemiesKilled = 0;
    }

    public static Player getPlayer() {
        return entities.stream().filter(entity -> entity.getType().equals("player")).findFirst().map(playerEntity -> {
            return (Player) playerEntity;
        }).orElse(null);
    }

    public static Entity getEntityFromId(String id) {
        return entities.stream().filter(ent -> ent.getId().equals(id)).findFirst().map(entity -> {
            return entity;
        }).orElse(null);
    }

    public static void setupConfigFile(String configName) throws FileNotFoundException {
        File configFile = new File("src/main/resources/configs/".concat(configName).concat(".json"));
        FileReader configReader = new FileReader(configFile);
        JsonObject obj = (JsonObject) JsonParser.parseReader(configReader);
        Dungeon.config = obj;
    }

    public static int getConfigValue(String key) {
        return Integer.parseInt(config.get(key).getAsString());
    }

    public static void removeEntity(Entity entity) {
        entities.remove(entity);
    }

    public static void addEntity(Entity entity) {
        entities.add(entity);
    }

    public static void addEntityToAddAfterTick(Entity entity) {
        entitiesToAddAfterTick.add(entity);
    }

    public static void addEntityToRemoveAfterTick(Entity entity) {
        entitiesToRemoveAfterTick.add(entity);
    }

    public static void addCompletedGoal(String goal) {
        completedGoals.add(goal);
    }

    public static Set<String> getCompletedGoals() {
        return completedGoals;
    }

    public static List<Entity> getEntitiesAtPosition(Position position) {
        return Dungeon.entities.stream().filter(entity -> entity.getPosition().equals(position)).collect(Collectors.toList());
    }

    public static List<Entity> getEntitiesOfType(String type) {
        return Dungeon.entities.stream().filter(entity -> entity.getType().equals(type)).collect(Collectors.toList());
    }

    public static boolean isEntityOnPosition(Position position, String type) {
        return getEntitiesAtPosition(position).stream().anyMatch(entity -> entity.getType().equals(type));
    }

    public static Entity getFirstEntityOfTypeOnPosition(Position position, String type) {
        return getEntitiesAtPosition(position).stream().filter(entity -> entity.getType().equals(type)).findFirst().map(entity -> {
            return entity;
        }).orElse(null);
    }

    public static DungeonResponse getDungeonResponse() {
        List<EntityResponse> entityResponses = entities.stream().map(entity -> new EntityResponse(entity.getId(), entity.getType(), entity.getPosition(), entity.getIsInteractable())).collect(Collectors.toList());
        List<ItemResponse> itemResponses = getPlayer().getInventory().toItemResponse();
        List<BattleResponse> battleResponses = battles.stream().map(battle -> new BattleResponse(battle.getEnemy().getType(), battle.getRoundResponses(), battle.getInitialPlayerHp(), battle.getInitialEnemyHp())).collect(Collectors.toList());

        return new DungeonResponse(id, dungeonName, entityResponses, itemResponses, battleResponses, getPlayer().getBuildables(), goals.toString());
    }

    public static void tick() {
        entities.forEach(entity -> System.err.println(entity));

        int spiderSpawnRate = Dungeon.getConfigValue("spider_spawn_rate");
        if (numberOfTicks % spiderSpawnRate == 0) {
            spawnSpider();
        }
        numberOfTicks++;

        getPlayer().tick();
        entities.stream().filter(entity -> !entity.getType().equals("player")).forEach(entity -> entity.tick());
    }

    private static void spawnSpider() {
        int xMin = -20;
        int xMax = 20;
        int yMin = -20;
        int yMax = 20;

        Random rand = new Random();
        int xRandom = xMin+ rand.nextInt(xMax - xMin + 1);
        int yRandom = yMin+ rand.nextInt(yMax - yMin + 1);

        Map<String, String> creationArgs = new HashMap<String, String>();
        creationArgs.put("x", Integer.toString(xRandom));
        creationArgs.put("y", Integer.toString(yRandom));
        creationArgs.put("id", Integer.toString(Dungeon.getEntities().size()));

        Dungeon.addEntityToAddAfterTick(EntityFactory.createEntity("spider", creationArgs));
    }

    private static void updateGoals() {
        if (Dungeon.getEntitiesOfType("zombie_toast_spawner").size() == 0 && enemiesKilled > getConfigValue("enemy_goal")) completedGoals.add(":enemies");
    
        if (getPlayer().getNumberOfTreasures() >= getConfigValue("treasure_goal")) completedGoals.add(":treasure");
        else if (completedGoals.contains(":treasure")) completedGoals.remove(":treasure");
    
        if (getEntitiesOfType("switch").stream().allMatch(floorSwitch -> getFirstEntityOfTypeOnPosition(floorSwitch.getPosition(), "boulder") != null)) completedGoals.add(":boulders");
        else completedGoals.remove(":boulders");

        if ((goals.toString().contains(":treasure") && !completedGoals.contains(":treasure")) 
        || (goals.toString().contains(":boulders") && !completedGoals.contains(":boulders")) 
        || (goals.toString().contains(":enemies") && !completedGoals.contains(":enemies"))) completedGoals.remove(":exit");
        else if (getFirstEntityOfTypeOnPosition(getPlayer().getPosition(), "exit") != null) completedGoals.add(":exit");
        else completedGoals.remove(":exit");

        goals.computeComplete();
        System.err.println(completedGoals);
        System.err.println(goals.toString());
    }

    public static void doAfterTick() {
        entitiesToAddAfterTick.forEach(entity -> addEntity(entity));
        entitiesToAddAfterTick = new ArrayList<Entity>();
        entitiesToRemoveAfterTick.forEach(entity -> removeEntity(entity));
        entitiesToRemoveAfterTick = new ArrayList<Entity>();
        updateGoals();
    }

    public static void tick(Direction movementDirection) {
        tick();
        getPlayer().tick(movementDirection);
        entities.stream().filter(entity -> !entity.getType().equals("player")).forEach(entity -> entity.tick(movementDirection));
        doAfterTick();
    }
    
    public static void tick(String itemId) throws InvalidActionException, IllegalArgumentException {
        tick();
        getPlayer().tick(itemId);
        for (Entity entity : entities.stream().filter(entity -> !(entity.getType().equals("player"))).collect(Collectors.toList())) entity.tick(itemId);
        doAfterTick();
    }

    // only building the buildable string there, not all
    public static void build(String buildable) throws InvalidActionException, IllegalArgumentException {
        tick();
        getPlayer().build(buildable);
        doAfterTick();
    }

    public static void interact(String entityId) throws IllegalArgumentException, InvalidActionException {
        tick();
        Entity entity = getEntityFromId(entityId);
        if (entity == null) throw new IllegalArgumentException("No matching ID");
        entity.interact();
        doAfterTick();
    }

    public static ArrayList<Entity> getEntities() {
        return entities;
    }

    public static void setEntities(ArrayList<Entity> entities) {
        Dungeon.entities = entities;
    }
    //
    
}
