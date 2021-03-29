package edu.phema.quantify.visiting;

import edu.phema.quantify.ElmQuantities;
import edu.phema.visiting.ElmBaseStatementPostOrderTransformationContext;
import org.hl7.elm.r1.ExpressionDef;
import org.hl7.elm.r1.Library;

public class ElmQuantifyContext extends ElmBaseStatementPostOrderTransformationContext {
  private Library library;
  private ElmQuantities quantities;

  public ElmQuantifyContext(Library library) {
    this.library = library;
    this.quantities = new ElmQuantities();
  }

  public Library getLibrary() {
    return this.library;
  }

  public ElmQuantities getQuantities() {
    return this.quantities;
  }
}
