package main.java.dungeonmania.util.EntityPositions.StaticEntity;

public class ZombieToastSpawner extends EntityPosition{

    //Spawns zombie toasts in an open square cardinally adjacent to the spawner. The Player can destroy
    // a zombie spawner if they have a weapon and are cardinally adjacent to the spawner.

    public ZombieToastSpawner(String id, String type, boolean isInteractable, Position position) {
        super(id, "zombie_toast_spawner", true, position);
    }
    
}
