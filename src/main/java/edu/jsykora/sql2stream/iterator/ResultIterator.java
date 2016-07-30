package edu.jsykora.sql2stream.iterator;

import java.util.Iterator;

// TODO: Auto-generated Javadoc

public interface ResultIterator<T> extends Iterator<T> {

    @Override
    T next();

    @Override
    boolean hasNext();

}
