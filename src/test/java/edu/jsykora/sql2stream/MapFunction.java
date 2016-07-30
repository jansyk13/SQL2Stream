package edu.jsykora.sql2stream;

import java.util.List;
import java.util.function.Function;

public class MapFunction {

    public class MappingFunction implements Function<List<Object>, TestingModel> {

        @Override
        public TestingModel apply(List<Object> t) {
            TestingModel local = new TestingModel();
            local.setName((String) t.get(0));
            return local;
        }

    }

}
