package edu.phema.quantify;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import edu.phema.phenotype.Phenotype;
import edu.phema.quantify.visiting.ElmQuantifyContext;
import edu.phema.quantify.visiting.ElmQuantifyVisitor;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hl7.elm.r1.IncludeDef;
import org.hl7.elm.r1.Library;
import org.hl7.fhir.r4.model.Bundle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class PhenotypeQuantifier {
  private Phenotype phenotype;

  public PhenotypeQuantifier(Phenotype phenotype) {
    this.phenotype = phenotype;
  }


  public static ElmQuantities visitDefinitions(Library library, Phenotype phenotype,
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

        visitor.visitIncludeDef(i, context);
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

  public static void main(String[] args) throws Exception {
    Logger.getRootLogger().setLevel(Level.OFF);

    if (args.length != 1) {
      System.out.println("Usage: java edu.phema.quantify.PhenotypeQuantifier <phenotype bundle path>");
    } else {
      FhirContext ctx = FhirContext.forR4();

      IParser parser = ctx.newJsonParser();

      Bundle bundle = parser.parseResource(Bundle.class, new FileInputStream(args[0]));

      Phenotype phenotype = new Phenotype(bundle);

      ElmQuantifyContext context = new ElmQuantifyContext(phenotype);

      ElmQuantifyVisitor visitor = new ElmQuantifyVisitor(false);

      // Visit the entry point
      visitor.visitExpressionDef(phenotype.getEntryPointExpressionDef(), context);

      // Visit the definitions
      visitDefinitions(phenotype.getEntryPoint(), phenotype, visitor, context);

      System.out.println(context.getQuantities().getJson());
    }
  }
}