package edu.jsykora.sql2stream;

import com.foundationdb.sql.parser.*;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
	try {
	    this.sqlTree = (CursorNode) parser.parseSQL(visitor);
	    // sqlTree.treePrint();
	} catch (SQL2StreamException e) {
	    e.printStackTrace();
	}
		SelectNode selectNode = (SelectNode) sqlTree.getResultSetNode();
	this.fromList = selectNode.getFromList();
	this.resultList = selectNode.getResultColumns();
	this.orderList = sqlTree.getOrderByList();
	this.whereClause = selectNode.getWhereClause();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public <F> ResultIterator<F> interpret() throws SQL2StreamException {
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

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public <F> ResultIterator<F> interpret(Function<List<?>, ?> mappingFunction) throws SQL2StreamException {
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
