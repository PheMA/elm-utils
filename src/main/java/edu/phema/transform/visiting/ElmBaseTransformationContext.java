package edu.phema.transform.visiting;

import edu.phema.visiting.ElmBaseStatementPostOrderTransformationContext;
import org.hl7.elm.r1.ExpressionDef;
import org.hl7.elm.r1.Library;

public class ElmBaseTransformationContext extends ElmBaseStatementPostOrderTransformationContext {
    private Library library;
    private ExpressionDef result;

    public ElmBaseTransformationContext(Library library, ExpressionDef source) {
        this.library = library;
        this.result = source;
    }

    public Library getLibrary() {
        return this.library;
    }

    public ExpressionDef getResult() {
        return this.result;
    }
}
