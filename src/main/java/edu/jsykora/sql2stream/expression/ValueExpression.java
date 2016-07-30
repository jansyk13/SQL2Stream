package edu.jsykora.sql2stream.expression;

import com.foundationdb.sql.parser.BooleanConstantNode;
import com.foundationdb.sql.parser.CharConstantNode;
import com.foundationdb.sql.parser.NumericConstantNode;
import com.foundationdb.sql.parser.QueryTreeNode;

// TODO: Auto-generated Javadoc

public abstract class ValueExpression<O extends Comparable<O>> extends BaseExpression<O> {

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <O extends Comparable<O>> BaseExpression<O> createValueExpression(QueryTreeNode node) {
        if (node instanceof CharConstantNode) {
            return (BaseExpression<O>) new CharExpression((CharConstantNode) node);
        }
        if (node instanceof NumericConstantNode) {
            return new NumberExpression((NumericConstantNode) node);
        }

        if (node instanceof BooleanConstantNode) {
            return (BaseExpression<O>) new BooleanExpression((BooleanConstantNode) node);
        }

        return null;
    }

}
