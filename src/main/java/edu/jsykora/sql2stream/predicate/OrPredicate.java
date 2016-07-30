package edu.jsykora.sql2stream.predicate;

import com.foundationdb.sql.parser.OrNode;

import edu.jsykora.sql2stream.element.BaseElement;

// TODO: Auto-generated Javadoc

final class OrPredicate<T> extends TwoOperationsPredicate<T> {

    protected OrPredicate(OrNode node) {
        super();
        this.leftOperand = BasePredicate.build(node.getLeftOperand());
        this.rightOperand = BasePredicate.build(node.getRightOperand());
        this.children.add(leftOperand);
        this.children.add(rightOperand);
    }

    @Override
    public boolean test(BaseElement<T> t) {
        return !(children != null && !children.isEmpty()) || children.stream().parallel().map(p -> p.test(t)).anyMatch(b -> b);
    }

}
