package edu.phema.transform;

import edu.phema.util.ElmUtil;
import org.cqframework.cql.cql2elm.CqlTranslator;
import org.cqframework.cql.cql2elm.LibraryManager;
import org.cqframework.cql.cql2elm.ModelManager;
import org.hl7.elm.r1.ExpressionDef;
import org.hl7.elm.r1.Library;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ElmTransformerTest {
    private CqlTranslator translator;
    private Library library;

    private ElmUtil elmUtil;

    @BeforeEach
    public void setup() throws Exception {
        ModelManager modelManager = new ModelManager();
        translator = CqlTranslator.fromStream(this.getClass().getClassLoader().getResourceAsStream("cql/elm-transformer-test.cql"), modelManager, new LibraryManager(modelManager));
        library = translator.toELM();

        elmUtil = new ElmUtil();
    }

    @Test
    public void simple() throws Exception {
        ElmTransformer transformer = new ElmTransformer();

        ExpressionDef statement = elmUtil.getStatementByName(library, "HF Case");

        transformer.resolveReferences(library, statement);

        System.out.println("hello");
    }

    @Test
    public void testUnaryExpression() throws Exception {
//        ElmTransformer transformer = new ElmTransformer();
//
//        ExpressionDef statement = elmUtil.getStatementByName(library, "Exists from referenced expression");
//
//        transformer.resolveReferences(library, statement);
//
//        System.out.println("goodbye");
    }

    @Test
    public void simpleGraph() throws Exception {
        ElmTransformer transformer = new ElmTransformer();

        ExpressionDef statement = elmUtil.getStatementByName(library, "HF Case");

//        SimpleGraph graph = transformer.getGraph(library, statement);

        String graph = transformer.getDOTGraph(library, statement);

        System.out.println(graph);
    }
}
