package edu.phema.transform.visiting;

import edu.phema.visiting.ElmBaseStatementPostOrderTransformationVisitor;
import org.hl7.elm.r1.Expression;
import org.hl7.elm.r1.ExpressionDef;
import org.hl7.elm.r1.ExpressionRef;

import java.util.Optional;

public class ElmReferenceResolvingVisitor extends ElmBaseStatementPostOrderTransformationVisitor<Void, ElmReferenceResolvingContext> {
    public ElmReferenceResolvingVisitor() {
        super(false);
    }

    public ElmReferenceResolvingVisitor(boolean debug) {
        super(debug);
    }

    private ExpressionDef resolveReference(ExpressionRef expressionRef, ElmReferenceResolvingContext context) {
        Optional<ExpressionDef> referencedExpressionDef = context
            .getLibrary()
            .getStatements()
            .getDef()
            .stream()
            .filter(ed -> ed.getName().equals(expressionRef.getName()))
            .findFirst();

        Expression target = referencedExpressionDef.map(ExpressionDef::getExpression).orElse(null);

        // FIXME: Figure out how to fail
        context.addMap(target);

        return referencedExpressionDef.orElse(null);
    }

    @Override
    public Void visitExpression(Expression elm, ElmReferenceResolvingContext context) {
        if (elm instanceof ExpressionRef) {
            ExpressionDef target = this.resolveReference((ExpressionRef) elm, context);
            return super.visitExpressionDef(target, context);
        }

        return super.visitExpression(elm, context);
    }
}
