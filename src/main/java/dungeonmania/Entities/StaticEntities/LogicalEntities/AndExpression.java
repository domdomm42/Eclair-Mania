package dungeonmania.Entities.StaticEntities.LogicalEntities;

public class AndExpression extends CompoundNode {

    public AndExpression(BooleanNode left, BooleanNode right) {
        super(left, right);
    }


    // fix 
    @Override
    public boolean evaluate() {

        return getLeft().evaluate() && getRight().evaluate();
    }


    
    
}
