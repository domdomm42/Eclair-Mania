package dungeonmania;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import dungeonmania.util.Position;

public class Dungeon {
    private static String id;
    private static String dungeonName;
    private static JSONObject config;
    private static ArrayList<Entity> entities;
    private static ArrayList<CollectableEntity> items;
    private static ArrayList<Battle> battles;
    private static Goal goals;

    public static void setId(String id) {
        Dungeon.id = id;
    }

    public static void instantiateDungeonEntitiesAndGoals(String dungeonName) throws FileNotFoundException {
        File dungeonFile = new File("src/test/resources/dungeons/".concat(dungeonName));
        FileReader reader = new FileReader(dungeonFile);
        JSONObject obj = new JSONObject( JsonParser.parseReader(reader) );
        JSONArray entities = obj.getJSONArray("entities");
        for (int i = 0; i < entities.length(); i++) {
            String type = entities.getJSONObject(i).getString("type");
            Map<String, String> creationArguments = new HashMap<String, String>();
            creationArguments.put("x", entities.getJSONObject(i).getString("x"));
            creationArguments.put("y", entities.getJSONObject(i).getString("y"));
            creationArguments.put("id", Integer.toString(Dungeon.entities.size()));

            Dungeon.entities.add(EntityFactory.createEntity(type, creationArguments));
        }
        JSONObject goals = obj.getJSONObject("goal-conditions");
        Dungeon.goals = new Goal(goals);
    }

    public static Player getPlayer() {
        Entity player = entities.stream().filter(entity -> entity.getType() == "player").findFirst().get();
        if (player.getClass() == Player.class) return (Player) player;
        return null;
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

    public static List<Entity> getEntityAtPosition(Position position) {
        return Dungeon.entities.stream().filter(entity -> entity.getPosition() == position).collect(Collectors.toList());
    }

    public static DungeonResponse getDungeonResponse() {
        List<EntityResponse> entityResponses = entities.stream().map(entity -> new EntityResponse(entity.getId(), entity.getType(), entity.getPosition(), entity.getIsInteractable())).collect(Collectors.toList());
        List<ItemResponse> itemResponses = items.stream().map(item -> new ItemResponse(item.getId(), item.getType())).collect(Collectors.toList());
        List<BattleResponse> battleResponses = battles.stream().map(battle -> new BattleResponse(battle.getEnemy().getType(), battle.getRoundResponses(), battle.getInitialPlayerHp(), battle.getInitialEnemyHp())).collect(Collectors.toList());

        return new DungeonResponse(id, dungeonName, entityResponses, itemResponses, battleResponses, getPlayer().getBuildables(), goals.toString());
    }

    public static void tick() {
        entities.forEach(entity -> entity.tick());
    }

    public static void tick(Direction movementDirection) {
        tick();
        entities.forEach(entity -> entity.tick(movementDirection));
    }
    
    public static void tick(String itemId) throws InvalidActionException, IllegalArgumentException {
        tick();
        entities.forEach(entity -> entity.tick(itemId));
    }

    public static void build(String buildable) throws InvalidActionException, IllegalArgumentException {
        tick();
        entities.forEach(entity -> entity.build(buildable));
    }

    public static void interact(String entityId) throws IllegalArgumentException, InvalidActionException {
        tick();
        entities.forEach(entity -> entity.interact(entityId));
    }
}
