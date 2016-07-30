package edu.jsykora.sql2stream;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.jsykora.sql2stream.element.BaseElement;
import edu.jsykora.sql2stream.element.CompositeBaseElement;

public class SelectTest {
    private List<TestingModel> list1;
    private List<TestingModel> list2;

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

    }

    @Test
    public void oneColumnTest() throws SQL2StreamException {
        String sql = "SELECT t.name FROM list1 t";
        SQL2Stream<TestingModel> test = new SQL2Stream<>(sql, new InputContainer<>(list1, TestingModel.class));
        ResultIterator<BaseElement<String>> result = test.interpret();
        if (!result.next().getE().equalsIgnoreCase("test")) {
            fail();
        }
        if (!result.next().getE().equalsIgnoreCase("testing")) {
            fail();
        }
        if (!result.next().getE().equalsIgnoreCase("test")) {
            fail();
        }
        if (!result.next().getE().equalsIgnoreCase("testing")) {
            fail();
        }
        int count = 0;
        while (result.hasNext()) {
            count++;
            result.next();
        }
        if (count != 0) {
            fail();
        }
    }

    @Test
    public void TwoColumnsTest() throws SQL2StreamException {
        String sql = "SELECT t.name, t.number FROM list1 t";
        SQL2Stream<TestingModel> test = new SQL2Stream<>(sql, new InputContainer<>(list1, TestingModel.class));
        ResultIterator<BaseElement<String>> result = test.interpret();
        CompositeBaseElement<String> local = (CompositeBaseElement<String>) result.next();
        if (!local.getE().equalsIgnoreCase("test") && ((Integer) local.getNext().getE()) != 1) {
            fail();
        }
    }

    @Test
    public void OneEntitytTest() throws SQL2StreamException {
        String sql = "SELECT t FROM list1 t";
        SQL2Stream<TestingModel> test = new SQL2Stream<>(sql, new InputContainer<>(list1, TestingModel.class));
        ResultIterator<BaseElement<TestingModel>> result = test.interpret();
        if (!(result.next().getE() instanceof TestingModel)) {
            fail();
        }
    }

    @Test
    public void OneEntityAndColumntTest() throws SQL2StreamException {
        String sql = "SELECT t, t.number FROM list1 t";
        SQL2Stream<TestingModel> test = new SQL2Stream<>(sql, new InputContainer<>(list1, TestingModel.class));
        ResultIterator<BaseElement<TestingModel>> result = test.interpret();
        CompositeBaseElement<TestingModel> local = (CompositeBaseElement<TestingModel>) result.next();
        if (!(local.getE() instanceof TestingModel) && local.getNext().getE() instanceof Integer) {
            fail();
        }
    }

    @Test
    public void TwoEntitiesTest() throws SQL2StreamException {
        String sql = "SELECT t, u FROM list1 t JOIN list2 u ON t.number=u.number";
        SQL2Stream<TestingModel> test = new SQL2Stream<>(sql, new InputContainer<>(list1, TestingModel.class), new InputContainer<>(list2, TestingModel.class));
        ResultIterator<BaseElement<TestingModel>> result = test.interpret();
        CompositeBaseElement<TestingModel> local = (CompositeBaseElement<TestingModel>) result.next();
        if (!(local.getE() instanceof TestingModel) && local.getNext().getE() instanceof TestingModel) {
            fail();
        }
    }

}
