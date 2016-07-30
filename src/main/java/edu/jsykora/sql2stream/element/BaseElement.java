package edu.jsykora.sql2stream.element;

// TODO: Auto-generated Javadoc

public abstract class BaseElement<E> extends AbstractElement<E> {

    private static final String BASE_ELEMENT = "BASE_ELEMENT";

    protected BaseElement(E e, String alias, Class<E> clazz) {
        super(alias, clazz);
        this.e = e;
    }

    private E e;

    public E getE() {
        return e;
    }

    public void setE(E e) {
        this.e = e;
    }

    @Override
    public String getStrategy() {
        return BASE_ELEMENT;
    }

}
