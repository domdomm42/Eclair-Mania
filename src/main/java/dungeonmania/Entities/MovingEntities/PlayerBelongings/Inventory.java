package dungeonmania.Entities.MovingEntities.PlayerBelongings;

import java.util.ArrayList;

import dungeonmania.Entities.StaticEntities.CollectableEntities.CollectableEntity;

public class Inventory {
    ArrayList<CollectableEntity> items;
    ArrayList<String> craftableItems;

    public Inventory() {
        items = new ArrayList<CollectableEntity>();
        craftableItems = new ArrayList<String>();
    }
}
