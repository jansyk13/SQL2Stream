package edu.jsykora.sql2stream;

// TODO: Auto-generated Javadoc

interface Expression<O extends Comparable<O>> {

    O returnExpression();

    static boolean getResult(String operator, Integer result) {
	switch (operator) {
	case "=":
	    return result == 0;
	case "<":
	    return result < 0;
	case ">":
	    return result > 0;
	case "<=":
	    return result <= 0;
	case ">=":
	    return result >= 0;
	case "!=":
	    return result != 0;
	default:
	    return true;
	}
    }
}
