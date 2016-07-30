package edu.jsykora.sql2stream.element;

import java.util.function.BiFunction;

public final class ElementAppender<A, B> implements BiFunction<Element<A>, Element<B>, Element<A>> {

    private static final String BASE_ELEMENT = "BASE_ELEMENT";

    private static final String SOURCE_ELEMENT = "SOURCE_ELEMENT";

    @SuppressWarnings("unchecked")
    @Override
    public Element<A> apply(Element<A> e, Element<B> n) {
        Element<A> cloneE;
        Element<B> cloneN;
        try {
            cloneE = e.clone();
            cloneN = n.clone();
        } catch (CloneNotSupportedException e1) {
            return null;
        }

        if (e.hasNext()) {
            switch (e.getStrategy()) {
                case BASE_ELEMENT:
                    BaseElement<A> retype = (BaseElement<A>) cloneE;
                    CompositeBaseElement<?> retypePenultime = (CompositeBaseElement<?>) retype.getPenultimate();
                    retypePenultime.setNext(CompositeBaseElement.createCompositeBaseElement(retypePenultime.getNext().getE(), retypePenultime.getNext().getAlias(),
                            (BaseElement<B>) cloneN));
                    return retype;
                case SOURCE_ELEMENT:
                    SourceElement<A> retype2 = (SourceElement<A>) cloneE;
                    CompositeSourceElement<?> retypePenultime2 = (CompositeSourceElement<?>) retype2.getPenultimate();
                    retypePenultime2.setNext(CompositeSourceElement.createCompositeSourceElement(retypePenultime2.getNext().getIterable(), retypePenultime2
                            .getNext().getAlias(), retypePenultime2.getNext().getClazz(), (SourceElement<B>) cloneN));
                    return retype2;
            }
        }

        switch (e.getStrategy()) {
            case BASE_ELEMENT:
                return CompositeBaseElement.createCompositeBaseElement(((BaseElement<A>) cloneE).getE(), cloneE.getAlias(), (BaseElement<B>) cloneN);

            case SOURCE_ELEMENT:
                return (Element<A>) CompositeSourceElement.createCompositeSourceElement(((SourceElement<A>) cloneE).getIterable(), cloneE.getAlias(),
                        cloneE.getClazz(), (SourceElement<B>) cloneN);
        }
        return null;

    }
}
