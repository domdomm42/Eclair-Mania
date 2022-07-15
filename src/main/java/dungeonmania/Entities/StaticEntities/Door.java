package dungeonmania.Entities.StaticEntities;

import dungeonmania.Entities.StaticEntities.CollectableEntities.Key;
import dungeonmania.util.Position;

public class Door extends StaticEntity {
    private boolean isUnlocked;
    private Key keyThatUnlock;


    public Door(Position position, String id, String type, boolean Isinteractable, Key keyThatUnlock, boolean isUnlocked) {
        super(position, id, type, Isinteractable);

        this.keyThatUnlock = keyThatUnlock;
        this.isUnlocked = isUnlocked;
    }


    public boolean isUnlocked() {
        return isUnlocked;
    }


    public void setUnlocked(boolean isUnlocked) {
        this.isUnlocked = isUnlocked;
    }


    public Key getKeyThatUnlock() {
        return keyThatUnlock;
    }


    public void setKeyThatUnlock(Key keyThatUnlock) {
        this.keyThatUnlock = keyThatUnlock;
    }


    
    
}
