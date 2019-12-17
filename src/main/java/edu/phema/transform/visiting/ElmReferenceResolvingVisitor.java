package edu.phema.transform.visiting;

import edu.phema.visiting.ElmBaseStatementPostOrderTransformationVisitor;
import org.hl7.elm.r1.Element;
import org.hl7.elm.r1.Expression;
import org.hl7.elm.r1.ExpressionDef;
import org.hl7.elm.r1.ExpressionRef;

import java.util.Optional;

public class ElmReferenceResolvingVisitor extends ElmBaseStatementPostOrderTransformationVisitor<ElmTransformationContext> {
    private Element resolveReference(ExpressionRef expressionRef, ElmTransformationContext context) {
        Optional<Expression> referencedExpression = context
            .getLibrary()
            .getStatements()
            .getDef()
            .stream()
            .filter(ed -> ed.getName().equals(expressionRef.getName()))
            .map(ExpressionDef::getExpression)
            .findFirst();

        // FIXME: Figure out how to fail
        return referencedExpression.orElse(null);
    }

    @Override
    public Element visitExpression(Expression elm, ElmTransformationContext context) {
        if (elm instanceof ExpressionRef) {
            return this.resolveReference((ExpressionRef) elm, context);
        } else {
            return super.visitExpression(elm, context);
        }
    }
}
