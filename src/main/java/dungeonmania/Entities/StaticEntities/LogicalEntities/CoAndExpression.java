package dungeonmania.Entities.StaticEntities.LogicalEntities;

public class CoAndExpression extends CompoundNode {

    public CoAndExpression(BooleanNode left, BooleanNode right) {
        super(left, right);
    }


    // fix
    @Override
    public boolean evaluate() {

        return getLeft().evaluate() || getRight().evaluate();
    }


    
    
}
