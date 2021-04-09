package edu.phema.quantify;

import edu.phema.phenotype.Phenotype;
import edu.phema.quantify.visiting.ElmQuantifyContext;
import edu.phema.quantify.visiting.ElmQuantifyVisitor;
import org.hl7.elm.r1.IncludeDef;
import org.hl7.elm.r1.Library;

public class PhenotypeQuantifier {
  private Phenotype phenotype;

  public PhenotypeQuantifier(Phenotype phenotype) {
    this.phenotype = phenotype;
  }


  public ElmQuantities visitDefinitions(Library library, Phenotype phenotype,
                                        ElmQuantifyVisitor visitor, ElmQuantifyContext context) throws ElmQuantifierException {

    // usings
    if (library.getUsings() != null) {
      library.getUsings().getDef().forEach(u -> visitor.visitUsingDef(u, context));
    }

    // includes
    if (library.getIncludes() != null) {
      for (IncludeDef i : library.getIncludes().getDef()) {
        Library lib = phenotype.getLibrary(i.getPath());

        visitDefinitions(lib, phenotype, visitor, context);
      }
    }

    // code systems
    if (library.getCodeSystems() != null) {
      library.getCodeSystems().getDef().forEach(cs -> visitor.visitCodeSystemDef(cs, context));
    }

    // value sets
    if (library.getValueSets() != null) {
      library.getValueSets().getDef().forEach(vs -> visitor.visitValueSetDef(vs, context));
    }

    // codes
    if (library.getCodes() != null) {
      library.getCodes().getDef().forEach(c -> visitor.visitCodeDef(c, context));
    }

    // concepts
    if (library.getConcepts() != null) {
      library.getConcepts().getDef().forEach(c -> visitor.visitConceptDef(c, context));
    }

    return context.getQuantities();
  }

  public ElmQuantities quantify() throws ElmQuantifierException {
    ElmQuantifyContext context = new ElmQuantifyContext(phenotype);

    ElmQuantifyVisitor visitor = new ElmQuantifyVisitor(true);

    // Visit the entry point
    visitor.visitExpressionDef(phenotype.getEntryPointExpressionDef(), context);

    // Visit the definitions
    visitDefinitions(phenotype.getEntryPoint(), phenotype, visitor, context);

    return context.getQuantities();
  }

//  public
}