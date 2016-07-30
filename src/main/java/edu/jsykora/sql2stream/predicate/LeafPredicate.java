package edu.jsykora.sql2stream.predicate;

import com.foundationdb.sql.parser.BinaryRelationalOperatorNode;

import edu.jsykora.sql2stream.element.BaseElement;
import edu.jsykora.sql2stream.element.Element;
import edu.jsykora.sql2stream.expression.BaseExpression;
import edu.jsykora.sql2stream.expression.Expression;
import edu.jsykora.sql2stream.expression.ValueExpression;

// TODO: Auto-generated Javadoc

final class LeafPredicate<T, O extends Comparable<O>> extends BasePredicate<T> {

    private BinaryRelationalOperatorNode node;

    private O o;

    private boolean failFlag;

    private String operator;

    private String leftAlias;

    protected LeafPredicate(BinaryRelationalOperatorNode node) {
        this.node = node;
        this.failFlag = false;
        this.operator = this.node.getOperator();
        this.leftAlias = this.node.getLeftOperand().getTableName();
        Expression<O> right = ValueExpression.createValueExpression(node.getRightOperand());
        this.o = right.returnExpression();
    }

    @Override
    public boolean test(BaseElement<T> t) {
        if (failFlag) {
            return false;
        }
        Element<?> leftTarget = t.visit(leftAlias);

        Expression<O> left;
        try {
            left = BaseExpression.createBaseExpression((BaseElement<?>) leftTarget, node.getLeftOperand());
        } catch (Exception e1) {
            return false;
        }

        O current = left.returnExpression();
        int result = o.compareTo(current);
        return Expression.getResult(operator, result);
    }

}
