package edu.jsykora.sql2stream;

import java.util.List;
import java.util.function.Function;

public class ContainerTestModel<T> implements Function<List<TestingModel>, T> {

    public ContainerTestModel() {
	// TODO Auto-generated constructor stub
    }

    @Override
    public T apply(List<TestingModel> t) {
	return (T) t;
    }

}
