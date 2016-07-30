package edu.jsykora.sql2stream.integration;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.jsykora.sql2stream.InputContainer;
import edu.jsykora.sql2stream.SQL2Stream;
import edu.jsykora.sql2stream.SQL2StreamException;
import edu.jsykora.sql2stream.element.BaseElement;
import edu.jsykora.sql2stream.iterator.ResultIterator;
import edu.jsykora.sql2stream.models.TestingModel;

public class FullIntegrationTest {
    private List<TestingModel> list1;
    private List<TestingModel> list2;
    private List<TestingModel> list3;
    private List<TestingModel> list4;

    @Before
    public void setUp() throws Exception {
        list1 = new ArrayList<>();
        list1.add(new TestingModel("test", 1));
        list1.add(new TestingModel("testing", 1));
        list1.add(new TestingModel("test", 2));
        list1.add(new TestingModel("testing", 2));

        list2 = new ArrayList<>();
        list2.add(new TestingModel("testing", 1));
        list2.add(new TestingModel("testing", 2));
        list2.add(new TestingModel("test", 1));
        list2.add(new TestingModel("test", 2));

        list3 = new ArrayList<>();
        list3.add(new TestingModel("test", 1));
        list3.add(new TestingModel("test", 2));
        list3.add(new TestingModel("testing", 1));
        list3.add(new TestingModel("testing", 2));

        list4 = new ArrayList<>();
        list4.add(new TestingModel("testing", 1));
        list4.add(new TestingModel("testing", 2));
        list4.add(new TestingModel("test", 1));
        list4.add(new TestingModel("test", 2));

    }

    @Test
    public void test() throws SQL2StreamException {
        String sql = "SELECT v.name FROM (list2 u JOIN (list3 r JOIN list4 v ON r.number=v.number)ON r.number=u.number) JOIN list1 t ON t.number=u.number WHERE t.name='test' ORDER BY u.name, r.name DESC ";
        SQL2Stream<TestingModel> test = new SQL2Stream<>(sql, new InputContainer<>(list1, TestingModel.class), new InputContainer<>(list2, TestingModel.class),
                new InputContainer<>(list3, TestingModel.class), new InputContainer<>(list4, TestingModel.class));
        ResultIterator<BaseElement<String>> result = test.interpret();
        int count = 0;
        while (result.hasNext()) {
            count++;
            result.next();
        }
        if (count != 16) {
            fail();
        }
    }

}
