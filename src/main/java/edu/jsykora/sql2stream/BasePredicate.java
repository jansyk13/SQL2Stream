package edu.jsykora.sql2stream;

import java.util.function.Predicate;

import com.foundationdb.sql.parser.AndNode;
import com.foundationdb.sql.parser.BinaryRelationalOperatorNode;
import com.foundationdb.sql.parser.NotNode;
import com.foundationdb.sql.parser.OrNode;
import com.foundationdb.sql.parser.QueryTreeNode;

abstract class BasePredicate<T> implements Predicate<BaseElement<T>> {

    protected BasePredicate() {
    }

    protected static <T> BasePredicate<T> build(QueryTreeNode node) {
	if (node instanceof AndNode) {
	    return new AndPredicate<>((AndNode) node);
	}
	if (node instanceof OrNode) {
	    return new OrPredicate<>((OrNode) node);
	}
	if (node instanceof NotNode) {
	    return new NotPredicate<>((NotNode) node);
	}
	if (node instanceof BinaryRelationalOperatorNode) {
	    return new LeafPredicate<>((BinaryRelationalOperatorNode) node);
	}
	return new BasePredicate<T>() {
	    @Override
	    public boolean test(BaseElement<T> t) {
		return true;
	    }
	};
    }

}
