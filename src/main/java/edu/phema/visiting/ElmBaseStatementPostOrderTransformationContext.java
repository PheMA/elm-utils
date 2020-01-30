package edu.phema.visiting;

import org.hl7.elm.r1.Element;

import java.util.Stack;

public class ElmBaseStatementPostOrderTransformationContext {

    public static class StackNode {
        private int operandIndex;
        private Element element;

        public StackNode(Element element, int operandIndex) {
            this.element = element;
            this.operandIndex = operandIndex;
        }

        public StackNode(Element element) {
            this.element = element;
            this.operandIndex = -1;
        }

        public int getOperandIndex() {
            return operandIndex;
        }

        public Element getSource() {
            return element;
        }
    }

    private Stack<Object> stack;

    public ElmBaseStatementPostOrderTransformationContext() {
        this.stack = new Stack<>();
    }

    public Object push(Element element, int index) {
        return this.stack.push(new StackNode(element, index));
    }

    public Object push(Element element) {
        return this.stack.push(new StackNode(element));
    }

    public Object pop() {
        return this.stack.pop();
    }

    public Object peek() {
        return this.stack.peek();
    }
}
