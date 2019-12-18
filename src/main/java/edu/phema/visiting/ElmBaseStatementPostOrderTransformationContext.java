package edu.phema.visiting;

import org.hl7.elm.r1.Element;

import java.util.Stack;

public class ElmBaseStatementPostOrderTransformationContext {
    public static class ElmParent {
        private int operandIndex;
        private Element source;

        public ElmParent(Element source, int operandIndex) {
            this.source = source;
            this.operandIndex = operandIndex;
        }

        public int getOperandIndex() {
            return operandIndex;
        }

        public Element getSource() {
            return source;
        }
    }

    protected Stack<ElmParent> parent;

    public ElmBaseStatementPostOrderTransformationContext() {
        this.parent = new Stack<>();
    }

    public ElmParent pushParent(Element elm, int index) {
        return this.parent.push(new ElmParent(elm, index));
    }

    public ElmParent pushParent(Element elm) {
        return this.parent.push(new ElmParent(elm, -1));
    }

    public ElmParent popParent() {
        return this.parent.pop();
    }

    public ElmParent peekParent() {
        return this.parent.peek();
    }
}
