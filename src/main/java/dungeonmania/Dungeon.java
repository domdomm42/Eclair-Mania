package dungeonmania;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.JsonParser;

import dungeonmania.Entities.Entity;
import dungeonmania.Entities.MovingEntities.Player;
import dungeonmania.Entities.StaticEntities.CollectableEntities.CollectableEntity;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.FileLoader;
import dungeonmania.util.Position;

public class Dungeon {
    private static String id;
    private static String dungeonName;
    private static JSONObject config;
    private static ArrayList<Entity> entities = new ArrayList<Entity>();
    private static ArrayList<CollectableEntity> items = new ArrayList<CollectableEntity>();
    private static ArrayList<Battle> battles = new ArrayList<Battle>();
    private static Goal goals;
    private static Set<String> completedGoals = new HashSet<String>();
    private static int enemiesKilled = 0;

    public static int incrementKilledEntities() {
        return enemiesKilled + 1;
    }

    public static void addBattle(Battle battle) {
        battles.add(battle);
    }

    public static void instantiateDungeonEntitiesAndGoals(String dungeonName) throws FileNotFoundException {
        id = "dungeon";
        Dungeon.dungeonName = dungeonName;
        File dungeonFile = new File("src/test/resources/dungeons/".concat(dungeonName));
        FileReader reader = new FileReader(dungeonFile);
        JSONObject obj = new JSONObject( JsonParser.parseReader(reader) );
        JSONArray entities = obj.getJSONArray("entities");
        for (int i = 0; i < entities.length(); i++) {
            String type = entities.getJSONObject(i).getString("type");
            Map<String, String> creationArguments = new HashMap<String, String>();
            creationArguments.put("x", entities.getJSONObject(i).getString("x"));
            creationArguments.put("y", entities.getJSONObject(i).getString("y"));
            creationArguments.put("keyId", entities.getJSONObject(i).getString("key"));
            creationArguments.put("color", entities.getJSONObject(i).getString("colour"));
            creationArguments.put("id", Integer.toString(Dungeon.entities.size()));

            Dungeon.entities.add(EntityFactory.createEntity(type, creationArguments));
        }
        JSONObject goals = obj.getJSONObject("goal-conditions");
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
        File configFile = new File("src/test/resources/dungeons/".concat(configName));
        FileReader configReader = new FileReader(configFile);
        JSONObject configObj = new JSONObject( JsonParser.parseReader(configReader) );
        config = configObj;
    }

    public static int getConfigValue(String key) {
        return config.getInt(key);
    }

    public static void removeEntity(Entity entity) {
        entities.remove(entity);
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
        List<ItemResponse> itemResponses = items.stream().map(item -> new ItemResponse(item.getId(), item.getType())).collect(Collectors.toList());
        List<BattleResponse> battleResponses = battles.stream().map(battle -> new BattleResponse(battle.getEnemy().getType(), battle.getRoundResponses(), battle.getInitialPlayerHp(), battle.getInitialEnemyHp())).collect(Collectors.toList());

        return new DungeonResponse(id, dungeonName, entityResponses, itemResponses, battleResponses, getPlayer().getBuildables(), goals.toString());
    }

    public static void tick() {
        entities.stream().filter(entity -> entity.getType().equals("player")).forEach(entity -> entity.tick());
        entities.stream().filter(entity -> !entity.getType().equals("player")).forEach(entity -> entity.tick());
    }

    private static void updateGoals() {
        if (Dungeon.getEntitiesOfType("zombie_toast_spawner").size() == 0 && enemiesKilled > getConfigValue("enemy_goal")) completedGoals.add(":enemies");
    
        if (getPlayer().getNumberOfTreasures() > getConfigValue("treasure_goal")) completedGoals.add(":treasure");
        else if (completedGoals.contains(":treasure")) completedGoals.remove(":treasure");
    
        if (getEntitiesOfType("switch").stream().allMatch(floorSwitch -> getFirstEntityOfTypeOnPosition(floorSwitch.getPosition(), "boulder") != null)) completedGoals.add(":boulders");
        else completedGoals.remove(":boulders");

        if (goals.toString().contains(":treasure") || goals.toString().contains(":boulders") || goals.toString().contains(":enemies")) completedGoals.remove(":exit");
        else if (getFirstEntityOfTypeOnPosition(getPlayer().getPosition(), "exit") != null) completedGoals.add(":exit");
        else completedGoals.remove(":exit");
    }

    public static void tick(Direction movementDirection) {
        tick();
        entities.stream().filter(entity -> entity.getType().equals("player")).forEach(entity -> entity.tick(movementDirection));
        entities.stream().filter(entity -> !entity.getType().equals("player")).forEach(entity -> entity.tick(movementDirection));
        updateGoals();
    }
    
    public static void tick(String itemId) throws InvalidActionException, IllegalArgumentException {
        tick();
        for (Entity entity : entities.stream().filter(entity -> entity.getType().equals("player")).collect(Collectors.toList())) entity.tick(itemId);
        for (Entity entity : entities.stream().filter(entity -> !entity.getType().equals("player")).collect(Collectors.toList())) entity.tick(itemId);
        updateGoals();
    }

    // only building the buildable string there, not all
    public static void build(String buildable) throws InvalidActionException, IllegalArgumentException {
        tick();
        getPlayer().build(buildable);
        updateGoals();
    }

    public static void interact(String entityId) throws IllegalArgumentException, InvalidActionException {
        tick();
        Entity entity = getEntityFromId(entityId);
        if (entity == null) throw new IllegalArgumentException("No matching ID");
        entity.interact();
        updateGoals();
    }

    public static ArrayList<Entity> getEntities() {
        return entities;
    }

    public static void setEntities(ArrayList<Entity> entities) {
        Dungeon.entities = entities;
    }
    //
    
}
