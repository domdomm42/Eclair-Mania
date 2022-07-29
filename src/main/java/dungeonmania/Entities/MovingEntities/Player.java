package dungeonmania.Entities.MovingEntities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import dungeonmania.Dungeon;
import dungeonmania.EntityFactory;
import dungeonmania.Entities.Entity;
import dungeonmania.Entities.MovingEntities.Enemies.Mercenary;
import dungeonmania.Entities.MovingEntities.MovementStrategies.PlayerMovementStrategy;
import dungeonmania.Entities.MovingEntities.PlayerBelongings.Inventory;
import dungeonmania.Entities.MovingEntities.PlayerBelongings.PotionBag;
import dungeonmania.Entities.StaticEntities.CollectableEntities.CollectableEntity;
import dungeonmania.Entities.StaticEntities.CollectableEntities.Key;
import dungeonmania.Entities.StaticEntities.CollectableEntities.Usable;
import dungeonmania.Entities.StaticEntities.CollectableEntities.Potions.Potion;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Player extends MovingEntity {
    private Inventory inventory;
    private PotionBag potionBag;
    private Position lastPosition;
    private boolean isEvil;
    private ArrayList<String> actions;

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public void setPotionBag(PotionBag potionBag) {
        this.potionBag = potionBag;
    }

    public Position getLastPosition() {
        return lastPosition;
    }

    public void setLastPosition(Position lastPosition) {
        this.lastPosition = lastPosition;
    }

    public ArrayList<String> getActions() {
        return actions;
    }

    public void setActions(ArrayList<String> actions) {
        this.actions = actions;
    }

    public void addAction(String action) {
        actions.add(action);
    }

    public Player(String id, Position position) {
        super(id, "player", position, Dungeon.getConfigValue("player_health"), false, new PlayerMovementStrategy(), Dungeon.getConfigValue("player_attack"));
        inventory = new Inventory();
        potionBag = new PotionBag();
        lastPosition = position;
        isEvil = false;
        actions = new ArrayList<String>();
        getMovementStrategy().setEntity(this);
    };

    public Player deepClone() {
        Player clonedplayer = (Player) EntityFactory.createEntity(toJsonObject());

        Inventory inventory = new Inventory();
        JsonArray itemsArray = this.inventory.toJsonArray();
        itemsArray.forEach(itemJson -> {
            JsonObject itemJsonObject = itemJson.getAsJsonObject();
            CollectableEntity item = (CollectableEntity) EntityFactory.createEntity(itemJsonObject);
            item.setDurability(itemJsonObject.get("durability").getAsInt());
            inventory.addItem(item);
        });
        clonedplayer.setInventory(inventory);

        PotionBag potionBag = new PotionBag();
        this.potionBag.toJsonArray().forEach(potionJson -> {
            JsonObject potionJsonObject = potionJson.getAsJsonObject();
            Potion potion = (Potion) EntityFactory.createEntity(potionJsonObject);
            JsonElement currentTicks = potionJsonObject.get("current_ticks");
            if (currentTicks != null)potion.setCurrentTicks(currentTicks.getAsInt());
            potionBag.usePotion(potion);
        });
        clonedplayer.setPotionBag(potionBag);
        return clonedplayer;
    }

    @Override
    public void tick(Direction direction) {
        super.tick(direction);
        getMovementStrategy().move(direction);
        if (direction.getOffset().equals(Direction.DOWN.getOffset())) actions.add("move-".concat("down"));
        if (direction.getOffset().equals(Direction.UP.getOffset())) actions.add("move-".concat("up"));
        if (direction.getOffset().equals(Direction.LEFT.getOffset())) actions.add("move-".concat("left"));
        if (direction.getOffset().equals(Direction.RIGHT.getOffset())) actions.add("move-".concat("right"));
        
    }

    public void setActionsToLastNTicks(int ticks) {
        ArrayList<String> lastNTicksActions = new ArrayList<String>();
        Collections.reverse(actions);
        int numTicks = 0;
        for (String action : actions) {
            if (action.split("-")[0].equals("move") || action.split("-")[0].equals("item")) {
                numTicks += 1;
            }
            lastNTicksActions.add(action);
            if (numTicks >= ticks) break;
        }
        Collections.reverse(lastNTicksActions);
        setActions(lastNTicksActions);
    }

    @Override
    public void tick() {
        super.tick();
        potionBag.tick();
    }

    public void evilTick() {
        boolean tickOccurred = false;
        while (!tickOccurred && actions.size() != 0) {
            String action = actions.get(0).split("-")[0];
            String actionEffect = actions.get(0).split("-")[1];
            if (action.equals("build")) {
                try {
                    inventory.buildEntity(actionEffect);
                } catch (InvalidActionException err) {
                    return;
                }
            }
            if (action.equals("interact")) {
                try {
                    Entity entity = Dungeon.getEntityFromId(actionEffect);
                    if (entity != null) entity.interact(true);
                    else throw new IllegalArgumentException("No matching ID");
                } catch (InvalidActionException err) {
                    return;
                }
            }
            if (action.equals("move")) {
                if (actionEffect.equals("down")) getMovementStrategy().move(Direction.DOWN);
                if (actionEffect.equals("up")) getMovementStrategy().move(Direction.UP);
                if (actionEffect.equals("left")) getMovementStrategy().move(Direction.LEFT);
                if (actionEffect.equals("right")) getMovementStrategy().move(Direction.RIGHT);
                tickOccurred = true;
            }
            if (action.equals("item")) {
                try {
                    useItem(actionEffect);
                } catch (InvalidActionException err) {
                    return;
                }
                tickOccurred = true;
            }
            actions.remove(0);
        }
        if (actions.size() == 0) {
            Dungeon.removeEntity(this);
        }
    }

    @Override
    public void tick(String itemId) throws InvalidActionException, IllegalArgumentException {
        super.tick(itemId);
        useItem(itemId);
        actions.add("item-".concat(itemId));
    }

    public void useItem(String itemId) throws InvalidActionException, IllegalArgumentException {
        Entity entityToUse = Dungeon.getPlayer().getInventory().getItemFromId(itemId);
        if (!inventory.containsCollectableById(itemId)) throw new InvalidActionException("Item does not exist in player's inventory");
        if (entityToUse instanceof Usable) {
            ((Usable) entityToUse).use();
        } else throw new IllegalArgumentException(itemId.concat(" is not Usable"));
    }
    
    public Inventory getInventory() {
        return inventory;
    }

    public List<CollectableEntity> getInventory(String type) {
        return inventory.getItemsOfType(type);
    }

    public void useKey(Key key) {
        inventory.removeItem(key);
    }

    public void useSwordToBreakZombieToastSpawner() {
        if (hasCollectable("sword")) inventory.removeItem(inventory.getFirstItemsOfType("sword"));
    }

    public void usePotion(Potion potion) {
        inventory.removeItem(potion);
        potionBag.usePotion(potion);
    }

    public PotionBag getPotionBag() {
        return potionBag;
    }

    public ArrayList<String> getBuildables() {
        return inventory.getCraftableItems();
    }

    @Override
    public double getAttack() {
        double attack = super.getAttack();
        if (inventory.containsCollectable("sword")) attack += Dungeon.getConfigValue("sword_attack");
        if (Dungeon.getEntitiesOfType("mercenary").stream().anyMatch(merc -> ((Mercenary) merc).isAlly())) attack += Dungeon.getConfigValue("ally_attack");
        if (inventory.containsCollectable("bow")) attack *= 2;
        if (inventory.containsCollectable("midnight_armour")) attack += Dungeon.getConfigValue("midnight_armour_attack");
        return attack;
    }

    public double getDefence() {
        double defence = 0;
        if (inventory.containsCollectable("shield")) defence += Dungeon.getConfigValue("shield_defence");
        if (Dungeon.getEntitiesOfType("mercenary").stream().anyMatch(merc -> ((Mercenary) merc).isAlly())) defence += Dungeon.getConfigValue("ally_defence");
        if (inventory.containsCollectable("midnight_armour")) defence += Dungeon.getConfigValue("midnight_armour_defence");
        return defence;
    }

    public List<CollectableEntity> getWeaponryUsed() {
        List<CollectableEntity> weaponryUsed = inventory.getItemsOfType("sword");
        weaponryUsed.addAll(inventory.getItemsOfType("bow"));
        weaponryUsed.addAll(inventory.getItemsOfType("shield"));
        if (activePotionEffect().equals("invincibility_potion")) weaponryUsed.add(potionBag.getActivePotion());
        return weaponryUsed;
    }

    public void pickup(CollectableEntity item) {
        inventory.addItem(item);
        item.setPickedUp(true);
        Dungeon.removeEntity(item);
    }

    @Override
    public void build(String type) throws InvalidActionException, IllegalArgumentException {
        inventory.buildEntity(type);
        actions.add("build-".concat(type));
    }

    public boolean hasCollectable(String type) {
        return inventory.containsCollectable(type);
    }

    public String activePotionEffect() {
        return potionBag.getActivePotionType();
    }

    public void onDurabilityRunsOut(CollectableEntity entity) {
        inventory.removeItem(entity);
    }

    public int getNumberOfTreasures() {
        return inventory.getItemsOfType("treasure").size();
    }
    
    public int getNumberOfSunStones() {
        return inventory.getItemsOfType("sun_stone").size();
    }

    public void bribeMercenary() {
        IntStream.range(0, Dungeon.getConfigValue("bribe_amount")).forEach(i -> inventory.removeItem(inventory.getFirstItemsOfType("treasure")));
    }

    @Override
    public JsonObject toJsonObject() {
        JsonObject playerJson = super.toJsonObject();
        JsonArray actions = new JsonArray();
        playerJson.addProperty("lastPositionX", lastPosition.getX());
        playerJson.addProperty("lastPositionY", lastPosition.getY());
        playerJson.addProperty("isEvil", isEvil);
        this.actions.forEach(action -> actions.add(action));
        playerJson.add("actions", actions);
        return playerJson;
    }

    public JsonObject toTimeTravelJsonObject() {
        JsonObject playerJson = toJsonObject();
        playerJson.addProperty("isEvil", true);
        return playerJson;
    }

    public boolean isEvil() {
        return isEvil;
    }

    public void setEvil(boolean isEvil) {
        this.isEvil = isEvil;
    }
    
    public void mindControlMercenary() {
        inventory.removeItem(inventory.getFirstItemsOfType("sceptre"));
    }
}
