package dungeonmania.Entities.MovingEntities;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import dungeonmania.Dungeon;
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
    Inventory inventory;
    PotionBag potionBag;
    Position lastPosition;

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

    public Player(String id, Position position) {
        super(id, "player", position, Dungeon.getConfigValue("player_health"), false, new PlayerMovementStrategy(), Dungeon.getConfigValue("player_attack"));
        inventory = new Inventory();
        potionBag = new PotionBag();
        getMovementStrategy().setEntity(this);
    };

    @Override
    public void tick(Direction direction) {
        super.tick(direction);
        getMovementStrategy().move(direction);
    }

    @Override
    public void tick() {
        super.tick();
        potionBag.tick();
    }

    @Override
    public void tick(String itemId) throws InvalidActionException, IllegalArgumentException {
        super.tick(itemId);
        useItem(itemId);
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
        if (inventory.containsCollectable("sword")) defence += Dungeon.getConfigValue("shield_defence");
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

    public void mindControlMercenary() {
        inventory.removeItem(inventory.getFirstItemsOfType("sceptre"));
    }
}
