package edu.phema.transform.visiting;

import org.hl7.elm.r1.ExpressionDef;
import org.hl7.elm.r1.Library;

public class ElmTransformationContext {
    private Library library;
    private ExpressionDef result;

    public ElmTransformationContext(Library library, ExpressionDef source) {
        this.library = library;
        this.result = source;
    }

    public Library getLibrary() {
        return this.library;
    }

    public ExpressionDef getResult() { return this.result; }
}
