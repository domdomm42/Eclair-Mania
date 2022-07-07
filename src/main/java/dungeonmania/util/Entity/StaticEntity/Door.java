
public class Door extends EntityPosition{
        //Exists in conjunction with a single key that can open it. If the Player holds the key, 
        //they can open the door by moving through it. Once open, it remains open.

        public Door(String id, String type, boolean isInteractable, Position position) {
            super(id, "door", position, true);
        }
        
}
