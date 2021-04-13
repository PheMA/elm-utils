package edu.phema.quantify.visiting;

import edu.phema.quantify.ElmQuantifierException;
import edu.phema.quantify.ElmQuantities;
import org.cqframework.cql.elm.tracking.Trackable;
import org.cqframework.cql.elm.visiting.ElmBaseLibraryVisitor;
import org.hl7.cql.model.DataType;
import org.hl7.elm.r1.*;

import java.util.Collection;

/**
 * Documentation found here: http://build.fhir.org/ig/HL7/cql/04-logicalspecification.html
 */
public class ElmQuantifyVisitor extends ElmBaseLibraryVisitor<Void, ElmQuantifyContext> {
  private boolean debug;

  public ElmQuantifyVisitor() {
    super();
  }

  public ElmQuantifyVisitor(boolean debug) {
    this.debug = debug;
  }

  //<editor-fold desc="LOGGING FUNCTIONS">
  /////////////////////////////////////////////////////////////////////////////
  //
  // LOGGING FUNCTIONS
  //
  /////////////////////////////////////////////////////////////////////////////

  protected void debug(Object o) {
    if (debug) {
      System.out.printf("Visiting: %s%n", o.getClass().getName());
    }
  }

  protected void debug(String method) {
    if (debug) {
      System.out.printf("In method: %s%n", method);
    }
  }

  protected void debug(String method, Object o) {
    if (debug) {
      System.out.printf("In method: %s, visiting: %s%n", method, o.getClass().getName());
    }
  }

  public void debug_msg(String msg) {
    if (debug) {
      System.out.println(msg);
    }
  }

  protected void warn(String s) {
    System.out.println("WARNING: " + s);
  }
  //</editor-fold>

  //<editor-fold desc="MEASURE DERIVATION FUNCTIONS">
  /////////////////////////////////////////////////////////////////////////////
  //
  // MEASURE DERIVATION FUNCTIONS
  //
  /////////////////////////////////////////////////////////////////////////////

  protected DataType getType(Trackable el) {
    return el.getResultType();
  }

  protected boolean isTemporal(Trackable el) throws ElmQuantifierException {
    DataType dataType = getType(el);

    if (dataType == null) {
      throw new ElmQuantifierException("Unable to get result type for element: " + el.getClass().getSimpleName());
    }

    // This obviously isn't perfect
    return dataType.toString().toUpperCase().contains("DATE") ||
      dataType.toString().toUpperCase().contains("TIME");
  }


  protected boolean isTemporal(OperatorExpression exp) throws ElmQuantifierException {
    // return true if any operand is temporal

    if (exp instanceof UnaryExpression) {
      return isTemporal(((UnaryExpression) exp).getOperand());
    } else if (exp instanceof BinaryExpression) {
      for (Trackable t : ((BinaryExpression) exp).getOperand()) {
        if (isTemporal(t)) {
          return true;
        }
      }
    } else if (exp instanceof BinaryExpression) {
      for (Trackable t : ((BinaryExpression) exp).getOperand()) {
        if (isTemporal(t)) {
          return true;
        }
      }
    } else if (exp instanceof TernaryExpression) {
      for (Trackable t : ((TernaryExpression) exp).getOperand()) {
        if (isTemporal(t)) {
          return true;
        }
      }
    } else if (exp instanceof NaryExpression) {
      for (Trackable t : ((NaryExpression) exp).getOperand()) {
        if (isTemporal(t)) {
          return true;
        }
      }
    }

    return false;
  }

  //</editor-fold>

  //<editor-fold desc="ABSTRACT BASE OPERATORS">
  /////////////////////////////////////////////////////////////////////////////
  //
  // ABSTRACT BASE OPERATORS
  //
  /////////////////////////////////////////////////////////////////////////////

  @Override
  public Void visitBinaryExpression(BinaryExpression elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmExpressionCounts.binaryExpression++;
    return super.visitBinaryExpression(elm, context);
  }

  @Override
  public Void visitElement(Element elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmExpressionCounts.element++;
    return super.visitElement(elm, context);
  }

  @Override
  public Void visitExpression(Expression elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmExpressionCounts.expression++;

    // Dont decent for literals, data, or modularity (expressionRef, functionRef) expressions
    // data and modularity depth counting handled separately below

    // Count and increment depth for PhEMA expressions
    if (isPhemaLogicalExpression(elm)) {
      context.getQuantities().depths.forEach(ElmQuantities.PhemaAnalysisDepths::incrementPhemaLogicalDepth);
      context.getQuantities().depths.forEach(ElmQuantities.PhemaAnalysisDepths::incrementPhemaExpressionDepth);
    } else if (isPhemaComparisonExpression(elm)) {
      context.getQuantities().depths.forEach(ElmQuantities.PhemaAnalysisDepths::incrementPhemaComparisonDepth);
      context.getQuantities().depths.forEach(ElmQuantities.PhemaAnalysisDepths::incrementPhemaExpressionDepth);
    } else if (isPhemaArithmeticExpression(elm)) {
      context.getQuantities().depths.forEach(ElmQuantities.PhemaAnalysisDepths::incrementPhemaArithmeticDepth);
      context.getQuantities().depths.forEach(ElmQuantities.PhemaAnalysisDepths::incrementPhemaExpressionDepth);
    } else if (isPhemaAggregateExpression(elm)) {
      context.getQuantities().depths.forEach(ElmQuantities.PhemaAnalysisDepths::incrementPhemaAggregateDepth);
      context.getQuantities().depths.forEach(ElmQuantities.PhemaAnalysisDepths::incrementPhemaExpressionDepth);
    } else if (isPhemaConditionalExpression(elm)) {
      context.getQuantities().depths.forEach(ElmQuantities.PhemaAnalysisDepths::incrementPhemaConditionalDepth);
      context.getQuantities().depths.forEach(ElmQuantities.PhemaAnalysisDepths::incrementPhemaExpressionDepth);
    } else if (isPhemaTemporalExpression(elm)) {
      context.getQuantities().depths.forEach(ElmQuantities.PhemaAnalysisDepths::incrementPhemaTemporalDepth);
      context.getQuantities().depths.forEach(ElmQuantities.PhemaAnalysisDepths::incrementPhemaExpressionDepth);
    } else if (isPhemaTerminologyExpression(elm)) {
      context.getQuantities().depths.forEach(ElmQuantities.PhemaAnalysisDepths::incrementPhemaTerminologyDepth);
      context.getQuantities().depths.forEach(ElmQuantities.PhemaAnalysisDepths::incrementPhemaExpressionDepth);
    } else if (isPhemaCollectionExpression(elm)) {
      context.getQuantities().depths.forEach(ElmQuantities.PhemaAnalysisDepths::incrementPhemaCollectionDepth);
      context.getQuantities().depths.forEach(ElmQuantities.PhemaAnalysisDepths::incrementPhemaExpressionDepth);
    } else {
      debug_msg("Not increasing depth for expression: " + elm.getClass().getName());
    }

    super.visitExpression(elm, context);

    if (isPhemaLogicalExpression(elm)) {
      context.getQuantities().depths.forEach(ElmQuantities.PhemaAnalysisDepths::decrementPhemaLogicalDepth);
      context.getQuantities().depths.forEach(ElmQuantities.PhemaAnalysisDepths::decrementPhemaExpressionDepth);
    } else if (isPhemaComparisonExpression(elm)) {
      context.getQuantities().depths.forEach(ElmQuantities.PhemaAnalysisDepths::decrementPhemaComparisonDepth);
      context.getQuantities().depths.forEach(ElmQuantities.PhemaAnalysisDepths::decrementPhemaExpressionDepth);
    } else if (isPhemaArithmeticExpression(elm)) {
      context.getQuantities().depths.forEach(ElmQuantities.PhemaAnalysisDepths::decrementPhemaArithmeticDepth);
      context.getQuantities().depths.forEach(ElmQuantities.PhemaAnalysisDepths::decrementPhemaExpressionDepth);
    } else if (isPhemaAggregateExpression(elm)) {
      context.getQuantities().depths.forEach(ElmQuantities.PhemaAnalysisDepths::decrementPhemaAggregateDepth);
      context.getQuantities().depths.forEach(ElmQuantities.PhemaAnalysisDepths::decrementPhemaExpressionDepth);
    } else if (isPhemaConditionalExpression(elm)) {
      context.getQuantities().depths.forEach(ElmQuantities.PhemaAnalysisDepths::decrementPhemaConditionalDepth);
      context.getQuantities().depths.forEach(ElmQuantities.PhemaAnalysisDepths::decrementPhemaExpressionDepth);
    } else if (isPhemaTemporalExpression(elm)) {
      context.getQuantities().depths.forEach(ElmQuantities.PhemaAnalysisDepths::decrementPhemaTemporalDepth);
      context.getQuantities().depths.forEach(ElmQuantities.PhemaAnalysisDepths::decrementPhemaExpressionDepth);
    } else if (isPhemaTerminologyExpression(elm)) {
      context.getQuantities().depths.forEach(ElmQuantities.PhemaAnalysisDepths::decrementPhemaTerminologyDepth);
      context.getQuantities().depths.forEach(ElmQuantities.PhemaAnalysisDepths::decrementPhemaExpressionDepth);
    } else if (isPhemaCollectionExpression(elm)) {
      context.getQuantities().depths.forEach(ElmQuantities.PhemaAnalysisDepths::decrementPhemaCollectionDepth);
      context.getQuantities().depths.forEach(ElmQuantities.PhemaAnalysisDepths::decrementPhemaExpressionDepth);
    }

    return null;
  }

  @Override
  public Void visitOperatorExpression(OperatorExpression elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmExpressionCounts.operatorExpression++;
    return super.visitOperatorExpression(elm, context);
  }

  @Override
  public Void visitUnaryExpression(UnaryExpression elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmExpressionCounts.unaryExpression++;
    return super.visitUnaryExpression(elm, context);
  }

  @Override
  public Void visitTernaryExpression(TernaryExpression elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmExpressionCounts.ternaryExpression++;
    return super.visitTernaryExpression(elm, context);
  }

  @Override
  public Void visitNaryExpression(NaryExpression elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmExpressionCounts.naryExpression++;
    return super.visitNaryExpression(elm, context);
  }

  @Override
  public Void visitTypeSpecifier(TypeSpecifier elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmExpressionCounts.typeSpecifier++;
    return super.visitTypeSpecifier(elm, context);
  }

  @Override
  public Void visitAggregateExpression(AggregateExpression elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmExpressionCounts.aggregateExpression++;
    return super.visitAggregateExpression(elm, context);
  }
  //</editor-fold>

  //<editor-fold desc="1. SIMPLE VALUES">
  /////////////////////////////////////////////////////////////////////////////
  //
  // 1. SIMPLE VALUES
  //
  /////////////////////////////////////////////////////////////////////////////

  @Override
  public Void visitLiteral(Literal elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmLiteralCounts.literal++;

    // PhEMA counts
    context.getQuantities().dimensions.phemaLiteralCounts.total++;
    String type = elm.getValueType().getLocalPart();
    context.getQuantities().dimensions.phemaLiteralCounts.types.add(type);

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitLiteral(elm, context);
  }
  //</editor-fold>

  //<editor-fold desc="2. STRUCTURED VALUES">
  /////////////////////////////////////////////////////////////////////////////
  //
  // 2. STRUCTURED VALUES
  //
  /////////////////////////////////////////////////////////////////////////////

  @Override
  public Void visitTuple(Tuple elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmLiteralCounts.tuple++;

    // PhEMA Counts
    context.getQuantities().dimensions.phemaLiteralCounts.total++;
    context.getQuantities().dimensions.phemaLiteralCounts.types.add("Tuple");

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitTuple(elm, context);
  }

  @Override
  public Void visitTupleElement(TupleElement elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmLiteralCounts.tupleElement++;
    return super.visitTupleElement(elm, context);
  }

  @Override
  public Void visitTupleElementDefinition(TupleElementDefinition elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmLiteralCounts.tupleElementDefinition++;
    return super.visitTupleElementDefinition(elm, context);
  }

  @Override
  public Void visitInstance(Instance elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmLiteralCounts.instance++;

    // PhEMA Counts
    context.getQuantities().dimensions.phemaLiteralCounts.total++;
    context.getQuantities().dimensions.phemaLiteralCounts.types.add("Instance");

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitInstance(elm, context);
  }

  @Override
  public Void visitInstanceElement(InstanceElement elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmLiteralCounts.instanceElement++;
    return super.visitInstanceElement(elm, context);
  }

  @Override
  public Void visitProperty(Property elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmLiteralCounts.property++;

    // PhEMA Counts
    context.getQuantities().dimensions.phemaDataCounts.property++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitProperty(elm, context);
  }

  @Override
  public Void visitSearch(Search elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmLiteralCounts.search++;
    return super.visitSearch(elm, context);
  }
  //</editor-fold>

  //<editor-fold desc="3. CLINICAL VALUES">
  /////////////////////////////////////////////////////////////////////////////
  //
  // 3. CLINICAL VALUES
  //
  /////////////////////////////////////////////////////////////////////////////

  @Override
  public Void visitCodeSystemDef(CodeSystemDef elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmClinicalValueCounts.codeSystemDef++;

    // PhEMA Counts
    context.getQuantities().dimensions.phemaTerminologyCounts.codeSystemDef++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitCodeSystemDef(elm, context);
  }

  @Override
  public Void visitValueSetDef(ValueSetDef elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmClinicalValueCounts.valueSetDef++;

    // PhEMA Counts
    context.getQuantities().dimensions.phemaTerminologyCounts.valueSetDef++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitValueSetDef(elm, context);
  }

  @Override
  public Void visitCodeSystemRef(CodeSystemRef elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmClinicalValueCounts.codeSystemRef++;

    // PhEMA Counts
    context.getQuantities().dimensions.phemaTerminologyCounts.codeSystemRef++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitCodeSystemRef(elm, context);
  }

  @Override
  public Void visitValueSetRef(ValueSetRef elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmClinicalValueCounts.valueSetRef++;

    // PhEMA Counts
    try {
      // Generate some metrics based on the value set
      String libraryName;

      if (elm.getLibraryName() == null) {
        libraryName = context.currentLibrary();
      } else {
        libraryName = elm.getLibraryName();
      }

      // Only process each unique value set once
      if (!context.getQuantities().dimensions.phemaTerminologyCounts.uniqueValueSets.contains(libraryName + ":" + elm.getName())) {
        // Total count
        int totalCodeCount = context.getPhenotype().countCodesInValueSet(elm.getName(), libraryName);
        context.getQuantities().dimensions.phemaTerminologyCounts.codes += totalCodeCount;

        // Count per value set
        context.getQuantities().dimensions.phemaTerminologyCounts.perValueSetCounts.add(totalCodeCount);

        // Count per system
        Collection<String> systems = context.getPhenotype().getAllSystemsInValueSet(elm.getName(), libraryName);

        for (String system : systems) {
          int systemTotal = context.getPhenotype().countCodesForSystemInValueSet(system, elm.getName(), libraryName);

          if (context.getQuantities().dimensions.phemaTerminologyCounts.perSystemCounts.containsKey(system)) {
            int runningTotal = context.getQuantities().dimensions.phemaTerminologyCounts.perSystemCounts.get(system);

            context.getQuantities().dimensions.phemaTerminologyCounts.perSystemCounts.put(system, runningTotal + systemTotal);
          } else {
            context.getQuantities().dimensions.phemaTerminologyCounts.perSystemCounts.put(system, systemTotal);
          }
        }

        context.getQuantities().dimensions.phemaTerminologyCounts.uniqueValueSets.add(libraryName + ":" + elm.getName());
      }
    } catch (Exception e) {
      warn(e.getMessage());
    }

    context.getQuantities().dimensions.phemaTerminologyCounts.valueSetRef++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitValueSetRef(elm, context);
  }

  @Override
  public Void visitCode(Code elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmClinicalValueCounts.code++;

    // PhEMA Counts
    context.getQuantities().dimensions.phemaLiteralCounts.total++;
    context.getQuantities().dimensions.phemaLiteralCounts.types.add("Code");

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitCode(elm, context);
  }

  @Override
  public Void visitConcept(Concept elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmClinicalValueCounts.concept++;

    // PhEMA Counts
    context.getQuantities().dimensions.phemaLiteralCounts.total++;
    context.getQuantities().dimensions.phemaLiteralCounts.types.add("Code");

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitConcept(elm, context);
  }

  @Override
  public Void visitQuantity(Quantity elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmClinicalValueCounts.quantity++;

    // PhEMA Counts
    context.getQuantities().dimensions.phemaLiteralCounts.total++;
    context.getQuantities().dimensions.phemaLiteralCounts.types.add("Quantity (" + elm.getUnit() + ")");

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitQuantity(elm, context);
  }

  @Override
  public Void visitRatio(Ratio elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmClinicalValueCounts.ratio++;

    // PhEMA Counts
    context.getQuantities().dimensions.phemaLiteralCounts.total++;
    context.getQuantities().dimensions.phemaLiteralCounts.types.add("Ratio (" + elm.getNumerator().getUnit() + "/" + elm.getDenominator().getUnit() + ")");

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitRatio(elm, context);
  }

  @Override
  public Void visitCodeDef(CodeDef elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmClinicalValueCounts.codeDef++;

    // PhEMA Counts
    context.getQuantities().dimensions.phemaTerminologyCounts.codeDef++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitCodeDef(elm, context);
  }

  @Override
  public Void visitConceptDef(ConceptDef elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmClinicalValueCounts.conceptDef++;

    // PhEMA Counts
    context.getQuantities().dimensions.phemaTerminologyCounts.conceptDef++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitConceptDef(elm, context);
  }

  @Override
  public Void visitCodeRef(CodeRef elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmClinicalValueCounts.codeRef++;

    // PhEMA Counts
    context.getQuantities().dimensions.phemaTerminologyCounts.codeRef++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitCodeRef(elm, context);
  }

  @Override
  public Void visitConceptRef(ConceptRef elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmClinicalValueCounts.conceptRef++;

    // PhEMA Counts
    context.getQuantities().dimensions.phemaTerminologyCounts.conceptRef++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitConceptRef(elm, context);
  }
  //</editor-fold>

  //<editor-fold desc="4. TYPE SPECIFIERS">
  /////////////////////////////////////////////////////////////////////////////
  //
  // 4. TYPE SPECIFIERS
  //
  /////////////////////////////////////////////////////////////////////////////

  @Override
  public Void visitNamedTypeSpecifier(NamedTypeSpecifier elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTypeSpecifierCounts.namedTypeSpecifier++;
    return super.visitNamedTypeSpecifier(elm, context);
  }

  @Override
  public Void visitIntervalTypeSpecifier(IntervalTypeSpecifier elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTypeSpecifierCounts.intervalTypeSpecifier++;
    return super.visitIntervalTypeSpecifier(elm, context);
  }

  @Override
  public Void visitListTypeSpecifier(ListTypeSpecifier elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTypeSpecifierCounts.listTypeSpecifier++;
    return super.visitListTypeSpecifier(elm, context);
  }

  @Override
  public Void visitTupleTypeSpecifier(TupleTypeSpecifier elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTypeSpecifierCounts.tupleTypeSpecifier++;
    return super.visitTupleTypeSpecifier(elm, context);
  }

  @Override
  public Void visitChoiceTypeSpecifier(ChoiceTypeSpecifier elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTypeSpecifierCounts.choiceTypeSpecifier++;
    return super.visitChoiceTypeSpecifier(elm, context);
  }
  //</editor-fold>

  //<editor-fold desc="5/6. LIBRARIES + DATA MODEL">
  /////////////////////////////////////////////////////////////////////////////
  //
  // 5/6. LIBRARIES + DATA MODEL
  //
  /////////////////////////////////////////////////////////////////////////////

  @Override
  public Void visitLibrary(Library elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmReuseCounts.library++;
    return super.visitLibrary(elm, context);
  }

  @Override
  public Void visitUsingDef(UsingDef elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmReuseCounts.usingDef++;

    // PhEMA Counts
    context.getQuantities().dimensions.phemaDataCounts.dataModels.add(elm.getLocalIdentifier());

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitUsingDef(elm, context);
  }

  @Override
  public Void visitIncludeDef(IncludeDef elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmReuseCounts.includeDef++;

    // PhEMA Counts
    context.getQuantities().dimensions.phemaModularityCounts.includes.add(elm.getPath());

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitIncludeDef(elm, context);
  }

  @Override
  public Void visitContextDef(ContextDef elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmReuseCounts.contextDef++;
    return super.visitContextDef(elm, context);
  }
  //</editor-fold>

  //<editor-fold desc="7. PARAMETERS">
  /////////////////////////////////////////////////////////////////////////////
  //
  // 7. PARAMETERS
  //
  /////////////////////////////////////////////////////////////////////////////

  @Override
  public Void visitParameterDef(ParameterDef elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmReuseCounts.parameterDef++;
    return super.visitParameterDef(elm, context);
  }

  @Override
  public Void visitParameterRef(ParameterRef elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmReuseCounts.parameterRef++;
    return super.visitParameterRef(elm, context);
  }
  //</editor-fold>

  //<editor-fold desc="9. REUSING LOGIC">
  /////////////////////////////////////////////////////////////////////////////
  //
  // 9. REUSING LOGIC
  //
  /////////////////////////////////////////////////////////////////////////////

  @Override
  public Void visitExpressionDef(ExpressionDef elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmReuseCounts.expressionDef++;

    // PhEMA counts
    context.getQuantities().dimensions.phemaModularityCounts.statementDef++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitExpressionDef(elm, context);
  }

  @Override
  public Void visitFunctionDef(FunctionDef elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmReuseCounts.functionDef++;

    // PhEMA counts
    context.getQuantities().dimensions.phemaModularityCounts.functionDef++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitFunctionDef(elm, context);
  }

  @Override
  public Void visitFunctionRef(FunctionRef elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmReuseCounts.functionRef++;

    // Calling a function

    try {
      // Push the current library ID here so we can know where to
      // find refs later that have a libraryName of null
      if (elm.getLibraryName() != null) {
        String libId = context.getPhenotype().getLibraryIdFromName(elm.getLibraryName(), context.currentLibrary());

        context.pushLibrary(libId);
        context.getQuantities().dimensions.phemaModularityCounts.externalFunctionCalls++;
      } else {
        context.getQuantities().dimensions.phemaModularityCounts.localFunctionCalls++;
      }

      FunctionDef def;

      if (context.getPhenotype() != null) {
        def = context.getPhenotype().getFunctionDef(context.currentLibrary(), elm.getName());
      } else {
        def = context.getFunctionDef(elm.getName());
      }

      // increase depths
      context.getQuantities().depths.forEach(ElmQuantities.PhemaAnalysisDepths::incrementPhemaModularityDepth);
      context.getQuantities().depths.forEach(ElmQuantities.PhemaAnalysisDepths::incrementPhemaExpressionDepth);

      visitFunctionDef(def, context);

      // decrease depths
      context.getQuantities().depths.forEach(ElmQuantities.PhemaAnalysisDepths::decrementPhemaModularityDepth);
      context.getQuantities().depths.forEach(ElmQuantities.PhemaAnalysisDepths::decrementPhemaExpressionDepth);

      if (elm.getLibraryName() != null) {
        context.popLibrary();
      }
    } catch (Exception e) {
      warn(e.getMessage());
    }

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitFunctionRef(elm, context);
  }

  @Override
  public Void visitOperandDef(OperandDef elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmReuseCounts.operandDef++;
    // Used in function signature
    return super.visitOperandDef(elm, context);
  }

  @Override
  public Void visitExpressionRef(ExpressionRef elm, ElmQuantifyContext context) {
    if (elm instanceof FunctionRef) {
      visitFunctionRef((FunctionRef) elm, context);
    } else {

      // ELM Counts
      context.getQuantities().elmReuseCounts.expressionRef++;

      // Calling another define statement

      try {
        // Push the current library ID here so we can know where to
        // find refs later that have a libraryName of null
        if (elm.getLibraryName() != null) {
          context.pushLibrary(elm.getLibraryName());
          context.getQuantities().dimensions.phemaModularityCounts.externalStatementCalls++;
        } else {
          context.getQuantities().dimensions.phemaModularityCounts.localStatementCalls++;
        }

        // increase depths
        context.getQuantities().depths.forEach(ElmQuantities.PhemaAnalysisDepths::incrementPhemaModularityDepth);
        context.getQuantities().depths.forEach(ElmQuantities.PhemaAnalysisDepths::incrementPhemaExpressionDepth);

        ExpressionDef def;


        if (context.getPhenotype() != null) {
          def = context.getPhenotype().getExpressionDef(context.currentLibrary(), elm.getName());
        } else {
          def = context.getExpressionDef(elm.getName());
        }

        visitExpressionDef(def, context);

        // decrease depths
        context.getQuantities().depths.forEach(ElmQuantities.PhemaAnalysisDepths::decrementPhemaModularityDepth);
        context.getQuantities().depths.forEach(ElmQuantities.PhemaAnalysisDepths::decrementPhemaExpressionDepth);

        if (elm.getLibraryName() != null) {
          context.popLibrary();
        }
      } catch (Exception e) {
        warn(e.getMessage());
      }
    }

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return null;
  }

  @Override
  public Void visitOperandRef(OperandRef elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmReuseCounts.operandRef++;
    // Referring to a function argument
    return super.visitOperandRef(elm, context);
  }

  @Override
  public Void visitIdentifierRef(IdentifierRef elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmReuseCounts.identifierRef++;
    // Referring to an identifier
    return super.visitIdentifierRef(elm, context);
  }

  @Override
  public Void visitAccessModifier(AccessModifier elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmReuseCounts.accessModifier++;
    return super.visitAccessModifier(elm, context);
  }
  //</editor-fold>

  //<editor-fold desc="10/11. QUERIES + RETRIEVE">
  /////////////////////////////////////////////////////////////////////////////
  //
  // 10/11. QUERIES + RETRIEVE
  //
  /////////////////////////////////////////////////////////////////////////////

  @Override
  public Void visitAliasedQuerySource(AliasedQuerySource elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmQueryCounts.aliasedQuerySource++;
    return super.visitAliasedQuerySource(elm, context);
  }

  @Override
  public Void visitLetClause(LetClause elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmQueryCounts.letClause++;
    return super.visitLetClause(elm, context);
  }

  @Override
  public Void visitRelationshipClause(RelationshipClause elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmQueryCounts.relationshipClause++;
    return super.visitRelationshipClause(elm, context);
  }

  @Override
  public Void visitWith(With elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmQueryCounts.with++;
    return super.visitWith(elm, context);
  }

  @Override
  public Void visitWithout(Without elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmQueryCounts.without++;
    return super.visitWithout(elm, context);
  }

  @Override
  public Void visitSortByItem(SortByItem elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmQueryCounts.sortByItem++;

    // PhEMA Counts
    context.getQuantities().dimensions.phemaDataCounts.sort++;

    // Keep track of sort clause complexity
    int preSort = context.getQuantities().dimensions.phemaModularityCounts.expression;
    context.getQuantities().pushDepthTracker();

    if (elm instanceof ByDirection) {
      visitByDirection((ByDirection) elm, context);
    } else if (elm instanceof ByColumn) {
      visitByColumn((ByColumn) elm, context);
    } else if (elm instanceof ByExpression) {
      visitByExpression((ByExpression) elm, context);
    }

    ElmQuantities.PhemaAnalysisDepths depths = context.getQuantities().popDepthTracker();
    int postSort = context.getQuantities().dimensions.phemaModularityCounts.expression;

    context.getQuantities().dimensions.phemaDataCounts.recordSortClauseMaxDepth(depths.phemaExpressionMaxDepth);
    context.getQuantities().dimensions.phemaDataCounts.recordSortClauseExpressionCount(postSort - preSort);

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return null;
  }

  @Override
  public Void visitByDirection(ByDirection elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmQueryCounts.byDirection++;
    return super.visitByDirection(elm, context);
  }

  @Override
  public Void visitByColumn(ByColumn elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmQueryCounts.byColumn++;
    return super.visitByColumn(elm, context);
  }

  @Override
  public Void visitByExpression(ByExpression elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmQueryCounts.byExpression++;
    return super.visitByExpression(elm, context);
  }

  @Override
  public Void visitSortClause(SortClause elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmQueryCounts.sortClause++;
    return super.visitSortClause(elm, context);
  }

  @Override
  public Void visitReturnClause(ReturnClause elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmQueryCounts.returnClause++;
    return super.visitReturnClause(elm, context);
  }

  @Override
  public Void visitQuery(Query elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmQueryCounts.query++;

    // Fully overriding this method so we have more control
    for (AliasedQuerySource source : elm.getSource()) {
      visitElement(source, context);
    }
    if (elm.getLet() != null && !elm.getLet().isEmpty()) {
      elm.getLet().stream().forEach(let -> visitElement(let, context));
    }
    if (elm.getRelationship() != null && !elm.getRelationship().isEmpty()) {
      elm.getRelationship().stream().forEach(relationship -> visitElement(relationship, context));
    }
    if (elm.getWhere() != null) {

      int preWhere = context.getQuantities().dimensions.phemaModularityCounts.expression;
      context.getQuantities().pushDepthTracker();

      visitElement(elm.getWhere(), context);

      ElmQuantities.PhemaAnalysisDepths depths = context.getQuantities().popDepthTracker();
      int postWhere = context.getQuantities().dimensions.phemaModularityCounts.expression;

      context.getQuantities().dimensions.phemaDataCounts.recordWhereClauseMaxDepth(depths.phemaExpressionMaxDepth);
      context.getQuantities().dimensions.phemaDataCounts.recordWhereClauseExpressionCount(postWhere - preWhere);
    }
    if (elm.getReturn() != null) {
      visitElement(elm.getReturn(), context);
    }
    if (elm.getAggregate() != null) {
      visitElement(elm.getAggregate(), context);
    }
    if (elm.getSort() != null) {
      visitElement(elm.getSort(), context);
    }

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return null;
  }

  @Override
  public Void visitAliasRef(AliasRef elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmQueryCounts.aliasRef++;
    return super.visitAliasRef(elm, context);
  }

  @Override
  public Void visitQueryLetRef(QueryLetRef elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmQueryCounts.queryLetRef++;
    return super.visitQueryLetRef(elm, context);
  }

  @Override
  public Void visitRetrieve(Retrieve elm, ElmQuantifyContext context) {
    if (elm.getCodes() != null) {
      if (elm.getCodes() instanceof ValueSetRef) {
        debug("visit Retrieve: " + elm.getDataType().toString() + " : " + ((ValueSetRef) elm.getCodes()).getName());
      } else {
        debug("visit Retrieve: " + elm.getDataType().toString() + " : " + elm.getCodes());
      }
    } else {
      debug("visit Retrieve: " + elm.getDataType().toString() + " : (no codes)");
    }

    // ELM Counts
    context.getQuantities().elmQueryCounts.retrieve++;

    // PhEMA Counts
    context.getQuantities().dimensions.phemaDataCounts.retrieve++;
    context.getQuantities().dimensions.phemaDataCounts.dataSources.add(elm.getDataType().toString());

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitRetrieve(elm, context);
  }

  @Override
  public Void visitCodeFilterElement(CodeFilterElement elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmQueryCounts.codeFilterElement++;
    return super.visitCodeFilterElement(elm, context);
  }

  @Override
  public Void visitDateFilterElement(DateFilterElement elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmQueryCounts.dateFilterElement++;
    return super.visitDateFilterElement(elm, context);
  }

  @Override
  public Void visitOtherFilterElement(OtherFilterElement elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmQueryCounts.otherFilterElement++;
    return super.visitOtherFilterElement(elm, context);
  }

  @Override
  public Void visitIncludeElement(IncludeElement elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmQueryCounts.includeElement++;
    return super.visitIncludeElement(elm, context);
  }

  @Override
  public Void visitAggregateClause(AggregateClause elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmQueryCounts.aggregateClause++;

    // PhEMA Counts
    context.getQuantities().dimensions.phemaDataCounts.aggregate++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitAggregateClause(elm, context);
  }
  //</editor-fold>

  //<editor-fold desc="12. COMPARISON OPERATORS">
  /////////////////////////////////////////////////////////////////////////////
  //
  // 12. COMPARISON OPERATORS
  //
  /////////////////////////////////////////////////////////////////////////////

  @Override
  public Void visitEqual(Equal elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmComparisonCounts.equal++;

    // PhEMA counts
    context.getQuantities().dimensions.phemaComparisonCounts.equal++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitEqual(elm, context);
  }

  @Override
  public Void visitEquivalent(Equivalent elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmComparisonCounts.equivalent++;

    // PhEMA counts
    context.getQuantities().dimensions.phemaComparisonCounts.equivalent++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitEquivalent(elm, context);
  }

  @Override
  public Void visitNotEqual(NotEqual elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmComparisonCounts.notEqual++;

    // PhEMA counts
    context.getQuantities().dimensions.phemaComparisonCounts.notEqual++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitNotEqual(elm, context);
  }

  @Override
  public Void visitLess(Less elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmComparisonCounts.less++;

    // PhEMA counts
    context.getQuantities().dimensions.phemaComparisonCounts.less++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitLess(elm, context);
  }

  @Override
  public Void visitGreater(Greater elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmComparisonCounts.greater++;

    // PhEMA counts
    context.getQuantities().dimensions.phemaComparisonCounts.greater++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitGreater(elm, context);
  }

  @Override
  public Void visitLessOrEqual(LessOrEqual elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmComparisonCounts.lessOrEqual++;

    // PhEMA counts
    context.getQuantities().dimensions.phemaComparisonCounts.lessOrEqual++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitLessOrEqual(elm, context);
  }

  @Override
  public Void visitGreaterOrEqual(GreaterOrEqual elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmComparisonCounts.greaterOrEqual++;

    // PhEMA counts
    context.getQuantities().dimensions.phemaComparisonCounts.greaterOrEqual++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitGreaterOrEqual(elm, context);
  }
  //</editor-fold>

  //<editor-fold desc="13. LOGICAL OPERATORS">
  /////////////////////////////////////////////////////////////////////////////
  //
  // 13. LOGICAL OPERATORS
  //
  /////////////////////////////////////////////////////////////////////////////

  @Override
  public Void visitAnd(And elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmLogicalCounts.and++;

    // PhEMA counts
    context.getQuantities().dimensions.phemaLogicalCounts.and++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitAnd(elm, context);
  }

  @Override
  public Void visitOr(Or elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmLogicalCounts.or++;

    // PhEMA counts
    context.getQuantities().dimensions.phemaLogicalCounts.or++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitOr(elm, context);
  }

  @Override
  public Void visitXor(Xor elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmLogicalCounts.xor++;

    // PhEMA counts
    context.getQuantities().dimensions.phemaLogicalCounts.xor++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitXor(elm, context);
  }

  @Override
  public Void visitNot(Not elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmLogicalCounts.not++;

    // PhEMA counts
    context.getQuantities().dimensions.phemaLogicalCounts.not++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitNot(elm, context);
  }

  @Override
  public Void visitImplies(Implies elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmLogicalCounts.implies++;

    // PhEMA counts
    context.getQuantities().dimensions.phemaLogicalCounts.implies++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitImplies(elm, context);
  }
  //</editor-fold>

  //<editor-fold desc="14. NULLOLOGICAL OPERATORS">
  /////////////////////////////////////////////////////////////////////////////
  //
  // 14. NULLOLOGICAL OPERATORS
  //
  /////////////////////////////////////////////////////////////////////////////

  @Override
  public Void visitNull(Null elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmNullCounts._null++;

    // This is a typed null used for internal type consistency

    return super.visitNull(elm, context);
  }

  @Override
  public Void visitIsNull(IsNull elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmNullCounts.isNull++;

    // PhEMA counts
    // treat as "= null"
    context.getQuantities().dimensions.phemaComparisonCounts.equal++;
    context.getQuantities().dimensions.phemaLiteralCounts.total++;
    context.getQuantities().dimensions.phemaLiteralCounts.types.add("null");

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitIsNull(elm, context);
  }

  @Override
  public Void visitIsTrue(IsTrue elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmNullCounts.isTrue++;

    // PhEMA counts
    // treat as "= true"
    context.getQuantities().dimensions.phemaComparisonCounts.equal++;
    context.getQuantities().dimensions.phemaLiteralCounts.total++;
    context.getQuantities().dimensions.phemaLiteralCounts.types.add("Boolean");

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitIsTrue(elm, context);
  }

  @Override
  public Void visitIsFalse(IsFalse elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmNullCounts.isFalse++;

    // PhEMA counts
    // treat as "= false"
    context.getQuantities().dimensions.phemaComparisonCounts.equal++;
    context.getQuantities().dimensions.phemaLiteralCounts.total++;
    context.getQuantities().dimensions.phemaLiteralCounts.types.add("Boolean");

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitIsFalse(elm, context);
  }

  @Override
  public Void visitCoalesce(Coalesce elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmNullCounts.coalesce++;

    // PhEMA counts
    // treat as conditional (i.e. if x = null, then if y = null, then z)
    context.getQuantities().dimensions.phemaConditionalCounts.coalesce++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitCoalesce(elm, context);
  }
  //</editor-fold>

  //<editor-fold desc="15. CONDITIONAL OPERATORS">
  /////////////////////////////////////////////////////////////////////////////
  //
  // 15. CONDITIONAL OPERATORS
  //
  /////////////////////////////////////////////////////////////////////////////

  @Override
  public Void visitIf(If elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmConditionalCounts._if++;

    // PhEMA counts
    context.getQuantities().dimensions.phemaConditionalCounts._if++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    // I guess If isn't correctly implemented :/

    if (elm.getCondition() != null) {
      visitExpression(elm.getCondition(), context);
    }

    if (elm.getThen() != null) {
      visitExpression(elm.getThen(), context);
    }

    if (elm.getElse() != null) {
      visitExpression(elm.getElse(), context);
    }

    return null;
  }

  @Override
  public Void visitCaseItem(CaseItem elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmConditionalCounts.caseItem++;

    if (elm.getWhen() != null) {
      visitExpression(elm.getWhen(), context);
    }

    if (elm.getThen() != null) {
      visitExpression(elm.getThen(), context);
    }

    // Not implemented correctly!
    return null;
  }

  @Override
  public Void visitCase(Case elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmConditionalCounts._case++;

    // PhEMA counts
    context.getQuantities().dimensions.phemaConditionalCounts._case++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitCase(elm, context);
  }
  //</editor-fold>

  //<editor-fold desc="16. ARITHMETIC OPERATORS">
  /////////////////////////////////////////////////////////////////////////////
  //
  // 16. ARITHMETIC OPERATORS
  //
  /////////////////////////////////////////////////////////////////////////////

  @Override
  public Void visitAdd(Add elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmArithmeticCounts.add++;

    // PhEMA counts
    context.getQuantities().dimensions.phemaArithmeticCounts.add++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitAdd(elm, context);
  }

  @Override
  public Void visitSubtract(Subtract elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmArithmeticCounts.subtract++;

    // PhEMA counts
    context.getQuantities().dimensions.phemaArithmeticCounts.subtract++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;


    return super.visitSubtract(elm, context);
  }

  @Override
  public Void visitMultiply(Multiply elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmArithmeticCounts.multiply++;

    // PhEMA counts
    context.getQuantities().dimensions.phemaArithmeticCounts.multiply++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitMultiply(elm, context);
  }

  @Override
  public Void visitDivide(Divide elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmArithmeticCounts.divide++;

    // PhEMA counts
    context.getQuantities().dimensions.phemaArithmeticCounts.divide++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitDivide(elm, context);
  }

  @Override
  public Void visitTruncatedDivide(TruncatedDivide elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmArithmeticCounts.truncatedDivide++;

    // PhEMA counts
    context.getQuantities().dimensions.phemaArithmeticCounts.truncatedDivide++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitTruncatedDivide(elm, context);
  }

  @Override
  public Void visitModulo(Modulo elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmArithmeticCounts.modulo++;

    // PhEMA counts
    context.getQuantities().dimensions.phemaArithmeticCounts.modulo++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitModulo(elm, context);
  }

  @Override
  public Void visitCeiling(Ceiling elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmArithmeticCounts.ceiling++;

    // PhEMA counts
    context.getQuantities().dimensions.phemaArithmeticCounts.ceiling++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitCeiling(elm, context);
  }

  @Override
  public Void visitFloor(Floor elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmArithmeticCounts.floor++;

    // PhEMA counts
    context.getQuantities().dimensions.phemaArithmeticCounts.floor++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitFloor(elm, context);
  }

  @Override
  public Void visitTruncate(Truncate elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmArithmeticCounts.truncate++;

    // PhEMA counts
    context.getQuantities().dimensions.phemaArithmeticCounts.truncate++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitTruncate(elm, context);
  }

  @Override
  public Void visitAbs(Abs elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmArithmeticCounts.abs++;

    // PhEMA counts
    context.getQuantities().dimensions.phemaArithmeticCounts.abs++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitAbs(elm, context);
  }

  @Override
  public Void visitNegate(Negate elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmArithmeticCounts.negate++;

    // PhEMA counts
    context.getQuantities().dimensions.phemaArithmeticCounts.negate++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitNegate(elm, context);
  }

  @Override
  public Void visitRound(Round elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmArithmeticCounts.round++;

    // PhEMA counts
    context.getQuantities().dimensions.phemaArithmeticCounts.round++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitRound(elm, context);
  }

  @Override
  public Void visitLn(Ln elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmArithmeticCounts.ln++;

    // PhEMA counts
    context.getQuantities().dimensions.phemaArithmeticCounts.ln++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitLn(elm, context);
  }

  @Override
  public Void visitExp(Exp elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmArithmeticCounts.exp++;

    // PhEMA counts
    context.getQuantities().dimensions.phemaArithmeticCounts.exp++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitExp(elm, context);
  }

  @Override
  public Void visitLog(Log elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmArithmeticCounts.log++;

    // PhEMA counts
    context.getQuantities().dimensions.phemaArithmeticCounts.log++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitLog(elm, context);
  }

  @Override
  public Void visitPower(Power elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmArithmeticCounts.power++;

    // PhEMA counts
    context.getQuantities().dimensions.phemaArithmeticCounts.power++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitPower(elm, context);
  }

  @Override
  public Void visitSuccessor(Successor elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmArithmeticCounts.successor++;

    // PhEMA counts
    context.getQuantities().dimensions.phemaArithmeticCounts.successor++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitSuccessor(elm, context);
  }

  @Override
  public Void visitPredecessor(Predecessor elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmArithmeticCounts.predecessor++;

    // PhEMA counts
    context.getQuantities().dimensions.phemaArithmeticCounts.predecessor++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitPredecessor(elm, context);
  }

  @Override
  public Void visitMinValue(MinValue elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmArithmeticCounts.minValue++;

    // PhEMA counts
    context.getQuantities().dimensions.phemaArithmeticCounts.minValue++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitMinValue(elm, context);
  }

  @Override
  public Void visitMaxValue(MaxValue elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmArithmeticCounts.maxValue++;

    // PhEMA counts
    context.getQuantities().dimensions.phemaArithmeticCounts.maxValue++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitMaxValue(elm, context);
  }

  @Override
  public Void visitPrecision(Precision elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmArithmeticCounts.precision++;

    // PhEMA counts
    context.getQuantities().dimensions.phemaArithmeticCounts.precision++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitPrecision(elm, context);
  }

  @Override
  public Void visitLowBoundary(LowBoundary elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmArithmeticCounts.lowBoundary++;

    // PhEMA counts
    context.getQuantities().dimensions.phemaArithmeticCounts.lowBoundary++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitLowBoundary(elm, context);
  }

  @Override
  public Void visitHighBoundary(HighBoundary elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmArithmeticCounts.highBoundary++;

    // PhEMA counts
    context.getQuantities().dimensions.phemaArithmeticCounts.highBoundary++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitHighBoundary(elm, context);
  }

  @Override
  public Void visitTotal(Total elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmArithmeticCounts.total++;

    // PhEMA counts
    context.getQuantities().dimensions.phemaArithmeticCounts.total++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    // not sure where this belongs
    return super.visitTotal(elm, context);
  }
  //</editor-fold>

  //<editor-fold desc="17. STRING OPERATORS">
  /////////////////////////////////////////////////////////////////////////////
  //
  // 17. STRING OPERATORS (ignoring since we don't use them)
  //
  /////////////////////////////////////////////////////////////////////////////

  @Override
  public Void visitConcatenate(Concatenate elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmStringCounts.concatenate++;
    return super.visitConcatenate(elm, context);
  }

  @Override
  public Void visitCombine(Combine elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmStringCounts.combine++;
    return super.visitCombine(elm, context);
  }

  @Override
  public Void visitSplit(Split elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmStringCounts.split++;
    return super.visitSplit(elm, context);
  }

  @Override
  public Void visitLength(Length elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmStringCounts.length++;
    return super.visitLength(elm, context);
  }

  @Override
  public Void visitUpper(Upper elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmStringCounts.upper++;
    return super.visitUpper(elm, context);
  }

  @Override
  public Void visitLower(Lower elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmStringCounts.lower++;
    return super.visitLower(elm, context);
  }

  @Override
  public Void visitIndexer(Indexer elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmStringCounts.indexer++;
    return super.visitIndexer(elm, context);
  }

  @Override
  public Void visitPositionOf(PositionOf elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmStringCounts.positionOf++;
    return super.visitPositionOf(elm, context);
  }

  @Override
  public Void visitSubstring(Substring elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmStringCounts.substring++;
    return super.visitSubstring(elm, context);
  }

  @Override
  public Void visitSplitOnMatches(SplitOnMatches elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmStringCounts.splitOnMatches++;
    return super.visitSplitOnMatches(elm, context);
  }

  @Override
  public Void visitLastPositionOf(LastPositionOf elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmStringCounts.lastPositionOf++;
    return super.visitLastPositionOf(elm, context);
  }

  @Override
  public Void visitStartsWith(StartsWith elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmStringCounts.startsWith++;
    return super.visitStartsWith(elm, context);
  }

  @Override
  public Void visitEndsWith(EndsWith elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmStringCounts.endsWith++;
    return super.visitEndsWith(elm, context);
  }

  @Override
  public Void visitMatches(Matches elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmStringCounts.matches++;
    return super.visitMatches(elm, context);
  }

  @Override
  public Void visitReplaceMatches(ReplaceMatches elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmStringCounts.replaceMatches++;
    return super.visitReplaceMatches(elm, context);
  }
  //</editor-fold>

  //<editor-fold desc="18. DATE AND TIME OPERATORS">
  /////////////////////////////////////////////////////////////////////////////
  //
  // 18. DATE AND TIME OPERATORS
  //
  /////////////////////////////////////////////////////////////////////////////

  @Override
  public Void visitDurationBetween(DurationBetween elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTemporalCounts.durationBetween++;

    // PhEMA counts
    context.getQuantities().dimensions.phemaTemporalCounts.durationBetween++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitDurationBetween(elm, context);
  }

  @Override
  public Void visitDifferenceBetween(DifferenceBetween elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTemporalCounts.differenceBetween++;

    // PhEMA counts
    context.getQuantities().dimensions.phemaTemporalCounts.differenceBetween++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitDifferenceBetween(elm, context);
  }

  @Override
  public Void visitDateFrom(DateFrom elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTemporalCounts.dateFrom++;

    // Ignoring because it's basically a type conversion

    return super.visitDateFrom(elm, context);
  }

  @Override
  public Void visitTimeFrom(TimeFrom elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTemporalCounts.timeFrom++;

    // Ignoring because it's basically a type conversion

    return super.visitTimeFrom(elm, context);
  }

  @Override
  public Void visitTimezoneOffsetFrom(TimezoneOffsetFrom elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTemporalCounts.timezoneOffsetFrom++;

    // Ignoring because it's basically a type conversion

    return super.visitTimezoneOffsetFrom(elm, context);
  }

  @Override
  public Void visitDateTimeComponentFrom(DateTimeComponentFrom elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTemporalCounts.dateTimeComponentFrom++;

    // Ignoring because it's basically a type conversion

    return super.visitDateTimeComponentFrom(elm, context);
  }

  @Override
  public Void visitTimeOfDay(TimeOfDay elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTemporalCounts.timeOfDay++;

    // Ignoring because it's basically a type conversion

    return super.visitTimeOfDay(elm, context);
  }

  @Override
  public Void visitToday(Today elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTemporalCounts.today++;

    // PhEMA counts
    context.getQuantities().dimensions.phemaTemporalCounts.today++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitToday(elm, context);
  }

  @Override
  public Void visitNow(Now elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTemporalCounts.now++;

    // PhEMA counts
    context.getQuantities().dimensions.phemaTemporalCounts.now++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitNow(elm, context);
  }

  @Override
  public Void visitDateTime(DateTime elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTemporalCounts.dateTime++;

    // PhEMA Counts
    context.getQuantities().dimensions.phemaLiteralCounts.total++;
    context.getQuantities().dimensions.phemaLiteralCounts.types.add("DateTime");

    // Don't descend/evaluate subexpressions
    return null;
  }

  @Override
  public Void visitTime(Time elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTemporalCounts.time++;
    context.getQuantities().dimensions.phemaLiteralCounts.total++;
    context.getQuantities().dimensions.phemaLiteralCounts.types.add("Time");

    // Don't descend/evaluate subexpressions
    return null;
  }

  @Override
  public Void visitSameAs(SameAs elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTemporalCounts.sameAs++;

    // PhEMA counts
    context.getQuantities().dimensions.phemaTemporalCounts.sameAs++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitSameAs(elm, context);
  }

  @Override
  public Void visitSameOrBefore(SameOrBefore elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTemporalCounts.sameOrBefore++;

    // PhEMA counts
    context.getQuantities().dimensions.phemaTemporalCounts.sameOrBefore++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitSameOrBefore(elm, context);
  }

  @Override
  public Void visitSameOrAfter(SameOrAfter elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTemporalCounts.sameOrAfter++;

    // PhEMA counts
    context.getQuantities().dimensions.phemaTemporalCounts.sameOrAfter++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitSameOrAfter(elm, context);
  }

  @Override
  public Void visitTimezoneFrom(TimezoneFrom elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTemporalCounts.timezoneFrom++;

    // Ignoring because it's basically a type conversion

    return super.visitTimezoneFrom(elm, context);
  }

  @Override
  public Void visitDate(Date elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTemporalCounts.date++;
    context.getQuantities().dimensions.phemaLiteralCounts.total++;
    context.getQuantities().dimensions.phemaLiteralCounts.types.add("Date");

    // Don't descend/evaluate subexpressions
    return null;
  }
  //</editor-fold>

  //<editor-fold desc="19. INTERVAL OPERATORS">
  /////////////////////////////////////////////////////////////////////////////
  //
  // 19. INTERVAL OPERATORS
  //
  /////////////////////////////////////////////////////////////////////////////

  @Override
  public Void visitInterval(Interval elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmIntervalCounts.interval++;

    return super.visitInterval(elm, context);
  }

  @Override
  public Void visitWidth(Width elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmIntervalCounts.width++;
    return super.visitWidth(elm, context);
  }

  @Override
  public Void visitStart(Start elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmIntervalCounts.start++;

    // PhEMA Counts
    try {
      if (isTemporal(elm)) {
        context.getQuantities().dimensions.phemaTemporalCounts.start++;
      } else {
        context.getQuantities().dimensions.phemaCollectionCounts.start++;
      }
    } catch (Exception e) {
      warn(e.getMessage());
    }

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitStart(elm, context);
  }

  @Override
  public Void visitEnd(End elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmIntervalCounts.end++;

    // PhEMA Counts
    try {
      if (isTemporal(elm)) {
        context.getQuantities().dimensions.phemaTemporalCounts.end++;
      } else {
        context.getQuantities().dimensions.phemaCollectionCounts.end++;
      }
    } catch (Exception e) {
      warn(e.getMessage());
    }

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitEnd(elm, context);
  }

  @Override
  public Void visitContains(Contains elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmIntervalCounts.contains++;

    // PhEMA Counts
    try {
      if (isTemporal(elm)) {
        context.getQuantities().dimensions.phemaTemporalCounts.contains++;
      } else {
        context.getQuantities().dimensions.phemaCollectionCounts.contains++;
      }
    } catch (Exception e) {
      warn(e.getMessage());
    }

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitContains(elm, context);
  }

  @Override
  public Void visitProperContains(ProperContains elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmIntervalCounts.properContains++;

    // PhEMA Counts
    try {
      if (isTemporal(elm)) {
        context.getQuantities().dimensions.phemaTemporalCounts.properContains++;
      } else {
        context.getQuantities().dimensions.phemaCollectionCounts.properContains++;
      }
    } catch (Exception e) {
      warn(e.getMessage());
    }

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitProperContains(elm, context);
  }

  @Override
  public Void visitIn(In elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmIntervalCounts.in++;

    // PhEMA Counts
    try {
      if (isTemporal(elm)) {
        context.getQuantities().dimensions.phemaTemporalCounts.in++;
      } else {
        context.getQuantities().dimensions.phemaCollectionCounts.in++;
      }
    } catch (Exception e) {
      warn(e.getMessage());
    }

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitIn(elm, context);
  }

  @Override
  public Void visitProperIn(ProperIn elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmIntervalCounts.properIn++;

    // PhEMA Counts
    try {
      if (isTemporal(elm)) {
        context.getQuantities().dimensions.phemaTemporalCounts.properIn++;
      } else {
        context.getQuantities().dimensions.phemaCollectionCounts.properIn++;
      }
    } catch (Exception e) {
      warn(e.getMessage());
    }

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitProperIn(elm, context);
  }

  @Override
  public Void visitIncludes(Includes elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmIntervalCounts.includes++;

    // PhEMA Counts
    try {
      if (isTemporal(elm)) {
        context.getQuantities().dimensions.phemaTemporalCounts.includes++;
      } else {
        context.getQuantities().dimensions.phemaCollectionCounts.includes++;
      }
    } catch (Exception e) {
      warn(e.getMessage());
    }

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitIncludes(elm, context);
  }

  @Override
  public Void visitIncludedIn(IncludedIn elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmIntervalCounts.includedIn++;

    // PhEMA Counts
    try {
      if (isTemporal(elm)) {
        context.getQuantities().dimensions.phemaTemporalCounts.includedIn++;
      } else {
        context.getQuantities().dimensions.phemaCollectionCounts.includedIn++;
      }
    } catch (Exception e) {
      warn(e.getMessage());
    }

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitIncludedIn(elm, context);
  }

  @Override
  public Void visitProperIncludes(ProperIncludes elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmIntervalCounts.properIncludes++;

    // PhEMA Counts
    try {
      if (isTemporal(elm)) {
        context.getQuantities().dimensions.phemaTemporalCounts.properIncludes++;
      } else {
        context.getQuantities().dimensions.phemaCollectionCounts.properIncludes++;
      }
    } catch (Exception e) {
      warn(e.getMessage());
    }

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitProperIncludes(elm, context);
  }

  @Override
  public Void visitProperIncludedIn(ProperIncludedIn elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmIntervalCounts.properIncludedIn++;

    // PhEMA Counts
    try {
      if (isTemporal(elm)) {
        context.getQuantities().dimensions.phemaTemporalCounts.properIncludedIn++;
      } else {
        context.getQuantities().dimensions.phemaCollectionCounts.properIncludedIn++;
      }
    } catch (Exception e) {
      warn(e.getMessage());
    }

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitProperIncludedIn(elm, context);
  }

  @Override
  public Void visitBefore(Before elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmIntervalCounts.before++;

    // PhEMA Counts
    try {
      if (isTemporal(elm)) {
        context.getQuantities().dimensions.phemaTemporalCounts.before++;
      } else {
        context.getQuantities().dimensions.phemaCollectionCounts.before++;
      }
    } catch (Exception e) {
      warn(e.getMessage());
    }

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitBefore(elm, context);
  }

  @Override
  public Void visitAfter(After elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmIntervalCounts.after++;

    // PhEMA Counts
    try {
      if (isTemporal(elm)) {
        context.getQuantities().dimensions.phemaTemporalCounts.after++;
      } else {
        context.getQuantities().dimensions.phemaCollectionCounts.after++;
      }
    } catch (Exception e) {
      warn(e.getMessage());
    }

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitAfter(elm, context);
  }

  @Override
  public Void visitMeets(Meets elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmIntervalCounts.meets++;

    // PhEMA Counts
    try {
      if (isTemporal(elm)) {
        context.getQuantities().dimensions.phemaTemporalCounts.meets++;
      } else {
        context.getQuantities().dimensions.phemaCollectionCounts.meets++;
      }
    } catch (Exception e) {
      warn(e.getMessage());
    }

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitMeets(elm, context);
  }

  @Override
  public Void visitMeetsBefore(MeetsBefore elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmIntervalCounts.meetsBefore++;

    // PhEMA Counts
    try {
      if (isTemporal(elm)) {
        context.getQuantities().dimensions.phemaTemporalCounts.meetsBefore++;
      } else {
        context.getQuantities().dimensions.phemaCollectionCounts.meetsBefore++;
      }
    } catch (Exception e) {
      warn(e.getMessage());
    }

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitMeetsBefore(elm, context);
  }

  @Override
  public Void visitMeetsAfter(MeetsAfter elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmIntervalCounts.meetsAfter++;

    // PhEMA Counts
    try {
      if (isTemporal(elm)) {
        context.getQuantities().dimensions.phemaTemporalCounts.meetsAfter++;
      } else {
        context.getQuantities().dimensions.phemaCollectionCounts.meetsAfter++;
      }
    } catch (Exception e) {
      warn(e.getMessage());
    }

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitMeetsAfter(elm, context);
  }

  @Override
  public Void visitOverlaps(Overlaps elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmIntervalCounts.overlaps++;

    // PhEMA Counts
    try {
      if (isTemporal(elm)) {
        context.getQuantities().dimensions.phemaTemporalCounts.overlaps++;
      } else {
        context.getQuantities().dimensions.phemaCollectionCounts.overlaps++;
      }
    } catch (Exception e) {
      warn(e.getMessage());
    }

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitOverlaps(elm, context);
  }

  @Override
  public Void visitOverlapsBefore(OverlapsBefore elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmIntervalCounts.overlapsBefore++;

    // PhEMA Counts
    try {
      if (isTemporal(elm)) {
        context.getQuantities().dimensions.phemaTemporalCounts.overlapsBefore++;
      } else {
        context.getQuantities().dimensions.phemaCollectionCounts.overlapsBefore++;
      }
    } catch (Exception e) {
      warn(e.getMessage());
    }

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitOverlapsBefore(elm, context);
  }

  @Override
  public Void visitOverlapsAfter(OverlapsAfter elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmIntervalCounts.overlapsAfter++;

    // PhEMA Counts
    try {
      if (isTemporal(elm)) {
        context.getQuantities().dimensions.phemaTemporalCounts.overlapsAfter++;
      } else {
        context.getQuantities().dimensions.phemaCollectionCounts.overlapsAfter++;
      }
    } catch (Exception e) {
      warn(e.getMessage());
    }

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitOverlapsAfter(elm, context);
  }

  @Override
  public Void visitStarts(Starts elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmIntervalCounts.starts++;

    // PhEMA Counts
    try {
      if (isTemporal(elm)) {
        context.getQuantities().dimensions.phemaTemporalCounts.starts++;
      } else {
        context.getQuantities().dimensions.phemaCollectionCounts.starts++;
      }
    } catch (Exception e) {
      warn(e.getMessage());
    }

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitStarts(elm, context);
  }

  @Override
  public Void visitEnds(Ends elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmIntervalCounts.ends++;

    // PhEMA Counts
    try {
      if (isTemporal(elm)) {
        context.getQuantities().dimensions.phemaTemporalCounts.ends++;
      } else {
        context.getQuantities().dimensions.phemaCollectionCounts.ends++;
      }
    } catch (Exception e) {
      warn(e.getMessage());
    }

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitEnds(elm, context);
  }

  @Override
  public Void visitCollapse(Collapse elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmIntervalCounts.collapse++;

    // PhEMA Counts
    try {
      if (isTemporal(elm)) {
        context.getQuantities().dimensions.phemaTemporalCounts.collapse++;
      } else {
        context.getQuantities().dimensions.phemaCollectionCounts.collapse++;
      }
    } catch (Exception e) {
      warn(e.getMessage());
    }

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitCollapse(elm, context);
  }

  @Override
  public Void visitUnion(Union elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmIntervalCounts.union++;

    // PhEMA Counts
    try {
      if (isTemporal(elm)) {
        context.getQuantities().dimensions.phemaTemporalCounts.union++;
      } else {
        context.getQuantities().dimensions.phemaCollectionCounts.union++;
      }
    } catch (Exception e) {
      warn(e.getMessage());
    }

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitUnion(elm, context);
  }

  @Override
  public Void visitIntersect(Intersect elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmIntervalCounts.intersect++;

    // PhEMA Counts
    try {
      if (isTemporal(elm)) {
        context.getQuantities().dimensions.phemaTemporalCounts.intersect++;
      } else {
        context.getQuantities().dimensions.phemaCollectionCounts.intersect++;
      }
    } catch (Exception e) {
      warn(e.getMessage());
    }

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitIntersect(elm, context);
  }

  @Override
  public Void visitExcept(Except elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmIntervalCounts.except++;

    // PhEMA Counts
    try {
      if (isTemporal(elm)) {
        context.getQuantities().dimensions.phemaTemporalCounts.except++;
      } else {
        context.getQuantities().dimensions.phemaCollectionCounts.except++;
      }
    } catch (Exception e) {
      warn(e.getMessage());
    }

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitExcept(elm, context);
  }

  @Override
  public Void visitSize(Size elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmIntervalCounts.size++;

    // PhEMA Counts
    try {
      if (isTemporal(elm)) {
        context.getQuantities().dimensions.phemaTemporalCounts.size++;
      } else {
        context.getQuantities().dimensions.phemaCollectionCounts.size++;
      }
    } catch (Exception e) {
      warn(e.getMessage());
    }

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitSize(elm, context);
  }

  @Override
  public Void visitPointFrom(PointFrom elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmIntervalCounts.pointFrom++;

    // PhEMA Counts
    try {
      if (isTemporal(elm)) {
        context.getQuantities().dimensions.phemaTemporalCounts.pointFrom++;
      } else {
        context.getQuantities().dimensions.phemaCollectionCounts.pointFrom++;
      }
    } catch (Exception e) {
      warn(e.getMessage());
    }

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitPointFrom(elm, context);
  }

  @Override
  public Void visitExpand(Expand elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmIntervalCounts.expand++;

    // PhEMA Counts
    try {
      if (isTemporal(elm)) {
        context.getQuantities().dimensions.phemaTemporalCounts.expand++;
      } else {
        context.getQuantities().dimensions.phemaCollectionCounts.expand++;
      }
    } catch (Exception e) {
      warn(e.getMessage());
    }

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitExpand(elm, context);
  }

  //</editor-fold>

  //<editor-fold desc="20. LIST OPERATORS">
  /////////////////////////////////////////////////////////////////////////////
  //
  // 20. LIST OPERATORS
  //
  /////////////////////////////////////////////////////////////////////////////

  @Override
  public Void visitList(List elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmListCounts.list++;
    return super.visitList(elm, context);
  }

  @Override
  public Void visitExists(Exists elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmListCounts.exists++;

    // PhEMA Counts
    context.getQuantities().dimensions.phemaCollectionCounts.exists++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitExists(elm, context);
  }

  @Override
  public Void visitTimes(Times elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmListCounts.times++;

    // PhEMA Counts
    context.getQuantities().dimensions.phemaCollectionCounts.times++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitTimes(elm, context);
  }

  @Override
  public Void visitFilter(Filter elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmListCounts.filter++;

    // PhEMA Counts
    context.getQuantities().dimensions.phemaCollectionCounts.filter++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitFilter(elm, context);
  }

  @Override
  public Void visitFirst(First elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmListCounts.first++;

    // PhEMA Counts
    context.getQuantities().dimensions.phemaCollectionCounts.first++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitFirst(elm, context);
  }

  @Override
  public Void visitLast(Last elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmListCounts.last++;

    // PhEMA Counts
    context.getQuantities().dimensions.phemaCollectionCounts.last++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitLast(elm, context);
  }

  @Override
  public Void visitIndexOf(IndexOf elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmListCounts.indexOf++;

    // PhEMA Counts
    context.getQuantities().dimensions.phemaCollectionCounts.indexOf++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitIndexOf(elm, context);
  }

  @Override
  public Void visitFlatten(Flatten elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmListCounts.flatten++;

    // PhEMA Counts
    context.getQuantities().dimensions.phemaCollectionCounts.flatten++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitFlatten(elm, context);
  }

  @Override
  public Void visitSort(Sort elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmListCounts.sort++;

    // PhEMA Counts
    context.getQuantities().dimensions.phemaCollectionCounts.sort++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitSort(elm, context);
  }

  @Override
  public Void visitForEach(ForEach elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmListCounts.forEach++;

    // PhEMA Counts
    context.getQuantities().dimensions.phemaCollectionCounts.forEach++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitForEach(elm, context);
  }

  @Override
  public Void visitDistinct(Distinct elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmListCounts.distinct++;

    // PhEMA Counts
    context.getQuantities().dimensions.phemaCollectionCounts.distinct++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitDistinct(elm, context);
  }

  @Override
  public Void visitCurrent(Current elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmListCounts.current++;

    // PhEMA Counts
    context.getQuantities().dimensions.phemaCollectionCounts.current++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitCurrent(elm, context);
  }

  @Override
  public Void visitSingletonFrom(SingletonFrom elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmListCounts.singletonFrom++;

    // PhEMA Counts
    context.getQuantities().dimensions.phemaCollectionCounts.singletonFrom++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitSingletonFrom(elm, context);
  }

  // not sure where the following belong

  @Override
  public Void visitSlice(Slice elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmListCounts.slice++;

    // PhEMA Counts
    context.getQuantities().dimensions.phemaCollectionCounts.slice++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitSlice(elm, context);
  }

  @Override
  public Void visitRepeat(Repeat elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmListCounts.repeat++;

    // PhEMA Counts
    context.getQuantities().dimensions.phemaCollectionCounts.repeat++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitRepeat(elm, context);
  }

  @Override
  public Void visitIteration(Iteration elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmListCounts.iteration++;

    // PhEMA Counts
    context.getQuantities().dimensions.phemaCollectionCounts.iteration++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitIteration(elm, context);
  }
  //</editor-fold>

  //<editor-fold desc="21. AGGREGATE OPERATORS">
  /////////////////////////////////////////////////////////////////////////////
  //
  // 21. AGGREGATE OPERATORS
  //
  /////////////////////////////////////////////////////////////////////////////
  @Override
  public Void visitAggregate(Aggregate elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmAggregateCounts.aggregate++;
    return super.visitAggregate(elm, context);
  }

  @Override
  public Void visitProduct(Product elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmAggregateCounts.product++;

    // PhEMA Counts
    context.getQuantities().dimensions.phemaAggregateCounts.product++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitProduct(elm, context);
  }

  @Override
  public Void visitGeometricMean(GeometricMean elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmAggregateCounts.geometricMean++;

    // PhEMA Counts
    context.getQuantities().dimensions.phemaAggregateCounts.geometricMean++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitGeometricMean(elm, context);
  }

  @Override
  public Void visitCount(Count elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmAggregateCounts.count++;

    // PhEMA Counts
    context.getQuantities().dimensions.phemaAggregateCounts.count++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitCount(elm, context);
  }

  @Override
  public Void visitSum(Sum elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmAggregateCounts.sum++;

    // PhEMA Counts
    context.getQuantities().dimensions.phemaAggregateCounts.sum++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitSum(elm, context);
  }

  @Override
  public Void visitMin(Min elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmAggregateCounts.min++;

    // PhEMA Counts
    context.getQuantities().dimensions.phemaAggregateCounts.min++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitMin(elm, context);
  }

  @Override
  public Void visitMax(Max elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmAggregateCounts.max++;

    // PhEMA Counts
    context.getQuantities().dimensions.phemaAggregateCounts.max++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitMax(elm, context);
  }

  @Override
  public Void visitAvg(Avg elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmAggregateCounts.avg++;

    // PhEMA Counts
    context.getQuantities().dimensions.phemaAggregateCounts.avg++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitAvg(elm, context);
  }

  @Override
  public Void visitMedian(Median elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmAggregateCounts.median++;

    // PhEMA Counts
    context.getQuantities().dimensions.phemaAggregateCounts.median++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitMedian(elm, context);
  }

  @Override
  public Void visitMode(Mode elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmAggregateCounts.mode++;

    // PhEMA Counts
    context.getQuantities().dimensions.phemaAggregateCounts.mode++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitMode(elm, context);
  }

  @Override
  public Void visitVariance(Variance elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmAggregateCounts.variance++;

    // PhEMA Counts
    context.getQuantities().dimensions.phemaAggregateCounts.variance++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitVariance(elm, context);
  }

  @Override
  public Void visitPopulationVariance(PopulationVariance elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmAggregateCounts.populationVariance++;

    // PhEMA Counts
    context.getQuantities().dimensions.phemaAggregateCounts.populationVariance++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitPopulationVariance(elm, context);
  }

  @Override
  public Void visitStdDev(StdDev elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmAggregateCounts.stdDev++;

    // PhEMA Counts
    context.getQuantities().dimensions.phemaAggregateCounts.stdDev++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitStdDev(elm, context);
  }

  @Override
  public Void visitPopulationStdDev(PopulationStdDev elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmAggregateCounts.populationStdDev++;

    // PhEMA Counts
    context.getQuantities().dimensions.phemaAggregateCounts.populationStdDev++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitPopulationStdDev(elm, context);
  }

  @Override
  public Void visitAllTrue(AllTrue elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmAggregateCounts.allTrue++;

    // PhEMA Counts
    context.getQuantities().dimensions.phemaAggregateCounts.allTrue++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitAllTrue(elm, context);
  }

  @Override
  public Void visitAnyTrue(AnyTrue elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmAggregateCounts.anyTrue++;

    // PhEMA Counts
    context.getQuantities().dimensions.phemaAggregateCounts.anyTrue++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitAnyTrue(elm, context);
  }
  //</editor-fold>

  //<editor-fold desc="22. TYPE OPERATORS">
  /////////////////////////////////////////////////////////////////////////////
  //
  // 22. TYPE OPERATORS (Ignoring all)
  //
  /////////////////////////////////////////////////////////////////////////////

  @Override
  public Void visitIs(Is elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTypeCounts.is++;
    return super.visitIs(elm, context);
  }

  @Override
  public Void visitAs(As elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTypeCounts.as++;
    return super.visitAs(elm, context);
  }

  @Override
  public Void visitConvert(Convert elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTypeCounts.convert++;
    return super.visitConvert(elm, context);
  }

  @Override
  public Void visitToBoolean(ToBoolean elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTypeCounts.toBoolean++;
    return super.visitToBoolean(elm, context);
  }

  @Override
  public Void visitToConcept(ToConcept elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTypeCounts.toConcept++;
    return super.visitToConcept(elm, context);
  }

  @Override
  public Void visitToDateTime(ToDateTime elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTypeCounts.toDateTime++;
    return super.visitToDateTime(elm, context);
  }

  @Override
  public Void visitToDecimal(ToDecimal elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTypeCounts.toDecimal++;
    return super.visitToDecimal(elm, context);
  }

  @Override
  public Void visitToInteger(ToInteger elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTypeCounts.toInteger++;
    return super.visitToInteger(elm, context);
  }

  @Override
  public Void visitToQuantity(ToQuantity elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTypeCounts.toQuantity++;
    return super.visitToQuantity(elm, context);
  }

  @Override
  public Void visitToString(ToString elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTypeCounts.toString++;
    return super.visitToString(elm, context);
  }

  @Override
  public Void visitToTime(ToTime elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTypeCounts.toTime++;
    return super.visitToTime(elm, context);
  }

  @Override
  public Void visitCanConvert(CanConvert elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTypeCounts.canConvert++;
    return super.visitCanConvert(elm, context);
  }

  @Override
  public Void visitConvertsToBoolean(ConvertsToBoolean elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTypeCounts.convertsToBoolean++;
    return super.visitConvertsToBoolean(elm, context);
  }

  @Override
  public Void visitToChars(ToChars elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTypeCounts.toChars++;
    return super.visitToChars(elm, context);
  }

  @Override
  public Void visitConvertsToDate(ConvertsToDate elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTypeCounts.convertsToDate++;
    return super.visitConvertsToDate(elm, context);
  }

  @Override
  public Void visitToDate(ToDate elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTypeCounts.toDate++;
    return super.visitToDate(elm, context);
  }

  @Override
  public Void visitConvertsToDateTime(ConvertsToDateTime elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTypeCounts.convertsToDateTime++;
    return super.visitConvertsToDateTime(elm, context);
  }

  @Override
  public Void visitConvertsToLong(ConvertsToLong elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTypeCounts.convertsToLong++;
    return super.visitConvertsToLong(elm, context);
  }

  @Override
  public Void visitToLong(ToLong elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTypeCounts.toLong++;
    return super.visitToLong(elm, context);
  }

  @Override
  public Void visitConvertsToDecimal(ConvertsToDecimal elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTypeCounts.convertsToDecimal++;
    return super.visitConvertsToDecimal(elm, context);
  }

  @Override
  public Void visitConvertsToInteger(ConvertsToInteger elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTypeCounts.convertsToInteger++;
    return super.visitConvertsToInteger(elm, context);
  }

  @Override
  public Void visitToList(ToList elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTypeCounts.toList++;
    return super.visitToList(elm, context);
  }

  @Override
  public Void visitConvertQuantity(ConvertQuantity elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTypeCounts.convertQuantity++;
    return super.visitConvertQuantity(elm, context);
  }

  @Override
  public Void visitCanConvertQuantity(CanConvertQuantity elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTypeCounts.canConvertQuantity++;
    return super.visitCanConvertQuantity(elm, context);
  }

  @Override
  public Void visitConvertsToQuantity(ConvertsToQuantity elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTypeCounts.convertsToQuantity++;
    return super.visitConvertsToQuantity(elm, context);
  }

  @Override
  public Void visitConvertsToRatio(ConvertsToRatio elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTypeCounts.convertsToRatio++;
    return super.visitConvertsToRatio(elm, context);
  }

  @Override
  public Void visitToRatio(ToRatio elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTypeCounts.toRatio++;
    return super.visitToRatio(elm, context);
  }

  @Override
  public Void visitConvertsToString(ConvertsToString elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTypeCounts.convertsToString++;
    return super.visitConvertsToString(elm, context);
  }

  @Override
  public Void visitConvertsToTime(ConvertsToTime elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTypeCounts.convertsToTime++;
    return super.visitConvertsToTime(elm, context);
  }

  @Override
  public Void visitChildren(Children elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTypeCounts.children++;
    return super.visitChildren(elm, context);
  }

  @Override
  public Void visitDescendents(Descendents elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTypeCounts.descendents++;
    return super.visitDescendents(elm, context);
  }
  //</editor-fold>

  //<editor-fold desc="23. CLINICAL OPERATORS">
  /////////////////////////////////////////////////////////////////////////////
  //
  // 23. CLINICAL OPERATORS
  //
  /////////////////////////////////////////////////////////////////////////////

  @Override
  public Void visitInCodeSystem(InCodeSystem elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTerminologyCounts.inCodeSystem++;

    // PhEMA Counts
    context.getQuantities().dimensions.phemaTerminologyCounts.inCodeSystem++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitInCodeSystem(elm, context);
  }

  @Override
  public Void visitInValueSet(InValueSet elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTerminologyCounts.inValueSet++;

    // PhEMA Counts
    context.getQuantities().dimensions.phemaTerminologyCounts.inValueSet++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitInValueSet(elm, context);
  }

  @Override
  public Void visitCalculateAge(CalculateAge elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTerminologyCounts.calculateAge++;

    // PhEMA Counts
    context.getQuantities().dimensions.phemaTemporalCounts.calculateAge++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitCalculateAge(elm, context);
  }

  @Override
  public Void visitCalculateAgeAt(CalculateAgeAt elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTerminologyCounts.calculateAgeAt++;

    // PhEMA Counts
    context.getQuantities().dimensions.phemaTemporalCounts.calculateAgeAt++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitCalculateAgeAt(elm, context);
  }

  @Override
  public Void visitAnyInCodeSystem(AnyInCodeSystem elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTerminologyCounts.anyInCodeSystem++;

    // PhEMA Counts
    context.getQuantities().dimensions.phemaTerminologyCounts.anyInCodeSystem++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitAnyInCodeSystem(elm, context);
  }

  @Override
  public Void visitAnyInValueSet(AnyInValueSet elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTerminologyCounts.anyInValueSet++;

    // PhEMA Counts
    context.getQuantities().dimensions.phemaTerminologyCounts.anyInValueSet++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitAnyInValueSet(elm, context);
  }

  @Override
  public Void visitSubsumes(Subsumes elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTerminologyCounts.subsumes++;

    // PhEMA Counts
    context.getQuantities().dimensions.phemaTerminologyCounts.subsumes++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitSubsumes(elm, context);
  }

  @Override
  public Void visitSubsumedBy(SubsumedBy elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmTerminologyCounts.subsumedBy++;

    // PhEMA Counts
    context.getQuantities().dimensions.phemaTerminologyCounts.subsumedBy++;

    // Bump total expression count
    context.getQuantities().dimensions.phemaModularityCounts.expression++;

    return super.visitSubsumedBy(elm, context);
  }
  //</editor-fold>

  //<editor-fold desc="24. ERRORS AND MESSAGES">
  /////////////////////////////////////////////////////////////////////////////
  //
  // 24. ERRORS AND MESSAGES
  //
  /////////////////////////////////////////////////////////////////////////////

  @Override
  public Void visitMessage(Message elm, ElmQuantifyContext context) {
    // ELM Counts
    context.getQuantities().elmErrorCounts.message++;
    return super.visitMessage(elm, context);
  }
  //</editor-fold>

  //// Methods for detecting the expressions we care about
  public boolean isPhemaLogicalExpression(Expression elm) {
    return (elm instanceof And) ||
      (elm instanceof Or) ||
      (elm instanceof Not) ||
      (elm instanceof Implies) ||
      (elm instanceof Xor);
  }

  public boolean isPhemaComparisonExpression(Expression elm) {
    return (elm instanceof Equal) ||
      (elm instanceof Equivalent) ||
      (elm instanceof Greater) ||
      (elm instanceof GreaterOrEqual) ||
      (elm instanceof Less) ||
      (elm instanceof LessOrEqual) ||
      (elm instanceof NotEqual);
  }

  public boolean isPhemaArithmeticExpression(Expression elm) {
    return (elm instanceof Add) ||
      (elm instanceof Subtract) ||
      (elm instanceof Multiply) ||
      (elm instanceof Divide) ||
      (elm instanceof TruncatedDivide) ||
      (elm instanceof Modulo) ||
      (elm instanceof Ceiling) ||
      (elm instanceof Floor) ||
      (elm instanceof Truncate) ||
      (elm instanceof Abs) ||
      (elm instanceof Negate) ||
      (elm instanceof Round) ||
      (elm instanceof Ln) ||
      (elm instanceof Exp) ||
      (elm instanceof Log) ||
      (elm instanceof Power) ||
      (elm instanceof Successor) ||
      (elm instanceof Predecessor) ||
      (elm instanceof MinValue) ||
      (elm instanceof MaxValue) ||
      (elm instanceof Precision) ||
      (elm instanceof LowBoundary) ||
      (elm instanceof HighBoundary) ||
      (elm instanceof Total);
  }

  public boolean isPhemaAggregateExpression(Expression elm) {
    return (elm instanceof Count) ||
      (elm instanceof Sum) ||
      (elm instanceof Product) ||
      (elm instanceof Min) ||
      (elm instanceof Max) ||
      (elm instanceof Avg) ||
      (elm instanceof GeometricMean) ||
      (elm instanceof Median) ||
      (elm instanceof Mode) ||
      (elm instanceof Variance) ||
      (elm instanceof StdDev) ||
      (elm instanceof PopulationVariance) ||
      (elm instanceof PopulationStdDev) ||
      (elm instanceof AllTrue) ||
      (elm instanceof AnyTrue);
  }

  // Data expression depth handled manually in visitQuery method

  public boolean isPhemaConditionalExpression(Expression elm) {
    return (elm instanceof If) || (elm instanceof Case);
  }

  public boolean isPhemaTemporalExpression(Expression elm) {
    return (elm instanceof CalculateAge) ||
      (elm instanceof CalculateAgeAt) ||
      (elm instanceof DurationBetween) ||
      (elm instanceof DifferenceBetween) ||
      (elm instanceof DateFrom) ||
      (elm instanceof TimeFrom) ||
      (elm instanceof TimezoneOffsetFrom) ||
      (elm instanceof DateTimeComponentFrom) ||
      (elm instanceof TimeOfDay) ||
      (elm instanceof Today) ||
      (elm instanceof Now) ||
      (elm instanceof SameAs) ||
      (elm instanceof SameOrBefore) ||
      (elm instanceof SameOrAfter) ||
      (elm instanceof TimezoneFrom);
  }

  // Modularity/statement depth handled directly in visitExpression

  public boolean isPhemaTerminologyExpression(Expression elm) {
    return (elm instanceof InCodeSystem) ||
      (elm instanceof InValueSet) ||
      (elm instanceof AnyInCodeSystem) ||
      (elm instanceof AnyInValueSet) ||
      (elm instanceof Subsumes) ||
      (elm instanceof SubsumedBy);
  }

  public boolean isPhemaCollectionExpression(Expression elm) {
    return (elm instanceof Exists) ||
      (elm instanceof Times) ||
      (elm instanceof Filter) ||
      (elm instanceof First) ||
      (elm instanceof Last) ||
      (elm instanceof IndexOf) ||
      (elm instanceof Flatten) ||
      (elm instanceof Sort) ||
      (elm instanceof ForEach) ||
      (elm instanceof Distinct) ||
      (elm instanceof Current) ||
      (elm instanceof SingletonFrom) ||
      (elm instanceof Slice) ||
      (elm instanceof Repeat) ||
      (elm instanceof Iteration);
  }
} 