package edu.jsykora.sql2stream;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.foundationdb.sql.parser.CursorNode;
import com.foundationdb.sql.parser.FromList;
import com.foundationdb.sql.parser.OrderByList;
import com.foundationdb.sql.parser.ResultColumnList;
import com.foundationdb.sql.parser.SelectNode;
import com.foundationdb.sql.parser.ValueNode;

import edu.jsykora.sql2stream.element.BaseElement;
import edu.jsykora.sql2stream.element.CompositeSourceElement;
import edu.jsykora.sql2stream.element.SourceElement;
import edu.jsykora.sql2stream.element.TerminalSourceElement;
import edu.jsykora.sql2stream.iterator.BaseResultIterator;
import edu.jsykora.sql2stream.iterator.MappedResultIterator;
import edu.jsykora.sql2stream.iterator.ResultIterator;
import edu.jsykora.sql2stream.join.AbstractJoin;
import edu.jsykora.sql2stream.predicate.BasePredicate;
import edu.jsykora.sql2stream.sql.DynamicVisitor;
import edu.jsykora.sql2stream.sql.SQLParser;
import edu.jsykora.sql2stream.utils.DynamicComparator;
import edu.jsykora.sql2stream.utils.SelectFunction;

// TODO: Auto-generated Javadoc

public final class SQL2Stream<T> {

    private SourceElement<T> source;

    private CursorNode sqlTree;

    private SQLParser parser;

    private DynamicVisitor visitor;

    private FromList fromList;

    private ResultColumnList resultList;

    private OrderByList orderList;

    private ValueNode whereClause;

    private SQL2Stream(String sql) {


        if (sql != null && !sql.isEmpty()) {
            this.parser = new SQLParser(sql);
            this.visitor = new DynamicVisitor();
        }
    }

    public SQL2Stream(String sql, InputContainer<T> t) {
        this(sql);
        this.source = TerminalSourceElement.createTerminalSourceElement(t, "t");

    }

    public <U> SQL2Stream(String sql, InputContainer<T> t, InputContainer<U> u) {
        this(sql);
        this.source = CompositeSourceElement.createCompositeSourceElement(t, "t", TerminalSourceElement.createTerminalSourceElement(u, "u"));
    }

    public <U, R> SQL2Stream(String sql, InputContainer<T> t, InputContainer<U> u, InputContainer<R> r) {
        this(sql);
        this.source = CompositeSourceElement.createCompositeSourceElement(t, "t",
                CompositeSourceElement.createCompositeSourceElement(u, "u", TerminalSourceElement.createTerminalSourceElement(r, "r")));
    }

    public <U, R, V> SQL2Stream(String sql, InputContainer<T> t, InputContainer<U> u, InputContainer<R> r, InputContainer<V> v) {
        this(sql);
        this.source = CompositeSourceElement.createCompositeSourceElement(
                t,
                "t",
                CompositeSourceElement.createCompositeSourceElement(u, "u",
                        CompositeSourceElement.createCompositeSourceElement(r, "r", TerminalSourceElement.createTerminalSourceElement(v, "v"))));
    }

    protected void parse() {
        this.sqlTree = (CursorNode) parser.parseSQL(visitor);

        SelectNode selectNode = (SelectNode) sqlTree.getResultSetNode();
        this.fromList = selectNode.getFromList();
        this.resultList = selectNode.getResultColumns();
        this.orderList = sqlTree.getOrderByList();
        this.whereClause = selectNode.getWhereClause();
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public <F> ResultIterator<F> interpret() {
        if (parser == null) {
            return null;
        }
        parse();
        Predicate<? super BaseElement<Object>> predicate = BasePredicate.build(whereClause);
        Comparator<? super BaseElement<Object>> comparator = DynamicComparator.getComparator(orderList);
        SelectFunction select = new SelectFunction(resultList);

        List<BaseElement<?>> localResult = AbstractJoin.getJoin(fromList.get(0), source).interpret().stream().filter(predicate)
                .sorted(comparator).map(select).collect(Collectors.toList());
        return new BaseResultIterator(localResult);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public <F> ResultIterator<F> interpret(Function<List<?>, ?> mappingFunction) {
        if (parser == null) {
            return null;
        }
        parse();
        Predicate<? super BaseElement<Object>> predicate = BasePredicate.build(whereClause);
        Comparator<? super BaseElement<Object>> comparator = DynamicComparator.getComparator(orderList);
        SelectFunction select = new SelectFunction(resultList);

        List<BaseElement<?>> localResult = AbstractJoin.getJoin(fromList.get(0), source).interpret().stream().filter(predicate)
                .sorted(comparator).map(select).collect(Collectors.toList());
        return new MappedResultIterator(mappingFunction, localResult);
    }
}
