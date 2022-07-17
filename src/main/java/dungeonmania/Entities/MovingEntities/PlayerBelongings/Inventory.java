package dungeonmania.Entities.MovingEntities.PlayerBelongings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import dungeonmania.EntityFactory;
import dungeonmania.Entities.StaticEntities.CollectableEntities.CollectableEntity;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.ItemResponse;

public class Inventory {
    private ArrayList<CollectableEntity> items;

    public Inventory() {
        items = new ArrayList<CollectableEntity>();
    }

    public boolean containsCollectable(String type) {
        return items.stream().anyMatch(item -> item.getType().equals(type));
    }

    public boolean containsCollectableById(String id) {
        return items.stream().anyMatch(item -> item.getId().equals(id));
    }

    public void addItem(CollectableEntity item) {
        items.add(item);
    }

    public void removeItem(CollectableEntity item) {
        items.remove(item);
    }

    private void removeSomeItemsOfType(String type, int number) {
        for (int i = 0; i < number; i++) {
            removeItem(getFirstItemsOfType(type));
        }
    }

    public List<CollectableEntity> getItemsOfType(String type) {
        return items.stream().filter(item -> item.getType().equals(type)).collect(Collectors.toList());
    }

    public CollectableEntity getFirstItemsOfType(String type) {
        return items.stream().filter(item -> item.getType().equals(type)).findFirst().map(item -> {
            return item;
        }).orElse(null);
    }

    public ArrayList<String> getCraftableItems() {
        ArrayList<String> craftableItems = new ArrayList<String>();
        if (canBuildBow()) craftableItems.add("bow");
        if (canBuildShield()) craftableItems.add("shield");
        return craftableItems;
    }

    public boolean canBuildBow() {
        return getItemsOfType("wood").size() >= 1 && getItemsOfType("arrow").size() >= 3;
    }

    public boolean canBuildShield() {
        return getItemsOfType("wood").size() >= 2 && (getItemsOfType("key").size() >= 1 || getItemsOfType("treasure").size() >= 1);
    }

    public void removeCraftingMaterials(Map<String, Integer> craftingMaterials) {
        craftingMaterials.entrySet().forEach(entry -> removeSomeItemsOfType(entry.getKey(), entry.getValue()));
    }

    public void buildEntity(String type) throws InvalidActionException, IllegalArgumentException {
        Map<String, Integer> craftingMaterials = new HashMap<String, Integer>();
        if (!canBuildEntity(type)) throw new InvalidActionException("not enough materials to build ".concat(type));
        if (type.equals("bow")) {
            craftingMaterials.put("wood", 1);
            craftingMaterials.put("arrow", 3);
        }
        if (type.equals("shield")) {
            craftingMaterials.put("wood", 2);
            if (getItemsOfType("treasure").size() >= 1) craftingMaterials.put("treasure", 1);
            else craftingMaterials.put("key", 1);
        }
        removeCraftingMaterials(craftingMaterials);
        Map<String, String> newEntityArgs = new HashMap<String, String>();
        addItem((CollectableEntity) EntityFactory.createEntity(type, newEntityArgs));
    }

    public boolean canBuildEntity(String type) throws InvalidActionException, IllegalArgumentException {
        if (type.equals("bow")) return canBuildBow();
        if (type.equals("shield")) return canBuildShield();
        throw new IllegalArgumentException(type.concat(" cannot be built"));
    }

    public List<ItemResponse> toItemResponse() {
        return items.stream().map(item -> new ItemResponse(item.getId(), item.getType())).collect(Collectors.toList());
    }

    public CollectableEntity getItemFromId(String id) {
        return items.stream().filter(ent -> ent.getId().equals(id)).findFirst().map(entity -> {
            return entity;
        }).orElse(null);
    }
}
