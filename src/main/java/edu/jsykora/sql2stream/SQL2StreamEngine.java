package edu.jsykora.sql2stream;

import java.util.stream.Stream;

import com.foundationdb.sql.parser.CursorNode;
import com.foundationdb.sql.parser.FromList;
import com.foundationdb.sql.parser.OrderByList;
import com.foundationdb.sql.parser.QueryTreeNode;
import com.foundationdb.sql.parser.ResultColumnList;
import com.foundationdb.sql.parser.SelectNode;
import com.foundationdb.sql.parser.ValueNode;

import edu.jsykora.sql2stream.sql.DynamicVisitor;
import edu.jsykora.sql2stream.sql.SQLParser;

/**
 * Created by jansykora on 3.8.16.
 */
public class SQL2StreamEngine {

    private final String sql;

    private CursorNode sqlTree;

    private SQLParser parser;

    private DynamicVisitor visitor;

    private FromList fromList;

    private ResultColumnList resultList;

    private OrderByList orderList;

    private ValueNode whereClause;

    public SQL2StreamEngine(String sql) {
        this.sql = sql;
        parse();
    }

    public Stream<Stream<?>> compute() {
        if (fromList.get(0).convertDefaultNode())

        return null;
    }

    protected void parse() {
        QueryTreeNode node = parser.parseSQL(visitor);
        if (node instanceof CursorNode) {
            this.sqlTree = (CursorNode) node;

            node = sqlTree.getResultSetNode();
            if (node instanceof SelectNode) {
                SelectNode selectNode = (SelectNode) node;

                this.fromList = selectNode.getFromList();
                this.resultList = selectNode.getResultColumns();
                this.orderList = sqlTree.getOrderByList();
                this.whereClause = selectNode.getWhereClause();
            } else {
                throw new IllegalStateException("Parsed AST does not contain SelectNode");
            }
        } else {
            throw new IllegalStateException("Parsed AST does not contain CursorNode as root");
        }
    }
}
