package dungeonmania.Entities.StaticEntities.LogicalEntities;

public class XorExpression extends CompoundNode {

    public XorExpression(BooleanNode left, BooleanNode right) {
        super(left, right);
    }


    // fix
    @Override
    public boolean evaluate() {

        return getLeft().evaluate() || getRight().evaluate();
    }

    
    
}

