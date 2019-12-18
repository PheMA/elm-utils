package edu.phema.transform.visiting;

import edu.phema.graph.PhemaPhenotypeNode;
import edu.phema.visiting.ElmBaseStatementPostOrderTransformationVisitor;
import org.hl7.elm.r1.BinaryExpression;
import org.hl7.elm.r1.Element;
import org.hl7.elm.r1.Expression;
import org.hl7.elm.r1.Literal;

import java.util.ArrayList;

public class ElmGraphTransformationVisitor extends ElmBaseStatementPostOrderTransformationVisitor<PhemaPhenotypeNode, ElmGraphTransformationContext> {
    public ElmGraphTransformationVisitor() {
        super(true);
    }

    public String getLabel(Expression elm) {
        if (elm instanceof Literal) {
            return ((Literal) elm).getValue();
        } else {
            return elm.getClass().getSimpleName();
        }
    }

    @Override
    public PhemaPhenotypeNode visitExpression(Expression elm, ElmGraphTransformationContext context) {
        String label = getLabel(elm);

        context.addChild(elm.getTrackerId().hashCode(), label);

        return super.visitExpression(elm, context);
    }

    @Override
    public PhemaPhenotypeNode visitBinaryExpression(BinaryExpression elm, ElmGraphTransformationContext context) {
//        String label = getLabel(elm);
//
//        context.push(elm.getTrackerId().hashCode(), label);
//
//        ArrayList<Expression> transformedOps = new ArrayList<>();
//        for (Expression expression : elm.getOperand()) {
//            transformedOps.add((Expression) this.visitExpression(expression, context));
//        }
//        elm.getOperand().clear();
//        elm.withOperand(transformedOps);
//
//        context.pop();

        return super.visitBinaryExpression(elm, context);
    }
}
