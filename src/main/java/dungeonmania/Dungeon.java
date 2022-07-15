package dungeonmania;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.JsonParser;

import dungeonmania.Entities.Entity;
import dungeonmania.Entities.MovingEntities.Player;
import dungeonmania.Entities.StaticEntities.CollectableEntities.CollectableEntity;
import dungeonmania.Entities.StaticEntities.CollectableEntities.BuildableEntities.BuildableEntity;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Dungeon {
    private String id;
    private String dungeonName;
    private static JSONObject config;
    private ArrayList<Entity> entities;
    private ArrayList<CollectableEntity> items;
    private ArrayList<Battle> battles;
    private ArrayList<BuildableEntity> buildables;
    private Goal goals;


    public Dungeon(String id, String dungeonName, String configName) throws IllegalArgumentException, FileNotFoundException {
        instantiateDungeonEntitiesAndGoals(dungeonName);
        setupConfigFile(configName);
        this.id = id;
    }

    public void instantiateDungeonEntitiesAndGoals(String dungeonName) throws FileNotFoundException {
        File dungeonFile = new File("src/test/resources/dungeons/".concat(dungeonName));
        FileReader reader = new FileReader(dungeonFile);
        JSONObject obj = new JSONObject( JsonParser.parseReader(reader) );
        JSONArray entities = obj.getJSONArray("entities");
        for (int i = 0; i < entities.length(); i++) {
            String type = entities.getJSONObject(i).getString("type");
            String x = entities.getJSONObject(i).getString("x");
            String y = entities.getJSONObject(i).getString("y");
            //ADD MORE IF NEEDED TODO

            switch (type) {
                case "player":
                    this.entities.add(new Player(id, type, new Position(Integer.parseInt(x), Integer.parseInt(y)), false));
            }
        }
        JSONObject goals = obj.getJSONObject("goal-conditions");
        this.goals = new Goal(goals);
    }

    public Player getPlayer() {
        Entity player = entities.stream().filter(entity -> entity.getType() == "player").findFirst().get();
        if (player.getClass() == Player.class) return (Player) player;
        return null;
    }

    public void setupConfigFile(String configName) throws FileNotFoundException {
        File configFile = new File("src/test/resources/dungeons/".concat(configName));
        FileReader configReader = new FileReader(configFile);
        JSONObject configObj = new JSONObject( JsonParser.parseReader(configReader) );
        config = configObj;
    }

    public static int getConfigValue(String key) {
        return config.getInt(key);
    }

    public DungeonResponse getDungeonResponse() {
        List<EntityResponse> entityResponses = entities.stream().map(entity -> new EntityResponse(entity.getId(), entity.getType(), entity.getPosition(), entity.getIsInteractable())).collect(Collectors.toList());
        List<ItemResponse> itemResponses = items.stream().map(item -> new ItemResponse(item.getId(), item.getType())).collect(Collectors.toList());
        List<BattleResponse> battleResponses = battles.stream().map(battle -> new BattleResponse(battle.getEnemy().getType(), battle.getRoundResponses(), battle.getInitialPlayerHp(), battle.getInitialEnemyHp())).collect(Collectors.toList());

        return new DungeonResponse(id, dungeonName, entityResponses, itemResponses, battleResponses, /*buildables.toList()*/ new ArrayList<String>(), goals.toString());
    }

    public void tick() {
        //TODO FOR STUFF THAT HAPPENS EVERY TICK REGARDLESS OF ACTION
    }

    public void tick(Direction movementDirection) {
        tick();
        //TODO 
    }
    
    public void tick(String itemId) {
        tick();
        //TODO 
    }

    public void build(String buildable) throws IllegalArgumentException, InvalidActionException {
        tick();
        //TODO 
    }

    public void interact(String entityId) throws IllegalArgumentException, InvalidActionException {
        tick();
        //TODO 
    }
}
