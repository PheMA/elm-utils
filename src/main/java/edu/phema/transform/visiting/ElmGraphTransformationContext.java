package edu.phema.transform.visiting;

import edu.phema.graph.PhemaPhenotypeNode;
import edu.phema.visiting.ElmBaseStatementPostOrderTransformationContext;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.io.ComponentNameProvider;
import org.jgrapht.io.DOTExporter;
import org.jgrapht.io.GraphExporter;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Stack;

public class ElmGraphTransformationContext extends ElmBaseStatementPostOrderTransformationContext {
    private SimpleGraph<PhemaPhenotypeNode, DefaultEdge> graph;
    private Stack<PhemaPhenotypeNode> stack;

    public ElmGraphTransformationContext(int id, String name) {
        this.graph = new SimpleGraph<>(DefaultEdge.class);

        PhemaPhenotypeNode root = new PhemaPhenotypeNode(id, name);
        this.graph.addVertex(root);

        this.stack = new Stack<>();
        this.stack.push(root);
    }

    public PhemaPhenotypeNode getCurrentRoot() {
        return this.stack.peek();
    }

    public PhemaPhenotypeNode addChild(int id, String name) {
        PhemaPhenotypeNode child = new PhemaPhenotypeNode(id, name);

        this.graph.addVertex(child);
        this.graph.addEdge(this.stack.peek(), child);

        return child;
    }

    public PhemaPhenotypeNode push(int id, String name) {
        PhemaPhenotypeNode vertex = new PhemaPhenotypeNode(id, name);

        this.graph.addVertex(vertex);

        return this.stack.push(vertex);
    }

    public PhemaPhenotypeNode push(int id, String name, PhemaPhenotypeNode dst) {
        PhemaPhenotypeNode src = this.push(id, name);

        this.graph.addEdge(src, dst);

        return src;
    }

    public PhemaPhenotypeNode pop() {
        return this.stack.pop();
    }

//    public void addEdge(String src, String dst) {
//        this.graph.addEdge(new PhemaPhenotypeNode(src), new PhemaPhenotypeNode(src));
//    }

    public SimpleGraph getGraph() {
        return this.graph;
    }

    public String getDOTGraph() throws Exception {
        ComponentNameProvider<PhemaPhenotypeNode> vertexIdProvider = new ComponentNameProvider<PhemaPhenotypeNode>() {
            public String getName(PhemaPhenotypeNode vertex) {
                return "" + vertex.getId();
            }
        };

        ComponentNameProvider<PhemaPhenotypeNode> vertexLabelProvider = new ComponentNameProvider<PhemaPhenotypeNode>() {
            public String getName(PhemaPhenotypeNode vertex) {
                return vertex.getName();
            }
        };

        GraphExporter<PhemaPhenotypeNode, DefaultEdge> exporter = new DOTExporter<>(vertexIdProvider, vertexLabelProvider, null);
        Writer writer = new StringWriter();

        exporter.exportGraph(this.graph, writer);

        return writer.toString();
    }
}
