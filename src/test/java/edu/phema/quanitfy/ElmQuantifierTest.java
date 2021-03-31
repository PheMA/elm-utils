package edu.phema.quanitfy;

import edu.phema.quantify.ElmQuantifier;
import edu.phema.quantify.ElmQuantities;
import edu.phema.transform.ElmTransformer;
import edu.phema.util.ElmUtil;
import org.cqframework.cql.cql2elm.CqlTranslator;
import org.cqframework.cql.cql2elm.LibraryManager;
import org.cqframework.cql.cql2elm.ModelManager;
import org.hl7.elm.r1.ExpressionDef;
import org.hl7.elm.r1.Library;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ElmQuantifierTest {
  private CqlTranslator translator;
  private ModelManager modelManager;
  private Library library;

  @BeforeEach
  public void setup() throws Exception {
    modelManager = new ModelManager();
  }

  public void runTest(String libraryFileName, String entryPoint) throws Exception {
    translator = CqlTranslator.fromStream(this.getClass().getClassLoader().getResourceAsStream(libraryFileName), modelManager, new LibraryManager(modelManager));
    library = translator.toELM();

    ElmQuantifier quantifier = new ElmQuantifier();

    ElmQuantities quantities = quantifier.quantify(library, entryPoint, true);

    System.out.println(quantities.getJson());
  }

  @Test
  public void simple() throws Exception {
    runTest("cql/quantify/quantify-simple.cql", "simplest");
  }

  @Test
  public void simpleBoolean() throws Exception {
    runTest("cql/quantify/quantify-simple.cql", "simpleBoolean");
  }

  @Test
  public void simpleCall() throws Exception {
    runTest("cql/quantify/quantify-simple.cql", "callingStatement");
  }

  @Test
  public void simpleRetrieve() throws Exception {
    runTest("cql/quantify/quantify-simple.cql", "SimpleRetrieve");
  }

  @Test
  public void simpleDateInterval() throws Exception {
    runTest("cql/quantify/quantify-simple.cql", "SimpleDateInterval");
  }

  @Test
  public void simpleAg() throws Exception {
    runTest("cql/quantify/quantify-simple.cql", "SimpleAg");
  }
}
