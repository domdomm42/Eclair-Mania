package dungeonmania.Entities.StaticEntities.LogicalEntities;

import dungeonmania.Entities.StaticEntities.StaticEntity;
import dungeonmania.util.Position;

public class LightBulbOff extends StaticEntity{
    private boolean LightBulbOff;

    public LightBulbOff(Position position, String id) {
        super(position, id, "light_bulb_off", false);
        this.LightBulbOff = true;
    }

    public boolean isLightBulbOff() {
        return LightBulbOff;
    }

    public void setLightBulbOff(boolean LightBulbOff) {
        this.LightBulbOff = LightBulbOff;
    }

    
    
}
