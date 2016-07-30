package edu.jsykora.sql2stream.expression;

import com.foundationdb.sql.StandardException;
import com.foundationdb.sql.parser.CharConstantNode;

// TODO: Auto-generated Javadoc

final class CharExpression extends ValueExpression<String> {

    private String o;

    protected CharExpression(CharConstantNode node) {
        try {
            this.o = node.getString();
        } catch (StandardException e) {
            this.o = "";
        }
    }

    @Override
    public String returnExpression() {
        return o;
    }

}
