
package dungeonmania.Entities.StaticEntities.CollectableEntities;

import dungeonmania.util.Position;

public class SunStone extends CollectableEntity {
    public SunStone(Position position, String id) {
        super(false, 10000, position, id, "sun_stone");
    }
}