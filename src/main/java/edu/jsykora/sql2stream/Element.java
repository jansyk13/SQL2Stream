/*
 * 
 */
package edu.jsykora.sql2stream;

public interface Element<E> extends Cloneable {

    Class<E> getClazz();

    void setClazz(Class<E> clazz);

    String getAlias();

    void setAlias(String alias);

    boolean hasNext();

    Element<?> visit(String alias);

    Element<?> getLast();

    void setLast(Element<?> element);

    Element<?> getPenultimate();

    String getStrategy();

    Element<E> clone() throws CloneNotSupportedException;

}
