package edu.jsykora.sql2stream;

import com.foundationdb.sql.parser.BooleanConstantNode;
import com.foundationdb.sql.parser.CharConstantNode;
import com.foundationdb.sql.parser.ColumnReference;
import com.foundationdb.sql.parser.NumericConstantNode;
import com.foundationdb.sql.parser.QueryTreeNode;

// TODO: Auto-generated Javadoc

abstract class BaseExpression<O extends Comparable<O>> implements Expression<O> {

    protected O o;

    protected static <O extends Comparable<O>> BaseExpression<O> createBaseExpression(BaseElement<?> e, QueryTreeNode node) throws Exception {
	if (node instanceof ColumnReference) {
	    return new ReferenceExpression<>(e, (ColumnReference) node);
	}

	if (node instanceof CharConstantNode || node instanceof BooleanConstantNode || node instanceof NumericConstantNode) {
	    return ValueExpression.createValueExpression(node);
	}
	return null;
    }

}
