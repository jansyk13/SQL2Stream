package edu.jsykora.sql2stream.iterator;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import edu.jsykora.sql2stream.element.BaseElement;
import edu.jsykora.sql2stream.element.CompositeBaseElement;

// TODO: Auto-generated Javadoc

public final class MappedResultIterator<T> implements ResultIterator<T> {

    private Function<List<Object>, T> mappingFunction;

    private List<BaseElement<?>> result;

    private Integer size;

    public MappedResultIterator(Function<List<Object>, T> mappingFunction) {
        this.mappingFunction = mappingFunction;
    }

    public MappedResultIterator(Function<List<Object>, T> mappingFunction, List<BaseElement<?>> result) {
        this(mappingFunction);
        this.result = result;
        this.size = result.size();
    }

    @Override
    public T next() {
        if (size == 0) {
            return null;
        }
        BaseElement<?> local = result.get(0);
        List<Object> localResult = new ArrayList<>();
        localResult.add(local.getE());
        while (local.hasNext()) {
            local = ((CompositeBaseElement<?>) local).getNext();
            localResult.add(local.getE());
        }

        result.remove(0);
        setSize();
        return mappingFunction.apply(localResult);
    }

    public Integer getLeft() {
        return this.size;
    }

    @Override
    public boolean hasNext() {
        return this.size > 0;
    }

    private void setSize() {
        this.size = result.size();
    }

}
