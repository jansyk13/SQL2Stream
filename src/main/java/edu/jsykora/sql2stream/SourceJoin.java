package edu.jsykora.sql2stream;

import com.foundationdb.sql.parser.FromBaseTable;
import com.foundationdb.sql.parser.QueryTreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

// TODO: Auto-generated Javadoc

@SuppressWarnings("rawtypes")
final class SourceJoin extends AbstractJoin {

	private String target;

    private List<BaseElement<?>> targetList;

    @SuppressWarnings("unchecked")
    protected SourceJoin(SourceElement<?> source, QueryTreeNode node) {
	super(source);
		FromBaseTable node1 = (FromBaseTable) node;
	this.target = node1.getCorrelationName();

    }

    @Override
    public List<BaseElement<?>> interpret() {
	this.targetList = new ArrayList<>();
	StreamSupport.stream(((SourceElement<?>) getSource().visit(target)).getIterable().spliterator(), false).forEach(t -> {
	    TerminalBaseElement<Object> test = TerminalBaseElement.createTerminateBaseElement(t, target);
	    targetList.add(test);
	});
	return targetList;
    }
}
