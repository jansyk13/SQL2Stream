package edu.jsykora.sql2stream.expression;

import java.lang.reflect.Field;

import com.foundationdb.sql.parser.ColumnReference;

import edu.jsykora.sql2stream.SQL2StreamException;
import edu.jsykora.sql2stream.element.BaseElement;

// TODO: Auto-generated Javadoc

final class ReferenceExpression<O extends Comparable<O>> extends BaseExpression<O> {

    @SuppressWarnings("unchecked")
    protected ReferenceExpression(BaseElement<?> element, ColumnReference reference) throws SQL2StreamException {
        String target = reference.getColumnName();
        try {
            Field targetField = element.getClazz().getDeclaredField(target);
            targetField.setAccessible(true);
            this.o = (O) targetField.get(element.getE());
        } catch (Exception e) {
            throw new SQL2StreamException(e);
        }
    }

    @Override
    public O returnExpression() {
        return o;
    }

}
