package edu.jsykora.sql2stream;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.foundationdb.sql.parser.ResultColumnList;

import edu.jsykora.sql2stream.element.BaseElement;
import edu.jsykora.sql2stream.element.CompositeBaseElement;
import edu.jsykora.sql2stream.element.TerminalBaseElement;

// TODO: Auto-generated Javadoc

final class SelectFunction implements Function<BaseElement<?>, BaseElement<?>> {

    private List<ColumnSelectFunction<?>> trueSelectFunctions;

    private Iterator<ReferenceObject<?>> iter;

    protected SelectFunction(ResultColumnList list) {
        if (list == null || list.get(0) == null || list.get(0).getName() == null) {
            return;
        }

        this.trueSelectFunctions = StreamSupport.stream(list.spliterator(), false).map(ColumnSelectFunction::new).collect(Collectors.toList());
    }

    @Override
    public BaseElement<?> apply(BaseElement<?> t) {
        if (trueSelectFunctions == null || trueSelectFunctions.isEmpty()) {
            return t;
        }

        List<ReferenceObject<?>> localResult = trueSelectFunctions.stream().map(f -> f.apply(t)).collect(Collectors.toList());
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
