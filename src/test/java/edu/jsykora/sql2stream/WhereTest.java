package edu.jsykora.sql2stream;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class WhereTest {
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
    public void OneConditionTest() throws SQL2StreamException {
	String sql = "SELECT * FROM list1 t WHERE t.number=1";
	SQL2Stream<TestingModel> test = new SQL2Stream<>(sql, new InputContainer<>(list1, TestingModel.class));
	ResultIterator<BaseElement<TestingModel>> result = test.interpret();
	int count = 0;
	while (result.hasNext()) {
	    count++;
	    result.next();
	}
	if (count != 2) {
	    fail();
	}
    }

    @Test
    public void TwoConditionsANDTest() throws SQL2StreamException {
	String sql = "SELECT * FROM list1 t WHERE t.number=1 AND t.name='test'";
	SQL2Stream<TestingModel> test = new SQL2Stream<>(sql, new InputContainer<>(list1, TestingModel.class));
	ResultIterator<BaseElement<TestingModel>> result = test.interpret();
	int count = 0;
	while (result.hasNext()) {
	    count++;
	    result.next();
	}
	if (count != 1) {
	    fail();
	}
    }

    @Test
    public void TwoConditionsORTest() throws SQL2StreamException {
	String sql = "SELECT * FROM list1 t WHERE t.number=1 OR t.name='test'";
	SQL2Stream<TestingModel> test = new SQL2Stream<>(sql, new InputContainer<>(list1, TestingModel.class));
	ResultIterator<BaseElement<TestingModel>> result = test.interpret();
	int count = 0;
	while (result.hasNext()) {
	    count++;
	    result.next();
	}
	if (count != 3) {
	    fail();
	}
    }

    @Test
    public void TwoConditionsANDNOTTest() throws SQL2StreamException {
	String sql = "SELECT * FROM list1 t WHERE t.number=1 AND NOT t.name='test'";
	SQL2Stream<TestingModel> test = new SQL2Stream<>(sql, new InputContainer<>(list1, TestingModel.class));
	ResultIterator<BaseElement<TestingModel>> result = test.interpret();
	int count = 0;
	while (result.hasNext()) {
	    count++;
	    result.next();
	}
	if (count != 1) {
	    fail();
	}
    }

    @Test
    public void OneConditionAndJoinTest() throws SQL2StreamException {
	String sql = "SELECT * FROM list1 t JOIN list2 u ON t.number=u.number WHERE t.number=1";
	SQL2Stream<TestingModel> test = new SQL2Stream<>(sql, new InputContainer<>(list1, TestingModel.class), new InputContainer<>(list2, TestingModel.class));
	ResultIterator<BaseElement<TestingModel>> result = test.interpret();
	int count = 0;
	while (result.hasNext()) {
	    count++;
	    result.next();
	}
	if (count != 4) {
	    fail();
	}
    }

    @Test
    public void TwoConditionsANDAndJoinTest() throws SQL2StreamException {
	String sql = "SELECT * FROM list1 t JOIN list2 u ON t.number=u.number WHERE t.number=1 AND u.number=0";
	SQL2Stream<TestingModel> test = new SQL2Stream<>(sql, new InputContainer<>(list1, TestingModel.class), new InputContainer<>(list2, TestingModel.class));
	ResultIterator<BaseElement<TestingModel>> result = test.interpret();
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
