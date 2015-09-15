package edu.jsykora.sql2stream;

import com.foundationdb.sql.StandardException;
import com.foundationdb.sql.parser.QueryTreeNode;
import com.foundationdb.sql.parser.Visitable;
import com.foundationdb.sql.parser.Visitor;

// TODO: Auto-generated Javadoc

class DynamicVisitor implements Visitor {

    protected QueryTreeNode node;

    @Override
    public Visitable visit(Visitable node) throws StandardException {
	if (this.node == null) {
	    this.node = (QueryTreeNode) node;
	}
	return node;
    }

    @Override
    public boolean visitChildrenFirst(Visitable node) {
	return false;
    }

    @Override
    public boolean stopTraversal() {
	return false;
    }

    @Override
    public boolean skipChildren(Visitable node) throws StandardException {
	return false;
    }

}
