package edu.phema.transform.visiting;

import edu.phema.visiting.ElmBaseStatementPostOrderTransformationVisitor;
import org.hl7.elm.r1.*;

public class ElmGraphTransformationVisitor extends ElmBaseStatementPostOrderTransformationVisitor<Void, ElmGraphTransformationContext> {
    public ElmGraphTransformationVisitor() {
        super(true);
    }

    public String buildLabelForQuery(Query query) {
        return "QQQ";
    }

    public String getLabel(Expression elm) {
        if (elm instanceof Literal) {
            return ((Literal) elm).getValue();
        } else if (elm instanceof Query) {
            return buildLabelForQuery((Query) elm);
        } else if (elm instanceof Retrieve) {
            return String.format("%s in '%s'", ((Retrieve) elm).getDataType().getLocalPart(), ((ValueSetRef) ((Retrieve) elm).getCodes()).getName());
        } else {
            return elm.getClass().getSimpleName();
        }
    }

    @Override
    public Void visitExpression(Expression elm, ElmGraphTransformationContext context) {
        String label = getLabel(elm);

        context.addChild(elm.getTrackerId().hashCode(), label);

        super.visitExpression(elm, context);

        return null;
    }

}
