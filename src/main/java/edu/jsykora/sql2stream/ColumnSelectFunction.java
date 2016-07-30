package edu.jsykora.sql2stream;

import java.lang.reflect.Field;
import java.util.function.Function;

import com.foundationdb.sql.parser.ResultColumn;

import edu.jsykora.sql2stream.element.BaseElement;
import edu.jsykora.sql2stream.element.Element;

// TODO: Auto-generated Javadoc

final class ColumnSelectFunction<R> implements Function<BaseElement<?>, ReferenceObject<R>> {

    private String alias;

    private String target;

    protected ColumnSelectFunction(ResultColumn result) {
        this.alias = result.getTableName() != null ? result.getTableName() : result.getName();
        this.target = result.getName();

    }

    @SuppressWarnings("unchecked")
    @Override
    public ReferenceObject<R> apply(BaseElement<?> t) {
        Element<?> local = t.visit(alias);

        if (alias.equals(target)) {
            return new ReferenceObject<>((R) t.getE(), alias);
        }

        try {
            Field targetField = local.getClazz().getDeclaredField(target);
            targetField.setAccessible(true);
            return new ReferenceObject<>((R) targetField.get(((BaseElement<?>) local).getE()), alias);
        } catch (Exception e) {
            return null;
        }

    }
}
