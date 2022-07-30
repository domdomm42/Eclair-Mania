package dungeonmania.Entities.StaticEntities;

import java.io.IOException;

import dungeonmania.Dungeon;
import dungeonmania.util.Position;

public class TimeTravellingPortal extends StaticEntity {
    private final int TIME_TRAVEL_TICKS = 30;

    public TimeTravellingPortal(Position position, String id) {
        super(position, id, "time_travelling_portal", false);
    }

    public void teleport() throws IOException {
        Dungeon.timeTravel(TIME_TRAVEL_TICKS);
    }
}
