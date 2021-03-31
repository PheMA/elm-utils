package edu.phema.quantify;

import edu.phema.phenotype.Phenotype;
import edu.phema.quantify.visiting.ElmQuantifyContext;
import edu.phema.quantify.visiting.ElmQuantifyVisitor;

public class PhenotypeQuantifier {
  private Phenotype phenotype;

  public PhenotypeQuantifier(Phenotype phenotype) {
    this.phenotype = phenotype;
  }

  public ElmQuantities quantify() throws ElmQuantifierException {
    ElmQuantifyContext context = new ElmQuantifyContext(phenotype);

    ElmQuantifyVisitor visitor = new ElmQuantifyVisitor(true);

    visitor.visitExpressionDef(phenotype.getEntryPointExpressionDef(), context);

    return context.getQuantities();
  }
}