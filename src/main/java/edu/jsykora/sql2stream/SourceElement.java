package edu.jsykora.sql2stream;

// TODO: Auto-generated Javadoc

abstract class SourceElement<E> extends AbstractElement<E> {

    private static final String SOURCE_ELEMENT = "SOURCE_ELEMENT";

    private Iterable<E> iterable;

    protected SourceElement(Iterable<E> iterable, String alias, Class<E> clazz) {
	super(alias, clazz);
	this.iterable = iterable;
    }

    public Iterable<E> getIterable() {
	return iterable;
    }

    public void setIterable(Iterable<E> iterable) {
	this.iterable = iterable;
    }

    @Override
    public String getStrategy() {
	return SOURCE_ELEMENT;
    }
}
