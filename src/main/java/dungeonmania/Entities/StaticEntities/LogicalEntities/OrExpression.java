package dungeonmania.Entities.StaticEntities.LogicalEntities;

public class OrExpression extends CompoundNode {

    public OrExpression(BooleanNode left, BooleanNode right) {
        super(left, right);
    }


    // fix
    @Override
    public boolean evaluate() {

        return getLeft().evaluate() || getRight().evaluate();
    }

    
    
}
