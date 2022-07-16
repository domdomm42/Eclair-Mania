package dungeonmania.Entities.StaticEntities.CollectableEntities;

import dungeonmania.util.Position;

public class Arrow extends CollectableEntity {
    public Arrow(String id, Position position) {   
        super(false, 1, position, id, "arrow");
    }
}