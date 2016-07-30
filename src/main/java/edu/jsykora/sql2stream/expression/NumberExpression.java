package edu.jsykora.sql2stream.expression;

import com.foundationdb.sql.parser.NumericConstantNode;

// TODO: Auto-generated Javadoc

final class NumberExpression<O extends Number & Comparable<O>> extends ValueExpression<O> {

    private O o;

    @SuppressWarnings("unchecked")
    protected NumberExpression(NumericConstantNode node) {
        this.o = (O) node.getValue();
    }

    @Override
    public O returnExpression() {
        return o;
    }

}
