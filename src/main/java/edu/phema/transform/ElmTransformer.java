package edu.phema.transform;

import edu.phema.graph.PhemaPhenotypeNode;
import edu.phema.transform.visiting.*;
import org.hl7.elm.r1.ExpressionDef;
import org.hl7.elm.r1.Library;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

public class ElmTransformer {

    /**
     * Given an ELM library, and a specific expression definition within that library, resolve references by inlining
     * expression references. The result is an ELM tree with all references already resolved.
     *
     * @param library The library from which all expressions and references can be found
     * @param expressionDef The expression to use as the root of the tree
     * @return
     */
    public void resolveReferences(Library library, ExpressionDef expressionDef) throws ElmTransformerException {
        ElmReferenceResolvingContext context = new ElmReferenceResolvingContext(library, expressionDef);

        ElmReferenceResolvingVisitor visitor = new ElmReferenceResolvingVisitor();

        visitor.visitExpressionDef(expressionDef, context);

        context.resolveReferences();
    }

    /**
     * Translates and ELM subtree into a JGraphT graph
     *
     * @param expressionDef The expression definition
     * @return
     * @throws ElmTransformerException
     */
    public SimpleGraph<PhemaPhenotypeNode, DefaultEdge> getGraph(Library library, ExpressionDef expressionDef) throws ElmTransformerException {
        ElmGraphTransformationContext context = new ElmGraphTransformationContext(expressionDef.hashCode(), expressionDef.getName());

        ElmGraphTransformationVisitor visitor = new ElmGraphTransformationVisitor();

        this.resolveReferences(library, expressionDef);

        visitor.visitExpression(expressionDef.getExpression(), context);

        return context.getGraph();
    }

    public String getDOTGraph(Library library, ExpressionDef expressionDef) throws ElmTransformerException {
        ElmGraphTransformationContext context = new ElmGraphTransformationContext(expressionDef.hashCode(), expressionDef.getName());

        ElmGraphTransformationVisitor visitor = new ElmGraphTransformationVisitor();

        this.resolveReferences(library, expressionDef);

        visitor.visitExpression(expressionDef.getExpression(), context);

        try {
            return context.getDOTGraph();
        } catch (Exception e) {
            throw new ElmTransformerException("Failed to generate DOT graph", e);
        }
    }
}
