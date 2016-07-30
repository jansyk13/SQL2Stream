package edu.jsykora.sql2stream;

import java.util.List;

import edu.jsykora.sql2stream.element.BaseElement;

// TODO: Auto-generated Javadoc

public final class BaseResultIterator<T> implements ResultIterator<BaseElement<T>> {

    private List<BaseElement<T>> result;

    private Integer size;

    protected BaseResultIterator(List<BaseElement<T>> result) {
        this.result = result;
        this.size = result.size();
    }

    @Override
    public BaseElement<T> next() {
        if (size == 0) {
            return null;
        }
        BaseElement<T> local = result.get(0);
        result.remove(0);
        setSize();
        return local;
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
