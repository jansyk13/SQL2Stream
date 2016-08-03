package edu.jsykora.sql2stream.utils;

import static java.util.stream.Collectors.toList;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.StreamSupport;

import com.foundationdb.sql.parser.ResultColumnList;

import edu.jsykora.sql2stream.element.BaseElement;
import edu.jsykora.sql2stream.element.CompositeBaseElement;
import edu.jsykora.sql2stream.element.TerminalBaseElement;

// TODO: Auto-generated Javadoc

public final class SelectFunction implements Function<BaseElement<?>, BaseElement<?>> {

    private final List<ColumnSelectFunction<?>> trueSelectFunctions;

    private Iterator<ReferenceObject<?>> iter;

    public SelectFunction(ResultColumnList list) {
        if (list == null || list.get(0) == null || list.get(0).getName() == null) {
            this.trueSelectFunctions = Collections.emptyList();
        } else {
            this.trueSelectFunctions = StreamSupport.stream(list.spliterator(), false).map(ColumnSelectFunction::new).collect(toList());
        }
    }

    @Override
    public BaseElement<?> apply(BaseElement<?> t) {
        if (trueSelectFunctions.isEmpty()) {
            return t;
        }

        List<ReferenceObject<?>> localResult = trueSelectFunctions.stream().map(f -> f.apply(t)).collect(toList());
        iter = localResult.iterator();

        return recursiveCreation();

    }

    private BaseElement<?> recursiveCreation() {
        ReferenceObject<?> local = iter.next();
        if (iter.hasNext()) {
            return CompositeBaseElement.createCompositeBaseElement(local.getR(), local.getAlias(), recursiveCreation());
        }

        return TerminalBaseElement.createTerminateBaseElement(local.getR(), local.getAlias());
    }

}
