package edu.jsykora.sql2stream;

import com.foundationdb.sql.parser.NotNode;

// TODO: Auto-generated Javadoc

final class NotPredicate<T> extends BasePredicate<T> {

    private BasePredicate<T> child;

    protected NotPredicate(NotNode node) {
	this.child = BasePredicate.build(node.getOperand());
    }

    @Override
    public boolean test(BaseElement<T> t) {
	return !child.test(t);
    }

}
