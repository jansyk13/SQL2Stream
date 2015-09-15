package edu.jsykora.sql2stream;

// TODO: Auto-generated Javadoc

public final class TerminalBaseElement<E> extends BaseElement<E> {

    private TerminalBaseElement(E e, String alias, Class<E> clazz) {
	super(e, alias, clazz);
    }

    @SuppressWarnings("unchecked")
    protected static <E> TerminalBaseElement<E> createTerminateBaseElement(E e, String alias) {
	Class<E> klass = null;
	if (e != null) {
	    klass = (Class<E>) e.getClass();
	}

	return new TerminalBaseElement<>(e, alias, klass);
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
	return TerminalBaseElement.createTerminateBaseElement(this.getE(), this.getAlias());
    }

}
