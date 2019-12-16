package edu.phema.transform.visiting;

import edu.phema.visiting.ElmBaseStatementPostOrderVisitor;
import org.hl7.elm.r1.ExpressionRef;

public class ElmTransformationVisitor extends ElmBaseStatementPostOrderVisitor<Void, ElmTransformationContext> {

    @Override
    public Void visitExpressionRef(ExpressionRef elm, ElmTransformationContext context) {
        System.out.println("Visiting: " + elm.getName());

        return null;
    }

}
