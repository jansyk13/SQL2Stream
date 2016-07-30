package edu.jsykora.sql2stream.expression;

import com.foundationdb.sql.parser.BooleanConstantNode;
import com.foundationdb.sql.parser.CharConstantNode;
import com.foundationdb.sql.parser.ColumnReference;
import com.foundationdb.sql.parser.NumericConstantNode;
import com.foundationdb.sql.parser.QueryTreeNode;

import edu.jsykora.sql2stream.element.BaseElement;

// TODO: Auto-generated Javadoc

public abstract class BaseExpression<O extends Comparable<O>> implements Expression<O> {

    protected O o;

    public static <O extends Comparable<O>> BaseExpression<O> createBaseExpression(BaseElement<?> e, QueryTreeNode node) throws Exception {
        if (node instanceof ColumnReference) {
            return new ReferenceExpression<>(e, (ColumnReference) node);
        }

        if (node instanceof CharConstantNode || node instanceof BooleanConstantNode || node instanceof NumericConstantNode) {
            return ValueExpression.createValueExpression(node);
        }
        return null;
    }

}
