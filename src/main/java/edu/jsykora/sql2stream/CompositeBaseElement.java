package edu.jsykora.sql2stream;

// TODO: Auto-generated Javadoc

public final class CompositeBaseElement<E> extends BaseElement<E> {

    private CompositeBaseElement(E e, String alias, Class<E> clazz, BaseElement<?> next) {
	super(e, alias, clazz);
	this.next = next;
    }

    @SuppressWarnings("unchecked")
    protected static <E> CompositeBaseElement<E> createCompositeBaseElement(E e, String alias, BaseElement<?> next) {
	Class<E> klass = null;
	if (e != null) {
	    klass = (Class<E>) e.getClass();
	}

	return new CompositeBaseElement<>(e, alias, klass, next);
    }

    private BaseElement<?> next;

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
	((CompositeBaseElement<?>) this.getPenultimate()).setNext((BaseElement<?>) element);
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
	return new CompositeBaseElement<>(this.getE(), this.getAlias(), this.getClazz(), (BaseElement<?>) this.getNext().clone());
    }

    public BaseElement<?> getNext() {
	return next;
    }

    public void setNext(BaseElement<?> next) {
	this.next = next;
    }

}
