package edu.phema.quantify.visiting;

import edu.phema.phenotype.Phenotype;
import edu.phema.quantify.ElmQuantifierException;
import edu.phema.quantify.ElmQuantities;
import edu.phema.visiting.ElmBaseStatementPostOrderTransformationContext;
import org.hl7.elm.r1.ExpressionDef;
import org.hl7.elm.r1.FunctionDef;
import org.hl7.elm.r1.Library;
import org.hl7.fhir.Bundle;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.Stack;

public class ElmQuantifyContext extends ElmBaseStatementPostOrderTransformationContext {
  public Phenotype getPhenotype() {
    return phenotype;
  }

  public void setPhenotype(Phenotype phenotype) {
    this.phenotype = phenotype;
  }

  private Stack<String> currentLibrary;

  public void pushLibrary(String library) {
    currentLibrary.push(library);
  }

  public void popLibrary() {
    currentLibrary.pop();
  }

  public String currentLibrary() {
    return currentLibrary.peek();
  }

  public Set<String> visitedDefs;
  public Set<String> visitedExprs;

  // Store the full phenotype bundle so we can assess all the artifacts
  private Phenotype phenotype;

  private Library library;
  private ElmQuantities quantities;

  public void setLibrary(Library library) {
    this.library = library;
  }

  public void setQuantities(ElmQuantities quantities) {
    this.quantities = quantities;
  }

  public ExpressionDef getExpressionDef(String name) throws ElmQuantifierException {
    Optional<ExpressionDef> mayExprDef = this.library.getStatements().getDef().stream()
      .filter(s -> s.getName().equals(name))
      .findFirst();

    if (!mayExprDef.isPresent()) {
      throw new ElmQuantifierException("Could not find statement with name: " + name);
    }

    return mayExprDef.get();
  }

  public FunctionDef getFunctionDef(String name) throws ElmQuantifierException {
    Optional<FunctionDef> mayFuncDef = this.library.getStatements().getDef().stream()
      .filter(s -> s.getName().equals(name))
      .filter(e -> e instanceof FunctionDef)
      .map(e -> (FunctionDef) e)
      .findFirst();

    if (!mayFuncDef.isPresent()) {
      throw new ElmQuantifierException("Could not find function with name: " + name);
    }

    return mayFuncDef.get();
  }

  public ElmQuantifyContext(Library library) {
    this.library = library;
    this.quantities = new ElmQuantities();
    this.visitedDefs = new HashSet<>();
    this.visitedExprs = new HashSet<>();
  }

  public ElmQuantifyContext(Library library, Phenotype phenotype) throws ElmQuantifierException {
    this.library = library;
    this.phenotype = phenotype;

    this.quantities = new ElmQuantities();

    this.currentLibrary = new Stack<>();

    this.currentLibrary.push(phenotype.getEntryPointLibraryName());
    this.visitedDefs = new HashSet<>();
    this.visitedExprs = new HashSet<>();
  }

  public ElmQuantifyContext(Phenotype phenotype) throws ElmQuantifierException {
    this(phenotype.getEntryPoint(), phenotype);
  }

  public Library getLibrary() {
    return this.library;
  }

  public ElmQuantities getQuantities() {
    return this.quantities;
  }
}
