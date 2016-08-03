package edu.jsykora.sql2stream.utils;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.foundationdb.sql.parser.ResultColumnList;

import edu.jsykora.sql2stream.element.BaseElement;

@RunWith(MockitoJUnitRunner.class)
public class SelectFunctionTest {

    @Mock(answer = RETURNS_DEEP_STUBS)
    private ResultColumnList resultColumnList;

    @Mock
    private BaseElement<String> input;

    @Test
    public void testConstructorNull() throws Exception {
        SelectFunction selectFunction = new SelectFunction(null);
        BaseElement<?> applied = selectFunction.apply(input);
        assertThat(applied, is(input));
    }
    @Test
    public void testConstructorEmpty() throws Exception {
        when(resultColumnList.get(0)).thenReturn(null);

        SelectFunction selectFunction = new SelectFunction(resultColumnList);
        BaseElement<?> applied = selectFunction.apply(input);
        assertThat(applied, is(input));
    }
    @Test
    public void testConstructorFirstNameWithoutName() throws Exception {
        when(resultColumnList.get(0).getName()).thenReturn(null);

        SelectFunction selectFunction = new SelectFunction(resultColumnList);
        BaseElement<?> applied = selectFunction.apply(input);
        assertThat(applied, is(input));
    }

    //TODO missing cases
}
