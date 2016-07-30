package edu.jsykora.sql2stream.element;

// TODO: Auto-generated Javadoc

public abstract class AbstractElement<E> implements Element<E> {

    private String alias;

    private Class<E> clazz;

    protected AbstractElement(String alias, Class<E> clazz) {
        this.alias = alias;
        this.clazz = clazz;
    }

    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public Class<E> getClazz() {
        return clazz;
    }

    @Override
    public void setClazz(Class<E> clazz) {
        this.clazz = clazz;
    }

    @Override
    public Element<E> clone() throws CloneNotSupportedException {
        return null;
    }

}
