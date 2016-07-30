/*
 * 
 */
package edu.jsykora.sql2stream.expression;

import com.foundationdb.sql.parser.BooleanConstantNode;

// TODO: Auto-generated Javadoc

/**
 * The Class BooleanExpression.
 */
final class BooleanExpression extends ValueExpression<Boolean> {

    /**
     * The o.
     */
    private Boolean o;

    /**
     * Instantiates a new boolean expression.
     *
     * @param node the node
     */
    protected BooleanExpression(BooleanConstantNode node) {
        this.o = node.getBooleanValue();
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.jsykora.sql2stream.expressions.Expression#returnExpression()
     */
    @Override
    public Boolean returnExpression() {
        return o;
    }

}
