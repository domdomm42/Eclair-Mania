package dungeonmania.Entities.StaticEntities.CollectableEntities;

import dungeonmania.util.Position;

public class Key extends Usable {

    public Key(Position position, String id) {
        super(false, 1, position, id, "key");
    }

    @Override
    public void use() {
        // TODO Auto-generated method stub
    }
}
