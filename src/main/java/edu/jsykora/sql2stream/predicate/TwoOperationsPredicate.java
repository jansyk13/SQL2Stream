package edu.jsykora.sql2stream.predicate;

import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc

abstract class TwoOperationsPredicate<T> extends BasePredicate<T> {

    protected BasePredicate<T> leftOperand;

    protected BasePredicate<T> rightOperand;

    protected List<BasePredicate<T>> children;

    protected TwoOperationsPredicate() {
        this.children = new ArrayList<>();
    }

}
