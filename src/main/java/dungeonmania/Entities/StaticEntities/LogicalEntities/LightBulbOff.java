package dungeonmania.Entities.StaticEntities.LogicalEntities;

import dungeonmania.Entities.StaticEntities.StaticEntity;
import dungeonmania.util.Position;

public class LightBulbOff extends StaticEntity{
    private boolean LightBulbOff;
    private String LogicalType;

    public LightBulbOff(Position position, String id, String LogicalType) {
        super(position, id, "light_bulb_off", false);
        this.LightBulbOff = true;
        this.LogicalType = LogicalType;
    }

    public boolean isLightBulbOff() {
        return LightBulbOff;
    }

    public void setLightBulbOff(boolean LightBulbOff) {
        this.LightBulbOff = LightBulbOff;
    }

    public String getLogicalType() {
        return LogicalType;
    }

    public void setLogicalType(String logicalType) {
        LogicalType = logicalType;
    }

    
    
}
