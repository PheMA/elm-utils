package edu.phema.transform.visiting;

import edu.phema.visiting.ElmBaseStatementPostOrderTransformationVisitor;
import org.hl7.elm.r1.*;

public class ElmGraphTransformationVisitor extends ElmBaseStatementPostOrderTransformationVisitor<Void, ElmGraphTransformationContext> {
    public ElmGraphTransformationVisitor() {
        super(true);
    }

    public String buildLabelForQuery(Query query) {
        return "Query";
    }

    public String getLabel(Expression elm) {
        if (elm instanceof Literal) {
            return ((Literal) elm).getValue();
        } else if (elm instanceof Query) {
            return buildLabelForQuery((Query) elm);
        } else if (elm instanceof Retrieve) {
          return String.format("%s in '%s'", ((Retrieve) elm).getDataType().getLocalPart(), ((ValueSetRef) ((Retrieve) elm).getCodes()).getName());
        } else if (elm instanceof Property) {
          Property property = (Property)elm;
          String scopeName = property.getScope();
          if (scopeName == null) {
            Expression source = property.getSource();
            if (source != null && source instanceof ExpressionRef) {
              scopeName = ((ExpressionRef)source).getName();
            }
          }

          return String.format("%s.%s", scopeName, property.getPath());
        } else if (elm instanceof As) {
          return String.format("%s %s", elm.getClass().getSimpleName(), ((As)elm).getAsType());
        } else {
            return elm.getClass().getSimpleName();
        }
    }

    @Override
    public Void visitQuery(Query elm, ElmGraphTransformationContext context) {
      String label = getLabel(elm);
      context.addChild(elm.getTrackerId().hashCode(), label);
      super.visitQuery(elm, context);
      return null;
    }

    @Override
    public Void visitRelationshipClause(RelationshipClause elm, ElmGraphTransformationContext context) {
      context.addChild(elm.getTrackerId().hashCode(), elm.getClass().getSimpleName());
      super.visitRelationshipClause(elm, context);
      return null;
    }

    @Override
    public Void visitElement(Element elm, ElmGraphTransformationContext context) {
      context.addChild(elm.getTrackerId().hashCode(), elm.toString());
      super.visitElement(elm, context);
      return null;
    }

    @Override
    public Void visitExpression(Expression elm, ElmGraphTransformationContext context) {
        if (elm == null) {
          return null;
        }

        String label = getLabel(elm);

        context.addChild(elm.getTrackerId().hashCode(), label);

        super.visitExpression(elm, context);

        return null;
    }

}
