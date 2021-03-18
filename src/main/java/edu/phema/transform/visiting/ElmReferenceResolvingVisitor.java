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

  private Expression resolveReference(ExpressionRef expressionRef, ElmReferenceResolvingContext context) {
    Optional<ExpressionDef> referencedExpressionDef = context
      .getLibrary()
      .getStatements()
      .getDef()
      .stream()
      .filter(ed -> ed.getName().equals(expressionRef.getName()))
      .findFirst();

    // This just returns the Ref if the actual expression isn't found
    // Need to figure out a way to check included libraries
    Expression target = referencedExpressionDef.map(ExpressionDef::getExpression).orElse(expressionRef);

    // FIXME: Figure out how to fail
    context.addMap(target);

    return target;
  }

  @Override
  public Void visitExpression(Expression elm, ElmReferenceResolvingContext context) {
    if (elm instanceof ExpressionRef) {
      Expression target = this.resolveReference((ExpressionRef) elm, context);

      return super.visitExpression(target, context);
    }

    return super.visitExpression(elm, context);
  }
}
