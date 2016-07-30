package edu.jsykora.sql2stream.join;

import java.util.List;

import edu.jsykora.sql2stream.element.BaseElement;

// TODO: Auto-generated Javadoc

interface Join<E> {

    List<BaseElement<E>> interpret();
}
