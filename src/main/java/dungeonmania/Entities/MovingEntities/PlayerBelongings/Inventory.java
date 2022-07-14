package dungeonmania.Entities.MovingEntities.PlayerBelongings;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dungeonmania.Entities.StaticEntities.CollectableEntities.CollectableEntity;

public class Inventory {
    ArrayList<CollectableEntity> items;
    ArrayList<String> craftableItems;

    public Inventory() {
        items = new ArrayList<CollectableEntity>();
        craftableItems = new ArrayList<String>();
    }

    public List<CollectableEntity> getItemsOfType(String type) {
        return items.stream().filter(item -> item.getType() == type).collect(Collectors.toList());
    }
}
