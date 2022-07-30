package dungeonmania.Entities.StaticEntities.LogicalEntities;

import dungeonmania.Entities.StaticEntities.StaticEntity;
import dungeonmania.util.Position;

public class LightBulbOff extends LogicalEntity {
    private boolean LightBulbOff;

    public LightBulbOff(Position position, String id, String LogicalType) {
        super(position, id, "light_bulb_off", false, LogicalType);
        this.LightBulbOff = true;
    }

    public boolean isLightBulbOff() {
        return LightBulbOff;
    }

    public void setLightBulbOff(boolean LightBulbOff) {
        this.LightBulbOff = LightBulbOff;
    }

    public void TurnOnLightIfPossible() {
        if (LogicType == "and") {
            if (AndIsActivated()) {
                this.setType("light_bulb_on");
            }
        }

        else if (LogicType == "or") {
            if (OrIsActivated()) {
                this.setType("light_bulb_on");
            }
        }

        else if (LogicType == "xor") {
            if (XORIsActivated()) {
                this.setType("light_bulb_on");
            }
        }


    }


    
    
}
