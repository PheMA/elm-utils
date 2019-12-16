package edu.phema.visiting;

import org.cqframework.cql.elm.visiting.ElmBaseLibraryVisitor;
import org.cqframework.cql.elm.visiting.ElmLibraryVisitor;
import org.hl7.elm.r1.BinaryExpression;
import org.hl7.elm.r1.Expression;

public class ElmBaseStatementPostOrderVisitor<T, C> extends ElmBaseLibraryVisitor<T, C> implements ElmLibraryVisitor<T, C> {
    @Override
    public T visitBinaryExpression(BinaryExpression elm, C context) {
        for(Expression expression : elm.getOperand()) {
            super.visitExpression(expression, context);
        }
        return super.visitBinaryExpression(elm, context);
    }
}
