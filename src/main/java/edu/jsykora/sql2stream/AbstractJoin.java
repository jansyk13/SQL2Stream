package edu.jsykora.sql2stream;

import java.util.ArrayList;
import java.util.List;

import com.foundationdb.sql.parser.FromBaseTable;
import com.foundationdb.sql.parser.JoinNode;
import com.foundationdb.sql.parser.QueryTreeNode;

// TODO: Auto-generated Javadoc

abstract class AbstractJoin<E> implements Join<E> {

    private SourceElement<?> source;

    protected AbstractJoin(SourceElement<?> source) {
	this.setSource(source);
    }

    @SuppressWarnings("unchecked")
    protected static <E> AbstractJoin<E> getJoin(QueryTreeNode node, SourceElement<?> source) {
	if (node instanceof FromBaseTable) {
	    return new SourceJoin(source, node);
	}
	if (node instanceof JoinNode) {
	    return new TrueJoin<>(source, node);
	}

	return new AbstractJoin<E>(source) {
	};
    }

    public SourceElement<?> getSource() {
	return source;
    }

    public void setSource(SourceElement<?> source) {
	this.source = source;
    }

    @Override
    public List<BaseElement<E>> interpret() {
	return new ArrayList<>();
    }
}
