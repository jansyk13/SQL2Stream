package edu.jsykora.sql2stream;

import com.foundationdb.sql.parser.OrderByColumn;
import com.foundationdb.sql.parser.OrderByList;

import java.util.Comparator;

// TODO: Auto-generated Javadoc

class DynamicComparator<T, O extends Comparable<O>> implements Comparator<BaseElement<T>> {

	private OrderByColumn orderBy;

    private Ordering ordering;

    private DynamicComparator<T, O> nextComparator;

    private DynamicComparator(OrderByList orderByList) {
		this.orderBy = orderByList.get(0);
	this.ordering = orderBy.isAscending() ? Ordering.ASC : Ordering.DESC;

	orderByList.remove(0);
	if (!orderByList.isEmpty()) {
	    this.nextComparator = getDynamicComparator(orderByList);
	}
    }

    protected static <T, O extends Comparable<O>> DynamicComparator<T, O> getDynamicComparator(OrderByList orderByList) {
	if (orderByList == null || orderByList.isEmpty() || orderByList.get(0) == null) {
	    return new DynamicComparator<T, O>(orderByList) {
		@Override
		public int compare(BaseElement<T> t1, BaseElement<T> t2) {
		    return 0;
		}
	    };
	}
	return new DynamicComparator<>(orderByList);
    }

    @SuppressWarnings("unchecked")
    protected static <T, O extends Comparable<O>> Comparator<T> getComparator(OrderByList orderByList) {
	if (orderByList == null || orderByList.isEmpty() || orderByList.get(0) == null) {
	    return (o1, o2) -> 0;
	}
	return (Comparator<T>) new DynamicComparator<T, O>(orderByList);
    }

    @Override
    public int compare(BaseElement<T> arg0, BaseElement<T> arg1) {
	try {
	    Expression<O> left = BaseExpression.createBaseExpression(arg0, orderBy.getExpression());
	    Expression<O> right = BaseExpression.createBaseExpression(arg1, orderBy.getExpression());
	    int result = left.returnExpression().compareTo(right.returnExpression());
	    if (result == 0 && nextComparator != null) {
		nextComparator.compare(arg0, arg1);
	    }
	    if (ordering == Ordering.DESC) {
		return result * (-1);
	    }
	    return result;
	} catch (Exception e) {
	    return 0;
	}

    }

    private enum Ordering {

	ASC, DESC
    }

}
