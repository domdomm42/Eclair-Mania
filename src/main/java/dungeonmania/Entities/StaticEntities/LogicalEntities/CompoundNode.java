package dungeonmania.Entities.StaticEntities.LogicalEntities;

public abstract class CompoundNode implements BooleanNode {

    private BooleanNode left;
    private BooleanNode right;
    
    public CompoundNode(BooleanNode left, BooleanNode right) {
        this.left = left;
        this.right = right;
    }

    public BooleanNode getLeft() {
        return left;
    }

    public BooleanNode getRight() {
        return right;
    }

    
}
