package edu.phema.transform;

import edu.phema.graph.PhemaPhenotypeNode;
import edu.phema.transform.visiting.ElmReferenceResolvingVisitor;
import edu.phema.transform.visiting.ElmTransformationContext;
import org.hl7.elm.r1.ExpressionDef;
import org.hl7.elm.r1.Library;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;

public class ElmTransformer {

    /**
     * Given an ELM library, and a specific expression definition within that library, resolved references be inlining
     * expression references. The result is an ELM tree will all references already resolved.
     *
     * @param expressionDef The express to use as the root of the tree
     * @return
     */
    public ExpressionDef resolveReferences(Library library, ExpressionDef expressionDef) throws ElmTransformerException {
        ElmTransformationContext context = new ElmTransformationContext(library, expressionDef);

        ElmReferenceResolvingVisitor visitor = new ElmReferenceResolvingVisitor(true);

        return (ExpressionDef) visitor.visitExpressionDef(expressionDef, context);
    }

    /**
     * Translates and ELM subtree into a JGraphT graph
     *
     * @param expressionDef The expression definition
     * @return
     * @throws ElmTransformerException
     */
    public DefaultUndirectedGraph<PhemaPhenotypeNode, DefaultEdge> getGraph(ExpressionDef expressionDef) throws ElmTransformerException {
        return null;
    }
}
