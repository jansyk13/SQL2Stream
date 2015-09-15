package edu.jsykora.sql2stream;

import com.foundationdb.sql.parser.JoinNode;
import com.foundationdb.sql.parser.QueryTreeNode;

import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc

final class TrueJoin<E, N> extends AbstractJoin<E> {

	private AbstractJoin<E> left;

    private AbstractJoin<N> right;

    private JoinPredicate<E, N, ? extends Comparable<?>> predicate;

    private ElementAppender<E, N> appender;

    protected TrueJoin(SourceElement<?> source, QueryTreeNode node) {
	super(source);
		JoinNode node1 = (JoinNode) node;
	this.left = AbstractJoin.getJoin(node1.getLeftResultSet(), source);
	this.right = AbstractJoin.getJoin(node1.getRightResultSet(), source);
	this.predicate = new JoinPredicate<>(node1.getJoinClause());
	this.appender = new ElementAppender<>();
    }

    @Override
    public List<BaseElement<E>> interpret() {
	List<BaseElement<E>> result = new ArrayList<>();

	List<BaseElement<E>> leftList = left.interpret();
	List<BaseElement<N>> rightList = right.interpret();

	leftList.forEach(upper -> rightList.forEach(lower -> {
    if (predicate.test(upper, lower)) {
        result.add((BaseElement<E>) appender.apply(upper, lower));
    }
    }));
	return result;
    }
}
