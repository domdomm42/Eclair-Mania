package dungeonmania.Entities.MovingEntities.PlayerBelongings;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dungeonmania.Entities.StaticEntities.CollectableEntities.CollectableEntity;

public class Inventory {
    private ArrayList<CollectableEntity> items;

    public Inventory() {
        items = new ArrayList<CollectableEntity>();
    }

    public List<CollectableEntity> getItemsOfType(String type) {
        return items.stream().filter(item -> item.getType() == type).collect(Collectors.toList());
    }

    public ArrayList<String> getCraftableItems() {
        ArrayList<String> craftableItems = new ArrayList<String>();
        
        return craftableItems;
    }
}
