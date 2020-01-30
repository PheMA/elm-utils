package edu.phema.transform.visiting;

import edu.phema.graph.PhemaPhenotypeNode;
import edu.phema.visiting.ElmBaseStatementPostOrderTransformationContext;
import org.hl7.elm.r1.Element;
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

    public void printStack() {
        System.out.print("[ ");

        stack.stream().forEach(node -> System.out.print(node.getName() + ", "));

        print(" ]");
    }

    protected void print(String message) {
        System.out.println(message);
    }

    public ElmGraphTransformationContext(int id, String name) {
        this.graph = new SimpleGraph<>(DefaultEdge.class);

        PhemaPhenotypeNode root = new PhemaPhenotypeNode(id, name);
        this.graph.addVertex(root);

        this.stack = new Stack<>();
        this.stack.push(root);
    }

    public PhemaPhenotypeNode addChild(int id, String name) {
        PhemaPhenotypeNode child = new PhemaPhenotypeNode(id, name);

        this.graph.addVertex(child);

        PhemaPhenotypeNode parent = this.stack.peek();
        if (!this.graph.containsVertex(parent)) {
            this.graph.addVertex(parent);
        }

        this.graph.addEdge(parent, child);

        return child;
    }

    @Override
    public Object push(Element elm, int index) {
        PhemaPhenotypeNode node = new PhemaPhenotypeNode(elm.getTrackerId().hashCode(), elm.getClass().getSimpleName());

        return stack.push(node);
    }

    @Override
    public Object push(Element elm) {
        PhemaPhenotypeNode node = new PhemaPhenotypeNode(elm.getTrackerId().hashCode(), elm.getClass().getSimpleName());

        return stack.push(node);
    }

    @Override
    public Object pop() {
        return stack.pop();
    }

    @Override
    public Object peek() {
        return stack.peek();
    }

    public SimpleGraph<PhemaPhenotypeNode, DefaultEdge> getGraph() {
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
