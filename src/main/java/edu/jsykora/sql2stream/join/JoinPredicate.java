package edu.jsykora.sql2stream.join;

import java.util.function.BiPredicate;

import com.foundationdb.sql.parser.BinaryRelationalOperatorNode;
import com.foundationdb.sql.parser.ValueNode;

import edu.jsykora.sql2stream.element.BaseElement;
import edu.jsykora.sql2stream.element.Element;
import edu.jsykora.sql2stream.expression.BaseExpression;
import edu.jsykora.sql2stream.expression.Expression;

// TODO: Auto-generated Javadoc

final class JoinPredicate<E, N, O extends Comparable<O>> implements BiPredicate<BaseElement<E>, BaseElement<N>> {

    private String operator;

    private BinaryRelationalOperatorNode node;

    private String leftAlias;

    private String rightAlias;

    protected JoinPredicate(ValueNode node) {
        this.node = (BinaryRelationalOperatorNode) node;
        this.operator = this.node.getOperator();
        this.leftAlias = this.node.getLeftOperand().getTableName();
        this.rightAlias = this.node.getRightOperand().getTableName();
    }

    @Override
    public boolean test(BaseElement<E> t, BaseElement<N> u) {
        Element<?> leftTarget = t.visit(leftAlias);
        if (leftTarget == null) {
            leftTarget = u.visit(leftAlias);
        }
        Expression<O> left;
        try {
            left = BaseExpression.createBaseExpression((BaseElement<?>) leftTarget, node.getLeftOperand());
        } catch (Exception e1) {
            return false;
        }

        Element<?> rightTarget = t.visit(rightAlias);
        if (rightTarget == null) {
            rightTarget = u.visit(rightAlias);
        }
        Expression<O> right;
        try {
            right = BaseExpression.createBaseExpression((BaseElement<?>) rightTarget, node.getRightOperand());
        } catch (Exception e1) {
            return false;
        }
        O o1 = left.returnExpression();
        O o2 = right.returnExpression();
        int result = o1.compareTo(o2);
        return Expression.getResult(operator, result);
    }
}
