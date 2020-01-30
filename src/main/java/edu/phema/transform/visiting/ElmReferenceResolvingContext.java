package edu.phema.transform.visiting;

import edu.phema.transform.ElmTransformerException;
import org.hl7.elm.r1.*;

import java.util.HashMap;
import java.util.Map;

public class ElmReferenceResolvingContext extends ElmBaseTransformationContext {
    private Map<StackNode, Element> referenceMap;

    public ElmReferenceResolvingContext(Library library, ExpressionDef source) {
        super(library, source);
        this.referenceMap = new HashMap<>();
    }

    public void addMap(Expression target) {
        this.referenceMap.put((StackNode) peek(), target);
    }

    public void resolveReferences() throws ElmTransformerException {
        for (StackNode ref : referenceMap.keySet()) {
            Element source = ref.getSource();
            Expression target = (Expression) referenceMap.get(ref);

            if (source instanceof UnaryExpression) {
                ((UnaryExpression) source).setOperand(target);
            } else if (source instanceof BinaryExpression) {
                ((BinaryExpression) source).getOperand().set(ref.getOperandIndex(), target);
            } else if (source instanceof TernaryExpression) {
                ((TernaryExpression) source).getOperand().set(ref.getOperandIndex(), target);
            } else if (source instanceof NaryExpression) {
                ((NaryExpression) source).getOperand().set(ref.getOperandIndex(), target);
            } else if (source instanceof ExpressionDef) {
                ((ExpressionDef) source).setExpression(target);
            } else if (source instanceof AggregateExpression) {
                ((AggregateExpression) source).setSource(target);
            } else {
                throw new ElmTransformerException(String.format("Cannot resolve reference for source type: %s", source.getClass().getSimpleName()));
            }
        }
    }
}
