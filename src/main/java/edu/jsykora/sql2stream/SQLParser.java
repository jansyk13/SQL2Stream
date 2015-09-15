package edu.jsykora.sql2stream;

import com.foundationdb.sql.StandardException;
import com.foundationdb.sql.parser.QueryTreeNode;
import com.foundationdb.sql.parser.StatementNode;

// TODO: Auto-generated Javadoc

class SQLParser {

    private com.foundationdb.sql.parser.SQLParser parser;

    private String sql;

	SQLParser(String sql) {
	this.parser = new com.foundationdb.sql.parser.SQLParser();
	this.sql = sql;
    }

    protected QueryTreeNode parseSQL(DynamicVisitor visitor) throws SQL2StreamException {
	try {
		StatementNode stmt = parser.parseStatement(sql);
	    stmt.accept(visitor);
	    return visitor.node;
	} catch (StandardException e) {
	    throw new SQL2StreamException(e);
	}
    }
}
