package dungeonmania.Entities.StaticEntities;

import dungeonmania.Dungeon;
import dungeonmania.Entities.StaticEntities.CollectableEntities.Key;
import dungeonmania.util.Position;

public class Door extends StaticEntity {
    private boolean isUnlocked;


    public Door(Position position, String id) {
        super(position, id, "door", false);
        this.isUnlocked = false;
    }


    public boolean isUnlocked() {
        return isUnlocked;
    }


    public void setUnlocked(boolean isUnlocked) {
        this.isUnlocked = isUnlocked;
    }


    public Key getKeyThatUnlock() {
        return (Key) Dungeon.getEntityFromId("key-".concat(getId()));
    }




    
    
}
