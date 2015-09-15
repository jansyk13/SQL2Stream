package edu.jsykora.sql2stream;

// TODO: Auto-generated Javadoc

final class CompositeSourceElement<E> extends SourceElement<E> {

    private CompositeSourceElement(Iterable<E> iterable, String alias, Class<E> clazz, SourceElement<?> next) {
	super(iterable, alias, clazz);
	this.next = next;
    }

    protected static <E> CompositeSourceElement<E> createCompositeSourceElement(InputContainer<E> container, String alias, SourceElement<?> next) {
	return new CompositeSourceElement<>(container.getIterable(), alias, container.getClazz(), next);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    protected static CompositeSourceElement<?> createCompositeSourceElement(Iterable<?> iterable, String alias, Class<?> clazz, SourceElement<?> next) {
	return new CompositeSourceElement(iterable, alias, clazz, next);
    }

    private SourceElement<?> next;

    @Override
    public boolean hasNext() {
	return true;
    }

    @Override
    public Element<?> visit(String alias) {
	if (alias.equalsIgnoreCase(this.getAlias())) {
	    return this;
	}

	return next.visit(alias);
    }

    @Override
    public Element<?> getLast() {
	return next;
    }

    @Override
    public void setLast(Element<?> element) {
	this.getPenultimate().setLast(element);
    }

    @Override
    public Element<?> getPenultimate() {
	if (next.hasNext()) {
	    return next.getPenultimate();
	}
	return this;
    }

    @Override
    public Element<E> clone() throws CloneNotSupportedException {
	return new CompositeSourceElement<>(this.getIterable(), this.getAlias(), this.getClazz(), (SourceElement<?>) this.getNext().clone());
    }

    public SourceElement<?> getNext() {
	return next;
    }

    public void setNext(SourceElement<?> next) {
	this.next = next;
    }

}
