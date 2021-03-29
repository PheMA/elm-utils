package edu.phema.quantify;

import edu.phema.quantify.visiting.ElmQuantifyContext;
import edu.phema.quantify.visiting.ElmQuantifyVisitor;
import edu.phema.transform.visiting.ElmReferenceResolvingContext;
import edu.phema.transform.visiting.ElmReferenceResolvingVisitor;
import edu.phema.util.ElmUtil;
import edu.phema.util.ElmUtilException;
import org.hl7.elm.r1.ExpressionDef;
import org.hl7.elm.r1.Library;

public class ElmQuantifier {
  private ElmUtil elmUtil;
  
  public ElmQuantifier() {
    this.elmUtil = new ElmUtil();
  }

  public ElmQuantities quantify(Library library, ExpressionDef expressionDef, boolean debug) {
    ElmQuantifyContext context = new ElmQuantifyContext(library);

    ElmQuantifyVisitor visitor = new ElmQuantifyVisitor(debug);

    visitor.visitExpressionDef(expressionDef, context);

    return context.getQuantities();
  }

  public ElmQuantities quantify(Library library, String statementName) throws ElmUtilException {
    ExpressionDef expressionDef = elmUtil.getStatementByName(library, statementName);

    return quantify(library, expressionDef, false);
  }

  public ElmQuantities quantify(Library library, String statementName, boolean debug) throws ElmUtilException {
    ExpressionDef expressionDef = elmUtil.getStatementByName(library, statementName);

    return quantify(library, expressionDef, debug);
  }
}
