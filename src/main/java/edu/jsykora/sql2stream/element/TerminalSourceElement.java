package edu.jsykora.sql2stream.element;

// TODO: Auto-generated Javadoc

import edu.jsykora.sql2stream.InputContainer;

public final class TerminalSourceElement<E> extends SourceElement<E> {

    private TerminalSourceElement(Iterable<E> iterable, String alias, Class<E> clazz) {
        super(iterable, alias, clazz);
    }

    public static <E> TerminalSourceElement<E> createTerminalSourceElement(InputContainer<E> container, String alias) {
        return new TerminalSourceElement<>(container.getIterable(), alias, container.getClazz());
    }

    private static <E> Element<E> createTerminalSourceElement(Iterable<E> iterable, Class<E> clazz, String alias) {
        return new TerminalSourceElement<>(iterable, alias, clazz);
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public Element<?> visit(String alias) {
        if (alias.equalsIgnoreCase(this.getAlias())) {
            return this;
        }
        return null;
    }

    @Override
    public Element<?> getLast() {
        return this;
    }

    @Override
    public void setLast(Element<?> element) {
    }

    @Override
    public Element<?> getPenultimate() {
        return null;
    }

    @Override
    public Element<E> clone() throws CloneNotSupportedException {
        return TerminalSourceElement.createTerminalSourceElement(this.getIterable(), this.getClazz(), this.getAlias());
    }

}
