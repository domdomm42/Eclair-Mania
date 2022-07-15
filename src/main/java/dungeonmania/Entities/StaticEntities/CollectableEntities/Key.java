package dungeonmania.Entities.StaticEntities.CollectableEntities;

public class Key extends Usable {

    @Override
    public void use() {
        // TODO Auto-generated method stub
        
    }

    public Key(Position position, String id) {
        super(false, 1, position, id, "sword");
    } // fix durability to get from config file
    
}
