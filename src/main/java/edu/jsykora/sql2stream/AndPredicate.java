package edu.jsykora.sql2stream;

import com.foundationdb.sql.parser.AndNode;

// TODO: Auto-generated Javadoc

final class AndPredicate<T> extends TwoOperationsPredicate<T> {

    protected AndPredicate(AndNode node) {
	super();
	this.leftOperand = BasePredicate.build(node.getLeftOperand());
	this.rightOperand = BasePredicate.build(node.getRightOperand());
	this.children.add(leftOperand);
	this.children.add(rightOperand);
    }

    @Override
    public boolean test(BaseElement<T> t) {
		return !(children != null && !children.isEmpty()) || children.stream().parallel().map(p -> p.test(t)).allMatch(b -> b);
	}

}
