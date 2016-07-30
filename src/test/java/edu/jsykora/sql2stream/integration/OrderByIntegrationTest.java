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

public class OrderByIntegrationTest {
    private List<TestingModel> list1;

    @Before
    public void setUp() throws Exception {
        list1 = new ArrayList<>();
        list1.add(new TestingModel("test", 1));
        list1.add(new TestingModel("testing", 1));
        list1.add(new TestingModel("test", 2));
        list1.add(new TestingModel("testing", 2));

        List<TestingModel> list2 = new ArrayList<>();
        list2.add(new TestingModel("testing", 1));
        list2.add(new TestingModel("testing", 2));
        list2.add(new TestingModel("test", 1));
        list2.add(new TestingModel("test", 2));
    }

    @Test
    public void OneSortTest() throws SQL2StreamException {
        String sql = "SELECT * FROM list1 t ORDER BY t.name";
        SQL2Stream<TestingModel> test = new SQL2Stream<>(sql, new InputContainer<>(list1, TestingModel.class));
        ResultIterator<BaseElement<TestingModel>> result = test.interpret();
        if (!result.next().getE().getName().equalsIgnoreCase("test")) {
            fail();
        }
        if (!result.next().getE().getName().equalsIgnoreCase("test")) {
            fail();
        }
        if (!result.next().getE().getName().equalsIgnoreCase("testing")) {
            fail();
        }
        if (!result.next().getE().getName().equalsIgnoreCase("testing")) {
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
    public void OneSortDESCTest() throws SQL2StreamException {
        String sql = "SELECT * FROM list1 t ORDER BY t.name DESC";
        SQL2Stream<TestingModel> test = new SQL2Stream<>(sql, new InputContainer<>(list1, TestingModel.class));
        ResultIterator<BaseElement<TestingModel>> result = test.interpret();
        if (!result.next().getE().getName().equalsIgnoreCase("testing")) {
            fail();
        }
        if (!result.next().getE().getName().equalsIgnoreCase("testing")) {
            fail();
        }
        if (!result.next().getE().getName().equalsIgnoreCase("test")) {
            fail();
        }
        if (!result.next().getE().getName().equalsIgnoreCase("test")) {
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
    public void TwoSortTest() throws SQL2StreamException {
        String sql = "SELECT * FROM list1 t ORDER BY t.name, t.number DESC";
        SQL2Stream<TestingModel> test = new SQL2Stream<>(sql, new InputContainer<>(list1, TestingModel.class));
        ResultIterator<BaseElement<TestingModel>> result = test.interpret();
        BaseElement<TestingModel> local = result.next();
        if (!local.getE().getName().equalsIgnoreCase("test") && local.getE().getNumber() != 2) {
            fail();
        }
        local = result.next();
        if (!local.getE().getName().equalsIgnoreCase("test") && local.getE().getNumber() != 1) {
            fail();
        }
        local = result.next();
        if (!local.getE().getName().equalsIgnoreCase("testing") && local.getE().getNumber() != 2) {
            fail();
        }
        local = result.next();
        if (!local.getE().getName().equalsIgnoreCase("testing") && local.getE().getNumber() != 1) {
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
}
