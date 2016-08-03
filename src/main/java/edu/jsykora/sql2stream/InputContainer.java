package edu.jsykora.sql2stream;


import java.util.stream.Stream;

public class InputContainer<T> {

    private Stream<T> stream;

    private Class<T> clazz;

    public InputContainer(Stream<T> stream, Class<T> clazz) {
        this.clazz = clazz;
        this.stream = stream;
    }

    public Stream<T> getStream() {
        return stream;
    }

    public void setStream(Stream<T> stream) {
        this.stream = stream;
    }

    public Class<T> getClazz() {
        return clazz;
    }

    public void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }
}
