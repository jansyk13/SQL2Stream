package edu.jsykora.sql2stream;


public class InputContainer<T> {

    private Iterable<T> iterable;

    private Class<T> clazz;

    public InputContainer(Iterable<T> iterable, Class<T> clazz) {
        this.clazz = clazz;
        this.iterable = iterable;
    }

    public Iterable<T> getIterable() {
        return iterable;
    }

    protected void setStream(Iterable<T> iterable) {
        this.iterable = iterable;
    }

    public Class<T> getClazz() {
        return clazz;
    }

    protected void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }

}
