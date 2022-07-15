package dungeonmania;

import java.util.Map;

import dungeonmania.Entities.Entity;
import dungeonmania.Entities.MovingEntities.Player;
import dungeonmania.util.Position;

public class EntityFactory {
    static public Entity createEntity(String type, Map<String, String> args) throws IllegalArgumentException {
        Position position = new Position(Integer.parseInt(args.get("x")), Integer.parseInt(args.get("x")));
        String id = args.get("id");
        switch (type) {
            case "player":
                return new Player(id, position, false);
                

        }
        throw new IllegalArgumentException("Entity type does not exist");
    }
}
