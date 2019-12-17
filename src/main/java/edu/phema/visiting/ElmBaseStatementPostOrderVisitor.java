package edu.phema.visiting;

import org.cqframework.cql.elm.visiting.ElmBaseLibraryVisitor;
import org.cqframework.cql.elm.visiting.ElmLibraryVisitor;
import org.hl7.elm.r1.*;

public class ElmBaseStatementPostOrderVisitor<T, C> extends ElmBaseLibraryVisitor<T, C> implements ElmLibraryVisitor<T, C> {
    @Override
    public T visitUnaryExpression(UnaryExpression elm, C context) {
        super.visitExpression(elm.getOperand(), context);
        return super.visitUnaryExpression(elm, context);
    }

    @Override
    public T visitBinaryExpression(BinaryExpression elm, C context) {
        for (Expression expression : elm.getOperand()) {
            super.visitExpression(expression, context);
        }
        return super.visitBinaryExpression(elm, context);
    }

    @Override
    public T visitTernaryExpression(TernaryExpression elm, C context) {
        for (Expression expression : elm.getOperand()) {
            super.visitExpression(expression, context);
        }
        return super.visitTernaryExpression(elm, context);
    }

    @Override
    public T visitNaryExpression(NaryExpression elm, C context) {
        for (Expression expression : elm.getOperand()) {
            super.visitExpression(expression, context);
        }
        return super.visitNaryExpression(elm, context);
    }
}
