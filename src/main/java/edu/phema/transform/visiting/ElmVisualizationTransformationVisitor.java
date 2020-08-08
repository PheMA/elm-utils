package edu.phema.transform.visiting;

import edu.phema.visiting.ElmBaseStatementPostOrderTransformationVisitor;
import org.hl7.elm.r1.*;

public class ElmVisualizationTransformationVisitor extends ElmBaseStatementPostOrderTransformationVisitor<Void, ElmVisualizationTransformationContext> {
    public ElmVisualizationTransformationVisitor() {
        super(true);
    }

    public String getLabel(Expression elm) {
        if (elm instanceof Literal) {
            return ((Literal) elm).getValue();
        } else if (elm instanceof Query) {
            return "Query";
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
          return String.format("%s %s", elm.getClass().getSimpleName(), ((As) elm).getAsType());
        } else if (elm instanceof ExpressionRef) {
          return ((ExpressionRef) elm).getName();
        } else {
            return elm.getClass().getSimpleName();
        }
    }

    @Override
    public Void visitQuery(Query elm, ElmVisualizationTransformationContext context) {
      String label = getLabel(elm);
      context.addChild(elm.getTrackerId().hashCode(), label, elm.getTrackbacks());
      super.visitQuery(elm, context);
      return null;
    }

    @Override
    public Void visitAliasedQuerySource(AliasedQuerySource elm, ElmVisualizationTransformationContext context) {
      context.addChild(elm.getTrackerId().hashCode(), elm.getAlias(), elm.getTrackbacks());
      super.visitAliasedQuerySource(elm, context);
      return null;
    }

    @Override
    public Void visitRelationshipClause(RelationshipClause elm, ElmVisualizationTransformationContext context) {
      String label = elm.getClass().getSimpleName();
      String alias = elm.getAlias();
      if (alias != null && !alias.trim().equals("")) {
        label = String.format("%s %s", label, alias);
      }
      context.addChild(elm.getTrackerId().hashCode(), label, elm.getTrackbacks());
      super.visitRelationshipClause(elm, context);
      return null;
    }

    @Override
    public Void visitElement(Element elm, ElmVisualizationTransformationContext context) {
      context.addChild(elm.getTrackerId().hashCode(), elm.toString(), elm.getTrackbacks());
      super.visitElement(elm, context);
      return null;
    }

    @Override
    public Void visitExpression(Expression elm, ElmVisualizationTransformationContext context) {
        if (elm == null) {
          return null;
        }

        String label = getLabel(elm);

        context.addChild(elm.getTrackerId().hashCode(), label, elm.getTrackbacks());

        super.visitExpression(elm, context);

        return null;
    }

}
