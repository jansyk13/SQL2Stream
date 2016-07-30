package edu.jsykora.sql2stream.models;

import java.util.List;
import java.util.function.Function;

public class ContainerTestModel<T> implements Function<List<TestingModel>, T> {

    public ContainerTestModel() {
    }

    @Override
    public T apply(List<TestingModel> t) {
	return (T) t;
    }

}
