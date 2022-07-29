package dungeonmania.Entities.StaticEntities.LogicalEntities;

import java.util.List;

import dungeonmania.Dungeon;
import dungeonmania.Entities.StaticEntities.FloorSwitch;
import dungeonmania.Entities.StaticEntities.StaticEntity;
import dungeonmania.util.Position;

public class LightBulbOn extends StaticEntity{
    private boolean LightBulbOn;

    public LightBulbOn(Position position, String id) {
        super(position, id, "light_bulb_on", false);
        this.LightBulbOn = false;
    }

    public boolean isLightBulbOn() {
        return LightBulbOn;
    }

    public void setLightBulbOn(boolean LightBulbOn) {
        this.LightBulbOn = LightBulbOn;
    }

    public void TurnOnLight() {
        List<Position> adjacentPositions = getPosition().getAdjacentPositions();

        // for the surronding areas of lightbulb
        for (Position pos: adjacentPositions) {

            // if nearby wire is triggered.
            if (Dungeon.getFirstEntityOfTypeOnPosition(pos, "wire") != null) {
                Wire SurroundingWire = (Wire) Dungeon.getFirstEntityOfTypeOnPosition(getPosition(), "wire");
                if (SurroundingWire.isActivated() == true) {
                    setLightBulbOn(true);
                }
            }

            // if nearby floorswitch is triggered
            if (Dungeon.getFirstEntityOfTypeOnPosition(pos, "FloorSwitch") != null) {
                FloorSwitch SurroundingSwitch = (FloorSwitch) Dungeon.getFirstEntityOfTypeOnPosition(getPosition(), "FloorSwitch");
                if (SurroundingSwitch.isTriggered() == true) {
                    setLightBulbOn(true);
                }
            }


            
        }


    }

    
    
}
