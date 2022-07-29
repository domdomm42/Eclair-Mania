package dungeonmania;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import dungeonmania.Entities.Entity;
import dungeonmania.Entities.MovingEntities.Player;
import dungeonmania.Entities.MovingEntities.Enemies.Mercenary;
import dungeonmania.Entities.MovingEntities.PlayerBelongings.Inventory;
import dungeonmania.Entities.MovingEntities.PlayerBelongings.PotionBag;
import dungeonmania.Entities.StaticEntities.CollectableEntities.CollectableEntity;
import dungeonmania.Entities.StaticEntities.CollectableEntities.Potions.Potion;
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
    private static JsonObject config;
    private static JsonObject originalGoals;
    private static ArrayList<Entity> entities;
    private static ArrayList<Battle> battles;
    private static Goal goals;
    private static Set<String> completedGoals;
    private static int enemiesKilled;
    private static ArrayList<Entity> entitiesToAddAfterTick;
    private static ArrayList<Entity> entitiesToRemoveAfterTick;
    private static int numberOfTicks;
    private static JsonArray previousGameStates;

    public static int incrementKilledEntities() {
        return enemiesKilled += 1;
    }

    public static void addBattle(Battle battle) {
        battles.add(battle);
    }

    private static void resetDungeon() {
        id = "dungeon";
        dungeonName = null;
        entities = new ArrayList<Entity>();
        entitiesToAddAfterTick = new ArrayList<Entity>();
        entitiesToRemoveAfterTick = new ArrayList<Entity>();
        battles = new ArrayList<Battle>();
        completedGoals = new HashSet<String>();
        enemiesKilled = 0;
        numberOfTicks = 0;
        originalGoals = null;
        previousGameStates = new JsonArray();
        EntityFactory.resetTotalEntitiesCreated();
    }

    private static void createEntities(JsonObject dungeonJson) {
        JsonArray entities = dungeonJson.getAsJsonArray("entities");
        loadEntities(entities);
    }

    private static JsonObject dungeonFileToJson(String dungeonName) throws IOException {
        String dungeonFile = FileLoader.loadResourceFile("dungeons/".concat(dungeonName).concat(".json"));
        return (JsonObject) JsonParser.parseString(dungeonFile);
    }

    public static void saveGame(String name) throws IOException {
        FileWriter savedGame = new FileWriter("src/main/resources/saved_games".concat("/".concat(name.concat(".json"))));
        savedGame.write(toJsonObject(false).toString());
        savedGame.close();
    }

    public static void loadGame(String name) throws IOException {
        FileReader savedGame = new FileReader("src/main/resources/saved_games".concat("/".concat(name).concat(".json")));
        JsonObject savedGameJson = JsonParser.parseReader(savedGame).getAsJsonObject();
        loadGame(savedGameJson);
    }

    public static void loadGame(JsonObject saveState) throws IOException {
        resetDungeon();
        loadAll(saveState);
    }

    public static void loadGame(JsonObject saveState, Player truePlayer) throws IOException {
        resetDungeon();
        Dungeon.addEntity(truePlayer);
        loadAll(saveState);
    }

    public static void loadAll(JsonObject saveState) {
        config = saveState.get("configuration").getAsJsonObject();
        dungeonName = saveState.get("dungeonName").getAsString();
        numberOfTicks = saveState.get("numberOfTicks").getAsInt();
        enemiesKilled = saveState.get("enemiesKilled").getAsInt();
        previousGameStates = saveState.get("previousGameStates").getAsJsonArray();
        loadEntities(saveState.get("entities").getAsJsonArray());
        System.err.println(entitiesToJsonArray(false));
        loadGoals(saveState.get("goal-condition").getAsJsonObject());
        loadInventory(saveState.get("inventory").getAsJsonArray());
        loadPotionBag(saveState.get("potionBag").getAsJsonArray());
    }

    private static void loadGoals(JsonObject goalJson) {
        originalGoals = goalJson;
        goals = new Goal(goalJson);
    }

    private static void loadEntities(JsonArray entitiesJson) {
        for (int i = 0; i < entitiesJson.size(); i++) {;
            Entity requestedEntity = EntityFactory.createEntity(entitiesJson.get(i).getAsJsonObject());
            if (requestedEntity != null ) Dungeon.entities.add(requestedEntity);
        }
    }

    private static void loadInventory(JsonArray itemsArray) {
        Inventory inventory = new Inventory();
        itemsArray.forEach(itemJson -> {
            JsonObject itemJsonObject = itemJson.getAsJsonObject();
            CollectableEntity item = (CollectableEntity) EntityFactory.createEntity(itemJsonObject);
            item.setDurability(itemJsonObject.get("durability").getAsInt());
            inventory.addItem(item);
        });
        getPlayer().setInventory(inventory);
    }

    private static void loadPotionBag(JsonArray potionsArray) {
        PotionBag potionBag = new PotionBag();
        potionsArray.forEach(potionJson -> {
            JsonObject potionJsonObject = potionJson.getAsJsonObject();
            Potion potion = (Potion) EntityFactory.createEntity(potionJsonObject);
            JsonElement currentTicks = potionJsonObject.get("current_ticks");
            if (currentTicks != null)potion.setCurrentTicks(currentTicks.getAsInt());
            potionBag.usePotion(potion);
        });
        getPlayer().setPotionBag(potionBag);
    }

    private static JsonObject toJsonObject(boolean isTimeTravel) {
        JsonObject dungeon = new JsonObject();
        dungeon.addProperty("dungeonName", dungeonName);
        dungeon.addProperty("enemiesKilled", enemiesKilled);
        dungeon.addProperty("numberOfTicks", numberOfTicks);
        dungeon.add("entities", entitiesToJsonArray(isTimeTravel));
        dungeon.add("configuration", config);
        dungeon.add("goal-condition", originalGoals);
        dungeon.add("previousGameStates", previousGameStates);
        if (getPlayer() == null) return dungeon;
        dungeon.add("inventory", getPlayer().getInventory().toJsonArray());
        dungeon.add("potionBag", getPlayer().getPotionBag().toJsonArray());
        return dungeon;
    }

    private static JsonArray entitiesToJsonArray(boolean isTimeTravel) {
        JsonArray entities = new JsonArray();
        Dungeon.entities.forEach(entity -> {
            if (entity.getType().equals("player") && isTimeTravel) {
                entities.add(((Player) entity).toTimeTravelJsonObject());
            } else {
                entities.add(entity.toJsonObject());
            }
        });
        return entities;
    }

    public static void startNewGame(String dungeonName) throws FileNotFoundException, IOException {
        resetDungeon();
        Dungeon.dungeonName = dungeonName;
        JsonObject dungeonJson = dungeonFileToJson(dungeonName);
        createEntities(dungeonJson);
        loadGoals(dungeonJson.getAsJsonObject("goal-condition"));
    }

    public static Player getPlayer() {
        return entities.stream().filter(entity -> entity.getType().equals("player") && !((Player) entity).isEvil()).findFirst().map(playerEntity -> {
            return (Player) playerEntity;
        }).orElse(null);
    }

    public static Entity getEntityFromId(String id) {
        return entities.stream().filter(ent -> ent.getId().equals(id)).findFirst().map(entity -> {
            return entity;
        }).orElse(null);
    }

    public static void setupConfigFile(String configName) throws FileNotFoundException, IOException {
        String configFile = FileLoader.loadResourceFile("configs/".concat(configName).concat(".json"));
        JsonObject obj = (JsonObject) JsonParser.parseString(configFile);
        Dungeon.config = obj;
    }

    public static int getConfigValue(String key) {
        if (config.get(key).isJsonNull()) return 0;
        if (config.get(key) == null) return 0;
        return Integer.parseInt(config.get(key).getAsString());
    }

    public static void timeTravel(int ticks) throws IOException {
        Player truePlayer = getPlayer().deepClone();
        truePlayer.setId("truePlayer");
        System.err.println(truePlayer.toJsonObject());
        getPlayer().setActionsToLastNTicks(ticks);
        loadGame(previousGameStates.get(Math.max(previousGameStates.size() - ticks - 1, 0)).getAsJsonObject(), truePlayer);
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
        List<EntityResponse> entityResponses = entities.stream().map(Entity::toEntityResponse).collect(Collectors.toList());
        List<ItemResponse> itemResponses = getPlayer() != null ? getPlayer().getInventory().toItemResponse() : new ArrayList<ItemResponse>();
        List<BattleResponse> battleResponses = battles.stream().map(Battle::toBattleResponse).collect(Collectors.toList());

        List<String> buildables = getPlayer() != null ? getPlayer().getBuildables() : new ArrayList<String>();
        return new DungeonResponse(id, dungeonName, entityResponses, itemResponses, battleResponses, buildables, goals.toString());
    }

    public static void tick() {
        int spiderSpawnRate = Dungeon.getConfigValue("spider_spawn_rate");
        if (!(spiderSpawnRate == 0)) {
            if (numberOfTicks % spiderSpawnRate == 0) {
                spawnSpider();
            }
        }
        numberOfTicks++;

        getEntitiesOfType("mercenary").forEach(merc -> {
            Mercenary mercenary = (Mercenary) merc;
            if (getPlayer() != null && Position.isAdjacent(merc.getPosition(), getPlayer().getPosition()) && mercenary.isAlly()) mercenary.setHasReachedPlayer(true);
        });

        if (getPlayer() != null) getPlayer().tick();
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

        JsonObject spiderDetails = new JsonObject();
        spiderDetails.addProperty("x", xRandom);
        spiderDetails.addProperty("y", yRandom);
        spiderDetails.addProperty("type", "spider");

        Dungeon.addEntityToAddAfterTick(EntityFactory.createEntity(spiderDetails));
    }

    private static void updateGoals() {
        if (getPlayer() == null) return;
        if (Dungeon.getEntitiesOfType("zombie_toast_spawner").size() == 0 && enemiesKilled >= getConfigValue("enemy_goal")) completedGoals.add(":enemies");
    
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
    }

    public static void doAfterTick() {
        entitiesToAddAfterTick.forEach(entity -> addEntity(entity));
        entitiesToAddAfterTick = new ArrayList<Entity>();
        entitiesToRemoveAfterTick.forEach(entity -> removeEntity(entity));
        entitiesToRemoveAfterTick = new ArrayList<Entity>();
        updateGoals();
        previousGameStates.add(toJsonObject(true));
    }

    public static void tick(Direction movementDirection) {
        tick();
        getPlayer().tick(movementDirection);
        getEntitiesOfType("player").stream().filter(player -> ((Player) player).isEvil()).forEach(evilPlayer -> ((Player)evilPlayer).evilTick());
        entities.stream().filter(entity -> entity != null && !entity.getType().equals("player")).forEach(entity -> entity.tick(movementDirection));
        doAfterTick();
    }
    
    public static void tick(String itemId) throws InvalidActionException, IllegalArgumentException {
        tick();
        getPlayer().tick(itemId);
        getEntitiesOfType("player").stream().filter(player -> ((Player) player).isEvil()).forEach(evilPlayer -> ((Player)evilPlayer).evilTick());
        for (Entity entity : entities.stream().filter(entity -> !entity.getType().equals("player")).collect(Collectors.toList())) entity.tick(itemId);
        doAfterTick();
    }

    public static void build(String buildable) throws InvalidActionException, IllegalArgumentException {
        getPlayer().build(buildable);
    }

    public static void interact(String entityId) throws IllegalArgumentException, InvalidActionException {
        Entity entity = getEntityFromId(entityId);
        if (entity == null) throw new IllegalArgumentException("No matching ID");
        entity.interact();
        getPlayer().addAction("interact-".concat(entityId));
    }

    public static ArrayList<Entity> getEntities() {
        return entities;
    }

    public static void setEntities(ArrayList<Entity> entities) {
        Dungeon.entities = entities;
    }
    //
    
}
