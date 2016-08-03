package edu.jsykora.sql2stream;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.foundationdb.sql.parser.CursorNode;
import com.foundationdb.sql.parser.FromList;
import com.foundationdb.sql.parser.OrderByList;
import com.foundationdb.sql.parser.ResultColumnList;
import com.foundationdb.sql.parser.SelectNode;
import com.foundationdb.sql.parser.ValueNode;

import edu.jsykora.sql2stream.element.BaseElement;
import edu.jsykora.sql2stream.element.SourceElement;
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

public final class SQL2Stream {


    private final List<InputContainer<?>> inputContainerList;

    private SQL2StreamEngine engine;

    public SQL2Stream(String sql) {
        this.engine = new SQL2StreamEngine(sql);
        this.inputContainerList = new ArrayList<>();
    }

    public <T> SQL2Stream addSource(Stream<T> inputStream, Class<T> clazz) {
        InputContainer<T> inputContainer = new InputContainer<>(inputStream, clazz);
        if (inputContainerList.contains(inputContainer)) {
            throw new IllegalStateException("Cannot enter two same input containers.");
        }
        this.inputContainerList.add(inputContainer);
        return this;
    }

    public <F> Stream<F> compute(Function<Stream<?>, F> mappingFunction) {
        Stream<Stream<?>> computedResult = this.compute();
        return computedResult.map(innerStream -> mappingFunction.apply(innerStream));
    }

    public Stream<Stream<?>> compute() {
        Stream<Stream<?>> computedResult = this.engine.compute();
        return computedResult;
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

    @SuppressWarnings({"unchecked"})
    public <F> ResultIterator<F> interpret(Function<List<?>, F> mappingFunction) {
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
